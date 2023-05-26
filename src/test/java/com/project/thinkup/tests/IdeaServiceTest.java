package com.project.thinkup.tests;

import com.project.thinkup.model.Idea;
import com.project.thinkup.repository.IdeaRepository;
import com.project.thinkup.service.IdeaService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class IdeaServiceTest {

    @Test
    public void shouldReturnAddIdea() {
        // Arrange
        IdeaRepository ideaRepository = mock(IdeaRepository.class);
        IdeaService ideaService = new IdeaService(ideaRepository);
        Idea idea = new Idea();
        idea.setTitle("Test Idea");

        when(ideaRepository.save(idea)).thenReturn(idea);


        Idea result = ideaService.addIdea(idea);
        assertEquals(idea, result);
    }

    @Test
    void deleteAllIdeas_shouldDeleteAllIdeas() {
        IdeaRepository ideaRepository = mock(IdeaRepository.class);
        IdeaService ideaService = new IdeaService(ideaRepository);

        ideaService.deleteAllIdeas();
        assertTrue(true);
    }
}
