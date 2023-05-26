package com.project.thinkup.repository;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.project.thinkup.model.KeyWord;


@Repository
public interface KeyWordRepository extends JpaRepository<KeyWord, String> {

    boolean existsByWord(String word);

    void deleteByWord(String word);

    Page<KeyWord> findByWord(String word, Pageable pageable);

    KeyWord findByWord(String word);


}
