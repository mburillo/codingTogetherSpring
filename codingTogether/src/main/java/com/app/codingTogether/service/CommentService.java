package com.app.codingTogether.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.codingTogether.model.Comment;
import com.app.codingTogether.model.Like;
import com.app.codingTogether.model.User;
import com.app.codingTogether.repository.CommentRepository;

@Service
public class CommentService {
	@Autowired
	CommentRepository commentRepo;

	public Comment getById(Long id) {
		Optional<Comment> comment = commentRepo.findById(id);
		if (comment.isPresent()) {
			return comment.get();
		}
		return null;
	}

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

	public Comment saveNestedPost(User u, Comment c, String content) {
		Comment nestedC = new Comment();
		nestedC.setContent(content);
		nestedC.setCreatedAt(LocalDateTime.now());
		nestedC.setUser(u);
		nestedC.setParentComment(c);
		return commentRepo.save(nestedC);
	}

	public List<Comment> getCommentsByParentId(Long id) {
		return commentRepo.findCommentsByParentCommentId(id);
	}

	public Like searchLike(User u, Comment c) {
		for (Like like : c.getLikes()) {
			if (like.getUser().equals(u)) {
			return like;
			}
		}
		return null;
	}
	
	public Integer likeComment(User u, Comment c) {
		Like likeToRemove = searchLike(u,c);		
		if (likeToRemove != null) {
			c.getLikes().remove(likeToRemove);
		} else {
			Like newLike = new Like();
			newLike.setUser(u);
			newLike.setComment(c);
			c.getLikes().add(newLike);
		}

		Comment updatedComment = commentRepo.save(c);
		return updatedComment.getLikes().size();
	}

}
