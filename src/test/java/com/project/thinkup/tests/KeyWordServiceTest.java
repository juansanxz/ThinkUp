package com.project.thinkup.tests;

import com.project.thinkup.model.KeyWord;
import com.project.thinkup.repository.KeyWordRepository;
import com.project.thinkup.service.KeyWordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class KeyWordServiceTest {
    @Mock
    private KeyWordRepository keyWordRepository;

    @InjectMocks
    private KeyWordService keyWordService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        keyWordService = new KeyWordService(keyWordRepository);
    }

    @Test
    public void shouldAddNewWordToRepository() {
        String word = "Keyword 1";
        KeyWord keyWord = new KeyWord();
        keyWord.setWord(word);
        when(keyWordRepository.save(eq(keyWord))).thenReturn(keyWord);

        KeyWord result = keyWordService.addKeyWord(keyWord);

        assertEquals(keyWord, result);
    }

    @Test
    public void shouldNotAddExistingWordToRepository() {
        String word = "Keyword 1";
        KeyWord keyWord = new KeyWord();
        keyWord.setWord(word);
        when(keyWordRepository.existsByWord(eq(word))).thenReturn(true);

        KeyWord result = keyWordService.addKeyWord(keyWord);
        assertNull(result);
    }

    @Test
    public void deleteKeyWord() {
        String word = "Keyword 1";
        keyWordService.deleteKeyWord(word);
        assertNull(keyWordService.getKeyWord(word));
    }

    @Test
    public void deleteAllKeyWords() {
        keyWordService.deleteAllKeyWords();
        assertTrue(keyWordService.getAllKeyWords().isEmpty());
    }
}


