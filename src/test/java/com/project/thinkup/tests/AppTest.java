package com.project.thinkup.tests;

import com.project.thinkup.model.*;

import com.project.thinkup.repository.IdeaRepository;
import com.project.thinkup.service.IdeaService;
import com.project.thinkup.service.ThinkUp;
import com.project.thinkup.service.UserService;
import junit.framework.Assert;
import org.aspectj.lang.annotation.Before;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AppTest {

    private ThinkUp thinkUp;

    @Mock
    private IdeaRepository ideaRepository;

    @InjectMocks
    private IdeaService ideaService;

    @Test
    public void createNewUser() {
        // Arrange
        String firstName = "andres";
        String lastName = "oñate";
        String mail = "andrescamiloquimbayo@gmail.com";
        String password = "123";
        String status = "activo";
        String role = "user";
        String area = "estudiante";

        // Act
        User user = new User(firstName, lastName, mail, password, status, role, area);

        // Assert
        assertEquals(firstName, user.getFirstName());
        assertEquals(lastName, user.getLastName());
        assertEquals(mail, user.getMail());
        assertEquals(password, user.getPassword());
        assertEquals(status, user.getStatus());
        assertEquals(role, user.getRole());
        assertEquals(area, user.getArea());
    }

    @Test
    public void testIdea() {
        String title = "Test Idea";
        String description = "Test Description";
        List<KeyWord> keywords = new ArrayList<>();
        keywords.add(new KeyWord("Test Keyword 1"));
        keywords.add(new KeyWord("Test Keyword 2"));

        Idea idea = new Idea(title, description, keywords);

        assertEquals(title, idea.getTitle());
        assertEquals(LocalDate.now(), idea.getCreationDate());
        assertEquals(Status.CREATED, idea.getStatus());
        assertEquals(description, idea.getDescription());
        assertEquals(keywords, idea.getKeyWords());
        assertTrue(idea.getComments().isEmpty());
    }

    @Test
    public void testConstructor() {
        // Arrange
        String title = "Test Topic";
        String description = "This is a test topic";

        // Act
        Topic topic = new Topic(title, description);

        // Assert
        assertEquals(title, topic.getTitle());
        assertEquals(description, topic.getDescription());
        assertNotNull(topic.getCreationDate());
        assertEquals(LocalDate.now(), topic.getCreationDate());
        assertNotNull(topic.getIdeas());
        assertTrue(topic.getIdeas().isEmpty());
    }

    @Test
    public void testSetAndGetUser() {
        User userMock = mock(User.class);
        Like like = new Like();

        like.setUser(userMock);
        assertEquals(userMock, like.getUser());
    }

    @Test
    public void shouldReturnSetPassword() {
        User user = new User();
        String password = "password123";

        user.setPassword(password);
        String result = user.getPassword();
        assertEquals(password, result);
    }

    @Test
    public void shouldSetNewPassword() {
        User user = new User();
        String newPassword = "newpassword";
        user.setPassword("oldpassword");

        user.setPassword(newPassword);
        String result = user.getPassword();
        assertEquals(newPassword, result);
    }


}
