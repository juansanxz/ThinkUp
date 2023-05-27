package com.project.thinkup.tests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.project.thinkup.model.User;
import com.project.thinkup.repository.UserRepository;
import com.project.thinkup.service.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    public void beforeEach() {
        MockitoAnnotations.initMocks(this);
        user = new User("Juan", "Poveda", "juan.poveda@gmail.com", "123", "activo", "user", "estudiante");
        user.setUserId(1L);
        when(userRepository.save(user)).thenReturn(user);
    }

    @Test
    public void savedUserIsSuccessfullyCreated() {
        User newUser = userService.addUser(user);
        assertNotNull(newUser.getUserId());
    }

    @Test
    public void testDeleteUser() {
        Long userId = 1L;
        userService.deleteUser(userId);
    }

    @Test
    public void testDeleteAllUsers() {
        doNothing().when(userRepository).deleteAll();
        userService.deleteAllUsers();
        assertTrue(userService.getAllUsers().isEmpty());
    }

    @Test
    public void shouldReturnTrueWhenUserExists() {
        String mail = "juan.poveda@gmail.com";
        UserRepository userRepository = mock(UserRepository.class);
        UserService userService = new UserService(userRepository);
        when(userRepository.existsByMail(mail)).thenReturn(true);

        boolean result = userService.userExist(mail);
        assertTrue(result);
    }

    @Test
    public void shouldReturnUserWithEmail() {
        String mail = "juan.poveda@gmail.com";
        User expectedUser = new User();
        expectedUser.setMail(mail);

        UserRepository userRepository = mock(UserRepository.class);
        UserService userService = new UserService(userRepository);
        when(userRepository.findByMail(mail)).thenReturn(expectedUser);

        User resultUser = userService.getUserByEmail(mail);
        assertEquals(expectedUser, resultUser);
    }

    }