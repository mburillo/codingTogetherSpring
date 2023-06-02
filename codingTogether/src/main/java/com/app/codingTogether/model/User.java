package com.app.codingTogether.model;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "favorite_language_id")
    private FavoriteLanguage favoriteLanguage;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_followers",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "follower_id")
    )
    @JsonIgnoreProperties("followers")
    private Set<User> followers;
    
    @ManyToMany(mappedBy = "followers", fetch = FetchType.EAGER)
    @JsonIgnoreProperties("following")
    private Set<User> following;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
        name = "comment_reposts",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "comment_id")
    )
    private Set<Comment> repostedComments;


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

public Set<Comment> getRepostedComments() {
	return repostedComments;
}

public void setRepostedComments(Set<Comment> repostedComments) {
	this.repostedComments = repostedComments;
}
   

}
