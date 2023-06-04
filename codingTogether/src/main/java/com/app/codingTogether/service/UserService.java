package com.app.codingTogether.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.codingTogether.model.FavoriteLanguage;
import com.app.codingTogether.model.User;
import com.app.codingTogether.model.DTO.UserDTO;
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
	public UserDTO getUserDTOById(Long id) {
	    Optional<User> optUser = userRepo.findById(id);
	    if (optUser.isPresent()) {
	    	User user = optUser.get();
	       return DataToDTO.userToDTO(user);
	    }
	    return null;
	}


	public User getById(Long id) {
	Optional<User> u = userRepo.findById(id);
	if(u.isPresent()) {
		return u.get();
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
	            user.setFollowing(null);
	        }
	    }
	    return users;
	}
	public List<UserDTO> filterByLanguageAndLevel(String programmingLanguage, String level, Long userId) {
		 List<User> filteredUsers = userRepo.findByFavoriteLanguageLanguageAndFavoriteLanguageExperienceLevel(programmingLanguage, level);
		 List<UserDTO> dtoUsers = new ArrayList<>();
		 for (User user : filteredUsers) {
		       dtoUsers.add(DataToDTO.userToDTO(user));
		    }
		 return dtoUsers;
	}
	public boolean checkIfFollower(User userToFollow, User follower) {
		return userRepo.checkIfUserIsFollowingAnother(userToFollow.getId(), follower.getId());
	}
	public List<UserDTO> getRandomUsers(Long id){
		List<User> randomUsers = userRepo.findRandomUsersNotFollowed(id, 2);
		List<UserDTO> dtoUsers = new ArrayList<>();
		for(User u : randomUsers) {
			dtoUsers.add(DataToDTO.userToDTO(u));
		}
		return dtoUsers;
	}
	public UserDTO updateUser(Long id, String language, String level, String imagePath) {
	    User u = getById(id);
	    FavoriteLanguage userLanguage = u.getFavoriteLanguage();
	    userLanguage.setExperienceLevel(level);
	    userLanguage.setLanguage(language);
	    u.setFavoriteLanguage(userLanguage);
	    if (!imagePath.isBlank() && !imagePath.isEmpty()) {
	        u.setProfileImage(imagePath);
	    }
	    return DataToDTO.userToDTO(userRepo.save(u));
	}

}
