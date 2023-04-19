package com.project.thinkup.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.thinkup.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
