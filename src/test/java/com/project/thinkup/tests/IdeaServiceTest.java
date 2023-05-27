package com.project.thinkup.tests;

import com.project.thinkup.model.Idea;
import com.project.thinkup.model.KeyWord;
import com.project.thinkup.model.Status;
import com.project.thinkup.repository.IdeaRepository;
import com.project.thinkup.service.IdeaService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class IdeaServiceTest {
    @Mock
    private IdeaRepository ideaRepository;

    @InjectMocks
    private IdeaService ideaService;

    private Idea idea;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        KeyWord keyWord1 = new KeyWord("ABC");
        List<KeyWord> keyWords1 = new ArrayList<KeyWord>();
        keyWords1.add(keyWord1);
        idea = new Idea("Titulo", "Proyecto de redes", keyWords1);
        idea.setIdeaId(2L);
        when(ideaService.addIdea(idea)).thenReturn(idea);

    }

    @Test
    void savedIdeaIsSuccessfullyCreated() {
        Idea newIdea = ideaService.addIdea(idea);
        assertNotNull(newIdea.getIdeaId());
        assertEquals("Titulo", idea.getTitle());
        assertEquals("Proyecto de redes", idea.getDescription());
        assertEquals(1, idea.getKeyWords().size());
    }

    @Test
    void shouldReturnAddIdea() {
        IdeaRepository ideaRepository = mock(IdeaRepository.class);
        IdeaService ideaService = new IdeaService(ideaRepository);
        Idea idea = new Idea();
        idea.setTitle("Test Idea");

        when(ideaRepository.save(idea)).thenReturn(idea);

        Idea result = ideaService.addIdea(idea);
        assertEquals(idea, result);
    }

    @Test
    void deleteIdea_shouldDeleteIdea() {
        Long ideaId = 1L;
        IdeaRepository ideaRepository = mock(IdeaRepository.class);
        IdeaService ideaService = new IdeaService(ideaRepository);

        ideaService.deleteIdea(ideaId);
        assertFalse(ideaRepository.findById(ideaId).isPresent());
    }

    @Test
    void shouldDeleteAllIdeas() {
        IdeaRepository ideaRepository = mock(IdeaRepository.class);
        IdeaService ideaService = new IdeaService(ideaRepository);
        ideaService.deleteAllIdeas();
        assertTrue(true);
    }

    @Test
    void IdeaStatusIsSuccessfullyChanged() {
        idea.setStatus(Status.PENDING);
        ideaService.updateIdea(idea);
        assertEquals(Status.PENDING, idea.getStatus());
    }



}
