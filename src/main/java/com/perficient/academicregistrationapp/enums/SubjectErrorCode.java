package com.perficient.academicregistrationapp.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SubjectErrorCode {

    CODE_01("Subject not found."),

    CODE_02("Weekdays format is invalid.");

    private final String message;
}
