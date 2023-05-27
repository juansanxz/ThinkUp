package com.project.thinkup.tests;

import com.project.thinkup.model.Idea;
import com.project.thinkup.model.KeyWord;
import com.project.thinkup.model.Topic;
import com.project.thinkup.repository.TopicRepository;
import com.project.thinkup.service.TopicService;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TopicServiceTest {

    @Mock
    private TopicRepository topicRepository;

    @InjectMocks
    private TopicService topicService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void shouldReturnAddedTopic() {
        String title = "Test Topic";
        Topic topic = new Topic();
        topic.setTitle(title);
        TopicRepository topicRepository = mock(TopicRepository.class);
        TopicService topicService = new TopicService(topicRepository);
        when(topicRepository.save(topic)).thenReturn(topic);

        Topic resultTopic = topicService.addTopic(topic);
        assertEquals(topic, resultTopic);
    }

    @Test
    void testGetTopic() {
        Long topicId = 1L;
        Topic topic = new Topic("Technology", "Topic description");
        topic.setTopicId(topicId);

        when(topicRepository.findById(topicId)).thenReturn(Optional.of(topic));

        Topic retrievedTopic = topicService.getTopic(topicId);
        assertNotNull(retrievedTopic);
        assertEquals(topic, retrievedTopic);
        verify(topicRepository, times(1)).findById(topicId);
    }

    @Test
    void shouldGetTopicByTitle() {
        String title = "Test Topic";
        Topic topic = new Topic();
        topic.setTitle(title);
        TopicRepository topicRepository = mock(TopicRepository.class);
        when(topicRepository.findByTitle(title)).thenReturn(topic);

        TopicService topicService = new TopicService(topicRepository);
        Topic result = topicService.getTopicByTitle(title);

        assertEquals(topic, result);
    }

    @Test
    void shouldDeletesTopic() {
        Long topicId = 1L;
        doNothing().when(topicRepository).deleteById(topicId);

        topicService.deleteTopic(topicId);
        assertEquals(0, topicRepository.count());
    }

    @Test
    void shouldDeletesAllTopics() {
        doNothing().when(topicRepository).deleteAll();

        topicService.deleteAllTopics();
        assertEquals(0, topicRepository.count());
    }

    @Test
    void shouldExistingTopicReturnsTrue() {
        String topicTitle = "Test Topic";
        when(topicRepository.existsByTitle(topicTitle)).thenReturn(true);
        boolean result = topicService.topicExist(topicTitle);

        assertTrue(result);
    }

    @Test
    void shouldNonExistingTopicReturnsFalse() {
        String topicTitle = "Nonexistent Topic";
        when(topicRepository.existsByTitle(topicTitle)).thenReturn(false);
        boolean result = topicService.topicExist(topicTitle);

        assertFalse(result);
    }

    @Test
    void testGetIdeasByTopicId() {
        Long topicId = 1L;
        KeyWord keyWord1 = new KeyWord("ABC");
        List<KeyWord> keyWords1 = new ArrayList<KeyWord>();
        keyWords1.add(keyWord1);
        KeyWord keyWord2 = new KeyWord("XYZ");
        List<KeyWord> keyWords2 = new ArrayList<KeyWord>();
        keyWords2.add(keyWord2);
        List<Idea> ideas = new ArrayList<>();
        ideas.add(new Idea("Idea 1", "Proyecto de redes", keyWords1));
        ideas.add(new Idea("Idea 2", "Proyecto de aupn", keyWords2));

        Topic topic = new Topic();
        topic.setTopicId(topicId);
        topic.setIdeas(ideas);

        when(topicRepository.findById(topicId)).thenReturn(Optional.of(topic));

        List<Idea> result = topicService.getIdeasByTopicId(topicId);
        assertEquals(ideas, result);
    }

    @Test
    void testGetIdeasByTopicIdTopicDoesNotExist() {
        Long topicId = 1L;
        when(topicRepository.findById(topicId)).thenReturn(Optional.empty());
        List<Idea> result = topicService.getIdeasByTopicId(topicId);
        assertEquals(new ArrayList<>(), result);
    }

    @Test
    void testUpdateTopic() {
        Long topicId = 1L;
        String newTitle = "Updated Title";

        Topic existingTopic = new Topic();
        existingTopic.setTopicId(topicId);
        existingTopic.setTitle("Original Title");

        Topic updatedTopic = new Topic();
        updatedTopic.setTopicId(topicId);
        updatedTopic.setTitle(newTitle);

        when(topicRepository.existsById(topicId)).thenReturn(true);
        when(topicRepository.save(existingTopic)).thenReturn(updatedTopic);
        Topic result = topicService.updateTopic(existingTopic);
        assertNotNull(result);

    }
}
