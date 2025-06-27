package com.learn.springrest.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InstructorDetailDTO {

    private Integer id;

    @NotBlank(message = "YouTube channel cannot be blank")
    private String youtubeChannel;

    @NotBlank(message = "Hobby cannot be blank")
    private String hobby;
}