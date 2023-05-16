package com.perficient.academicregistrationapp.service;

import com.perficient.academicregistrationapp.persistance.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.UUID;

public interface UserService {

    User createUser(User user);

    User getUser(UUID id);

    List<User> getUsers();

    User updateUser(User user);

    boolean deleteUser(UUID id);

    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
