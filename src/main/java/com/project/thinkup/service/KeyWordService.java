package com.project.thinkup.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.primefaces.push.annotation.PushEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import com.project.thinkup.model.KeyWord;
import com.project.thinkup.model.Idea;
import com.project.thinkup.repository.KeyWordRepository;
import com.project.thinkup.repository.IdeaRepository;

@Service
public class KeyWordService {
    private final KeyWordRepository keyWordRepository;
    
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public KeyWordService(KeyWordRepository keyWordRepository) {
        this.keyWordRepository = keyWordRepository;
    }

    public KeyWord addKeyWord(KeyWord keyWord) {
        if (!keyWordRepository.existsByWord(keyWord.getWord())) {
            return keyWordRepository.save(keyWord);
        }
        return null;

    }

    public KeyWord getKeyWord(String word) {
        return keyWordRepository.findByWord(word);
    }

    public List<KeyWord> getAllKeyWords() {
        return keyWordRepository.findAll();
    }

    public KeyWord updateKeyWord(KeyWord keyWord) {
        if (keyWordRepository.existsByWord(keyWord.getWord())) {
            return keyWordRepository.save(keyWord);
        }

        return null;
    }

    public void deleteKeyWord(String word) {
        keyWordRepository.deleteByWord(word);
    }

    public void deleteAllKeyWords() {
        keyWordRepository.deleteAll();
    }
    public Page<Idea> getAllIdeasByKeyword(String[] Keywords, int pageNumber) {
        List<Idea> keywordslist = new ArrayList<>();
        for (String keyword : Keywords) {
            KeyWord key = keyWordRepository.findByWord(keyword);

            key.getKeyWordId();
            
        }
        return new PageImpl<>(keywordslist);
    }

}
