package com.perficient.academicregistrationapp.mapper;

import com.perficient.academicregistrationapp.persistance.model.Course;
import com.perficient.academicregistrationapp.web.dto.CourseDTO;
import com.perficient.academicregistrationapp.web.dto.SubjectDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring", uses = SubjectMapper.class)
public interface CourseMapper {

    @Mapping(source = "subjectDTOList", target = "subjectList")
    Course fromCourseDTO(CourseDTO courseDTO);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "subjectDTOList", target = "subjectList")
    Course fromCourseDTO(UUID id, List<SubjectDTO> subjectDTOList, CourseDTO courseDTO);

    @Mapping(source = "subjectList", target = "subjectDTOList")
    CourseDTO fromCourse(Course course);

    List<CourseDTO> fromCourseList(List<Course> courseList);
}
