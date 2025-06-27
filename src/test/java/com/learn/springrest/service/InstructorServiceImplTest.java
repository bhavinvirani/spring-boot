package com.learn.springrest.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class InstructorServiceImplTest {



    @InjectMocks
    InstructorServiceImpl instructorService;

    @Test
    void getInstructorByIdTest() {
        System.out.println("getInstructorByIdTest -> running Test");
        instructorService.getInstructorById(1);
    }

    @Test
    void addInstructorTest() {

    }

}