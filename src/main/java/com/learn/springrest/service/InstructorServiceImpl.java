package com.learn.springrest.service;

import com.learn.springrest.dto.InstructorDTO;
import com.learn.springrest.exception.EmailAlreadyExistsException;
import com.learn.springrest.exception.ResourceNotFoundException;
import com.learn.springrest.mapper.InstructorMapper;
import com.learn.springrest.model.Instructor;
import com.learn.springrest.repository.InstructorRepository;
import com.learn.springrest.utils.PagedResponse;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Service
public class InstructorServiceImpl implements InstructorService {
    // This service would typically interact with a repository to fetch instructor data.

    private final InstructorRepository instructorRepository;

    // Constructor injection for the repository
    public InstructorServiceImpl(InstructorRepository instructorRepository) {
        this.instructorRepository = instructorRepository;
    }


    // Get all instructors
    @Override
    public PagedResponse<InstructorDTO> getAllInstructors(String keyword, int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());

        Page<Instructor> instructorPage;
        if (keyword != null && !keyword.isEmpty()) {
            // If a keyword is provided, search for instructors by first name, last name, or email
            instructorPage = instructorRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCase(
                    keyword, keyword, keyword, pageable);
        } else {
            // If no keyword is provided, fetch all instructors
            instructorPage = instructorRepository.findAll(pageable);
        }

        List<InstructorDTO> instructorDTOs = instructorPage.getContent().stream()
                .map(InstructorMapper::toInstructorDTO)
                .toList();

        return new PagedResponse<>(
                instructorDTOs,
                instructorPage.getNumber(),
                instructorPage.getSize(),
                instructorPage.getTotalElements(),
                instructorPage.getTotalPages(),
                instructorPage.isLast()
        );
    }

    // Get instructor by ID
    @Override
    public Instructor getInstructorById(Integer id) {
        return instructorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Instructor not found with id: " + id));
    }

    // Save a new instructor
    @Override
    @Transactional
    public InstructorDTO createInstructor(InstructorDTO instructorDTO) {
        // check Email is unique
        if (instructorRepository.existsByEmail(instructorDTO.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists: " + instructorDTO.getEmail());
        }
        Instructor instructorModel = InstructorMapper.toInstructorModel(instructorDTO);
        Instructor saved = instructorRepository.save(instructorModel); // Save the instructor to the database and instructorDetail if it exists. If not it will be null
        return InstructorMapper.toInstructorDTO(saved);
    }

    // Update an existing instructor
    @Override
    @Transactional
    public InstructorDTO updateInstructor(Integer id, InstructorDTO instructorDTO) {
        Instructor existingInstructor = getInstructorById(id);
        existingInstructor.setFirstName(instructorDTO.getFirstName());
        existingInstructor.setLastName(instructorDTO.getLastName());
        existingInstructor.setEmail(instructorDTO.getEmail());

        if (instructorDTO.getInstructorDetail() != null) {
            existingInstructor.setInstructorDetail(InstructorMapper.toInstructorDetailModel(instructorDTO.getInstructorDetail()));
        } else {
            existingInstructor.setInstructorDetail(null); // Set to null if no detail is provided
        }

        return InstructorMapper.toInstructorDTO(instructorRepository.save(existingInstructor));
    }

    // Delete an instructor by ID
    @Override
    @Transactional
    public void deleteInstructor(Integer id) {
        Instructor existingInstructor = getInstructorById(id);
        instructorRepository.delete(existingInstructor);
    }

}
