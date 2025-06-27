package com.learn.springrest.service;

import com.learn.springrest.dto.StudentDTO;
import com.learn.springrest.exception.ResourceNotFoundException;
import com.learn.springrest.mapper.StudentMapper;
import com.learn.springrest.model.Student;
import com.learn.springrest.repository.StudentRepository;
import com.learn.springrest.utils.PagedResponse;
import jakarta.transaction.Transactional;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional // Apply to all public methods in this class
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public PagedResponse<StudentDTO> getAllStudents(String keyword, int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());

        Page<Student> studenPage;
        if (keyword != null && !keyword.isEmpty()) {
            // If a keyword is provided, search for students by first name, last name, or email
            studenPage = studentRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCase(
                    keyword, keyword, keyword, pageable);
        } else {
            // If no keyword is provided, fetch all students
            studenPage = studentRepository.findAll(pageable);
        }

        List<StudentDTO> studentDTOS = studenPage.getContent().stream()
                .map(StudentMapper::toStudentDTO)
                .toList();

        return new PagedResponse<>(
                studentDTOS,
                studenPage.getNumber(),
                studenPage.getSize(),
                studenPage.getTotalElements(),
                studenPage.getTotalPages(),
                studenPage.isLast()
        );
    }

    @Override
    @Cacheable(value = "students", key = "#id")
    public StudentDTO getStudentById(Integer id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));
        return StudentMapper.toStudentDTO(student);
    }

    @Override
    public StudentDTO createStudent(StudentDTO studentDTO) {
        Student savedStudent = studentRepository.save(StudentMapper.toStudentModel(studentDTO));
        return StudentMapper.toStudentDTO(savedStudent);
    }

    @Override
    public StudentDTO updateStudent(int id, StudentDTO studentDTO) {
        Student existingStudent = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));

        // Update the existing student's fields
        existingStudent.setFirstName(studentDTO.getFirstName());
        existingStudent.setLastName(studentDTO.getLastName());
        existingStudent.setEmail(studentDTO.getEmail());

        // Save the updated student
        Student updatedStudent = studentRepository.save(existingStudent);
        return StudentMapper.toStudentDTO(updatedStudent);
    }

    @Override
    public void deleteStudent(int id) {
        if (!studentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Student not found with id: " + id);
        }
        studentRepository.deleteById(id);

    }
}
