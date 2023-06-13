package com.app.codingTogether.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.codingTogether.model.Like;
import com.app.codingTogether.model.Reply;
import com.app.codingTogether.model.User;
import com.app.codingTogether.repository.LikeRepository;
import com.app.codingTogether.repository.ReplyRepository;

@Service
public class ReplyService {
	@Autowired
	ReplyRepository replyRepository;
	 public void deleteByUser(User user) {
		 replyRepository.deleteByUser(user);
	 }
	public List<Reply> getRepliesByUser(User user) {
		return replyRepository.findByUser(user);
	}
	public void deleteReply(Reply reply) {
		replyRepository.delete(reply);
		
	}
	
}