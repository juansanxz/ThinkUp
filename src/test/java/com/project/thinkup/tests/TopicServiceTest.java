package com.project.thinkup.tests;

import com.project.thinkup.model.Topic;
import com.project.thinkup.repository.TopicRepository;
import com.project.thinkup.service.TopicService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TopicServiceTest {

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


}
