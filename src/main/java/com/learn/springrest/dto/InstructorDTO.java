package com.learn.springrest.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InstructorDTO {
    private Integer id;

    @NotBlank(message = "First name cannot be blank")
    private String firstName;

    @NotBlank(message = "Last name cannot be blank")
    private String lastName;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email")
    private String email;

    @Valid
    private InstructorDetailDTO instructorDetail;
}
