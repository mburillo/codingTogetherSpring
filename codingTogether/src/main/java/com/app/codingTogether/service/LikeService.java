package com.app.codingTogether.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.codingTogether.model.Like;
import com.app.codingTogether.model.User;
import com.app.codingTogether.repository.LikeRepository;

@Service
public class LikeService {
	@Autowired
	LikeRepository likeRepository;
	 public void deleteByUser(User user) {
		 likeRepository.deleteByUser(user);
	 }
	public List<Like> getLikesByUser(User user) {
		return likeRepository.findByUser(user);
	}
	public void deleteLike(Like like) {
	likeRepository.delete(like);
		
	}
	
}
