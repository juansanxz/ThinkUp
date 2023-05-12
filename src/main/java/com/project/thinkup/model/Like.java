package com.project.thinkup.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "likes")
public class Like {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long likeId;

    // Idea asociada al Like
    @ManyToOne(targetEntity = Idea.class)
    private Idea idea;

    // User asociado al Like
    @ManyToOne(targetEntity = User.class)
    private User user;

    public Like () {
    }

    public Like (Idea idea, User user) {
        this.idea = idea;
        this.user = user;
    }

    public Long getLikeId() {
        return likeId;
    }

    public void setLikeId(Long likeId) {
        this.likeId = likeId;
    }

    public Idea getIdea() {
        return idea;
    }

    public void setIdea(Idea idea) {
        this.idea = idea;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Like [likeId=" + likeId + ", idea=" + idea + ", user=" + user + "]";
    }

    

    
}