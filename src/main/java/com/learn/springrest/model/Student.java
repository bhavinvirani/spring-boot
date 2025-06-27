package com.learn.springrest.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "student")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@ToString(exclude = {"courses", "reviews"})
@EqualsAndHashCode(exclude = {"courses", "reviews"})
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String firstName;
    private String lastName;

    @Column(unique = true, nullable = false)
    private String email;

    // Many-To-Many relationship withCourse
    @ManyToMany
    @JoinTable(name = "student_course", joinColumns = @JoinColumn(name = "student_id"), inverseJoinColumns = @JoinColumn(name = "course_id"))
    private Set<Course> courses = new HashSet<>();

    // One-To-Many relationship with Review
    // CascadeType.ALL allows all operations (persist, merge, remove, refresh, detach) to be cascaded to the reviews
    // orphanRemoval = true ensures that if a review is removed from the student, it will also be deleted from the database
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Review> reviews = new ArrayList<>();


    // Convenience methods for managing the bidirectional relationship with Course
    // 1. Add a course to the student and ensure the course's student list is updated
    public void addCourse(Course course) {
        this.courses.add(course);
        course.getStudents().add(this);
    }

    // 2. Remove a course from the student and ensure the course's student list is updated
    public void removeCourse(Course course) {
        this.courses.remove(course);
        course.getStudents().remove(this);
    }


}