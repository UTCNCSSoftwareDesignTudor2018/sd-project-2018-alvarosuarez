package com.example.websocketdemo.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Chat implements ChatEntity{
	@Id
	@GeneratedValue
	private Long id;
	@Column(unique = true, nullable = true)
	private String chatName;
	@OneToMany(targetEntity = User.class, mappedBy = "chat")
	private List<User> users = new ArrayList<User>();
	@ElementCollection(targetClass=String.class)
	private List<String> forbidWords;
	public Chat(Long id, String chatName, List<User> users, List<String> forbidWords) {
		super();
		this.id = id;
		this.chatName = chatName;
		this.users = users;
		this.forbidWords = forbidWords;
	}
	public Chat() {
		super();
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getChatName() {
		return chatName;
	}
	public void setChatName(String chatName) {
		this.chatName = chatName;
	}
	public List<User> getUsers() {
		return users;
	}
	public void setUsers(List<User> users) {
		this.users = users;
	}
	public List<String> getForbidWords() {
		return forbidWords;
	}
	public void setForbidWords(List<String> forbidWords) {
		this.forbidWords = forbidWords;
	}
	public void addWord(String word){
		this.forbidWords.add(word);
	}
	public void deleteWord(String word){
		this.forbidWords.remove(word);
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((chatName == null) ? 0 : chatName.hashCode());
		result = prime * result + ((forbidWords == null) ? 0 : forbidWords.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((users == null) ? 0 : users.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Chat other = (Chat) obj;
		if (chatName == null) {
			if (other.chatName != null)
				return false;
		} else if (!chatName.equals(other.chatName))
			return false;
		if (forbidWords == null) {
			if (other.forbidWords != null)
				return false;
		} else if (!forbidWords.equals(other.forbidWords))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (users == null) {
			if (other.users != null)
				return false;
		} else if (!users.equals(other.users))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Chat [id=" + id + ", chatName=" + chatName + ", users=" + users + ", forbidWords=" + forbidWords + "]";
	}
	
}
