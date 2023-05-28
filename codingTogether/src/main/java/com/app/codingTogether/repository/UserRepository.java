package com.app.codingTogether.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.codingTogether.model.User;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	 User findByUsernameAndPassword(String username, String password);
	
}
