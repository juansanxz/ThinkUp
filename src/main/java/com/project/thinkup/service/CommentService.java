package com.project.thinkup.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.thinkup.model.Comment;
import com.project.thinkup.model.Idea;
import com.project.thinkup.repository.CommentRepository;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }
    
    public Comment addComment(Comment comment) {
        return commentRepository.save(comment);
    }

    public void deleteAllComments() {
        commentRepository.deleteAll();
    }

    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    public List<Comment> getCommentByIdeaId(Idea idea){
        return commentRepository.findByIdea(idea);
    }
}
