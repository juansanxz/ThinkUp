package com.project.thinkup.tests;

import com.project.thinkup.beans.LoginBean;
import com.project.thinkup.model.User;
import com.project.thinkup.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class LoginBeanTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private LoginBean loginBean;

    private User user;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        user = new User("Santiago", "Arevalo", "santiago.arevalo@gmail.com", "123", "activo", "user",
                "estudiante");
        user.setUserId(10L);
        when(userService.getUserByEmail("santiago.arevalo@gmail.com")).thenReturn(user);
    }

    @Test
    void checkIfLoginIsSuccess() {
        String username = "santiago.arevalo@gmail.com";
        String password = "123";
        boolean loginResult = loginBean.login(username,password);
        assertTrue(loginResult);
    }

    @Test
    void checkIfLoginIsFailed() {
        String username = "invalid.email@gmail.com";
        String password = "123";
        boolean loginResult = loginBean.login(username,password);
        assertFalse(loginResult);
    }

}