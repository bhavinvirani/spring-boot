package com.learn.springrest.mapper;

import com.learn.springrest.dto.InstructorDTO;
import com.learn.springrest.dto.InstructorDetailDTO;
import com.learn.springrest.model.Instructor;
import com.learn.springrest.model.InstructorDetail;

public class InstructorMapper {

    private InstructorMapper() {
        // Private constructor to prevent instantiation
    }

    public static InstructorDTO toInstructorDTO(Instructor instructor) {
        if (instructor == null) return null;

        return InstructorDTO.builder()
                .id(instructor.getId())
                .firstName(instructor.getFirstName())
                .lastName(instructor.getLastName())
                .email(instructor.getEmail())
                .instructorDetail(toInstructorDetailDTO(instructor.getInstructorDetail()))
                .build();
    }

    public static Instructor toInstructorModel(InstructorDTO instructorDTO) {
        if (instructorDTO == null) return null;

        return Instructor.builder()
                .firstName(instructorDTO.getFirstName())
                .lastName(instructorDTO.getLastName())
                .email(instructorDTO.getEmail())
                .instructorDetail(toInstructorDetailModel(instructorDTO.getInstructorDetail()))
                .build();
    }

    public static InstructorDetailDTO toInstructorDetailDTO(InstructorDetail detail) {
        if (detail == null) return null;

        return InstructorDetailDTO.builder()
                .id(detail.getId())
                .youtubeChannel(detail.getYoutubeChannel())
                .hobby(detail.getHobby())
                .build();
    }

    public static InstructorDetail toInstructorDetailModel(InstructorDetailDTO dto) {
        if (dto == null) return null;

        return InstructorDetail.builder()
                .youtubeChannel(dto.getYoutubeChannel())
                .hobby(dto.getHobby())
                .build();
    }
}
