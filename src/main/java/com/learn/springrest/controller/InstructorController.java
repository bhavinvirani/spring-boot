package com.learn.springrest.controller;

import com.learn.springrest.dto.InstructorDTO;
import com.learn.springrest.mapper.InstructorMapper;
import com.learn.springrest.model.Instructor;
import com.learn.springrest.service.InstructorService;
import com.learn.springrest.utils.ApiResponse;
import com.learn.springrest.utils.PagedResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/instructor")
public class InstructorController {

    private final InstructorService instructorService;

    public InstructorController(InstructorService instructorService) {
        this.instructorService = instructorService;
    }

    //     Get all instructors
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<PagedResponse<InstructorDTO>>> getAllInstructors(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        PagedResponse<InstructorDTO> response = instructorService.getAllInstructors(keyword, page, size, sortBy);
        return ResponseEntity.ok(
                ApiResponse.success(response)
        );
    }

    // Get instructor by id
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<InstructorDTO>> getInstructorById(@PathVariable Integer id) {
        Instructor instructor = instructorService.getInstructorById(id);
        InstructorDTO instructorDTO = InstructorMapper.toInstructorDTO(instructor);
        return ResponseEntity.ok(
                ApiResponse.success(instructorDTO)
        );
    }

    // Create instructor
    @PostMapping
    public ResponseEntity<ApiResponse<InstructorDTO>> createInstructor(@Valid @RequestBody InstructorDTO instructorDTO) {
        InstructorDTO createdInstructorDTO = instructorService.createInstructor(instructorDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(createdInstructorDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<InstructorDTO>> updateInstructor(@PathVariable Integer id, @RequestBody InstructorDTO instructorDTO) {
        InstructorDTO updatedInstructorDTO = instructorService.updateInstructor(id, instructorDTO);
        return ResponseEntity.ok(
                ApiResponse.success(updatedInstructorDTO)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteInstructor(@PathVariable Integer id) {
        instructorService.deleteInstructor(id);
        return ResponseEntity.ok(
                ApiResponse.success(null)
        );
    }
}