package com.learn.springrest.repository;

import com.learn.springrest.model.Instructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface InstructorRepository extends JpaRepository<Instructor, Integer> {
    Page<Instructor> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCase(
            String firstName, String lastName, String email, Pageable pageable);

    boolean existsByEmail(String email);

}
