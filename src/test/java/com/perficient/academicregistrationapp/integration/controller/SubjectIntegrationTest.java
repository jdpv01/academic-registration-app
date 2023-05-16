package com.perficient.academicregistrationapp.integration.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.perficient.academicregistrationapp.web.dto.SubjectDTO;
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

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class SubjectIntegrationTest {

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
    public void givenSubjectId_whenGetSubject_thenReturnSavedSubjectDTO(){
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .get("/subjects/get-subject")
                        .queryParam("id", "a804b4bf-0ca5-4a34-a81c-24da34bedde2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        SubjectDTO subjectDTOResult = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), SubjectDTO.class);
        assertThat(subjectDTOResult, hasProperty("id", is(UUID.fromString("a804b4bf-0ca5-4a34-a81c-24da34bedde2"))));
    }

    @Test
    @SneakyThrows
    @WithMockUser(roles="ADMIN")
    public void whenGetSubjects_thenReturnSubjectDTOList(){
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .get("/subjects/get-all-subjects")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        List<SubjectDTO> subjectDTOListResult = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>(){});
        assertThat(subjectDTOListResult, hasSize(6));
    }

    @Test
    @SneakyThrows
    @WithMockUser(roles="ADMIN")
    public void givenSubjectDTOAndSubjectId_whenUpdateSubject_thenReturnSavedSubject(){
        SubjectDTO subjectDTO = SubjectDTO.builder()
                .name("Scratch Edited")
                .startTime(LocalTime.of(14, 0))
                .endTime(LocalTime.of(16, 0))
                .weekdays("LU-MI-VI").build();

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .put("/subjects/update-subject?id=a804b4bf-0ca5-4a34-a81c-24da34bedde2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(subjectDTO)))
                .andExpect(status().isOk()).andReturn();

        SubjectDTO SubjectDTOResult = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), SubjectDTO.class);
        assertThat(SubjectDTOResult, hasProperty("name", is(subjectDTO.getName())));
    }

    @Test
    @SneakyThrows
    @WithMockUser(roles="ADMIN")
    public void givenSubjectId_whenDeleteSubject_thenReturnIfDeleted(){
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .delete("/subjects/delete-subject?id=1bba5c90-080f-4c41-8ba8-484cbcaf7f4a")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        Boolean response = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>(){});
        assertThat(response, is(true));
    }
}
