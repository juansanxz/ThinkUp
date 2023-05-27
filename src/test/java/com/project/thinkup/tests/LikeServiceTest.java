package com.project.thinkup.tests;

import com.project.thinkup.model.Idea;
import com.project.thinkup.model.KeyWord;
import com.project.thinkup.model.Like;
import com.project.thinkup.model.User;
import com.project.thinkup.repository.IdeaRepository;
import com.project.thinkup.repository.LikeRepository;
import com.project.thinkup.repository.UserRepository;
import com.project.thinkup.service.IdeaService;
import com.project.thinkup.service.LikeService;
import com.project.thinkup.service.UserService;

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
class LikeServiceTest {
    @Mock
    private LikeRepository likeRepository;

    @Mock
    private IdeaRepository ideaRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private IdeaService ideaService;

    @InjectMocks
    private UserService userService;

    @InjectMocks
    private LikeService likeService;

    private Idea idea;
    private User user;
    private Like like;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        KeyWord keyWord1 = new KeyWord("ABC");

        likeRepository = mock(LikeRepository.class);
        likeService = new LikeService(likeRepository);

        ideaRepository = mock(IdeaRepository.class);
        ideaService = new IdeaService(ideaRepository);

        userRepository = mock(UserRepository.class);
        userService = new UserService(userRepository);

        List<KeyWord> keyWords1 = new ArrayList<KeyWord>();
        keyWords1.add(keyWord1);
        idea = new Idea("Titulo", "Proyecto de redes", keyWords1);
        idea.setIdeaId(2L);
        when(ideaService.addIdea(idea)).thenReturn(idea);
        
        user = new User("Juan", "Poveda", "juan.poveda@gmail.com", "123", "activo", "user", "estudiante");
        user.setUserId(1L);
        when(userRepository.save(user)).thenReturn(user);

        like = new Like(idea, user);
        like.setLikeId(3L);
        when(likeService.addLike(like)).thenReturn(like);
    }

    @Test
    void savedLikeIsSuccessfullyCreated() {
        Like newLike = likeService.addLike(like);
        assertNotNull(newLike.getLikeId());
    }

    @Test
    void shouldReturnAddComment() {
        Like result = likeService.addLike(like);
        assertEquals(like, result);
    }

    @Test
    void shouldDeleteAllLikes() {
        likeService.deleteAllLikes();
        assertTrue(likeService.getLikeByIdea(idea).isEmpty());
    }

    @Test
    void shouldDeleteAllLikesByIdeaUser() {
        likeService.deleteAllLikes();
        assertTrue(likeService.getLikeByIdeaUser(idea, user) == null);
    }

    @Test
    void shouldDeleteLike() {
        likeService.deleteLike(like.getLikeId());
        assertTrue(likeService.getLikeByIdea(idea).isEmpty());
    }
}
