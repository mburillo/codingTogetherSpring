package com.app.codingTogether.service;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

public List<ChatMessageDTO> getLatestMessages() {
	List<ChatMessage> latestMessages = messageRepository.findTop25ByOrderByTimestampDesc();
	List<ChatMessageDTO> latestMessagesDTO = new ArrayList<>();
	for(ChatMessage cm : latestMessages) latestMessagesDTO.add(DataToDTO.fromChatMessage(cm));
	return latestMessagesDTO;
}

public List<ChatMessage> getChatMessagesByUser(User user) {
	return messageRepository.findBySender(user);
}

public void deleteAll(List<ChatMessage> messagesToDelete) {
	messageRepository.deleteAll(messagesToDelete);

	
}

}
