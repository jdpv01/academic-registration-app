package com.perficient.academicregistrationapp.integration.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.perficient.academicregistrationapp.web.dto.CourseDTO;
import com.perficient.academicregistrationapp.web.dto.SubjectDTO;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class CourseIntegrationTest {

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
    @WithMockUser(roles="ADMIN")
    public void givenCourseDTO_whenCreateCourse_thenReturnSavedCourseDTO(){
        SubjectDTO subjectDTO1 = SubjectDTO.builder()
                .name("PowerPoint")
                .startTime(LocalTime.of(14, 0))
                .endTime(LocalTime.of(17, 0))
                .weekdays("LU-MI").build();
        SubjectDTO subjectDTO2 = SubjectDTO.builder()
                .name("Word")
                .startTime(LocalTime.of(14, 0))
                .endTime(LocalTime.of(17, 0))
                .weekdays("LU-MI").build();
        CourseDTO courseDTO = CourseDTO.builder()
                .name("Habilidades basicas en computacion")
                .startDate(LocalDate.of(2023, 1, 16))
                .endDate(LocalDate.of(2023,5,16))
                .capacity(0)
                .subjectDTOList(Arrays.asList(subjectDTO1, subjectDTO2)).build();

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/courses/create-course")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(courseDTO)))
                .andExpect(status().isOk()).andReturn();

        CourseDTO courseDTOResult = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), CourseDTO.class);
        assertThat(courseDTOResult, hasProperty("name", is(courseDTO.getName())));
    }

    @Test
    @SneakyThrows
    @WithMockUser(roles="ADMIN")
    public void givenCourseId_whenGetCourse_thenReturnSavedCourseDTO(){
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .get("/courses/get-course")
                        .queryParam("id", "3f6d25ad-3a46-4ce5-beea-1a1b77d359b3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        CourseDTO courseDTOResult = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), CourseDTO.class);
        assertThat(courseDTOResult, hasProperty("id", is(UUID.fromString("3f6d25ad-3a46-4ce5-beea-1a1b77d359b3"))));
    }

    @Test
    @SneakyThrows
    @WithMockUser(roles="ADMIN")
    public void whenGetCourses_thenReturnCourseDTOList(){
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .get("/courses/get-all-courses")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        List<CourseDTO> courseDTOListResult = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>(){});
        assertThat(courseDTOListResult, hasSize(2));
    }

    @Test
    @SneakyThrows
    @WithMockUser(roles="ADMIN")
    public void givenLimitAndOffset_whenGetAllCoursesPaged_thenReturnCourseDTOList(){
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .get("/courses/get-all-courses-paged")
                        .queryParam("limit", "1")
                        .queryParam("offset", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        List<CourseDTO> courseDTOListResult = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>(){});
        assertThat(courseDTOListResult, hasSize(1));
        assertThat(courseDTOListResult.get(0), hasProperty("name", is("Matematicas")));
    }

    @Test
    @SneakyThrows
    @WithMockUser(roles="ADMIN")
    public void givenPageNumberAndPageSize_whenGetCoursesPage_thenReturnCourseDTOList(){
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .get("/courses/get-courses-page?pageNumber=0&pageSize=1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        List<CourseDTO> courseDTOListResult = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>(){});
        assertThat(courseDTOListResult, hasSize(1));
        assertThat(courseDTOListResult.get(0), hasProperty("name", is("Introduccion a las TIC")));
    }

    @Test
    @SneakyThrows
    @WithMockUser(roles="ADMIN")
    public void givenCourseDTOAndCourseId_whenUpdateCourse_thenReturnSavedCourse(){
        SubjectDTO subjectDTO1 = SubjectDTO.builder()
                .name("Cálculo de varias variables")
                .startTime(LocalTime.of(14, 0))
                .endTime(LocalTime.of(16, 0))
                .weekdays("LU-MI-VI").build();
        SubjectDTO subjectDTO2 = SubjectDTO.builder()
                .name("Algebra lineal")
                .startTime(LocalTime.of(14, 0))
                .endTime(LocalTime.of(16, 0))
                .weekdays("MA-JU").build();
        CourseDTO courseDTO = CourseDTO.builder()
                .name("Matematicas")
                .startDate(LocalDate.of(2023, 1, 16))
                .endDate(LocalDate.of(2023,5,16))
                .capacity(0)
                .subjectDTOList(Arrays.asList(subjectDTO1, subjectDTO2)).build();

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .put("/courses/update-course?id=3f6d25ad-3a46-4ce5-beea-1a1b77d359b3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(courseDTO)))
                .andExpect(status().isOk()).andReturn();

        CourseDTO courseDTOResult = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), CourseDTO.class);
        assertThat(courseDTOResult, hasProperty("name", is(courseDTO.getName())));
        assertThat(courseDTOResult, hasProperty("capacity", is(courseDTO.getCapacity())));
    }

    @Test
    @SneakyThrows
    @WithMockUser(roles="ADMIN")
    public void givenCourseId_whenDeleteCourse_thenReturnIfDeleted(){
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .delete("/courses/delete-course?id=3f6d25ad-3a46-4ce5-beea-1a1b77d359b3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        Boolean response = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>(){});
        assertThat(response, is(true));
    }
}
