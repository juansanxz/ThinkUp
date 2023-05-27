package com.project.thinkup.tests;

import com.project.thinkup.model.Comment;
import com.project.thinkup.model.Idea;
import com.project.thinkup.model.KeyWord;
import com.project.thinkup.model.Like;
import com.project.thinkup.model.User;

import javassist.compiler.ast.Keyword;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class KeyWordTest {
    
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

}
