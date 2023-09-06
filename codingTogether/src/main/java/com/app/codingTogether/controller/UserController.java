package com.app.codingTogether.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.app.codingTogether.controller.image.UserImageManager;
import com.app.codingTogether.controller.password.PasswordEncoder;
import com.app.codingTogether.model.ChatMessage;
import com.app.codingTogether.model.Comment;
import com.app.codingTogether.model.FavoriteLanguage;
import com.app.codingTogether.model.Like;
import com.app.codingTogether.model.Reply;
import com.app.codingTogether.model.User;
import com.app.codingTogether.model.DTO.UserDTO;
import com.app.codingTogether.repository.CommentRepository;
import com.app.codingTogether.service.ChatMessageService;
import com.app.codingTogether.service.CommentService;
import com.app.codingTogether.service.LikeService;
import com.app.codingTogether.service.ReplyService;
import com.app.codingTogether.service.UserService;
import com.app.codingTogether.service.image.ImageUploader;

@RestController
public class UserController {

	@Autowired
	UserService userService;
	@Autowired
	CommentService commentService;
	@Autowired
	LikeService likeService;
	@Autowired
	ChatMessageService chatService;
	@Autowired
	ReplyService replyService;
	@Autowired 
	ImageUploader imageUploader;
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
	public ResponseEntity<UserDTO> loginUser(@RequestParam("username") String username,
			@RequestParam("password") String password) {
		UserDTO userToLogin = userService.getLoginUser(username, PasswordEncoder.hashPassword(password));
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
			String imagePath = "https://res.cloudinary.com/djnkw1sa9/image/upload/v1694008351/kroda67ph2fbc0j33wie.png";
			if (image != null) {
				imagePath = imageUploader.uploadImages(image);
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
	public ResponseEntity<Boolean> deleteUser(@PathVariable Long id) {
		User user = userService.getById(id);
		if (user == null) {
			return ResponseEntity.notFound().build();
		}
		Set<User> followers = user.getFollowers();
		Set<User> following = user.getFollowing();
		for (User follower : followers) {
			follower.getFollowing().remove(user);
		}
		for (User followed : following) {
			followed.getFollowers().remove(user);
		}
		List<Like> userLikes = likeService.getLikesByUser(user);
		for (Like like : userLikes) {
			Comment comment = like.getComment();
			comment.getLikes().remove(like);
			likeService.deleteLike(like);
		}
		List<Reply> userReplies = replyService.getRepliesByUser(user);
		for (Reply reply : userReplies) {
			Comment comment = reply.getComment();
			comment.getReplies().remove(reply);
			replyService.deleteReply(reply);
		}
		List<ChatMessage> userMessages = chatService.getChatMessagesByUser(user);
		chatService.deleteAll(userMessages);
		user.setRepostedComments(null);
		commentService.deleteByUser(user);
		userService.deleteUser(user);
		return ResponseEntity.ok(true);
	}

	@PatchMapping("/update")
	public ResponseEntity<UserDTO> handlePatchRequest(@RequestParam("id") Long id,
			@RequestParam("language") String language, @RequestParam("level") String level,
			@RequestParam(value = "image", required = false) MultipartFile image) {
		String imagePath = "";
		if (image != null)
			imagePath = imageUploader.uploadImages(image);
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
