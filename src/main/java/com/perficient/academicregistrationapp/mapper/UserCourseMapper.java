package com.perficient.academicregistrationapp.mapper;

import com.perficient.academicregistrationapp.persistance.model.UserCourse;
import com.perficient.academicregistrationapp.web.dto.UserCourseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = CourseMapper.class)
public interface UserCourseMapper {

    @Mapping(source = "user", target = "userDTO")
    @Mapping(source = "course", target = "courseDTO")
    UserCourseDTO fromUserCourse(UserCourse userCourse);
}
