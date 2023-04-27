package com.project.thinkup.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.thinkup.model.User;
import com.project.thinkup.repository.UserRepository;

@Service
public class UserService {

	private final UserRepository userRepository;

	@Autowired
	public UserService(UserRepository UserRepository) {
		this.userRepository = UserRepository;
	}

	public User addUser(User user) {
		if (!userRepository.existsByMail(user.getMail())) {
			return userRepository.save(user);
		}
		return null;

	}

	public User getUser(Long userId) {
		return userRepository.findById(userId).get();
	}

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	public User updateUser(User user) {
		if (userRepository.existsById(user.getUserId())) {
			return userRepository.save(user);
		}

		return null;
	}

	public void deleteUser(Long userId) {
		userRepository.deleteById(userId);
	}

	public void deleteAllUsers() {
		userRepository.deleteAll();
		;
	}

	public User getUserByEmail(String mail) {
		return userRepository.findByMail(mail);

	}

	public boolean userExist(String mail){
		return userRepository.existsByMail(mail);
	}
}