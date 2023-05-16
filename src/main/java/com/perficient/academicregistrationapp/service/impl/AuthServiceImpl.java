package com.perficient.academicregistrationapp.service.impl;

import com.perficient.academicregistrationapp.enums.Roles;
import com.perficient.academicregistrationapp.persistance.model.Role;
import com.perficient.academicregistrationapp.persistance.model.User;
import com.perficient.academicregistrationapp.persistance.repository.RoleRepository;
import com.perficient.academicregistrationapp.persistance.repository.UserRepository;
import com.perficient.academicregistrationapp.security.jwt.JwtResponse;
import com.perficient.academicregistrationapp.security.jwt.JwtUtils;
import com.perficient.academicregistrationapp.security.jwt.MessageResponse;
import com.perficient.academicregistrationapp.service.AuthService;
import com.perficient.academicregistrationapp.web.dto.SigninRequestDTO;
import com.perficient.academicregistrationapp.web.dto.SignupRequestDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtils jwtUtils;

    @Override
    public ResponseEntity<?> authenticateUser(SigninRequestDTO signinRequestDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signinRequestDTO.getEmail(), signinRequestDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        User userDetails = (User) authentication.getPrincipal();
        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getEmail()));
    }

    @Override
    @Transactional
    public ResponseEntity<?> registerUser(SignupRequestDTO signUpRequestDTO) {
        if (userRepository.existsByEmail(signUpRequestDTO.getEmail())) {
            return ResponseEntity
                    .unprocessableEntity()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }
        List<String> roles = signUpRequestDTO.getRoleList();
        List<Role> roleList = new ArrayList<>();

        if (roles == null) {
            Role userRole = roleRepository.findByRole(Roles.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roleList.add(userRole);
        } else {
            roles.forEach(role -> {
                Role adminRole = roleRepository.findByRole(Roles.ROLE_ADMIN)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roleList.add(adminRole);
            });
        }
        User user = new User(roleList, signUpRequestDTO.getFirstName(), signUpRequestDTO.getLastName(),
                signUpRequestDTO.getEmail(), passwordEncoder.encode(signUpRequestDTO.getPassword()));
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
