package com.perficient.academicregistrationapp.web.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseDTO {

    private UUID id;

    @NotEmpty
    private String name;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    @NotNull
    @Min(value = 0, message = "Capacity cannot be lower than 0")
    @Max(value = 30, message = "Capacity cannot be greater than 30")
    private Integer capacity;

    @NotNull
    @Valid
    private List<SubjectDTO> subjectDTOList;
}
