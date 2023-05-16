package com.perficient.academicregistrationapp.web.error;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BusinessErrorDetails {

    private Object errorCode;

    private String message;

    private LocalDateTime timestamp;

    public BusinessErrorDetails(Object errorCode, String message){
        this.errorCode = errorCode;
        this.message = message;
        timestamp = LocalDateTime.now();
    }
}




























































