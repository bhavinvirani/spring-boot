package com.learn.springrest.mapper;

import com.learn.springrest.dto.CourseDTO;
import com.learn.springrest.dto.StudentDTO;
import com.learn.springrest.dto.course.CourseDetailResponseDTO;
import com.learn.springrest.model.Course;
import com.learn.springrest.model.Instructor;

import java.util.Set;
import java.util.stream.Collectors;

public class CourseMapper {

    private CourseMapper() {
        // Private constructor to prevent instantiation
    }

    public static Course toCourseModel(CourseDTO dto, Instructor instructor) {
        return Course.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .description(dto.getDescription())
                .instructor(instructor)
                .build();
    }

    public static CourseDTO toCourseDto(Course course) {
        return CourseDTO.builder()
                .id(course.getId())
                .title(course.getTitle())
                .description(course.getDescription())
                .instructorId(course.getInstructor().getId())

                .build();
    }

    public static CourseDetailResponseDTO toCourseDetailResponseDto(Course course) {
        Set<StudentDTO> studentDTOs = course.getStudents().stream()
                .map(student -> new StudentDTO(
                        student.getId(),
                        student.getFirstName(),
                        student.getLastName(),
                        student.getEmail(),
                        student.getCourses().stream()
                                .map(CourseMapper::toCourseDto)
                                .collect(Collectors.toSet()
                                ))
                ).collect(Collectors.toSet());

        return CourseDetailResponseDTO.builder()
                .id(course.getId())
                .title(course.getTitle())
                .description(course.getDescription())
                .instructor(InstructorMapper.toInstructorDTO(course.getInstructor()))
                .students(studentDTOs)
                .build();
    }


}
