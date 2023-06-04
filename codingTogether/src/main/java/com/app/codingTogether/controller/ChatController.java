package com.app.codingTogether.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.app.codingTogether.model.User;
import com.app.codingTogether.model.DTO.ChatMessageDTO;
import com.app.codingTogether.model.DTO.ReceivedChatMessage;
import com.app.codingTogether.service.ChatMessageService;
import com.app.codingTogether.service.UserService;

@Controller
public class ChatController {

	@Autowired
	UserService userService;
	@Autowired
	ChatMessageService messageService;

	@MessageMapping("/chat")
	@SendTo("/topic/messages")
	public ChatMessageDTO sendMessage(@Payload ReceivedChatMessage chatMessage) {
		User u = userService.getById(chatMessage.getId());
		ChatMessageDTO storedMessage = messageService.saveChatMessage(u, chatMessage.getContent());
		return storedMessage;
	}

	@GetMapping("/getChatMessages")
	public ResponseEntity<List<ChatMessageDTO>> getLatestMessages() {
		List<ChatMessageDTO> messages = messageService.getLatestMessages();
		Collections.reverse(messages);
		return ResponseEntity.ok(messages);
	}

}
