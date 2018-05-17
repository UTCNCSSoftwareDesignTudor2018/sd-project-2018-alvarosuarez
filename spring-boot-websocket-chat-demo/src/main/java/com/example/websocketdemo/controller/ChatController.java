package com.example.websocketdemo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.websocketdemo.entities.Chat;
import com.example.websocketdemo.repositories.ChatDao;

@Controller
public class ChatController {
	 @Autowired
	  private ChatDao chatDao;

	 @RequestMapping("/get-chat-by-chatName")
	  @ResponseBody
	  public Chat getChat(String name){
	    Chat chat= new Chat();
	    chat = chatDao.findBychatName(name);
	    return chat;
	  }

	public void saveChat(Chat c) {
		chatDao.saveAndFlush(c);
	}

}
