package com.example.socialnetwork.repository;

import com.example.socialnetwork.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
