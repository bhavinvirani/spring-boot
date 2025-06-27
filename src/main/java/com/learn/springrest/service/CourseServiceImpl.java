package com.learn.springrest.service;

import com.learn.springrest.dto.CourseDTO;
import com.learn.springrest.dto.course.CourseDetailResponseDTO;
import com.learn.springrest.exception.ResourceNotFoundException;
import com.learn.springrest.mapper.CourseMapper;
import com.learn.springrest.model.Course;
import com.learn.springrest.model.Instructor;
import com.learn.springrest.model.Review;
import com.learn.springrest.model.Student;
import com.learn.springrest.repository.CourseRepository;
import com.learn.springrest.repository.InstructorRepository;
import com.learn.springrest.repository.StudentRepository;
import com.learn.springrest.utils.PagedResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;


@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final InstructorRepository instructorRepository;
    private final StudentRepository studentRepository;


    public CourseServiceImpl(CourseRepository courseRepository, InstructorRepository instructorRepository, StudentRepository studentRepository) {
        this.courseRepository = courseRepository;
        this.instructorRepository = instructorRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public PagedResponse<CourseDTO> getAllCourses(String keyword, int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        Page<Course> coursePage;
        if (keyword != null && !keyword.isEmpty()) {
            // If a keyword is provided, search for courses by title
            coursePage = courseRepository.findByTitleContainingIgnoreCase(keyword, pageable);
        } else {
            // If no keyword is provided, fetch all courses
            coursePage = courseRepository.findAll(pageable);
        }

        // Convert the Page<Course> to a List<CourseDTO>
        return new PagedResponse<>(
                coursePage.getContent().stream()
                        .map(CourseMapper::toCourseDto)
                        .toList(),
                coursePage.getNumber(),
                coursePage.getSize(),
                coursePage.getTotalElements(),
                coursePage.getTotalPages(),
                coursePage.isLast()
        );
    }

    @Override
    public CourseDetailResponseDTO getCourseById(Integer id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + id));

        // Convert Course to CourseDTO
        return CourseMapper.toCourseDetailResponseDto(course);
    }

    @Override
    public CourseDTO createCourse(CourseDTO courseDTO) {
        Instructor instructor = instructorRepository.findById(courseDTO.getInstructorId())
                .orElseThrow(() -> new ResourceNotFoundException("Instructor not found with ID: " + courseDTO.getInstructorId()));


        Course course = CourseMapper.toCourseModel(courseDTO, instructor);
        Course saved = courseRepository.save(course);

        return CourseMapper.toCourseDto(saved);

    }

    @Override
    public CourseDTO updateCourse(Integer id, CourseDTO courseDTO) {
        Course existingCourse = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + id));

        // Update the existing course with new values
        existingCourse.setTitle(courseDTO.getTitle());
        existingCourse.setDescription(courseDTO.getDescription());

        Course updatedCourse = courseRepository.save(existingCourse);
        return CourseMapper.toCourseDto(updatedCourse);
    }

    @Override
    public void deleteCourse(Integer id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + id));

        // Remove all reviews associated with the course
        List<Review> reviews = course.getReviews();
        if (reviews != null) {
            reviews.clear();
        }

        // Remove all students associated with the course
        Set<Student> students = course.getStudents();
        if (students != null) {
            students.clear();
        }

        // Delete the course
        courseRepository.delete(course);

    }

    @Override
    public void addStudentToCourse(Integer courseId, Integer studentId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with ID: " + courseId));

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with ID: " + studentId));

        // Bidirectional update
        student.getCourses().add(course);
        course.getStudents().add(student);

        studentRepository.save(student);
    }

    @Override
    public PagedResponse<CourseDTO> getCoursesByInstructorId(Integer instructorId, String keyword, int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        Instructor instructor = instructorRepository.findById(instructorId)
                .orElseThrow(() -> new ResourceNotFoundException("Instructor not found with ID: " + instructorId));

        Page<Course> coursePage;
        if (keyword != null && !keyword.isEmpty()) {
            // If a keyword is provided, search for courses by title
            coursePage = courseRepository.findByTitleContainingIgnoreCaseAndInstructor(keyword, instructor, pageable);
        } else {
            // If no keyword is provided, fetch all courses for the instructor
            coursePage = courseRepository.findByInstructor(instructor, pageable);
        }

        // Convert the Page<Course> to a List<CourseDTO>
        return new PagedResponse<>(
                coursePage.getContent().stream()
                        .map(CourseMapper::toCourseDto)
                        .toList(),
                coursePage.getNumber(),
                coursePage.getSize(),
                coursePage.getTotalElements(),
                coursePage.getTotalPages(),
                coursePage.isLast()
        );
    }

}
