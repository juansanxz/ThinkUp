package com.project.thinkup.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.thinkup.model.User;
import com.project.thinkup.repository.UserRepository;

@Service
public class UserService {

	private final UserRepository UserRepository;

	@Autowired
	public UserService(UserRepository UserRepository) {
		this.UserRepository = UserRepository;
	}

	public User addUser(User User) {
		return UserRepository.save(User);
	}

	public User getUser(Long UserId) {
		return UserRepository.findById(UserId).get();
	}

	public List<User> getAllUsers() {
		return UserRepository.findAll();
	}

	public User updateUser(User User) {
		if (UserRepository.existsById(User.getUserId())) {
			return UserRepository.save(User);
		}

		return null;
	}

	public void deleteUser(Long UserId) {
		UserRepository.deleteById(UserId);
	}

	public void deleteAllUsers() {
		UserRepository.deleteAll();
		;
	}
}