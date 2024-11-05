package com.example.socialnetwork.service;

import com.example.socialnetwork.model.User;
import com.example.socialnetwork.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User addUser(User user) {
        return userRepository.save(user);
    }

    public void removeUser(Long userId) {
        userRepository.deleteById(userId);
    }

    public List<User> listUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }

    public void addFriend(Long userId, Long friendId) {
        User user = userRepository.findById(userId).orElseThrow();
        User friend = userRepository.findById(friendId).orElseThrow();
        user.getFriends().add(friend);
        userRepository.save(user);
    }

    public void removeFriend(Long userId, Long friendId) {
        User user = userRepository.findById(userId).orElseThrow();
        User friend = userRepository.findById(friendId).orElseThrow();
        user.getFriends().remove(friend);
        userRepository.save(user);
    }

    public List<User> listFriends(Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        return List.copyOf(user.getFriends());
    }
}