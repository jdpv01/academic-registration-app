package com.perficient.academicregistrationapp.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CourseErrorCode {

    CODE_01("Course not found."),

    CODE_02("There is a course with the same name registered already."),

    CODE_03("Invalid dates: startDate has to come before endDate and at least 7 days apart."),

    CODE_04("Invalid times: startTime has to come before endTime and at least 60 minutes apart."),

    CODE_05("Invalid value for limit or offset."),

    CODE_06("Invalid value for pageNumber or pageSize.");

    private final String message;
}