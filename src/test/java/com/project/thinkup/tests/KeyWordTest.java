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

    @Test
    void shouldEqualsToKey () {
        KeyWord anotherKeyword = new KeyWord("Redes");
        assertTrue(keyword.equals(anotherKeyword));
    }

    @Test
    void shouldNotEqualsTwoKey () {
        KeyWord anotherKeyword = new KeyWord("Red");
        assertFalse(keyword.equals(anotherKeyword));
    }

    @Test
    void shouldNotEqualsNull () {
        assertFalse(keyword.equals(null));
    }

    @Test
    public void testEqualsSameObjectReturnsTrue() {
        KeyWord keyWord = new KeyWord("example");
        boolean result = keyWord.equals(keyWord);
        assertTrue(result);
    }

    @Test
    public void testEquals_NullObject_ReturnsFalse() {
        KeyWord keyWord = new KeyWord("example");
        boolean result = keyWord.equals(null);
        assertFalse(result);
    }

    @Test
    public void testEquals_DifferentClass_ReturnsFalse() {
        KeyWord keyWord = new KeyWord("example");
        Object obj = new Object();
        boolean result = keyWord.equals(obj);
        assertFalse(result);
    }

    @Test
    void testEqualsEqualObjectsReturnsTrue() {
        KeyWord keyWord1 = new KeyWord("example");
        keyWord1.setKeyWordId(1L);
        KeyWord keyWord2 = new KeyWord("example");
        keyWord2.setKeyWordId(1L);

        boolean result = keyWord1.equals(keyWord2);
        assertTrue(result);
    }

    @Test
    void testEqualsDifferentKeyWordIdReturnsFalse() {
        KeyWord keyWord1 = new KeyWord("example");
        keyWord1.setKeyWordId(1L);
        KeyWord keyWord2 = new KeyWord("example");
        keyWord2.setKeyWordId(2L);

        boolean result = keyWord1.equals(keyWord2);
        assertFalse(result);
    }

    @Test
    void testEqualsDifferentWordReturnsFalse() {
        KeyWord keyWord1 = new KeyWord("example");
        keyWord1.setKeyWordId(1L);
        KeyWord keyWord2 = new KeyWord("different");
        keyWord2.setKeyWordId(1L);

        boolean result = keyWord1.equals(keyWord2);
        assertFalse(result);
    }

}
