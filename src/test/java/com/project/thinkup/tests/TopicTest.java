package com.project.thinkup.tests;

import com.project.thinkup.model.Idea;
import com.project.thinkup.model.KeyWord;

import com.project.thinkup.model.Topic;
import javassist.bytecode.AccessFlag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import com.project.thinkup.repository.TopicRepository;
import com.project.thinkup.service.TopicService;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TopicTest {

    @Mock
    private TopicRepository topicRepository;

    @InjectMocks
    private TopicService topicService;

    private Topic topic;
    private List<Idea> ideas;

    private Idea idea;
    private LocalDate localDate;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        KeyWord keyWord1 = new KeyWord("ABC");
        List<KeyWord> keyWords1 = new ArrayList<KeyWord>();
        keyWords1.add(keyWord1);

        topicService.deleteAllTopics();
        topic = new Topic("Iniciativas Estudiantes", "Tema para agrupar las iniciativas de los estudiantes");
        idea = new Idea("Titulo", "Proyecto de redes", keyWords1);
        topic.setTopicId(100L);
        ideas = new ArrayList<>();
        when(topicService.addTopic(topic)).thenReturn(topic);

    }

    @Test
    void shouldRemovesIdeaAndSetsTopicToNull() {
        Idea idea = new Idea();
        ideas.add(idea);
        topic.setIdeas(ideas);
        Topic result = topic.removeIdea(idea);
        assertFalse(result.getIdeas().contains(idea));
    }

    @Test
    void savedTopicIsSuccessfullyCreated() {
        Topic newTopic = topicService.addTopic(topic);
        assertNotNull(newTopic.getTopicId());
        assertEquals("Iniciativas Estudiantes", topic.getTitle());
        assertEquals("Tema para agrupar las iniciativas de los estudiantes", topic.getDescription());
        assertEquals(0, topic.getIdeas().size());
    }

    @Test
    void TopicTitleIsSuccessfullyChanged() {
        topic.setTitle("Nuevo Titulo");
        topicService.updateTopic(topic);
        assertEquals("Nuevo Titulo", topic.getTitle());
    }

    @Test
    void TopicDescriptionIsSuccessfullyChanged() {
        topic.setDescription("Nueva Descripción");
        topicService.updateTopic(topic);
        assertEquals("Nueva Descripción", topic.getDescription());
    }

    @Test
    void ShouldAddIdeasToTheTopic() {
        int originalideas = topic.getIdeas().size();
        topic.addIdea(idea);
        topicService.updateTopic(topic);
        assertEquals(originalideas + 1, topic.getIdeas().size());
    }

    @Test
    void ShouldRemoveIdeasToTheTopic() {
        int originalideas = topic.getIdeas().size();
        topic.addIdea(idea);
        topicService.updateTopic(topic);
        assertEquals(originalideas + 1, topic.getIdeas().size());
        originalideas = topic.getIdeas().size();
        topic.removeIdea(idea);
        topicService.updateTopic(topic);
        assertEquals(originalideas - 1, topic.getIdeas().size());
    }

    @Test
    void testGetTitle() {
        String title = "Topic 1";
        topic.setTitle(title);
        String retrievedTitle = topic.getTitle();
        assertEquals(title, retrievedTitle);
    }

    @Test
    void testSetTitle() {
        String title = "Topic 1";
        topic.setTitle(title);
        assertEquals(title, topic.getTitle());
    }

    @Test
    void testGetTittle() {
        String title = "Topic 1";
        topic.setTitle(title);
        String retrievedTittle = topic.getTitle();
        assertEquals(title, retrievedTittle);
    }

    @Test
    void testSetTittle() {
        String title = "Topic 1";
        topic.setTitle(title);
        assertEquals(title, topic.getTitle());
    }

    @Test
    void testGetCreationDate() {
        LocalDate creationDate = LocalDate.now();
        topic.setCreationDate(creationDate);
        LocalDate retrievedCreationDate = topic.getCreationDate();
        assertEquals(creationDate, retrievedCreationDate);
    }

    @Test
    void testSetCreationDate() {
        LocalDate creationDate = LocalDate.of(2022, 5, 15);
        topic.setCreationDate(creationDate);
        assertEquals(creationDate, topic.getCreationDate());
    }
}