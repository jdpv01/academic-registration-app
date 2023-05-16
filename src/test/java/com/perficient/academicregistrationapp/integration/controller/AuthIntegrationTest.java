package com.perficient.academicregistrationapp.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.perficient.academicregistrationapp.web.dto.SigninRequestDTO;
import com.perficient.academicregistrationapp.web.dto.SignupRequestDTO;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class AuthIntegrationTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup(){
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(springSecurity()).build();
    }

    @Test
    @SneakyThrows
    public void givenSignupRequest_whenRegisterUser_thenReturnResponseEntityWithStatusOk(){
        SignupRequestDTO signupRequestDTO = SignupRequestDTO.builder()
                .firstName("user")
                .lastName("user")
                .email("newEmail@hotmail.com")
                .password("123456Aa").build();

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signupRequestDTO)))
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    public void givenSigninRequest_whenAuthenticateUser_thenReturnResponseEntityWithStatusOk(){
        SigninRequestDTO signinRequestDTO = SigninRequestDTO.builder()
                .email("juandpv@hotmail.com")
                .password("123456Aa").build();
        mockMvc.perform(MockMvcRequestBuilders.post("/auth/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signinRequestDTO)))
                .andExpect(status().isOk());
    }
}
