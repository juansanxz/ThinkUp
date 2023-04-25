package com.project.thinkup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.thinkup.model.KeyWord;

@Repository
public interface KeyWordRepository extends JpaRepository<KeyWord, String> {

    boolean existsByWord(String word);

    void deleteByWord(String word);

    KeyWord findByWord(String word);
}
