package com.perficient.academicregistrationapp.web.api;

import com.perficient.academicregistrationapp.web.dto.SigninRequestDTO;
import com.perficient.academicregistrationapp.web.dto.SignupRequestDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/auth")
public interface AuthAPI {

    @PostMapping("/sign-in")
    ResponseEntity<?> authenticateUser(@Valid @RequestBody SigninRequestDTO signinRequestDTO);

    @PostMapping("/sign-up")
    ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequestDTO signUpRequestDTO);
}
