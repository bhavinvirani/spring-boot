package com.learn.springrest.repository;

import com.learn.springrest.model.Course;
import com.learn.springrest.model.Instructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {
    Page<Course> findByTitleContainingIgnoreCase(String title,
                                                 Pageable pageable);

    Page<Course> findByInstructor(Instructor instructor, Pageable pageable);

    Page<Course> findByTitleContainingIgnoreCaseAndInstructor(String keyword, Instructor instructor, Pageable pageable);
}
