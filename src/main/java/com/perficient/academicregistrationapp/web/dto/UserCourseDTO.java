package com.perficient.academicregistrationapp.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserCourseDTO {

    private UUID id;

    private UserDTO userDTO;

    private CourseDTO courseDTO;

    private LocalDateTime registrationDate;
}
