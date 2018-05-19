package com.example.websocketdemo.entities;

public class EntityFactory {
	
	public ChatEntity getEntity(String string) {
		if(string == null){
	         return null;
	      }		
	      if(string.equalsIgnoreCase("chat")){
	         return new Chat();
	         
	      } else if(string.equalsIgnoreCase("User")){
	         return new User();
	      } else if(string.equalsIgnoreCase("Message")){
	         return new ChatMessage();
	      }
	      return null;
	   }

}
