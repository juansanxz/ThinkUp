package com.project.thinkup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.thinkup.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByMail(String mail);
    boolean existsByMail(String mail);
    void deleteByMail(String mail);
}
