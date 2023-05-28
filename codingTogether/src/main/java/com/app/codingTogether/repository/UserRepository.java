package com.app.codingTogether.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.codingTogether.model.User;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	 User findByUsernameAndPassword(String username, String password);
	 
	 @Query("SELECT u FROM User u LEFT JOIN FETCH u.following WHERE u.id <> :userId")
	 List<User> findAllExceptCurrentUser(@Param("userId") Long userId);

	List<User> findByFavoriteLanguageLanguageAndFavoriteLanguageExperienceLevel(String programmingLanguage,
			String level);
}
