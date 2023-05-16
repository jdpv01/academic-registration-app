package com.perficient.academicregistrationapp.web.api;

import com.perficient.academicregistrationapp.web.dto.CourseDTO;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/courses")
public interface CourseAPI {

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create-course")
    CourseDTO createCourse(@Valid @RequestBody CourseDTO courseDTO);

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/get-course")
    CourseDTO getCourse(@RequestParam UUID id);

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/get-all-courses")
    List<CourseDTO> getCourses();

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/get-all-courses-paged")
    List<CourseDTO> getAllCoursesPaged(@RequestParam int limit, @RequestParam int offset);

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/get-courses-page")
    List<CourseDTO> getCoursesPage(@RequestParam int pageNumber, @RequestParam int pageSize);

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update-course")
    CourseDTO updateCourse(@RequestParam UUID id, @Valid @RequestBody CourseDTO courseDTO);

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete-course")
    boolean deleteCourse(@RequestParam UUID id);
}