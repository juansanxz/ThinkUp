package com.project.thinkup.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.thinkup.model.Idea;
import com.project.thinkup.model.Topic;
import com.project.thinkup.repository.TopicRepository;

@Service
public class TopicService {

	private final TopicRepository topicRepository;

    @PersistenceContext
    private EntityManager entityManager;

	@Autowired
	public TopicService(TopicRepository TopicRepository) {
		this.topicRepository = TopicRepository;
	}

	public Topic addTopic(Topic Topic) {
		if (!topicRepository.existsByTitle(Topic.getTitle())) {
			return topicRepository.save(Topic);
		}
		return null;

	}

	public Topic getTopic(Long topicId) {
		return topicRepository.findById(topicId).get();
	}

	public List<Topic> getAllTopics() {
		return topicRepository.findAll();
	}

	public Topic updateTopic(Topic Topic) {
		if (topicRepository.existsById(Topic.getTopicId())) {
			return topicRepository.save(Topic);
		}

		return null;
	}

	public void deleteTopic(Long topicId) {
		topicRepository.deleteById(topicId);
	}

	public void deleteAllTopics() {
		topicRepository.deleteAll();
		;
	}

	public Topic getTopicByTitle(String title) {
		return topicRepository.findByTitle(title);

	}

	public boolean TopicExist(String title){
		return topicRepository.existsByTitle(title);
	}

    public List<Idea> getAllIdeasByTopicId(Long topicId) {
        TypedQuery<Idea> query = entityManager.createQuery("SELECT i FROM Idea WHERE i.topic_id = :topicId", Idea.class);
        query.setParameter("topicId", topicId);
        List<Idea> ideas = query.getResultList();
        return ideas;
    }
}