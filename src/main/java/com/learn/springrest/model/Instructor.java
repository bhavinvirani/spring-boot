package com.learn.springrest.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "instructor")
@AllArgsConstructor
@Builder
@Data
@ToString(exclude = {"courses", "instructorDetail"})
@EqualsAndHashCode(exclude = {"courses", "instructorDetail"})
public class Instructor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String email;
    // One-to-one relationship with InstructorDetail. Instructor owns the InstructorDetail
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "instructor_detail_id", referencedColumnName = "id", nullable = false)
    private InstructorDetail instructorDetail;
    // One-to-many relationship with Course
    // CascadeType.ALL allows all operations (persist, merge, remove, refresh, detach) to be cascaded to the courses. Course is managed by the instructor.
    // orphanRemoval = true ensures that if a course is removed from the instructor, it will also be deleted from the database
    @OneToMany(mappedBy = "instructor", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Course> courses = new ArrayList<>();

    public Instructor() { // default implementation ignored
    }

    // Convenience methods for managing the bidirectional relationship with Course
    // 1. Add a course to the instructor and ensure the course's instructor is updated
    public void addCourse(Course course) {
        this.courses.add(course);
        course.setInstructor(this);
    }

    // 2. Remove a course from the instructor and ensure the course's instructor is updated
    public void removeCourse(Course course) {
        this.courses.remove(course);
        course.setInstructor(null);
    }

}
