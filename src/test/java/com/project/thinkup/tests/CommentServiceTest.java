package com.project.thinkup.tests;

import com.project.thinkup.model.Comment;
import com.project.thinkup.model.Idea;
import com.project.thinkup.model.KeyWord;
import com.project.thinkup.model.User;
import com.project.thinkup.repository.CommentRepository;
import com.project.thinkup.repository.IdeaRepository;
import com.project.thinkup.repository.UserRepository;
import com.project.thinkup.service.CommentService;
import com.project.thinkup.service.IdeaService;
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
class CommentServiceTest {
    @Mock
    private CommentRepository commentRepository;

    @Mock
    private IdeaRepository ideaRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CommentService commentService;

    @InjectMocks
    private IdeaService ideaService;

    @InjectMocks
    private UserService userService;

    private Comment comment;
    private Idea idea;
    private User user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        KeyWord keyWord1 = new KeyWord("ABC");

        commentRepository = mock(CommentRepository.class);
        commentService = new CommentService(commentRepository);

        List<KeyWord> keyWords1 = new ArrayList<KeyWord>();
        keyWords1.add(keyWord1);
        idea = new Idea("Titulo", "Proyecto de redes", keyWords1);
        idea.setIdeaId(2L);
        when(ideaService.addIdea(idea)).thenReturn(idea);
        
        user = new User("Juan", "Poveda", "juan.poveda@gmail.com", "123", "activo", "user", "estudiante");
        user.setUserId(1L);
        when(userRepository.save(user)).thenReturn(user);

        comment = new Comment(idea, user, "Comenteario de prueba");
        comment.setCommentId(3L);
        when(commentService.addComment(comment)).thenReturn(comment);
    }

    @Test
    void savedCommentIsSuccessfullyCreated() {
        Comment newComment = commentService.addComment(comment);
        assertNotNull(newComment.getCommentId());
    }

    @Test
    void shouldReturnAddComment() {
        when(commentService.addComment(comment)).thenReturn(comment);
        Comment result = commentService.addComment(comment);
        assertEquals(comment, result);
    }

    @Test
    void shouldDeleteAllComments() {
        commentService.deleteAllComments();;
        assertTrue(commentService.getAllComments().isEmpty());
    }

    @Test
    void shouldReturnAlloComments() {
        when(commentService.addComment(comment)).thenReturn(comment);
        Comment result = commentService.addComment(comment);
        List<Comment> comments = commentService.getAllComments();
        assertTrue(comments.isEmpty());
    }
}
