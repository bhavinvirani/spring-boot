package com.learn.springrest.service;

import com.learn.springrest.dto.StudentDTO;
import com.learn.springrest.utils.PagedResponse;

public interface StudentService {

    PagedResponse<StudentDTO> getAllStudents(String keyword, int page, int size, String sortBy);

    StudentDTO getStudentById(Integer id);

    StudentDTO createStudent(StudentDTO studentDTO);

    StudentDTO updateStudent(int id, StudentDTO studentDTO);

    void deleteStudent(int id);
}
