package com.perficient.academicregistrationapp.service;

import com.perficient.academicregistrationapp.persistance.model.Course;
import com.perficient.academicregistrationapp.persistance.model.UserCourse;

import java.util.List;
import java.util.UUID;

public interface EnrollmentService {

    UserCourse enrollUser(String userEmail, String courseName);

    String unenrollUser(String userEmail, String courseName);

    List<Course> getEnrolledUserCourses(UUID userId);
}
