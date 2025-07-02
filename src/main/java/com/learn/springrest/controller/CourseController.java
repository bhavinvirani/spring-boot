package com.learn.springrest.controller;

import com.learn.springrest.dto.CourseDTO;
import com.learn.springrest.dto.course.CourseDetailResponseDTO;
import com.learn.springrest.service.CourseService;
import com.learn.springrest.utils.ApiResponse;
import com.learn.springrest.utils.PagedResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/course")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    // Get all courses
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<PagedResponse<CourseDTO>>> getAllCourses(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        PagedResponse<CourseDTO> response = courseService.getAllCourses(keyword, page, size, sortBy);
        return ResponseEntity.ok(
                ApiResponse.success(response)
        );
    }

    // Get course by id
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CourseDetailResponseDTO>> getCourseById(@PathVariable Integer id) {
        CourseDetailResponseDTO courseDetailResponseDTO = courseService.getCourseById(id);
        return ResponseEntity.ok(
                ApiResponse.success(courseDetailResponseDTO)
        );
    }

    // Create course
    @PostMapping
    public ResponseEntity<ApiResponse<CourseDTO>> createCourse(@Valid @RequestBody CourseDTO courseDTO) {
        CourseDTO createdCourseDTO = courseService.createCourse(courseDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(createdCourseDTO));
    }

    // Update course
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CourseDTO>> updateCourse(@PathVariable Integer id, @RequestBody CourseDTO courseDTO) {
        CourseDTO updatedCourseDTO = courseService.updateCourse(id, courseDTO);
        return ResponseEntity.ok(
                ApiResponse.success(updatedCourseDTO)
        );
    }

    // Delete course
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCourse(@PathVariable Integer id) {
        courseService.deleteCourse(id);
        return ResponseEntity.ok(
                ApiResponse.success(null)
        );
    }

    // Add student to course
    @PostMapping("/{courseId}/student/{studentId}")
    public ResponseEntity<ApiResponse<Void>> addStudentToCourse(
            @PathVariable Integer courseId,
            @PathVariable Integer studentId
    ) {
        courseService.addStudentToCourse(courseId, studentId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(null));
    }

    // Get courses by instructor id
    @GetMapping("/instructor/{instructorId}")
    public ResponseEntity<ApiResponse<PagedResponse<CourseDTO>>> getCoursesByInstructorId(
            @PathVariable Integer instructorId,
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        PagedResponse<CourseDTO> response = courseService.getCoursesByInstructorId(instructorId, keyword, page, size, sortBy);
        return ResponseEntity.ok(
                ApiResponse.success(response)
        );
    }
}
