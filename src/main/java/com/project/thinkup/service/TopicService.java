package com.project.thinkup.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.thinkup.model.Idea;
import com.project.thinkup.model.Topic;
import com.project.thinkup.repository.TopicRepository;

@Service
public class TopicService {

	private final TopicRepository topicRepository;

	@Autowired
	public TopicService(TopicRepository topicRepository) {
		this.topicRepository = topicRepository;
	}

	public Topic addTopic(Topic topic) {
		if (!topicRepository.existsByTitle(topic.getTitle())) {
			return topicRepository.save(topic);
		}
		return null;

	}

	public Topic getTopic(Long topicId) {
		return topicRepository.findById(topicId).get();
	}

	public List<Topic> getAllTopics() {
		return topicRepository.findAll();
	}

	public Topic updateTopic(Topic topic) {
		if (topicRepository.existsById(topic.getTopicId())) {
			return topicRepository.save(topic);
		}

		return null;
	}

	public void deleteTopic(Long topicId) {
		topicRepository.deleteById(topicId);
	}

	public void deleteAllTopics() {
		topicRepository.deleteAll();
	}

	public Topic getTopicByTitle(String title) {
		return topicRepository.findByTitle(title);

	}

	public boolean topicExist(String title){
		return topicRepository.existsByTitle(title);
	}

    public List<Idea> getIdeasByTopicId(Long topicId) {
		Optional<Topic> optionalTopic = topicRepository.findById(topicId);
		if (optionalTopic.isPresent()) {
			Topic topic = optionalTopic.get();
			return topic.getIdeas();
		}
		return new ArrayList<>();
    }
}