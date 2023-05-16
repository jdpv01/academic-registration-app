package com.perficient.academicregistrationapp.service;

import com.perficient.academicregistrationapp.persistance.model.Course;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface CourseService {

    Course createCourse(Course course);

    Course getCourse(UUID id);

    List<Course> getCourses();

    List<Course> getAllCoursesPaged(int limit, int offset);

    Page<Course> getCoursesPage(int pageNumber, int pageSize);

    Course updateCourse(Course course);

    boolean deleteCourse(UUID id);
}
