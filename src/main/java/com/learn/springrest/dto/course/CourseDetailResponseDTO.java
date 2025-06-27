package com.learn.springrest.dto.course;

import com.learn.springrest.dto.InstructorDTO;
import com.learn.springrest.dto.StudentDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseDetailResponseDTO {
    private Integer id;
    private String title;
    private String description;
    private InstructorDTO instructor;
    private Set<StudentDTO> students;
}
