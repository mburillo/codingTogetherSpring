package com.app.codingTogether.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.codingTogether.controller.image.UserImageManager;
import com.app.codingTogether.controller.password.PasswordEncoder;
import com.app.codingTogether.model.Comment;
import com.app.codingTogether.model.FavoriteLanguage;
import com.app.codingTogether.model.User;
import com.app.codingTogether.service.CommentService;
import com.app.codingTogether.service.UserService;

@RestController
public class CommentController {
	@Autowired
	CommentService commentService;
	@Autowired
	UserService userService;
	
	@PostMapping("/savePost")
	public ResponseEntity<Comment> registerUser(@RequestParam("id") String userId,
            @RequestParam("comentario") String content) {
		User u = userService.getById(Long.valueOf(userId));
		Comment c = commentService.savePost(u, content);
		return ResponseEntity.status(HttpStatus.OK).body(c);
	}
	@GetMapping("/getFeed/{userId}")
	public ResponseEntity<List<Comment>> getFeed(@PathVariable("userId") Long userId) {
		User u = userService.getById(Long.valueOf(userId));
	    List<Comment> feedPosts = commentService.getAllPosts(u);
	    return ResponseEntity.ok(feedPosts);
	}
}
