package com.project.thinkup.tests;

import com.project.thinkup.model.Comment;
import com.project.thinkup.model.Idea;
import com.project.thinkup.model.KeyWord;
import com.project.thinkup.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CommentTest {

    private User user;
    private Idea idea;
    private List<KeyWord> keywords;
    

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        user = new User("John", "Doe", "john@example.com", "password", "activo", "user", "area");
        keywords = new ArrayList<>();
        idea = new Idea("Idea Prueba", "Esta idea se hace de prueba", keywords);
    }

    @Test
    void shouldCreateComment () {
        Comment commentToProve = new Comment(idea, user, "La idea de prueba es buena");
        assertEquals("La idea de prueba es buena", commentToProve.getDescription());
        assertEquals(idea, commentToProve.getIdea());
        assertEquals(user, commentToProve.getUser());
    }

    @Test
    void shouldPutCurrentDate () {
        Comment commentToProve = new Comment(idea, user, "La idea de prueba es buena");
        commentToProve.setCreationDate(LocalDate.now());
        assertEquals(LocalDate.now().toString(), commentToProve.getCreationDate().toString());
    }

    @Test
    void shouldChangeDescription () {
        Comment commentToProve = new Comment(idea, user, "La idea de prueba es buena");
        commentToProve.setDescription("Nueva descripción");
        assertEquals("Nueva descripción", commentToProve.getDescription());
    }
}
