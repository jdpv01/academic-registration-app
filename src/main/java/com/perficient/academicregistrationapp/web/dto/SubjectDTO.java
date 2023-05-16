package com.perficient.academicregistrationapp.web.dto;

import com.perficient.academicregistrationapp.web.validation.CustomAnnotations;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubjectDTO {

    private UUID id;

    @NotEmpty
    private String name;

    @NotNull
    private LocalTime startTime;

    @NotNull
    private LocalTime endTime;

    @CustomAnnotations.WeekdaysValidation
    @NotNull
    private String weekdays;
}
