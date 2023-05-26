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
        assertEquals(Status.created, idea.getStatus());
        assertEquals(description, idea.getDescription());
        assertEquals(keywords, idea.getKeyWords());
        assertTrue(idea.getComments().isEmpty());
    }

    @Test
    public void testConstructor_InitializesProperties() {
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
    public void shouldPublishedIdea(){
        ThinkUp thinkUp = new ThinkUp();
        thinkUp.addStringKeyWord("K1");
        thinkUp.addStringKeyWord("K2");

        int inicial = thinkUp.getAmountOfIdeas();
        thinkUp.publishAnIdea("Titulo","Idea test");
        int fin = thinkUp.getAmountOfIdeas();
        boolean flag = inicial == fin;
        assertFalse(flag);
    }

    @Test
    public void testSetAndGetUser() {
        // Crear el mock de la clase User
        User userMock = mock(User.class);

        // Crear un objeto Like
        Like like = new Like();

        // Llamar al método setUser() con el mock de User
        like.setUser(userMock);

        // Verificar que el mock de User se haya configurado correctamente
        assertEquals(userMock, like.getUser());
    }


}
