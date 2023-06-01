package com.app.codingTogether.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
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
	
	@Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u JOIN u.followers f WHERE u.id = :userToFollowId AND f.id = :followerId")
	boolean checkIfUserIsFollowingAnother(@Param("userToFollowId") Long userToFollowId, @Param("followerId") Long followerId);

	@Query(value = "SELECT * FROM users u WHERE u.id != :userId AND u.id NOT IN (SELECT follower_id FROM user_followers WHERE user_id = :userId) AND u.id NOT IN (SELECT user_id FROM user_followers WHERE follower_id = :userId) ORDER BY RAND() LIMIT :limit", nativeQuery = true)
	List<User> findRandomUsersNotFollowed(@Param("userId") Long userId, @Param("limit") int limit);

	 @EntityGraph(attributePaths = { "followers", "following" })
	 Optional<User> findUserWithFollowersAndFollowingById(Long id);
	
	 @Query("SELECT u FROM User u LEFT JOIN FETCH u.followers WHERE u.id = :id")
	    Optional<User> findUserWithFollowersById(@Param("id") Long id);

	    @Query("SELECT u FROM User u LEFT JOIN FETCH u.following WHERE u.id = :id")
	    Optional<User> findUserWithFollowingById(@Param("id") Long id);
}
