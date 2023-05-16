package com.perficient.academicregistrationapp.web.dto;

import com.perficient.academicregistrationapp.web.validation.CustomAnnotations;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequestDTO {

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;
    
    private List<String> roleList;

    @Email
    @NotNull
    private String email;

    @CustomAnnotations.PasswordValidation
    @NotNull
    private String password;
}
