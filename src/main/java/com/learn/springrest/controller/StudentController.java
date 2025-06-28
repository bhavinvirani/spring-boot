package com.learn.springrest.controller;

import com.learn.springrest.dto.StudentDTO;
import com.learn.springrest.service.StudentService;
import com.learn.springrest.utils.ApiResponse;
import com.learn.springrest.utils.PagedResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/student")

public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // Get all students
    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<PagedResponse<StudentDTO>>> getAllStudents(@RequestParam(defaultValue = "") String keyword, @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size, @RequestParam(defaultValue = "id") String sortBy) {
        PagedResponse<StudentDTO> response = studentService.getAllStudents(keyword, page, size, sortBy);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    // Get student by id
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentDTO>> getStudentById(@PathVariable Integer id) {
        StudentDTO studentDTO = studentService.getStudentById(id);
        return ResponseEntity.ok(ApiResponse.success(studentDTO));
    }

    // Create student
    @PostMapping
    public ResponseEntity<ApiResponse<StudentDTO>> createStudent(@RequestBody StudentDTO studentDTO) {
        StudentDTO createdStudentDTO = studentService.createStudent(studentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(createdStudentDTO));
    }

    // Update student
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentDTO>> updateStudent(@PathVariable Integer id, @RequestBody StudentDTO studentDTO) {
        StudentDTO updatedStudentDTO = studentService.updateStudent(id, studentDTO);
        return ResponseEntity.ok(ApiResponse.success(updatedStudentDTO));
    }

    // Delete student
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteStudent(@PathVariable Integer id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok(ApiResponse.success(null));
    }


}
