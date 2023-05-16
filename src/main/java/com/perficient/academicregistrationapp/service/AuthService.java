package com.perficient.academicregistrationapp.service;

import com.perficient.academicregistrationapp.web.dto.SigninRequestDTO;
import com.perficient.academicregistrationapp.web.dto.SignupRequestDTO;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    ResponseEntity<?> authenticateUser(SigninRequestDTO signinRequestDTO);

    ResponseEntity<?> registerUser(SignupRequestDTO signUpRequestDTO);
}
