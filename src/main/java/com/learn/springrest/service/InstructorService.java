package com.learn.springrest.service;

import com.learn.springrest.dto.InstructorDTO;
import com.learn.springrest.model.Instructor;
import com.learn.springrest.utils.PagedResponse;

public interface InstructorService {
    PagedResponse<InstructorDTO> getAllInstructors(String keyword, int page, int size, String sortBy);

    Instructor getInstructorById(Integer id);

    InstructorDTO createInstructor(InstructorDTO instructorDTO);

    InstructorDTO updateInstructor(Integer id, InstructorDTO instructorDTO);

    void deleteInstructor(Integer id);
}
