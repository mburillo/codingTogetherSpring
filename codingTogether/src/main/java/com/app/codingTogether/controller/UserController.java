package com.app.codingTogether.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.app.codingTogether.controller.image.UserImageManager;
import com.app.codingTogether.controller.password.PasswordEncoder;
import com.app.codingTogether.model.FavoriteLanguage;
import com.app.codingTogether.model.User;
import com.app.codingTogether.model.DTO.RegisterRequest;
import com.app.codingTogether.model.DTO.UserDTO;
import com.app.codingTogether.model.DTO.UserPatchRequest;
import com.app.codingTogether.service.UserService;

@RestController
public class UserController {
	@Autowired
	UserService userService;

	@GetMapping("/getById")
	public ResponseEntity<UserDTO> userById(@RequestParam("userId") Long id) {
		UserDTO u = userService.getUserDTOById(id);
		if (u != null) {
			return ResponseEntity.ok().body(u);
		}
		return ResponseEntity.badRequest().body(null);
	}

	@GetMapping("/getAll")
	public ResponseEntity<List<User>> getAllUsers(@RequestParam("userId") Long userId) {
		return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUsersWithFollowers(userId));
	}

	@PostMapping("/login")
	public ResponseEntity<User> loginUser(@RequestParam("username") String username,
			@RequestParam("password") String password) {
		User userToLogin = userService.getLoginUser(username, PasswordEncoder.hashPassword(password));
		System.out.println(userToLogin);
		if (userToLogin != null) {
			return ResponseEntity.status(HttpStatus.OK).body(userToLogin);
		}
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}

	@PostMapping("/register")
	public ResponseEntity<User> registerUser(@RequestParam("username") String username,
			@RequestParam("password") String password, @RequestParam("language") String language,
			@RequestParam("level") String level, @RequestParam(value = "image", required = false) MultipartFile image) {
		if (!username.isBlank() && !username.isBlank() && !password.isBlank() && !password.isEmpty()) {
			String imagePath = "img.png";
			if (image != null) {
				imagePath = UserImageManager.saveImage(image);
			}
			FavoriteLanguage userLanguage = new FavoriteLanguage();
			userLanguage.setExperienceLevel(level);
			userLanguage.setLanguage(language);
			User userToSave = new User();
			userToSave.setUsername(username);
			userToSave.setPassword(PasswordEncoder.hashPassword(password));
			userToSave.setProfileImage(imagePath);
			userToSave.setFavoriteLanguage(userLanguage);
			User savedUser = userService.saveUser(userToSave);
			if (savedUser != null) {
				return ResponseEntity.status(HttpStatus.OK).body(savedUser);
			}
		}
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}

	@PostMapping("/follow")
	public ResponseEntity<Boolean> followUser(@RequestParam("idPerfil") String userToFollow,
			@RequestParam("idSeguidor") String follower) {
		System.out.println(userToFollow + " " + follower);
		User userBeingFollowed = userService.getById(Long.valueOf(userToFollow));
		User userFollowing = userService.getById(Long.valueOf(follower));
		if (userBeingFollowed == null || userFollowing == null)
			return ResponseEntity.badRequest().body(false);
		boolean followOrUnfollowed = true;
		if (userBeingFollowed.getFollowers().contains(userFollowing)) {
			userBeingFollowed.getFollowers().remove(userFollowing);
			userFollowing.getFollowing().remove(userBeingFollowed);
			followOrUnfollowed = false;
		} else {
			userBeingFollowed.getFollowers().add(userFollowing);
			userFollowing.getFollowing().add(userBeingFollowed);
		}
		userService.saveUser(userBeingFollowed);
		userService.saveUser(userFollowing);
		return ResponseEntity.ok(followOrUnfollowed);
	}

	@PostMapping("/isFollower")
	public ResponseEntity<Boolean> isFollowingUser(@RequestParam("idPerfil") String userToFollow,
			@RequestParam("idSeguidor") String follower) {
		return ResponseEntity.ok(userService.checkIfFollower(userService.getById(Long.valueOf(userToFollow)),
				userService.getById(Long.valueOf(follower))));
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<User> deleteUser(@PathVariable Long id) {
		User user = userService.getById(id);
		if (user == null) {
			return ResponseEntity.notFound().build();
		}
		user.setFollowers(null);
		user.setFollowing(null);
		userService.deleteUser(user);
		return ResponseEntity.ok(user);
	}

	@PatchMapping("/update")
	public ResponseEntity<UserDTO> handlePatchRequest(@RequestParam("id") Long id,
			@RequestParam("language") String language, @RequestParam("level") String level,
			@RequestParam(value = "image", required = false) MultipartFile image) {
		String imagePath = "";
		if (image != null)
			imagePath = UserImageManager.saveImage(image);
		return ResponseEntity.ok(userService.updateUser(id, language, level, imagePath));
	}

	@PostMapping("/filter")
	public ResponseEntity<List<UserDTO>> filter(@RequestParam("language") String programmingLanguage,
			@RequestParam("level") String level, @RequestParam("userId") Long userId) {
		List<UserDTO> filteredUsers = userService.filterByLanguageAndLevel(programmingLanguage, level, userId);
		return ResponseEntity.ok().body(filteredUsers);
	}

	@GetMapping("/randomUsers/{userId}")
	public ResponseEntity<List<UserDTO>> getRandomUsersNotFollowed(@PathVariable("userId") Long userId) {
		List<UserDTO> randomUsers = userService.getRandomUsers(userId);
		return ResponseEntity.ok(randomUsers);
	}

}
