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
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;

@Entity
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long topicId;

    @Column(name = "creationDate")
    private LocalDate creationDate;
    private String description;
    private String title;

	@OneToMany(targetEntity = Idea.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "topic_id")
	private List<Idea> ideas;


    public Topic(){
        this.creationDate = LocalDate.now();
    }

    public Topic(String title, String description) {
        this.creationDate = LocalDate.now();
        this.description = description;
        this.title = title;
        this.ideas = new ArrayList<Idea>();
    }

    public void addIdea (Idea ideaToAdd) {
		ideas.add(ideaToAdd);
	}

    public Long getTopicId() {
        return topicId;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Idea> getIdeas() {
        return ideas;
    }

    public void setIdeas(List<Idea> ideas) {
        this.ideas = ideas;
    }

    public Topic removeIdea(Idea Idea){
       ideas.remove(Idea);
       return this;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((title == null) ? 0 : title.hashCode());
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
        Topic other = (Topic) obj;
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
        return true;
    }

    @Override
    public String toString() {
        return "Topic [creationDate=" + creationDate + ", description=" + description + ", title=" + title + "]";
    }
  

}
