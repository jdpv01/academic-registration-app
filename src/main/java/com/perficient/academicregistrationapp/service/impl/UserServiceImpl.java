package com.perficient.academicregistrationapp.service.impl;

import com.perficient.academicregistrationapp.enums.UserErrorCode;
import com.perficient.academicregistrationapp.persistance.model.User;
import com.perficient.academicregistrationapp.persistance.repository.UserRepository;
import com.perficient.academicregistrationapp.service.UserService;
import com.perficient.academicregistrationapp.web.error.BusinessErrorDetails;
import com.perficient.academicregistrationapp.web.error.exception.BusinessValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService, UserService {

    private final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with email: " + email));
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUser(UUID id) {
        return userRepository.findById(id).orElseThrow(() ->
                new BusinessValidationException(HttpStatus.NOT_FOUND, new BusinessErrorDetails(UserErrorCode.CODE_01,
                UserErrorCode.CODE_01.getMessage())));
    }

    @Override
    public List<User> getUsers() {
        return new ArrayList<>(userRepository.findAll());
    }

    @Override
    public User updateUser(User user) {
        User savedUser = userRepository.findById(user.getId()).orElseThrow(() ->
                new BusinessValidationException(HttpStatus.NOT_FOUND, new BusinessErrorDetails(UserErrorCode.CODE_01,
                UserErrorCode.CODE_01.getMessage())));
        savedUser.setFirstName(user.getFirstName());
        savedUser.setLastName(user.getLastName());
        savedUser.setEmail(user.getEmail());
        savedUser.setPassword(user.getPassword());
        return userRepository.save(savedUser);
    }

    @Override
    public boolean deleteUser(UUID id) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new BusinessValidationException(HttpStatus.NOT_FOUND, new BusinessErrorDetails(UserErrorCode.CODE_01,
                UserErrorCode.CODE_01.getMessage())));
        userRepository.delete(user);
        return true;
    }
}
