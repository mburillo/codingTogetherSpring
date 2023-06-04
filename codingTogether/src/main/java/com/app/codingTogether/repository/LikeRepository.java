package com.app.codingTogether.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.codingTogether.model.Like;
import com.app.codingTogether.model.User;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface LikeRepository extends JpaRepository<Like, Long> {

    void deleteByUser(User user);
    @Query("SELECT l FROM Like l WHERE l.user = :user")
    List<Like> findByUser(@Param("user") User user);
}
