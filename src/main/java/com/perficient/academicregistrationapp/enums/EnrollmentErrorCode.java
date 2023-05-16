package com.perficient.academicregistrationapp.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EnrollmentErrorCode {

    CODE_01("Enrollment not found.");

    private final String message;
}
