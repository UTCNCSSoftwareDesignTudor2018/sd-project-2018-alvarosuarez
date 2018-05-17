package com.example.websocketdemo.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.websocketdemo.entities.Chat;
import com.example.websocketdemo.entities.ChatMessage;
import com.example.websocketdemo.entities.ChatMessage.MessageType;
import com.example.websocketdemo.entities.User;
import com.example.websocketdemo.services.MailService;

@Controller
public class MainController {
	@Autowired
	private UserController userController;
	@Autowired
	private ChatController chatController;
	MailService mailService = new MailService("varosuarez@gmail.com"
																				,"!R1badesella");
	private Chat c;
	private User loggedUser;
	
	@RequestMapping("/")
	public String landing() {
		return "index";
	}
	@PostMapping(value = "/login")
	public String login(HttpServletRequest request, Model model) {
		String login = request.getParameter("login");
		String password = request.getParameter("password");
		loggedUser = userController.getUser(login, password);
		c = chatController.getChat("chat1");
		if(loggedUser==null){
			return "errorLogin";
		}else if(loggedUser.isAdmin() == false){
			model.addAttribute("user", loggedUser.getAlias());
			model.addAttribute("chat", this.c);
			return "chat";
		}else if(loggedUser.isAdmin() == true){
			model.addAttribute("users", userController.findAll());
			model.addAttribute("user", loggedUser.getAlias());
			model.addAttribute("chat", this.c);
			return "chatAdmin";
		}else{
			return "errorLogin";
		}
	}
	@PostMapping(value = "/reportUser")
	public String reportUser(HttpServletRequest request, Model model) {
		String alias = request.getParameter("alias");
		User u = userController.findByAlias(alias);
		if(u == null){
			return "errorLogin";
		}
		String messageHeader = "Chat: " + this.c.getChatName() +
				". Reported User with alias: " +  alias;
		String message = request.getParameter("comment");
		try{
		mailService.sendMail("varosuarez@gmail.com",messageHeader,message);
		}catch(Exception e){
			return "errorMail";
		}
		model.addAttribute("user", loggedUser.getAlias());
		model.addAttribute("chat", this.c);
		return "chat";	
	}
	@PostMapping(value = "/updateAlias")
	public String updateAlias(HttpServletRequest request, Model model) {
		String alias = request.getParameter("aliasUpdate");
		User u = userController.findByAlias(alias);
		if(u != null){
			return "errorRepeatedAlias";
		}
		loggedUser.setAlias(alias);
		try{
		userController.addUser(loggedUser);
		}catch(Exception e){
			return "errorOperation";
		}
		model.addAttribute("user", loggedUser.getAlias());
		model.addAttribute("chat", this.c);
		return "chat";	
	}
	@PostMapping(value = "/deleteUser")
	public String deleteUser(HttpServletRequest request, Model model) {
		try{
		userController.deleteUser(loggedUser);
		}catch(Exception e){
			return "errorOperation";
		}
		return "index";	
	}
	@PostMapping(value = "/backChat")
	public String backChat(HttpServletRequest request, Model model) {
		return "index";	
	}
	@PostMapping(value = "/backChatLogged")
	public String backChatLogged(HttpServletRequest request, Model model) {
		model.addAttribute("user", loggedUser.getAlias());
		model.addAttribute("chat", this.c);
		c = chatController.getChat("chat1");
		return "chat";	
	}
	@PostMapping(value = "/logOut")
	public String logOut(HttpServletRequest request, Model model) {
		return "index";	
	}
	@PostMapping(value = "/register")
	public String register(HttpServletRequest request, Model model) {
		return "register";	
	}
	@PostMapping(value = "/addUser")
	public String addUser(HttpServletRequest request, Model model) {
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String alias = request.getParameter("alias");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String nif = request.getParameter("nif");
		if(firstName.equals("") || lastName.equals("") || alias.equals("")
				|| email.equals("") || email.equals("") || password.equals("")
				|| nif.equals("")){
			return "errorBlankRegister";
		}
		if(userController.findAll()!=null){
			for(User u: userController.findAll()){
				if(alias.equals(u.getAlias())){
					return "errorRepeatedAlias";
				}else if(nif.equals(u.getNif())){
					return "errorRepeatedNif";
				}else if(email.equals(u.getEmail())){
					return "errorRepeatedEmail";
				}
			}
		}
		User u = new User();
		u.setAdmin(false);
		u.setFirstName(firstName);
		u.setLastName(lastName);
		u.setNif(nif);
		u.setEmail(email);
		u.setPassword(password);
		u.setAlias(alias);
		try{
		userController.addUser(u);
		}catch(Exception e){
			return "errorAdding";
		}
		model.addAttribute("alias", u.getAlias());
		return "SuccessRegistered";	
	}
	@PostMapping(value = "/outRegister")
	public String outRegister(HttpServletRequest request, Model model) {
		return "index";	
	}
	
