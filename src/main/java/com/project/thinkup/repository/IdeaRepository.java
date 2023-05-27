package com.project.thinkup.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.thinkup.model.Idea;
import com.project.thinkup.model.User;


@Repository
public interface IdeaRepository extends JpaRepository<Idea, Long> {
    List<Idea> findByStatus(String status);

    Page<Idea> findByStatus(String status, Pageable pageable);

    @Query("SELECT i FROM Idea i JOIN i.keyWords kw WHERE kw.word = :keyword")
    Page<Idea> findByKeyword(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT i FROM Idea i JOIN i.keyWords kw WHERE kw.word = :keyword")
    List<Idea> findByKeyword(@Param("keyword") String keyword);


    Page<Idea> findByUser(User user, Pageable pageable);

    boolean existsByIdeaId(Long ideaId);

    List<Idea> findByUser(User user);

    @Query("SELECT COUNT(i) FROM Idea i WHERE i.user.area = :areaName")
    Long countIdeasByUserArea(@Param("areaName") String areaName);

    @Query("SELECT COUNT(i) FROM Idea i WHERE i.status = :state")
    Long countByState(@Param("state") String state);

}
