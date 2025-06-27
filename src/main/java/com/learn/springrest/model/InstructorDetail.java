package com.learn.springrest.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "instructor_detail")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InstructorDetail {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private int id;
    private String youtubeChannel;
    private String hobby;

    // One-to-one relationship with Instructor
    @OneToOne(mappedBy = "instructorDetail")
    private Instructor instructor;
}
