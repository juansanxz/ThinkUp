package com.project.thinkup.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.project.thinkup.model.Idea;
import com.project.thinkup.model.User;
import com.project.thinkup.repository.IdeaRepository;

@Service
public class IdeaService {

    private final IdeaRepository ideaRepository;

    @PersistenceContext
    private EntityManager entityManager;

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

    public Page<Idea> getAllIdeasPageable(int pageNumber) {
        Page<Idea> pageable = ideaRepository.findAll(PageRequest.of(pageNumber, 1));
        return pageable;
    }

    public Page<Idea> getIdeasPageableByUser(int pageNumber, User user) {
        Page<Idea> pageable = ideaRepository.findByUser(user, PageRequest.of(pageNumber, 1));
        return pageable;
    }

    public Page<Idea> getAllIdeasOrdered(String column, String order, int pageNumber) {
        Direction orderBy;
        if (order.equals("asc")) {
            orderBy = Sort.Direction.ASC;
        } else {
            orderBy = Sort.Direction.DESC;
        }
        Sort sort = Sort.by(orderBy, column);

        Page<Idea> pageable = ideaRepository.findAll(PageRequest.of(pageNumber, 1, sort));
        return pageable;
    }

    public Page<Idea> getIdeasOrderedByUser(String column, String order, int pageNumber, User user) {
        Direction orderBy;
        if (order.equals("asc")) {
            orderBy = Sort.Direction.ASC;
        } else {
            orderBy = Sort.Direction.DESC;
        }
        Sort sort = Sort.by(orderBy, column);

        Page<Idea> pageable = ideaRepository.findByUser(user, PageRequest.of(pageNumber, 1, sort));
        return pageable;
    }

    @Transactional(propagation = Propagation.REQUIRED)
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
    }

    public List<Idea> getAllIdeasByStatus(String status) {
        return ideaRepository.findByStatus(status);
    }

    public List<Idea> getIdeasByUser(User user) {
        return ideaRepository.findByUser(user);
    }

    public List<Idea> getAllIdeasWithoutTopic() {
		TypedQuery<Idea> query = entityManager.createQuery("SELECT i FROM Idea i WHERE i.topic IS NULL", Idea.class);
		return query.getResultList();
	}
    
    public Long countIdeasByUserArea(String areaName) {
        return ideaRepository.countIdeasByUserArea(areaName);
    }

    public Long countByState(String state) {
        return ideaRepository.countByState(state);
    }
}
