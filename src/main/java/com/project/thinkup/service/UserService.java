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
		if (!UserRepository.existsByMail(User.getMail())) {
			return UserRepository.save(User);
		}
		return null;

	}

	public User getUser(String UserId) {
		return UserRepository.findById(UserId).get();
	}

	public List<User> getAllUsers() {
		return UserRepository.findAll();
	}

	public User updateUser(User User) {
		if (UserRepository.existsById(User.getMail())) {
			return UserRepository.save(User);
		}

		return null;
	}

	public void deleteUser(String UserId) {
		UserRepository.deleteById(UserId);
	}

	public void deleteAllUsers() {
		UserRepository.deleteAll();
		;
	}

	public User getUserByEmail(String mail) {
		return UserRepository.findByMail(mail);

	}
}