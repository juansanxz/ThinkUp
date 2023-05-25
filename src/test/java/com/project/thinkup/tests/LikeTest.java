package com.project.thinkup.tests;

import com.project.thinkup.model.User;
import com.project.thinkup.model.Like;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
}

