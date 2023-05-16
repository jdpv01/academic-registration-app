package com.perficient.academicregistrationapp.web.error.exception;

import com.perficient.academicregistrationapp.web.error.BusinessErrorDetails;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
public class BusinessValidationException extends RuntimeException {

    private HttpStatus httpStatus;
    private BusinessErrorDetails businessErrorDetails;
}