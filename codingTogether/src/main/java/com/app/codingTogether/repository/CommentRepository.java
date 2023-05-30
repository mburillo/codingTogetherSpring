package com.app.codingTogether.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.codingTogether.model.Comment;
import com.app.codingTogether.model.User;
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>{
	
	@Query("SELECT c FROM Comment c WHERE c.user IN (:followingUsers) AND c.parentComment IS NULL OR c.user = :currentUser  AND c.parentComment IS NULL")
	List<Comment> getCommentsFromFollowingUsers(@Param("followingUsers") Set<User> followingUsers, @Param("currentUser") User currentUser);

	@Query("SELECT c FROM Comment c WHERE c.parentComment.id = :parentCommentId")
	List<Comment> findCommentsByParentCommentId(@Param("parentCommentId") Long parentCommentId);

}
