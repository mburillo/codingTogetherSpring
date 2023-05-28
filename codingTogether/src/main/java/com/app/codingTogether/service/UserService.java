package com.app.codingTogether.service;

import java.util.List;
import java.util.Optional;

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
	public User saveUser(User u) {
		return userRepo.save(u);
	}
	public User getById(Long id) {
		Optional<User> user =userRepo.findById(id);
		if(user.isPresent()) {
			return user.get();
		}
		return null;
	}
	public void deleteUser(User user) {
		userRepo.delete(user);		
	}
	public List<User> getAllUsersWithFollowers(Long userId) {
	    List<User> users = userRepo.findAll();
	    for (User user : users) {
	        if (user.getId().equals(userId)) {
	            user.getFollowers().size();
	        } else {
	            user.setFollowers(null);
	        }
	    }
	    return users;
	}
	public List<User> filterByLanguageAndLevel(String programmingLanguage, String level) {
		 List<User> filteredUsers = userRepo.findByFavoriteLanguageLanguageAndFavoriteLanguageExperienceLevel(programmingLanguage, level);
		    return filteredUsers;
	}
}
