package com.app.codingTogether.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.app.codingTogether.controller.image.UserImageManager;
import com.app.codingTogether.model.FavoriteLanguage;
import com.app.codingTogether.model.User;
import com.app.codingTogether.service.UserService;

@RestController
public class UserController {
	@Autowired
	UserService userService;

	@GetMapping("/getAll")
	public ResponseEntity<List<User>> getAllUsers() {
		return ResponseEntity.status(HttpStatus.OK).body(userService.allUsers());
	}

	@PostMapping("/login")
	public ResponseEntity<User> loginUser(@RequestParam("username") String username,
			@RequestParam("password") String password) {
		User userToLogin = userService.getLoginUser(username, password);
		if (userToLogin != null)
			return ResponseEntity.status(HttpStatus.OK).body(userToLogin);
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}

	@PostMapping("/register")
	public ResponseEntity<User> registerUser(  @RequestParam("usuario") String username,
            @RequestParam("clave") String password,
            @RequestParam("lenguaje") String language,
            @RequestParam("nivel") String level,
            @RequestParam("imagen") MultipartFile image) {
		String imagePath = "C:\\Users\\Magancito\\git\\codingTogetherSpring\\codingTogether\\src\\main\\resources\\images";
		if(image!=null) imagePath = UserImageManager.saveImage(image);
		FavoriteLanguage userLanguage= new FavoriteLanguage();
		userLanguage.setExperienceLevel(level);
		userLanguage.setLanguage(language);
		User userToSave = new User();
		userToSave.setUsername(username);
		userToSave.setPassword(password);
		userToSave.setProfileImage(imagePath);
		userToSave.setFavoriteLanguage(userLanguage);
		User savedUser = userService.saveRegisteredUser(userToSave);
		if(savedUser!=null) {
			return ResponseEntity.status(HttpStatus.OK).body(savedUser);
		}
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}
	
}
