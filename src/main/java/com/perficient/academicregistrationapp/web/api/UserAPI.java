package com.perficient.academicregistrationapp.web.api;

import com.perficient.academicregistrationapp.web.dto.UserDTO;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/users")
public interface UserAPI {

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create-user")
    UserDTO createUser(@Valid @RequestBody UserDTO userDTO);

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/get-user")
    UserDTO getUser(@RequestParam UUID id);

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/get-all-users")
    List<UserDTO> getUsers();

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PutMapping("/update-user")
    UserDTO updateUser(@RequestParam UUID id, @Valid @RequestBody UserDTO userDTO);

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete-user")
    boolean deleteUser(@RequestParam UUID id);
}