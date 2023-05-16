package com.perficient.academicregistrationapp.service;

import com.perficient.academicregistrationapp.enums.Roles;
import com.perficient.academicregistrationapp.persistance.model.Role;
import com.perficient.academicregistrationapp.persistance.repository.RoleRepository;
import com.perficient.academicregistrationapp.persistance.repository.UserRepository;
import com.perficient.academicregistrationapp.service.impl.AuthServiceImpl;
import com.perficient.academicregistrationapp.web.dto.SignupRequestDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @InjectMocks
    private AuthServiceImpl authService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    public SignupRequestDTO setupSignupRequestDTO(){
        return SignupRequestDTO.builder()
                .firstName("user")
                .lastName("user")
                .email("pelaezd86@gmail.com")
                .password("123456Aa")
                .build();
    }

    @Test
    public void testRegisterUser(){
        SignupRequestDTO signupRequestDTO = setupSignupRequestDTO();
        when(userRepository.existsByEmail(any())).thenReturn(false);
        when(roleRepository.findByRole(any())).thenReturn(Optional.of(new Role(1, Roles.ROLE_USER)));
        PasswordEncoder BCryptPasswordEncoder = new BCryptPasswordEncoder();
        when(passwordEncoder.encode(any())).thenReturn(BCryptPasswordEncoder.encode(signupRequestDTO.getPassword()));
        assertEquals(authService.registerUser(signupRequestDTO).getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void testRegisterUserEmailAlreadyInUse(){
        SignupRequestDTO signupRequestDTO = setupSignupRequestDTO();
        when(userRepository.existsByEmail(any())).thenReturn(true);
        assertEquals(authService.registerUser(signupRequestDTO).getStatusCode(), HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
