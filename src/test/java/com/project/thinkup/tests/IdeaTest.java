package com.project.thinkup.tests;

import com.project.thinkup.model.Comment;
import com.project.thinkup.model.Idea;
import com.project.thinkup.model.KeyWord;
import com.project.thinkup.model.Like;
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
class IdeaTest {

    private User user;
    private Idea idea;
    private List<KeyWord> keywords;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        user = new User("John", "Doe", "john@example.com", "password", "activo", "user", "area");
        keywords = new ArrayList<>();
        keywords.add(new KeyWord("Redes"));
        keywords.add(new KeyWord("Ciclos"));
        idea = new Idea("Idea Prueba", "Esta idea se hace de prueba", keywords);
    }

    @Test
    void shouldgetIdeaId () {
        idea.setIdeaId(1L);
        assertEquals(1L, idea.getIdeaId());
    }

    @Test
    void shouldCreateInCreatedStatus() {
        assertEquals("Creada", idea.getStatus());
    }

    @Test
    void shouldSetCurrentDate() {
        assertEquals(LocalDate.now(), idea.getCreationDate());
    }

    @Test
    void shouldPrintCorrectly () {
        assertEquals("Redes, Ciclos", idea.getStringKeyWords());
    }

    @Test
    void shouldAddNewComment () {
        Comment comment = new Comment(idea, user, "Comentario de la idea");
        assertEquals(0, idea.getComments().size());
        idea.addComment(comment);
        assertEquals(1, idea.getComments().size());
    }

    @Test
    void shouldGiveLike () {
        Like like = new Like(idea, user);
        assertEquals(0, idea.getAmountOfLikes());
        idea.giveLike(like);
        assertEquals(1, idea.getAmountOfLikes());
    }

    @Test
    void shouldQuitLike () {
        Like like = new Like(idea, user);
        assertEquals(0, idea.getAmountOfLikes());
        idea.giveLike(like);
        assertEquals(1, idea.getAmountOfLikes());
        idea.quitLike(like);
        assertEquals(0, idea.getAmountOfLikes());
    }
}
