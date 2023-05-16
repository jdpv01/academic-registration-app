package com.perficient.academicregistrationapp.web.controller;

import com.perficient.academicregistrationapp.mapper.CourseMapper;
import com.perficient.academicregistrationapp.service.CourseService;
import com.perficient.academicregistrationapp.web.api.CourseAPI;
import com.perficient.academicregistrationapp.web.dto.CourseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class CourseController implements CourseAPI {

    private final CourseService courseService;
    private final CourseMapper courseMapper;

    @Override
    public CourseDTO createCourse(CourseDTO courseDTO) {
        return courseMapper.fromCourse(courseService.createCourse(courseMapper.fromCourseDTO(courseDTO)));
    }

    @Override
    public CourseDTO getCourse(UUID id) {
        return courseMapper.fromCourse(courseService.getCourse(id));
    }

    @Override
    public List<CourseDTO> getCourses() {
        return courseService.getCourses().stream().map(courseMapper::fromCourse).collect(Collectors.toList());
    }

    @Override
    public List<CourseDTO> getAllCoursesPaged(int limit, int offset) {
        return courseService.getAllCoursesPaged(limit, offset).stream().map(courseMapper::fromCourse).collect(Collectors.toList());
    }

    @Override
    public List<CourseDTO> getCoursesPage(int pageNumber, int pageSize) {
        return courseService.getCoursesPage(pageNumber, pageSize).stream().map(courseMapper::fromCourse).collect(Collectors.toList());
    }

    @Override
    public CourseDTO updateCourse(UUID id, CourseDTO courseDTO) {
        return courseMapper.fromCourse(courseService.updateCourse(courseMapper.fromCourseDTO(id,
                courseDTO.getSubjectDTOList(), courseDTO)));
    }

    @Override
    public boolean deleteCourse(UUID id) {
        return courseService.deleteCourse(id);
    }
}
