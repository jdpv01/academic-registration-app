package com.perficient.academicregistrationapp.integration.controller;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.perficient.academicregistrationapp.web.dto.UserDTO;
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

import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class UserIntegrationTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup(){
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(springSecurity()).build();
        objectMapper.setAnnotationIntrospector(IgnoreJacksonWriteOnlyAccess());
    }

    private JacksonAnnotationIntrospector IgnoreJacksonWriteOnlyAccess(){
        return new JacksonAnnotationIntrospector(){
            @Override
            public JsonProperty.Access findPropertyAccess(Annotated annotated) {
                JsonProperty.Access access = super.findPropertyAccess(annotated);
                if (access == JsonProperty.Access.WRITE_ONLY) {
                    return JsonProperty.Access.AUTO;
                }
                return access;
            }
        };
    }

    @Test
    @SneakyThrows
    @WithMockUser(roles="ADMIN")
    public void givenUserDTO_whenCreateUser_thenReturnSavedUserDTO(){
        UserDTO userDTO = UserDTO.builder()
                .firstName("Juan")
                .lastName("Pelaez")
                .email("newUser@hotmail.com")
                .password("123456Aaa").build();

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/users/create-user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isOk()).andReturn();

        UserDTO UserDTOResult = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), UserDTO.class);
        assertThat(UserDTOResult, hasProperty("firstName", is(userDTO.getFirstName())));
    }

    @Test
    @SneakyThrows
    @WithMockUser(roles="ADMIN")
    public void givenUserId_whenGetUser_thenReturnSavedUserDTO(){
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .get("/users/get-user")
                        .queryParam("id", "a364e22d-24a3-4954-b37e-b5719118c9ba")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        UserDTO UserDTOResult = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), UserDTO.class);
        assertThat(UserDTOResult, hasProperty("id", is(UUID.fromString("a364e22d-24a3-4954-b37e-b5719118c9ba"))));
    }

    @Test
    @SneakyThrows
    @WithMockUser(roles="ADMIN")
    public void whenGetUsers_thenReturnUserDTOList(){
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .get("/users/get-all-users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        List<UserDTO> UserDTOListResult = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>(){});
        assertThat(UserDTOListResult, hasSize(5));
    }

    @Test
    @SneakyThrows
    @WithMockUser(roles="USER")
    public void givenUserDTOAndUserId_whenUpdateUser_thenReturnSavedUserDTO(){
        UserDTO userDTO = UserDTO.builder()
                .firstName("Juan Edited")
                .lastName("Pelaez Edited")
                .email("newUserEdited@hotmail.com")
                .password("123456Aa Edited").build();

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .put("/users/update-user?id=ebfab851-419d-4707-b0e3-d3ff4d4596f5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isOk()).andReturn();

        UserDTO UserDTOResult = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), UserDTO.class);
        assertThat(UserDTOResult, hasProperty("firstName", is(userDTO.getFirstName())));
    }

    @Test
    @SneakyThrows
    @WithMockUser(roles="ADMIN")
    public void givenUserId_whenDeleteUser_thenReturnIfDeleted(){
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .delete("/users/delete-user?id=f56259d3-ab63-42a5-bcbe-6d8a01205944")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        Boolean response = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>(){});
        assertThat(response, is(true));
    }

    @Test
    @SneakyThrows
    @WithMockUser(roles="ADMIN")
    public void givenWrongUserId_whenDeleteUser_thenReturnNotFoundMessage(){
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/users/delete-user?id=a804b4bf-0ca5-4a34-a81c-24da34bedde2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()).andReturn();
    }
}
