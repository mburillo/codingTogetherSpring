package com.app.codingTogether.chat;


import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.codingTogether.model.User;
import com.app.codingTogether.service.DataToDTO;

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
