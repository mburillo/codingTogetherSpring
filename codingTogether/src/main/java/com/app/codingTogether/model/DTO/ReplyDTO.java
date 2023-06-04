package com.app.codingTogether.model.DTO;

import java.time.LocalDateTime;

public class ReplyDTO {
	private Long id;
	private Long commentId;
	private UserDTO user;
	private String content;
	private LocalDateTime createdAt;

	public ReplyDTO(Long id, Long commentId, UserDTO user, String content, LocalDateTime createdAt) {
		super();
		this.id = id;
		this.commentId = commentId;
		this.user = user;
		this.content = content;
		this.createdAt = createdAt;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCommentId() {
		return commentId;
	}

	public void setCommentId(Long commentId) {
		this.commentId = commentId;
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

}
