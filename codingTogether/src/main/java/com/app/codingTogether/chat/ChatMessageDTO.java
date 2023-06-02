package com.app.codingTogether.chat;

import java.util.Date;


import com.app.codingTogether.model.DTO.UserDTO;


public class ChatMessageDTO {
	 private Long id;
	    
	    private String content;
	    
	    private Date timestamp;
	    
	    private UserDTO sender;
	    public ChatMessageDTO() {
	    	
	    }
		public ChatMessageDTO(Long id, String content, Date timestamp, UserDTO sender) {
			super();
			this.id = id;
			this.content = content;
			this.timestamp = timestamp;
			this.sender = sender;
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
		public Date getTimestamp() {
			return timestamp;
		}
		public void setTimestamp(Date timestamp) {
			this.timestamp = timestamp;
		}
		public UserDTO getSender() {
			return sender;
		}
		public void setSender(UserDTO sender) {
			this.sender = sender;
		}
	    
}
