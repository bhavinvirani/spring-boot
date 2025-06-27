package com.learn.springrest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseDTO {
    private Integer id;

    @NotBlank(message = "Course title is required")
    private String title;

    @NotBlank(message = "Course description is required")
    private String description;

    @NotNull(message = "Instructor ID is required")
    private Integer instructorId;

}