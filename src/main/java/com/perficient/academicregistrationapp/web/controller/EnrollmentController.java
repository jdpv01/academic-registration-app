package com.perficient.academicregistrationapp.web.controller;

import com.perficient.academicregistrationapp.mapper.CourseMapper;
import com.perficient.academicregistrationapp.mapper.UserCourseMapper;
import com.perficient.academicregistrationapp.service.EnrollmentService;
import com.perficient.academicregistrationapp.web.api.EnrollmentAPI;
import com.perficient.academicregistrationapp.web.dto.CourseDTO;
import com.perficient.academicregistrationapp.web.dto.UserCourseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
public class EnrollmentController implements EnrollmentAPI {

    private final EnrollmentService enrollmentService;

    private final UserCourseMapper userCourseMapper;

    private final CourseMapper courseMapper;

    @Override
    public UserCourseDTO enrollUser(String userEmail, String courseName) {
        return userCourseMapper.fromUserCourse(enrollmentService.enrollUser(userEmail, courseName));
    }

    @Override
    public String unenrollUser(String userEmail, String courseName) {
        return enrollmentService.unenrollUser(userEmail, courseName);
    }

    @Override
    public List<CourseDTO> getEnrolledUserCourses(UUID userId) {
        return courseMapper.fromCourseList(enrollmentService.getEnrolledUserCourses(userId));
    }
}
