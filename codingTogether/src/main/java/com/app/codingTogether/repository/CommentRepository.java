package com.app.codingTogether.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.codingTogether.model.Comment;
import com.app.codingTogether.model.User;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface CommentRepository extends JpaRepository<Comment, Long> {

	@Query("SELECT c FROM Comment c WHERE c.user = :currentUser OR c.user IN (SELECT f FROM User u JOIN u.following f WHERE u = :currentUser) OR c.id IN (SELECT r.id FROM User u JOIN u.following f JOIN f.repostedComments r WHERE u = :currentUser) ORDER BY c.createdAt DESC")
	List<Comment> getCommentsFromFollowingUsers(@Param("currentUser") User currentUser);

	 @Modifying
	    @Query("DELETE FROM Comment c WHERE c.user = :user")
	    void deleteByUser(@Param("user") User user);
}
