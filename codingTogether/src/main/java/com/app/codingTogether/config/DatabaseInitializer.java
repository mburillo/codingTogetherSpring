package com.app.codingTogether.config;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.app.codingTogether.controller.password.PasswordEncoder;
import com.app.codingTogether.model.Comment;
import com.app.codingTogether.model.FavoriteLanguage;
import com.app.codingTogether.model.Reply;
import com.app.codingTogether.model.User;
import com.app.codingTogether.repository.CommentRepository;
import com.app.codingTogether.repository.ReplyRepository;
import com.app.codingTogether.repository.UserRepository;

@Component
public class DatabaseInitializer {
	@Autowired
	UserRepository userRepository;
	User firstUser;
	User secondUser;
	@Autowired
	CommentRepository commentRepository;
	Comment firstComment;
	@Autowired
	ReplyRepository replyRepository;
    @EventListener
    public void seed(ContextRefreshedEvent event) {
        seedProductsTable();
    }
    
    private void seedProductsTable() {  
        saveUsers();
    }
    
    private void saveUsers() {
    	User u = new User();
    	u.setPassword(PasswordEncoder.hashPassword("encodedPassword"));
    	u.setUsername("JohnDoe");
    	u.setFavoriteLanguage(new FavoriteLanguage(null,"PHP","Avanzado"));
    	u.setProfileImage("https://res.cloudinary.com/djnkw1sa9/image/upload/v1691188155/samples/shoe.jpg");
    	saveCommentsUser1(userRepository.save(u));
    	User u2 = new User();
    	u2.setUsername("Mary");
    	u2.setPassword(PasswordEncoder.hashPassword("encodedPassword"));
    	u2.setFavoriteLanguage(new FavoriteLanguage(null,"Java", "Principiante"));
    	u2.setProfileImage("https://res.cloudinary.com/djnkw1sa9/image/upload/v1694008351/kroda67ph2fbc0j33wie.png");
    	saveCommentUser2(userRepository.save(u2));
    	User u3 = new User();
    	u3.setUsername("Poch");
    	u3.setPassword(PasswordEncoder.hashPassword("encodedPassword"));
    	u3.setFavoriteLanguage(new FavoriteLanguage(null,"Java", "Medio"));
    	u3.setProfileImage("https://res.cloudinary.com/djnkw1sa9/image/upload/v1694008351/kroda67ph2fbc0j33wie.png");
    	userRepository.save(u3);
    	User u4 = new User();
    	u4.setUsername("LearningToCode");
    	u4.setPassword(PasswordEncoder.hashPassword("encodedPassword"));
    	u4.setFavoriteLanguage(new FavoriteLanguage(null,"Java", "Medio"));
    	u4.setProfileImage("https://res.cloudinary.com/djnkw1sa9/image/upload/v1694008351/kroda67ph2fbc0j33wie.png");
    	userRepository.save(u4);
    	User u5 = new User();
    	u5.setUsername("CodeFusion");
    	u5.setPassword(PasswordEncoder.hashPassword("encodedPassword"));;
    	u5.setFavoriteLanguage(new FavoriteLanguage(null,"Java", "Medio"));
    	u5.setProfileImage("https://res.cloudinary.com/djnkw1sa9/image/upload/v1694008351/kroda67ph2fbc0j33wie.png");
    	userRepository.save(u5);
    	User u6 = new User();
    	u6.setUsername("Charlie");
    	u6.setPassword(PasswordEncoder.hashPassword("encodedPassword"));;
    	u6.setFavoriteLanguage(new FavoriteLanguage(null,"Java", "Medio"));
    	u6.setProfileImage("https://res.cloudinary.com/djnkw1sa9/image/upload/v1694008351/kroda67ph2fbc0j33wie.png");
    	userRepository.save(u6);
    	User u7 = new User();
    	u7.setUsername("StackingOverflowing");
    	u7.setPassword(PasswordEncoder.hashPassword("encodedPassword"));;
    	u7.setFavoriteLanguage(new FavoriteLanguage(null,"Java", "Medio"));
    	u7.setProfileImage("https://res.cloudinary.com/djnkw1sa9/image/upload/v1694008264/pbz1tfs5kgx14zlwc8rw.png");
    	userRepository.save(u7);
    	User u8 = new User();
    	u8.setUsername("sampleUser1");
    	u8.setPassword(PasswordEncoder.hashPassword("encodedPassword"));
    	u8.setFavoriteLanguage(new FavoriteLanguage(null,"Java", "Medio"));
    	u8.setProfileImage("https://res.cloudinary.com/djnkw1sa9/image/upload/v1694008351/kroda67ph2fbc0j33wie.png");
    	u8=userRepository.save(u8);
    	User u9 = new User();
    	u9.setUsername("sampleUser2");
    	u9.setPassword(PasswordEncoder.hashPassword("encodedPassword"));;
    	u9.setFavoriteLanguage(new FavoriteLanguage(null,"Java", "Medio"));
    	u9.setProfileImage("https://res.cloudinary.com/djnkw1sa9/image/upload/v1694008264/pbz1tfs5kgx14zlwc8rw.png");
    	u9=userRepository.save(u9);
    	setFollowers(u8,u9);
    }


	private void setFollowers(User u8, User u9) {
		firstUser.setFollowers(Set.of(u8,u9));
		secondUser.setFollowers(Set.of(u8,u9));
		u8.setFollowing(Set.of(firstUser,secondUser));
		u9.setFollowing(Set.of(firstUser,secondUser));
		userRepository.save(firstUser);
		userRepository.save(secondUser);
		userRepository.save(u8);
		userRepository.save(u9);
	}

	private void saveCommentUser2(User u) {
		Comment c = new Comment();
		c.setContent("This looks interesting, can anybody help me figure out how to use this?");
		c.setCreatedAt(LocalDateTime.now());
		c.setUser(u);
		commentRepository.save(c);
		Reply reply = new Reply();
		reply.setComment(firstComment);
		reply.setContent("Hi mom!");
		reply.setCreatedAt(LocalDateTime.now());
		reply.setUser(u);
		reply.setImage(u.getProfileImage());
		replyRepository.save(reply);
		firstComment.setReplies(List.of(reply));
		firstComment.setRepostedByUsers(Set.of(u));
		secondUser = u;
	}

	private String getImage(String image) {
		try {
			ClassPathResource res = new ClassPathResource(image);
			File file = res.getFile();
			return file.getAbsolutePath();
		} catch (IOException e) {
			e.printStackTrace();
		}
       
		return null;
	}

	private void saveCommentsUser1(User u) {
		Comment c = new Comment();
		c.setContent("Hello world!");
		c.setCreatedAt(LocalDateTime.now());
		c.setUser(u);
		firstComment = commentRepository.save(c);
		firstUser = u;
	}
}
