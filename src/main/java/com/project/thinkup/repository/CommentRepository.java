package com.project.thinkup.repository;

import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.thinkup.model.Comment;
import com.project.thinkup.model.Idea;


@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByIdea(Idea idea);
}
