package com.app.codingTogether.model;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String profileImage;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "favorite_language_id")
    private FavoriteLanguage favoriteLanguage;

    @ManyToMany
    @JoinTable(
            name = "user_followers",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "follower_id")
    )
    private Set<User> followers;

    @ManyToMany(mappedBy = "followers")
    private Set<User> following;

    


public User(Long id, String username, String password, String profileImage,
			FavoriteLanguage favoriteLanguage, Set<User> followers, Set<User> following) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.profileImage = profileImage;
		this.favoriteLanguage = favoriteLanguage;
		this.followers = followers;
		this.following = following;
	}

public User() {
	   
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

public Set<User> getFollowers() {
	return followers;
}

public void setFollowers(Set<User> followers) {
	this.followers = followers;
}

public Set<User> getFollowing() {
	return following;
}

public void setFollowing(Set<User> following) {
	this.following = following;
}
   

}
