package com.learn.springrest.model;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "review", uniqueConstraints = @UniqueConstraint(columnNames = {"student_id", "course_id"}))
@Builder
@AllArgsConstructor
@Data
@ToString(exclude = {"student", "course"})
@EqualsAndHashCode(exclude = {"student", "course"})
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Float rating;
    private String comment;
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;
    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    public Review() { // default implementation ignored
    }

}
