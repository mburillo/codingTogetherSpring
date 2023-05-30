package com.app.codingTogether.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.codingTogether.model.Comment;
import com.app.codingTogether.model.Like;
import com.app.codingTogether.model.Reply;
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
		Reply reply = new Reply();
		reply.setContent(content);
		reply.setCreatedAt(LocalDateTime.now());
		reply.setUser(u);
		reply.setComment(c);
		reply.setUsername(u.getUsername());
		reply.setImage(u.getProfileImage());
		List<Reply> commentReplies = c.getReplies();
		commentReplies.add(reply);		
		return commentRepo.save(c);
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

	
	public Integer repost(User u, Comment c) {
	    Set<User> repostedByUsers = c.getRepostedByUsers();

	    if (repostedByUsers.contains(u)) {
	        repostedByUsers.remove(u);
	        u.getRepostedComments().remove(c);
	    } else {
	        repostedByUsers.add(u);
	        u.getRepostedComments().add(c);
	    }

	    Comment savedComment = commentRepo.save(c);
	    return savedComment.getRepostedByUsers().size();
	}

}
