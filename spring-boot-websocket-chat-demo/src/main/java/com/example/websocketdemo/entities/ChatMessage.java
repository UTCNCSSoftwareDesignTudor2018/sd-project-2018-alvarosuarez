package com.example.websocketdemo.entities;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
@Entity
public class ChatMessage implements ChatEntity{
	@Id
	@GeneratedValue
	private Long id;
	@Column(unique = false, nullable = true)
    private MessageType type;
	@Column(unique = false, nullable = true)
    private String content;
	@Column(unique = false, nullable = true)
    private Date date;
	@Column(unique = false, nullable = true)
    private String sender;
    @OneToOne()
	private User user;
	
    public ChatMessage() {
		super();
	}

	public enum MessageType {
        CHAT,
        JOIN,
        LEAVE
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}
