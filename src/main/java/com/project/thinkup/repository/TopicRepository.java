package com.project.thinkup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.thinkup.model.Idea;
import com.project.thinkup.model.Topic;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {
    boolean existsByTitle(String title);
    Topic findByTitle(String title);
    List<Idea> getIdeasByTopicId(Long topicId);
}
