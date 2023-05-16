package com.perficient.academicregistrationapp.web.controller;

import com.perficient.academicregistrationapp.service.AuthService;
import com.perficient.academicregistrationapp.web.api.AuthAPI;
import com.perficient.academicregistrationapp.web.dto.SigninRequestDTO;
import com.perficient.academicregistrationapp.web.dto.SignupRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController implements AuthAPI {

	private final AuthService authService;

	@Override
	public ResponseEntity<?> authenticateUser(SigninRequestDTO signinRequestDTO) {
		return authService.authenticateUser(signinRequestDTO);
	}

	@Override
	public ResponseEntity<?> registerUser(SignupRequestDTO signUpRequestDTO) {
		return authService.registerUser(signUpRequestDTO);
	}
}
