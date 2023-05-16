package com.perficient.academicregistrationapp.web.validation;

import com.perficient.academicregistrationapp.enums.SubjectErrorCode;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.SneakyThrows;

public class WeekdaysValidator implements ConstraintValidator<CustomAnnotations.WeekdaysValidation, String> {

    @Override
    @SneakyThrows
    public boolean isValid(String weekdays, ConstraintValidatorContext context) {
        boolean isValid = weekdays.matches("^(LU|MA|MI|JU|VI|SA)(-(?!$)(?!.*\\1)(LU|MA|MI|JU|VI|SA)){0,5}$");
        if(isValid)
            return true;
        context.buildConstraintViolationWithTemplate(SubjectErrorCode.CODE_02.getMessage())
                .addConstraintViolation()
                .disableDefaultConstraintViolation();
        return false;
    }
}
