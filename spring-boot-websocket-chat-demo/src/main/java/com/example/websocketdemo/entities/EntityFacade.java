package com.example.websocketdemo.entities;

public class EntityFacade {
	
	private Chat c;
	private User u;
	public EntityFacade(Chat c, User u) {
		super();
		this.c = c;
		this.u = u;
	}
	public String start() {
	//test purposes
       return "Chat: " + c.getChatName() + " and User:" + u.getAlias();
    }

	
}
