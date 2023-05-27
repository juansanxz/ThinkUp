package com.project.thinkup.tests;

import com.project.thinkup.model.Comment;
import com.project.thinkup.model.Idea;
import com.project.thinkup.model.KeyWord;
import com.project.thinkup.model.User;
import com.project.thinkup.model.Like;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class CommentTest {

    private User user;
    private Idea idea;
    private List<KeyWord> keywords;
    

    @BeforeEach
    public void setUp() {
        user = new User("John", "Doe", "john@example.com", "password", "activo", "user", "area");
        keywords = new ArrayList<>();
        idea = new Idea("Idea Prueba", "Esta idea se hace de prueba", keywords);
    }

    @Test
    void shouldCreateComment () {
        Comment commentToProve = new Comment(idea, user, "La idea de preuba es buena");
        assertEquals("La idea de preuba es buena", commentToProve.getDescription());
        assertEquals(idea, commentToProve.getIdea());
        assertEquals(user, commentToProve.getUser());
    }
}
