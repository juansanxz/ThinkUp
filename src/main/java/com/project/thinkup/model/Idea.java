package com.project.thinkup.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Idea {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long ideaId;

    @Column(name = "creationDate")
    private LocalDate creationDate;
    private String status;
    private String description;
    private String title;

    // @ManyToOne(cascade = { CascadeType.REMOVE })
    // User user;


    @ManyToMany(fetch = FetchType.EAGER)
    private List<KeyWord> keyWords;

    public Idea() {
    }

    public Idea(String title, String description, List<KeyWord> keywords) {
        this.title = title;
        this.creationDate = LocalDate.now();
        status = Status.created;
        this.description = description;
        this.keyWords = keywords;
    }

    public Long getIdeaId() {
        return ideaId;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public String getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    public void setIdeaId(Long ideaId) {
        this.ideaId = ideaId;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setKeyWords(ArrayList<KeyWord> keyWords) {
        this.keyWords = keyWords;
    }

    public List<KeyWord> getKeyWords() {
        return keyWords;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((ideaId == null) ? 0 : ideaId.hashCode());
        result = prime * result + ((creationDate == null) ? 0 : creationDate.hashCode());
        result = prime * result + ((status == null) ? 0 : status.hashCode());
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        result = prime * result + ((keyWords == null) ? 0 : keyWords.hashCode());
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
        Idea other = (Idea) obj;
        if (ideaId == null) {
            if (other.ideaId != null)
                return false;
        } else if (!ideaId.equals(other.ideaId))
            return false;
        if (creationDate == null) {
            if (other.creationDate != null)
                return false;
        } else if (!creationDate.equals(other.creationDate))
            return false;
        if (status == null) {
            if (other.status != null)
                return false;
        } else if (!status.equals(other.status))
            return false;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        if (title == null) {
            if (other.title != null)
                return false;
        } else if (!title.equals(other.title))
            return false;
        if (keyWords == null) {
            if (other.keyWords != null)
                return false;
        } else if (!keyWords.equals(other.keyWords))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Idea [ideaId=" + ideaId + ", creationDate=" + creationDate + ", status=" + status + ", description="
                + description + ", title=" + title + ", keyWords=" + keyWords + "]";
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
