package com.perficient.academicregistrationapp.integration.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.perficient.academicregistrationapp.web.dto.CourseDTO;
import com.perficient.academicregistrationapp.web.dto.UserCourseDTO;
import com.perficient.academicregistrationapp.web.dto.UserDTO;
import lombok.SneakyThrows;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EnrollmentIntegrationTest {

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
    @WithMockUser(roles="USER")
    @Order(3)
    public void givenUserEmailAndCourseName_whenEnrollUser_thenReturnSavedUserCourseDTO(){
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(
                "/enrollment/enroll-user?userEmail=juandpv@hotmail.com&courseName=Introduccion a las TIC")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(String.class)))
                .andExpect(status().isOk()).andReturn();

        UserCourseDTO userCourseDTOResult = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), UserCourseDTO.class);
        UserDTO userDTO = userCourseDTOResult.getUserDTO();
        CourseDTO courseDTO = userCourseDTOResult.getCourseDTO();
        assertThat(userDTO, hasProperty("email", is("juandpv@hotmail.com")));
        assertThat(courseDTO, hasProperty("name", is("Introduccion a las TIC")));
    }

    @Test
    @SneakyThrows
    @WithMockUser(roles="USER")
    @Order(2)
    public void givenUserEmailAndCourseName_whenUnenrollUser_thenReturnMessage(){
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(
                                "/enrollment/unenroll-user?userEmail=juandpv@hotmail.com&courseName=Matematicas")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        assertThat(response, equalTo("User juandpv@hotmail.com has been unenrolled from course Matematicas"));
    }

    @Test
    @SneakyThrows
    @WithMockUser(roles="USER")
    @Order(1)
    public void givenUserId_whenGetEnrolledUserCourses_thenReturnCourseDTOList(){
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(
                                "/enrollment/get-enrolled-courses")
                        .queryParam("userId", "a364e22d-24a3-4954-b37e-b5719118c9ba")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        List<CourseDTO> courseDTOList = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>(){});
        assertThat(courseDTOList, hasSize(1));
        assertThat(courseDTOList.get(0), hasProperty("name", is("Matematicas")));
    }

}
