package com.project.thinkup.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class KeyWord {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long keyWordId;

    private String word;

    public KeyWord() {
    }

    public KeyWord(String word) {
        this.word = word;
    }

    public Long getKeyWordId() {
        return keyWordId;
    }

    public String getWord() {
        return word;
    }

    public void setKeyWordId(Long keyWordId) {
        this.keyWordId = keyWordId;
    }

    public void setWord(String word) {
        this.word = word;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((keyWordId == null) ? 0 : keyWordId.hashCode());
        result = prime * result + ((word == null) ? 0 : word.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        KeyWord other = (KeyWord) obj;
        if (keyWordId == null) {
            if (other.keyWordId != null)
                return false;
        } else if (!keyWordId.equals(other.keyWordId))
            return false;
        if (word == null) {
            if (other.word != null)
                return false;
        } else if (!word.equals(other.word))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "KeyWord [keyWordId=" + keyWordId + ", word=" + word + "]";
    }

}
