package com.perficient.academicregistrationapp.validator;

import com.perficient.academicregistrationapp.web.dto.*;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.Set;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class DTOValidatorTest {

    private static Validator validator;

    @BeforeAll
    static void setup(){
        ValidatorFactory factoryValidator = Validation.buildDefaultValidatorFactory();
        validator = factoryValidator.getValidator();
    }

    @Test
    public void shouldFailSignupRequestDTOValidation(){
        final var signupRequestDTO = SignupRequestDTO.builder()
                .firstName(null)
                .lastName(null)
                .email("malformedEmail")
                .password("malformedPassword")
                .build();
        Set<ConstraintViolation<SignupRequestDTO>> violations = validator.validate(signupRequestDTO);
        assertThat(violations.size(), is(4));
    }

    @Test
    public void shouldFailLoginRequestDTOValidation(){
        final var loginRequestDTO = SigninRequestDTO.builder()
                .email("malformedEmail")
                .password(null)
                .build();
        Set<ConstraintViolation<SigninRequestDTO>> violations = validator.validate(loginRequestDTO);
        assertThat(violations.size(), is(2));
    }

    @Test
    public void shouldFailCourseDTOAndSubjectDTOValidation(){
        final var subjectDTO1 = SubjectDTO.builder()
                .id(UUID.randomUUID())
                .name("Cálculo de varias variables")
                .startTime(LocalTime.of(14, 0))
                .endTime(LocalTime.of(16, 0))
                .weekdays("LU-VI").build();
        final var subjectDTO2 = SubjectDTO.builder()
                .id(UUID.randomUUID())
                .name("")
                .startTime(null)
                .endTime(null)
                .weekdays("malformedWeekdays").build();
        final var courseDTO = CourseDTO.builder()
                .id(UUID.randomUUID())
                .name(null)
                .startDate(null)
                .endDate(null)
                .capacity(null)
                .subjectDTOList(Arrays.asList(subjectDTO1, subjectDTO2)).build();
        Set<ConstraintViolation<CourseDTO>> violations = validator.validate(courseDTO);
        assertThat(violations.size(), is(8));
    }

    @Test
    public void shouldFailSubjectDTOValidation() {
        final var subjectDTO = SubjectDTO.builder()
                .id(UUID.randomUUID())
                .name("")
                .startTime(null)
                .endTime(null)
                .weekdays("malformedWeekdays").build();
        Set<ConstraintViolation<SubjectDTO>> violations = validator.validate(subjectDTO);
        assertThat(violations.size(), is(4));
    }

    @Test
    public void shouldFailUserDTOValidation(){
        final var userDTO = UserDTO.builder()
                .id(UUID.randomUUID())
                .firstName(null)
                .lastName(null)
                .email("malformedEmail")
                .password("malformedPassword").build();
        Set<ConstraintViolation<UserDTO>> violations = validator.validate(userDTO);
        assertThat(violations.size(), is(4));
    }
}
