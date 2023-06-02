package com.app.codingTogether.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.codingTogether.model.ChatMessage;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long>{

}
