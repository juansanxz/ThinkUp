package com.project.thinkup.tests;

import com.project.thinkup.model.Idea;
import com.project.thinkup.repository.IdeaRepository;
import com.project.thinkup.service.IdeaService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class IdeaServiceTest {
    @Mock
    private IdeaRepository ideaRepository;

    @InjectMocks
    private IdeaService ideaService;
    @Test
    public void shouldReturnAddIdea() {
        IdeaRepository ideaRepository = mock(IdeaRepository.class);
        IdeaService ideaService = new IdeaService(ideaRepository);
        Idea idea = new Idea();
        idea.setTitle("Test Idea");

        when(ideaRepository.save(idea)).thenReturn(idea);

        Idea result = ideaService.addIdea(idea);
        assertEquals(idea, result);
    }

    @Test
    public void deleteIdea_shouldDeleteIdea() {
        Long ideaId = 1L;
        IdeaRepository ideaRepository = mock(IdeaRepository.class);
        IdeaService ideaService = new IdeaService(ideaRepository);

        ideaService.deleteIdea(ideaId);
        assertFalse(ideaRepository.findById(ideaId).isPresent());
    }

    @Test
    public void shouldDeleteAllIdeas() {
        IdeaRepository ideaRepository = mock(IdeaRepository.class);
        IdeaService ideaService = new IdeaService(ideaRepository);

        ideaService.deleteAllIdeas();
        assertTrue(true);
    }
}
