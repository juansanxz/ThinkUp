package com.project.thinkup.tests;

import com.project.thinkup.model.Topic;
import com.project.thinkup.repository.TopicRepository;
import com.project.thinkup.service.TopicService;
import org.junit.jupiter.api.*;
import org.mockito.*;

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
    void shouldNonExistingTopic_ReturnsFalse() {
        String topicTitle = "Nonexistent Topic";
        when(topicRepository.existsByTitle(topicTitle)).thenReturn(false);
        boolean result = topicService.topicExist(topicTitle);

        assertFalse(result);
    }

}
