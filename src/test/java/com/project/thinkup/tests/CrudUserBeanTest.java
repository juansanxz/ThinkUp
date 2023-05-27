package com.project.thinkup.tests;

import com.project.thinkup.beans.CrudUserBean;
import com.project.thinkup.model.User;
import com.project.thinkup.repository.UserRepository;
import com.project.thinkup.service.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CrudUserBeanTest {
    
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    private CrudUserBean crudUserBean;
    private User user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        crudUserBean = new CrudUserBean();

        userRepository = mock(UserRepository.class);
        userService = new UserService(userRepository);

        user = new User("Juan", "Poveda", "juan.poveda@gmail.com", "123", "activo", "user", "estudiante");
        user.setUserId(1L);
        when(userService.addUser(user)).thenReturn(user);
        userService.addUser(user);

        crudUserBean.setSelectedUser(user);
    }

    @Test
    void shouldReturnSelectedUser() {
        assertEquals(user, crudUserBean.getSelectedUser());
    }
}
