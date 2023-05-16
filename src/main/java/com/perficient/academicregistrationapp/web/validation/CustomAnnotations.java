package com.perficient.academicregistrationapp.web.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

public interface CustomAnnotations {

    @Target({TYPE, FIELD, ANNOTATION_TYPE})
    @Retention(RUNTIME)
    @Constraint(validatedBy = PasswordConstraintValidator.class)
    @Documented
    @interface PasswordValidation {

        String message() default "Invalid Password";

        Class<?>[] groups() default {};

        Class<? extends Payload>[] payload() default {};
    }

    @Target({TYPE, FIELD, ANNOTATION_TYPE})
    @Retention(RUNTIME)
    @Constraint(validatedBy = WeekdaysValidator.class)
    @Documented
    @interface WeekdaysValidation {

        String message() default "Invalid Weekdays";

        Class<?>[] groups() default {};

        Class<? extends Payload>[] payload() default {};
    }
}




