package com.app.codingTogether.model.DTO;

import org.springframework.web.multipart.MultipartFile;

public class UserPatchRequest {
    private Long id;
    private String username;
    private String language;
    private String level;
    private MultipartFile image;
    public UserPatchRequest() {
    	
    }
    
	public UserPatchRequest(Long id, String usuario, String lenguaje, String nivel, MultipartFile imagen) {
		super();
		this.id = id;
		this.username = usuario;
		this.language = lenguaje;
		this.level = nivel;
		this.image = imagen;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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
		return "UserPatchRequest [id=" + id + ", usuario=" + username + ", lenguaje=" + language + ", nivel=" + level
				+ ", imagen=" + image + "]";
	}

}