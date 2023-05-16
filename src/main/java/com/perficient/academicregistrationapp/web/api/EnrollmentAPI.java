package com.perficient.academicregistrationapp.web.api;

import com.perficient.academicregistrationapp.web.dto.CourseDTO;
import com.perficient.academicregistrationapp.web.dto.UserCourseDTO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/enrollment")
public interface EnrollmentAPI {

    @PreAuthorize(("hasRole('USER')"))
    @PostMapping("/enroll-user")
    UserCourseDTO enrollUser(@RequestParam String userEmail, @RequestParam String courseName);

    @PreAuthorize(("hasRole('USER')"))
    @PostMapping("/unenroll-user")
    String unenrollUser(@RequestParam String userEmail, @RequestParam String courseName);

    @PreAuthorize(("hasRole('USER')"))
    @GetMapping("/get-enrolled-courses")
    List<CourseDTO> getEnrolledUserCourses(@RequestParam UUID userId);
}
