package com.perficient.academicregistrationapp.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserErrorCode {

    CODE_01("User not found."),

    CODE_02("There are no available seats for this course."),

    CODE_03("Time conflict detected! Consider enrolling into a different course."),

    CODE_04("User is already enrolled in this course.");

    private final String message;
}
