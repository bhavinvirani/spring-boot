package com.learn.springrest.mapper;

import com.learn.springrest.dto.StudentDTO;
import com.learn.springrest.model.Student;

public class StudentMapper {
    private StudentMapper() {
        // Private constructor to prevent instantiation
    }

    public static StudentDTO toStudentDTO(Student student) {
        if (student == null) return null;

        return StudentDTO.builder()
                .id(student.getId())
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .email(student.getEmail())
                .build();
    }

    public static Student toStudentModel(StudentDTO studentDTO) {
        if (studentDTO == null) return null;

        return Student.builder()
                .firstName(studentDTO.getFirstName())
                .lastName(studentDTO.getLastName())
                .email(studentDTO.getEmail())
                .build();
    }
}
