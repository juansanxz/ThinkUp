package com.project.thinkup.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.thinkup.model.Idea;
import com.project.thinkup.model.User;
import com.project.thinkup.repository.IdeaRepository;

@Service
public class IdeaService {

    private final IdeaRepository ideaRepository;

    @Autowired
    public IdeaService(IdeaRepository ideaRepository) {
        this.ideaRepository = ideaRepository;
    }

    public Idea addIdea(Idea idea) {
        return ideaRepository.save(idea);

    }

    public Idea getIdea(Long ideaId) {
        return ideaRepository.findById(ideaId).get();
    }

    public List<Idea> getAllIdeas() {
        return ideaRepository.findAll();
    }

    public Idea updateIdea(Idea idea) {
        if (ideaRepository.existsByIdeaId(idea.getIdeaId())) {
            return ideaRepository.save(idea);
        }

        return null;
    }

    public void deleteIdea(Long ideaId) {
        ideaRepository.deleteById(ideaId);
    }

    public void deleteAllIdeas() {
        ideaRepository.deleteAll();
        ;
    }

    public List<Idea> getAllIdeasByStatus(String status) {
        return ideaRepository.findByStatus(status);
    }

}
