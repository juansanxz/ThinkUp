package com.project.thinkup.tests;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import com.project.thinkup.model.User;
import com.project.thinkup.repository.UserRepository;
import com.project.thinkup.service.UserService;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeAll
    public static void setup() {
        MockitoAnnotations.initMocks(UserServiceTest.class);
    }

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
}