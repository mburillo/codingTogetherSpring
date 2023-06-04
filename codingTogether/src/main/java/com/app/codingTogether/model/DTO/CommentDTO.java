package com.app.codingTogether.model.DTO;

import java.time.LocalDateTime;
import java.util.List;

import com.app.codingTogether.model.Reply;

public class CommentDTO {
	private Long id;
	private UserDTO user;
	private String content;
	private List<Long> likeIds;
	private List<UserDTO> repostedByUsers;
	private List<ReplyDTO> replies;
	private LocalDateTime createdAt;

	public CommentDTO(Long id, UserDTO user, String content, List<Long> likeIds, List<UserDTO> repostedByUserIds,
			List<ReplyDTO> replies, LocalDateTime createdAt) {
		super();
		this.id = id;
		this.user = user;
		this.content = content;
		this.likeIds = likeIds;
		this.replies = replies;
		this.repostedByUsers = repostedByUserIds;
		this.createdAt = createdAt;
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<Long> getLikeIds() {
		return likeIds;
	}

	public void setLikeIds(List<Long> likeIds) {
		this.likeIds = likeIds;
	}

	public List<UserDTO> getRepostedByUserIds() {
		return repostedByUsers;
	}

	public void setRepostedByUserIds(List<UserDTO> repostedByUsers) {
		this.repostedByUsers = repostedByUsers;
	}

	public List<ReplyDTO> getReplyIds() {
		return replies;
	}

	public void setReplyIds(List<ReplyDTO> replies) {
		this.replies = replies;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

}
