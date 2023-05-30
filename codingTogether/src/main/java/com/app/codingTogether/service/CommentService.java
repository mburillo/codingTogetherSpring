package com.app.codingTogether.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.app.codingTogether.controller.image.UserImageManager;
import com.app.codingTogether.controller.password.PasswordEncoder;
import com.app.codingTogether.model.Comment;
import com.app.codingTogether.model.FavoriteLanguage;
import com.app.codingTogether.model.User;
import com.app.codingTogether.repository.CommentRepository;
import com.app.codingTogether.repository.UserRepository;

@Service
public class CommentService {
	@Autowired
	CommentRepository commentRepo;

	public Comment savePost(User u, String content) {
		Comment c = new Comment();
		c.setContent(content);
		c.setCreatedAt(LocalDateTime.now());
		c.setUser(u);
		return commentRepo.save(c);
	}

	public List<Comment> getAllPosts(User u) {
		return commentRepo.getCommentsFromFollowingUsers(u.getFollowing(), u);
	}
	
}
