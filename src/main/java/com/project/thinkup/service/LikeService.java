package com.project.thinkup.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.thinkup.model.Idea;
import com.project.thinkup.model.Like;
import com.project.thinkup.model.User;
import com.project.thinkup.repository.LikeRepository;

@Service
public class LikeService {
    private final LikeRepository likeRepository;

    @Autowired
    public LikeService(LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }

    public Like addLike (Like like) {
        return likeRepository.save(like);
    }

    public void deleteAllLikes () {
        likeRepository.deleteAll();
    }

    public Like getLikeByIdeaUser (Idea idea, User user) {
        return likeRepository.findByIdeaAndUser(idea, user);
    }

    public List<Like> getLikeByIdea (Idea idea) {
        return likeRepository.findByIdea(idea);
    }

    public void deleteLike (Long likeId) {
        likeRepository.deleteById(likeId);
    }
}
