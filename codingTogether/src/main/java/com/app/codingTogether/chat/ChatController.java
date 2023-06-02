package com.app.codingTogether.chat;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.app.codingTogether.model.User;
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
	ChatMessageDTO storedMessage=messageService.saveChatMessage(u, chatMessage.getContent());
	System.out.println("paso aunque sea "+chatMessage.getContent()+" "+chatMessage.getId());
	return storedMessage;
}
}
