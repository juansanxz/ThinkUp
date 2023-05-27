package com.project.thinkup.tests;

import com.project.thinkup.model.*;
import com.project.thinkup.service.KeyWordService;
import com.project.thinkup.service.ThinkUp;
import org.mockito.InjectMocks;
import org.mockito.Mock;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.MockitoAnnotations;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AppTest {

    @Mock
    private KeyWordService keyWordService;

    @InjectMocks
    private ThinkUp thinkUp;

    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void createNewUser() {
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
    void testIdea() {
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
    void shouldCreateTopic() {
        String title = "Test Topic";
        String description = "This is a test topic";

        Topic topic = new Topic(title, description);

        assertEquals(title, topic.getTitle());
        assertEquals(description, topic.getDescription());
        assertNotNull(topic.getCreationDate());
        assertEquals(LocalDate.now(), topic.getCreationDate());
        assertNotNull(topic.getIdeas());
        assertTrue(topic.getIdeas().isEmpty());
    }

    @Test
    void testSetAndGetUser() {
        User userMock = mock(User.class);
        Like like = new Like();

        like.setUser(userMock);
        assertEquals(userMock, like.getUser());
    }

    @Test
    void shouldReturnSetPassword() {
        User user = new User();
        String password = "password123";

        user.setPassword(password);
        String result = user.getPassword();
        assertEquals(password, result);
    }

    @Test
    void shouldSetNewPassword() {
        User user = new User();
        String newPassword = "newpassword";
        user.setPassword("oldpassword");

        user.setPassword(newPassword);
        String result = user.getPassword();
        assertEquals(newPassword, result);
    }

    @Test
    public void shouldReturnsWord() {
        String word = "ejemplo";
        KeyWord keyWord = new KeyWord(word);

        String result = keyWord.getWord();
        assertEquals(word, result);
    }

    @Test
    public void shouldSetsWord() {
        String word = "ejemplo";
        KeyWord keyWord = new KeyWord();
        keyWord.setWord(word);

        assertEquals(word, keyWord.getWord());
    }


}
