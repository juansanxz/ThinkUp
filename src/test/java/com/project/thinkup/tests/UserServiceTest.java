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
class UserServiceTest {

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
    void savedUserIsSuccessfullyCreated() {
        User newUser = userService.addUser(user);
        assertNotNull(newUser.getUserId());
    }

    @Test
    void testDeleteUser() {
        Long userId = 1L;
        userService.deleteUser(userId);
        assertTrue(userService.getAllUsers().isEmpty());
    }

    @Test
    void testDeleteAllUsers() {
        doNothing().when(userRepository).deleteAll();
        userService.deleteAllUsers();
        assertTrue(userService.getAllUsers().isEmpty());
    }

    @Test
    void shouldReturnTrueWhenUserExists() {
        String mail = "juan.poveda@gmail.com";
        UserRepository userRepository = mock(UserRepository.class);
        UserService userService = new UserService(userRepository);
        when(userRepository.existsByMail(mail)).thenReturn(true);

        boolean result = userService.userExist(mail);
        assertTrue(result);
    }

    @Test
    void shouldReturnUserWithEmail() {
        String mail = "juan.poveda@gmail.com";
        User expectedUser = new User();
        expectedUser.setMail(mail);

        UserRepository userRepository = mock(UserRepository.class);
        UserService userService = new UserService(userRepository);
        when(userRepository.findByMail(mail)).thenReturn(expectedUser);

        User resultUser = userService.getUserByEmail(mail);
        assertEquals(expectedUser, resultUser);
    }

    
    @Test
    void UserNameIsSuccessfullyChanged() {
        String newName = "Hugo";
        user.setFirstName(newName);
        userService.updateUser(user);
        assertEquals(newName, user.getFirstName());
    }


    @Test
    void UserEmailIsSuccessfullyChanged() {
        String newEmail= "hugo@mail.com";
        when(userRepository.existsById(1L)).thenReturn(true);
        user.setMail(newEmail);
        userService.updateUser(user);
        assertEquals(newEmail, user.getMail());
    }

}