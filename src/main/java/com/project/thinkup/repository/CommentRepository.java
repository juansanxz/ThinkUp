package com.project.thinkup.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.project.thinkup.model.Comment;


@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    
}
