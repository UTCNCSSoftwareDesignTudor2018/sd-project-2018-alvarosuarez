package com.example.websocketdemo.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.websocketdemo.entities.Chat;


@Transactional
public interface ChatDao extends JpaRepository<Chat, Long>  {

	Chat findBychatName(String chatName);
	List<Chat> findAll();
	@SuppressWarnings("unchecked")
	Chat saveAndFlush(Chat c);
	void delete(Chat c); 
}
