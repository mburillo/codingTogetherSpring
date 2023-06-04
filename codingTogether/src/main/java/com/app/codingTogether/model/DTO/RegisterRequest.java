package com.app.codingTogether.model.DTO;

import org.springframework.web.multipart.MultipartFile;

public class RegisterRequest {
	private String username;
	private String password;
	private String language;
	private String level;
	private MultipartFile image;

	public RegisterRequest() {

	}

	public RegisterRequest(String username, String password, String language, String level, MultipartFile image) {
		super();
		this.username = username;
		this.password = password;
		this.language = language;
		this.level = level;
		this.image = image;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public MultipartFile getImage() {
		return image;
	}

	public void setImage(MultipartFile image) {
		this.image = image;
	}

	@Override
	public String toString() {
		return "RegisterRequest [username=" + username + ", password=" + password + ", language=" + language
				+ ", level=" + level + ", image=" + image + "]";
	}

}
