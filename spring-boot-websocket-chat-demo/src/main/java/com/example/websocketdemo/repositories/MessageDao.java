package com.example.websocketdemo.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.websocketdemo.entities.ChatMessage;
import com.example.websocketdemo.entities.User;


@Transactional
public interface MessageDao extends JpaRepository<ChatMessage, Long>  {

	ChatMessage findByUser(User user);
	List<ChatMessage> findAll();
	@SuppressWarnings("unchecked")
	ChatMessage saveAndFlush(ChatMessage m);
	void delete(ChatMessage m); 
}
