package com.perficient.academicregistrationapp.web.controller;

import com.perficient.academicregistrationapp.mapper.UserMapper;
import com.perficient.academicregistrationapp.service.UserService;
import com.perficient.academicregistrationapp.web.api.UserAPI;
import com.perficient.academicregistrationapp.web.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class UserController implements UserAPI {

    private final UserService userService;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        return userMapper.fromUser(userService.createUser(userMapper.fromUserDTO(userDTO)));
    }

    @Override
    public UserDTO getUser(UUID id) {
        return userMapper.fromUser(userService.getUser(id));
    }


    @Override
    public List<UserDTO> getUsers() {
        return userService.getUsers().stream().map(userMapper::fromUser).collect(Collectors.toList());
    }

    @Override
    public UserDTO updateUser(UUID id, UserDTO userDTO) {
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        return userMapper.fromUser(userService.updateUser(userMapper.fromUserDTO(id, userDTO)));
    }

    @Override
    public boolean deleteUser(UUID id) {
        return userService.deleteUser(id);
    }
}