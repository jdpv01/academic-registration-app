package com.perficient.academicregistrationapp.service;

import com.perficient.academicregistrationapp.enums.UserErrorCode;
import com.perficient.academicregistrationapp.persistance.model.User;
import com.perficient.academicregistrationapp.persistance.repository.UserRepository;
import com.perficient.academicregistrationapp.service.impl.UserServiceImpl;
import com.perficient.academicregistrationapp.web.error.exception.BusinessValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void resetMocks(){
        Mockito.reset(userRepository);
    }

    public User setupUser(){
        return User.builder()
                .id(UUID.randomUUID())
                .firstName("user")
                .lastName("user")
                .email("pelaezd86@gmail.com")
                .password("123456Aa").build();
    }

    @Test
    public void testLoadUserByUsername(){
        User expectedUser = setupUser();
        when(userRepository.findByEmail(expectedUser.getEmail())).thenReturn(Optional.of(expectedUser));
        UserDetails actualUser = userService.loadUserByUsername(expectedUser.getEmail());
        verify(userRepository, times(1)).findByEmail(expectedUser.getEmail());
        assertEquals(expectedUser.getEmail(), actualUser.getUsername());
    }

    @Test
    public void testLoadUserByUsernameNotFound(){
        User expectedUser = setupUser();
        when(userRepository.findByEmail(expectedUser.getEmail())).thenReturn(Optional.empty());
        try{
            userService.loadUserByUsername(expectedUser.getEmail());
            fail();
        }catch (UsernameNotFoundException e){
            assertEquals("User Not Found with email: " + expectedUser.getEmail(), e.getMessage());
        }
    }

    @Test
    public void testCreateUser(){
        User expectedUser = setupUser();
        when(userRepository.save(expectedUser)).thenReturn(expectedUser);
        User actualUser = userService.createUser(expectedUser);
        verify(userRepository, times(1)).save(expectedUser);
        assertEquals(expectedUser, actualUser);
    }

    @Test
    public void testGetUser(){
        User expectedUser = setupUser();
        when(userRepository.findById(expectedUser.getId())).thenReturn(Optional.of(expectedUser));
        User actualUser = userService.getUser(expectedUser.getId());
        verify(userRepository, times(1)).findById(expectedUser.getId());
        assertEquals(expectedUser, actualUser);
    }

    @Test
    public void testGetUserNotFound(){
        User expectedUser = setupUser();
        when(userRepository.findById(expectedUser.getId())).thenReturn(Optional.empty());
        try{
            userService.getUser(expectedUser.getId());
            fail();
        }catch (BusinessValidationException e){
            assertEquals(UserErrorCode.CODE_01.getMessage(), e.getBusinessErrorDetails().getMessage());
        }
    }

    @Test
    public void testGetUsers(){
        List<User> expectedUserList = new ArrayList<>();
        expectedUserList.add(setupUser());
        when(userRepository.findAll()).thenReturn(expectedUserList);
        List<User> actualUserList = userService.getUsers();
        verify(userRepository, times(1)).findAll();
        assertEquals(expectedUserList.size(), actualUserList.size());
    }

    @Test
    public void testUpdateUser(){
        User expectedUser = setupUser();
        expectedUser.setFirstName("User2");
        when(userRepository.findById(expectedUser.getId())).thenReturn(Optional.of(expectedUser));
        when(userRepository.save(expectedUser)).thenReturn(expectedUser);
        User actualUser = userService.updateUser(expectedUser);
        verify(userRepository, times(1)).findById(expectedUser.getId());
        verify(userRepository, times(1)).save(expectedUser);
        assertEquals(expectedUser, actualUser);
    }

    @Test
    public void testUpdateUserNotFound(){
        User expectedUser = setupUser();
        when(userRepository.findById(expectedUser.getId())).thenReturn(Optional.empty());
        try{
            userService.updateUser(expectedUser);
            fail();
        }catch (BusinessValidationException e){
            assertEquals(UserErrorCode.CODE_01.getMessage(), e.getBusinessErrorDetails().getMessage());
        }
    }

    @Test
    public void testDeleteUser(){
        User expectedUser = setupUser();
        when(userRepository.findById(expectedUser.getId())).thenReturn(Optional.of(expectedUser));
        Boolean deleted = userService.deleteUser(expectedUser.getId());
        verify(userRepository, times(1)).findById(expectedUser.getId());
        verify(userRepository, times(1)).delete(expectedUser);
        assertEquals(true, deleted);
    }

    @Test
    public void testDeleteUserNotFound(){
        User expectedUser = setupUser();
        when(userRepository.findById(expectedUser.getId())).thenReturn(Optional.empty());
        try{
            userService.deleteUser(expectedUser.getId());
            fail();
        }catch (BusinessValidationException e){
            assertEquals(UserErrorCode.CODE_01.getMessage(), e.getBusinessErrorDetails().getMessage());
        }
    }
}
