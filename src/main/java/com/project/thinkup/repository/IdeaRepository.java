package com.project.thinkup.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.thinkup.model.Idea;
import com.project.thinkup.model.User;

@Repository
public interface IdeaRepository extends JpaRepository<Idea, Long> {
    List<Idea> findByStatus(String status);
    Page<Idea> findByStatus(String status, Pageable pageable);
    Page<Idea> findByUser(User user, Pageable pageable);
    boolean existsByIdeaId(Long ideaId);
    List<Idea> findByUser(User user);

}