	@PostMapping(value = "/notifyUser")
	public String notifyUser(HttpServletRequest request, Model model) {
		String email = request.getParameter("email");
		User u = userController.findByEmail(email);
		if(u == null){
			return "errorLogin";
		}
		String messageHeader = "Admin: " + this.loggedUser.getAlias() +
				". Notifying User with alias: " +  u.getAlias();
		String message = request.getParameter("comment");
		try{
		//Note* :again this should be user email
		mailService.sendMail("varosuarez@gmail.com",messageHeader,message);
		}catch(Exception e){
			return "errorMail";
		}
		model.addAttribute("users", userController.findAll());
		model.addAttribute("user", loggedUser.getAlias());
		model.addAttribute("chat", this.c);
		return "chatAdmin";	
	}
	@PostMapping(value = "/deleteUserAdmin")
	public String deleteUserAdmin(HttpServletRequest request, Model model) {
		String alias = request.getParameter("aliasDelete");
		User u = userController.findByAlias(alias);
		if(u == null){
			return "errorLogin";
		}else{
			try{
				userController.deleteUser(u);
				model.addAttribute("alias", u.getAlias());
				return "SuccessDeletingUser";
			}catch(Exception e){
				return "errorOperation";
			}
		}
	}
	@PostMapping(value = "/addForbidenWord")
	public String addForbidenWord(HttpServletRequest request, Model model) {
		String word = request.getParameter("word");
		for(String w: this.c.getForbidWords()){
			if(w.equals(word)){
				return "errorRepeatedWord";
			}
		}
		this.c.addWord(word);
		this.chatController.saveChat(this.c);
		model.addAttribute("users", userController.findAll());
		model.addAttribute("user", loggedUser.getAlias());
		model.addAttribute("chat", this.c);
		return "chatAdmin";
	}
	@PostMapping(value = "/deleteForbidenWord")
	public String deleteForbidenWord(HttpServletRequest request, Model model) {
		String word = request.getParameter("word");
		try{
			this.c.deleteWord(word);
		}catch(Exception e){
			return "errorNotRepeatedWord";
		}
		this.chatController.saveChat(this.c);
		model.addAttribute("users", userController.findAll());
		model.addAttribute("user", loggedUser.getAlias());
		model.addAttribute("chat", this.c);
		return "chatAdmin";
	}
	@PostMapping(value = "/outSuccessDelete")
	public String outSuccessDelete(HttpServletRequest request, Model model) {
		model.addAttribute("users", userController.findAll());
		model.addAttribute("user", loggedUser.getAlias());
		model.addAttribute("chat", this.c);
		return "chatAdmin";	
	}
	@PostMapping(value = "/backAdminChatLogged")
	public String backAdminChatLogged(HttpServletRequest request, Model model) {
		model.addAttribute("users", userController.findAll());
		model.addAttribute("user", loggedUser.getAlias());
		model.addAttribute("chat", this.c);
		c = chatController.getChat("chat1");
		return "chatAdmin";	
	}
    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
    	String[] a = chatMessage.getContent().split(" ");
    	for(String s: a){
    		for(String forbidWord: this.c.getForbidWords()){
        		if(s.equals(forbidWord)){
        			chatMessage.setContent("This message has been deleted due to bad content.");
        			break;
        		}
    		}
    	}
        return chatMessage;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(@Payload ChatMessage chatMessage,
                               SimpMessageHeaderAccessor headerAccessor) {
        // Add username in web socket session to topic/public
    	User u = userController.findByAlias(chatMessage.getSender());
    	if(u.isAdmin()){
    		 headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
    		 chatMessage.setContent("ADMIN: " + u.getAlias() + "JOINED.");
    		 chatMessage.setType(MessageType.CHAT);
    		 this.sendMessage(chatMessage);
    	}else{
    		 headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
    	}
        return chatMessage;
    }

}
