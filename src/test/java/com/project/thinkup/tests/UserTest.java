package com.project.thinkup.tests;

import com.project.thinkup.model.Idea;
import com.project.thinkup.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserTest {

    private User user;

    @BeforeEach
    public void setUp() {
        user = new User("John", "Doe", "john.doe@example.com", "password", "active", "user", "profesor");
    }

    @Test
    void testIsAdmin() {
        boolean expectedIsAdmin = false;
        boolean actualIsAdmin = user.isAdmin();
        assertEquals(expectedIsAdmin, actualIsAdmin);
    }

    @Test
    void testAddIdea() {
        Idea idea = new Idea();
        List<Idea> ideas = new ArrayList<>();
        ideas.add(idea);

        user.addIdea(idea);
        Assertions.assertEquals(ideas, user.getIdeas());
    }

    @Test
    void testGetStatus() {
        String status = user.getStatus();
        assertEquals("active", status);
    }

    @Test
    void testSetStatus() {
        user.setStatus("inactive");
        String status = user.getStatus();
        assertEquals("inactive", status);
    }

    @Test
    void testGetRole() {
        String role = user.getRole();
        assertEquals("user", role);
    }

    @Test
    void testSetRole() {
        user.setRole("admin");
        String role = user.getRole();
        assertEquals("admin", role);
    }

    @Test
    void testGetArea() {
        String area = "profesor";
        String areaNew = user.getArea();
        assertEquals(area, areaNew);
    }

    @Test
    void testSetArea() {
        String newArea = "profesor";
        user.setArea(newArea);
        assertEquals(newArea, user.getArea());
    }

}

