package com.project.thinkup.tests;

import com.project.thinkup.model.KeyWord;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class KeyWordTest {
    
    private KeyWord keyword;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        keyword = new KeyWord("Redes");
    }

    @Test
    void shouldSetId () {
        keyword.setKeyWordId(1L);
        assertEquals(1L, keyword.getKeyWordId());
    }

    @Test
    void shouldChangeWord () {
        assertEquals("Redes", keyword.getWord());
        keyword.setWord("Ciclos");
        assertEquals("Ciclos", keyword.getWord());
    }

    @Test
    void shouldToString () {
        assertEquals("KeyWord [keyWordId=null, word=Redes]", keyword.toString());
    }

    @Test
    void shouldEqualsToKey () {
        KeyWord anotherKeyword = new KeyWord("Redes");
        assertEquals(keyword, anotherKeyword);
    }

    @Test
    void shouldNotEqualsTwoKey () {
        KeyWord anotherKeyword = new KeyWord("Red");
        assertNotEquals(keyword, anotherKeyword);
    }

    @Test
    void shouldNotEqualsNull () {
        assertNotEquals(null, keyword);
    }
}
