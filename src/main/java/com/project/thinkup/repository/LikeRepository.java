package com.project.thinkup.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.thinkup.model.Idea;
import com.project.thinkup.model.Like;
import com.project.thinkup.model.User;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    Like findByIdeaAndUser (Idea idea, User user);
    List<Like> findByIdea (Idea idea);
}

