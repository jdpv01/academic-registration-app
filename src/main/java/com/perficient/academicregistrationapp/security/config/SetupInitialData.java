package com.perficient.academicregistrationapp.security.config;

import com.perficient.academicregistrationapp.enums.Roles;
import com.perficient.academicregistrationapp.persistance.model.Role;
import com.perficient.academicregistrationapp.persistance.model.User;
import com.perficient.academicregistrationapp.persistance.repository.RoleRepository;
import com.perficient.academicregistrationapp.persistance.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SetupInitialData implements ApplicationListener<ContextRefreshedEvent> {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        List<Role> adminRoleList = new ArrayList<>();
        Role adminRole = roleRepository.findByRole(Roles.ROLE_ADMIN)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        adminRoleList.add(adminRole);
        User admin = new User(adminRoleList, "Admin", "Admin", "juandpv01@hotmail.com", passwordEncoder.encode("123456Aa"));
        if(!userRepository.existsByEmail(admin.getEmail()))
            userRepository.save(admin);
        else
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

        List<Role> userRoleList = new ArrayList<>();
        Role userRole = roleRepository.findByRole(Roles.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        userRoleList.add(userRole);
        User user = new User(userRoleList, "User", "User", "pelaezd86@gmail.com", passwordEncoder.encode("123456Aa"));
        if(!userRepository.existsByEmail(user.getEmail()))
            userRepository.save(user);
        else
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
    }
}
