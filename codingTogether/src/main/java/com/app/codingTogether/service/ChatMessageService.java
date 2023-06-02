package com.app.codingTogether.service;


import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.codingTogether.model.ChatMessage;
import com.app.codingTogether.model.User;
import com.app.codingTogether.model.DTO.ChatMessageDTO;
import com.app.codingTogether.repository.ChatMessageRepository;

@Service
public class ChatMessageService {
@Autowired
ChatMessageRepository messageRepository;

public ChatMessageDTO saveChatMessage(User u, String content) {
	ChatMessage chatMessage= new ChatMessage();
	chatMessage.setContent(content);
	chatMessage.setSender(u);
	chatMessage.setTimestamp(new Date());
	return DataToDTO.fromChatMessage(messageRepository.save(chatMessage));
}

}
