package com.learn.springrest.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "course")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@ToString(exclude = {"students", "reviews", "instructor"})
@EqualsAndHashCode(exclude = {"students", "reviews", "instructor"})
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String description;

    // Many-To-One relationship with Instructor
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "instructor_id", referencedColumnName = "id", nullable = false)
    private Instructor instructor;

    // One-To-Many relationship withReview
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Review> reviews = new ArrayList<>();

    // Many-To-Many relationship withStudent, Students can enroll in multiple courses
    @ManyToMany(mappedBy = "courses")
    private Set<Student> students = new HashSet<>();


    // Convenience methods for managing the bidirectional relationship with
    // 1. Add a student to the course and ensure the student's course list is updated
    public void addStudent(Student student) {
        this.students.add(student);
        student.getCourses().add(this);
    }

    // 2. Remove a student from the course and ensure the student's course list is updated
    public void removeStudent(Student student) {
        this.students.remove(student);
        student.getCourses().remove(this);
    }

    // Convenience methods for managing the bidirectional relationship with Review

}