package com.learn.springrest.service;

import com.learn.springrest.dto.CourseDTO;
import com.learn.springrest.dto.course.CourseDetailResponseDTO;
import com.learn.springrest.utils.PagedResponse;

public interface CourseService {
    PagedResponse<CourseDTO> getAllCourses(String keyword, int page, int size, String sortBy);

    CourseDetailResponseDTO getCourseById(Integer id);

    CourseDTO createCourse(CourseDTO courseDTO);

    CourseDTO updateCourse(Integer id, CourseDTO courseDTO);

    void deleteCourse(Integer id);

    //Add Student to Course:
    void addStudentToCourse(Integer courseId, Integer studentId);

    PagedResponse<CourseDTO> getCoursesByInstructorId(Integer instructorId, String keyword, int page, int size, String sortBy);

}
