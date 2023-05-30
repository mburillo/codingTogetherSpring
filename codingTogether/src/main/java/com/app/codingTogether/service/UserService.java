package com.app.codingTogether.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.codingTogether.model.FavoriteLanguage;
import com.app.codingTogether.model.User;
import com.app.codingTogether.model.DTO.UserPatchRequest;
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
	public List<User> filterByLanguageAndLevel(String programmingLanguage, String level, Long userId) {
		 List<User> filteredUsers = userRepo.findByFavoriteLanguageLanguageAndFavoriteLanguageExperienceLevel(programmingLanguage, level);
		 for (User user : filteredUsers) {
		        if (user.getId().equals(userId)) {
		            user.getFollowers().size();
		        } else {
		            user.setFollowers(null);
		        }
		    }
		 return filteredUsers;
	}
	public boolean checkIfFollower(User userToFollow, User follower) {
		return userRepo.checkIfUserIsFollowingAnother(userToFollow.getId(), follower.getId());
	}
	public List<User> getRandomUsers(Long id){
		return userRepo.findRandomUsersNotFollowed(id, 2);
	}
	public User updateUser(UserPatchRequest userPatchRequest, String imagePath) {
		User u = getById(userPatchRequest.getId());
		FavoriteLanguage userLanguage= new FavoriteLanguage();
		userLanguage.setExperienceLevel(userPatchRequest.getNivel());
		userLanguage.setLanguage(userPatchRequest.getLenguaje());
		u.setFavoriteLanguage(userLanguage);
		if(!imagePath.isBlank() && !imagePath.isEmpty()) u.setProfileImage(imagePath);
		if(userPatchRequest.getUsuario()!=null) u.setUsername(userPatchRequest.getUsuario());
		return userRepo.save(u);
	}
}
