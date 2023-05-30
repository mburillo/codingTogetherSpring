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
import com.app.codingTogether.model.Reply;
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
	public ResponseEntity<Comment> savePost(@RequestParam("id") String userId,
            @RequestParam("comentario") String content) {
		User u = userService.getById(Long.valueOf(userId));
		return ResponseEntity.status(HttpStatus.OK).body(commentService.savePost(u, content));
	}
	
	@PostMapping("/likePost")
	public ResponseEntity<Integer> likePost(@RequestParam("idCuenta") Long userId,
            @RequestParam("idPost") Long idPost) {
		User u = userService.getById(userId);
		Comment c = commentService.getById(idPost);		
		return ResponseEntity.status(HttpStatus.OK).body(commentService.likeComment(u, c));
	}
	
	@PostMapping("/repost")
	public ResponseEntity<Integer> repost(@RequestParam("idCuenta") Long userId,
            @RequestParam("idPost") Long idPost) {
		User u = userService.getById(userId);
		Comment c = commentService.getById(idPost);	
		return ResponseEntity.status(HttpStatus.OK).body(commentService.repost(u, c));
	}
	
	@PostMapping("/nestPost")
	public ResponseEntity<Comment> saveNestedPost(
            @RequestParam("idPost") Long idPost,
			@RequestParam("idComentador") Long idComentador,
            @RequestParam("comentario") String content) {
		User u = userService.getById(idComentador);
		Comment c = commentService.getById(idPost);
		return ResponseEntity.status(HttpStatus.OK).body(commentService.saveNestedPost(u, c, content));
	}
	
	@GetMapping("/getFeed/{userId}")
	public ResponseEntity<List<Comment>> getFeed(@PathVariable("userId") Long userId) {
		User u = userService.getById(Long.valueOf(userId));
	    List<Comment> feedPosts = commentService.getAllPosts(u);
	    return ResponseEntity.ok(feedPosts);
	}
	@GetMapping("/getPost/{postId}")
	public ResponseEntity<Comment> getPost(@PathVariable("postId") Long postId) {
	    return ResponseEntity.ok(commentService.getById(postId));
	}
}
