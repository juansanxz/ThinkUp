package com.project.thinkup.tests;

import com.project.thinkup.model.*;

import junit.framework.Assert;
import org.aspectj.lang.annotation.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

public class AppTest {

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
        assertEquals(title, topic.getTitle(), "The title should be set correctly");
        assertEquals(description, topic.getDescription(), "The description should be set correctly");
        assertNotNull(topic.getCreationDate(), "The creation date should not be null");
        assertEquals(LocalDate.now(), topic.getCreationDate(), "The creation date should be set to the current date");
        assertNotNull(topic.getIdeas(), "The ideas list should not be null");
        assertTrue(topic.getIdeas().isEmpty(), "The ideas list should be empty");
    }


}
