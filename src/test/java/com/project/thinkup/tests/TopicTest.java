package com.project.thinkup.tests;

import com.project.thinkup.model.Idea;
import com.project.thinkup.model.KeyWord;
import com.project.thinkup.model.Status;
import com.project.thinkup.model.Topic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TopicTest {

    private Topic topic;
    private List<Idea> ideas;

    private Idea idea;

    @BeforeEach
    public void setUp() {
        topic = new Topic();
        ideas = new ArrayList<>();
    }

    @Test
    public void shouldRemovesIdeaAndSetsTopicToNull() {
        Idea idea = new Idea();
        ideas.add(idea);
        topic.setIdeas(ideas);

        Topic result = topic.removeIdea(idea);
        assertFalse(result.getIdeas().contains(idea));
    }


}

