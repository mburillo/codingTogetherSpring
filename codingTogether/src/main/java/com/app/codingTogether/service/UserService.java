package com.app.codingTogether.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.codingTogether.model.User;
import com.app.codingTogether.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	UserRepository userRepo;
	public List<User> allUsers() {
		return userRepo.findAll();
	}
	public User getLoginUser(String username, String password) {
		return userRepo.findByUsernameAndPassword(username, password);
	}
	public User saveRegisteredUser(User u) {
		return userRepo.save(u);
	}
}
