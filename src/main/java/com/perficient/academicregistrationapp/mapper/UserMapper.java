package com.perficient.academicregistrationapp.mapper;

import com.perficient.academicregistrationapp.persistance.model.User;
import com.perficient.academicregistrationapp.web.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User fromUserDTO(UserDTO userDTO);

    @Mapping(source = "id", target = "id")
    User fromUserDTO(UUID id, UserDTO userDTO);

    UserDTO fromUser(User user);
}
