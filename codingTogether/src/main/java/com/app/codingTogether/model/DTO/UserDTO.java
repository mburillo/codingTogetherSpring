package com.app.codingTogether.model.DTO;

import java.util.Set;

import com.app.codingTogether.model.FavoriteLanguage;

public class UserDTO {
	private Long id;
	private String username;
	private String password;
	private String profileImage;
	private FavoriteLanguage favoriteLanguage;
	private Set<Long> followerIds;
	private Set<Long> followingIds;

	public UserDTO(Long id, String username, String password, String profileImage, FavoriteLanguage favoriteLanguage,
			Set<Long> followerIds, Set<Long> followingIds) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.profileImage = profileImage;
		this.favoriteLanguage = favoriteLanguage;
		this.followerIds = followerIds;
		this.followingIds = followingIds;
	}

	public UserDTO() {

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}

	public FavoriteLanguage getFavoriteLanguage() {
		return favoriteLanguage;
	}

	public void setFavoriteLanguage(FavoriteLanguage favoriteLanguage) {
		this.favoriteLanguage = favoriteLanguage;
	}

	public Set<Long> getFollowerIds() {
		return followerIds;
	}

	public void setFollowerIds(Set<Long> followerIds) {
		this.followerIds = followerIds;
	}

	public Set<Long> getFollowingIds() {
		return followingIds;
	}

	public void setFollowingIds(Set<Long> followingIds) {
		this.followingIds = followingIds;
	}

}
