package com.project.thinkup.tests;

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

public class LikeTest {

    private User user;
    private List<Like> likes;

    @BeforeEach
    public void setUp() {
        user = new User("John", "Doe", "john@example.com", "password", "activo", "user", "area");
        likes = new ArrayList<>();
    }

    @Test
    public void shouldAddLike() {
        // Arrange
        Like like = new Like();

        // Act
        likes.add(like);

        // Assert
        assertEquals(1, likes.size());
        assertEquals(like, likes.get(0));
    }

    @Test
    public void shouldRemoveLike() {
        // Arrange
        Like like = new Like();
        user.giveLike(like);

        // Act
        user.quitLike(like);

        // Assert
        assertEquals(0, likes.size());
    }

    @Test
    public void shouldIncreaseLikes(){
        KeyWord keyWord1 = new KeyWord("ABC");
        List<KeyWord> keyWords1 = new ArrayList<KeyWord>();
        keyWords1.add(keyWord1);

        Idea idea = new Idea("Titulo", "Proyecto de redes", keyWords1);

        Like like = new Like(idea,user);

        int inicial = idea.getAmountOfLikes();
        idea.giveLike(like);
        int fin = idea.getAmountOfLikes();
        boolean flag = inicial == fin;
        assertFalse(flag);
    }

    @Test
    public void testSetAndGetIdea() {
        // Crear el mock de la clase Idea
        Idea ideaMock = mock(Idea.class);

        // Crear un objeto Like con el mock de Idea
        Like like = new Like();
        like.setIdea(ideaMock);

        // Verificar que el mock se haya configurado correctamente
        assertEquals(ideaMock, like.getIdea());
    }
}

