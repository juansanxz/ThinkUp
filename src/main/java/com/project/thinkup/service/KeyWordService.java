package com.project.thinkup.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.thinkup.model.KeyWord;
import com.project.thinkup.repository.KeyWordRepository;

@Service
public class KeyWordService {
    private final KeyWordRepository keyWordRepository;

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
}
