package com.app.codingTogether.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.ArrayList;

import com.app.codingTogether.model.ChatMessage;
import com.app.codingTogether.model.Comment;
import com.app.codingTogether.model.Like;
import com.app.codingTogether.model.Reply;
import com.app.codingTogether.model.User;
import com.app.codingTogether.model.DTO.ChatMessageDTO;
import com.app.codingTogether.model.DTO.CommentDTO;
import com.app.codingTogether.model.DTO.ReplyDTO;
import com.app.codingTogether.model.DTO.UserDTO;

public class DataToDTO {
	public static UserDTO userToDTO(User user) {
		UserDTO userDTO = new UserDTO();
		userDTO.setId(user.getId());
		userDTO.setUsername(user.getUsername());
		userDTO.setPassword(user.getPassword());
		userDTO.setProfileImage(user.getProfileImage());
		userDTO.setFavoriteLanguage((user.getFavoriteLanguage()));
		userDTO.setFollowerIds(getUserIds(user.getFollowers()));
		userDTO.setFollowingIds(getUserIds(user.getFollowing()));
		return userDTO;
	}

	private static Set<Long> getUserIds(Set<User> users) {
		Set<Long> userIds = new HashSet<>();
		for (User user : users) {
			userIds.add(user.getId());
		}
		return userIds;
	}

	public static CommentDTO commentToDTO(Comment comment) {
		List<Long> likeIds = new ArrayList<>();
		if (comment.getLikes() != null && comment.getLikes().size() > 0) {
			likeIds = comment.getLikes().stream().map(Like::getId).collect(Collectors.toList());
		}
		List<ReplyDTO> replies = new ArrayList<>();
		if (comment.getReplies() != null) {
			for(Reply r : comment.getReplies()) replies.add(replyToDTO(r));
		}
		List<UserDTO> repostedByUsers = new ArrayList<>();
		if (comment.getRepostedByUsers() != null && comment.getRepostedByUsers().size() > 0) {
			for(User u : comment.getRepostedByUsers()) repostedByUsers.add(userToDTO(u));
		}
		UserDTO user = DataToDTO.userToDTO(comment.getUser());
		return new CommentDTO(comment.getId(), user, comment.getContent(), likeIds, repostedByUsers,replies,
				comment.getCreatedAt());
	}
	public static ReplyDTO replyToDTO(Reply reply) {
	    return new ReplyDTO(
	        reply.getId(),
	        reply.getComment().getId(),
	        DataToDTO.userToDTO(reply.getUser()),
	        reply.getContent(),
	        reply.getCreatedAt()
	    );
	}
	
	 public static ChatMessageDTO fromChatMessage(ChatMessage chatMessage) {
	        UserDTO senderDTO = userToDTO(chatMessage.getSender());
	        return new ChatMessageDTO(
	                chatMessage.getId(),
	                chatMessage.getContent(),
	                chatMessage.getTimestamp(),
	                senderDTO
	        );
	    }
	
}
