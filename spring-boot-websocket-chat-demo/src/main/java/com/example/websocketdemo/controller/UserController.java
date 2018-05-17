package com.example.websocketdemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.websocketdemo.entities.User;
import com.example.websocketdemo.repositories.UserDao;

@Controller
public class UserController {
	 @Autowired
	  private UserDao userDao;

	 @RequestMapping("/get-by-email")
	  @ResponseBody
	  public User getUser(String email, String password){
	    User user= new User();
	    user = userDao.findByEmailAndPassword(email, password);
	    return user;
	  }

	public List<User> findAll() {
		List<User> users = userDao.findAll();
		return users;
	}

	public void addUser(User u) {
		userDao.saveAndFlush(u);
	}

	public User findByAlias(String alias) {
		User user= new User();
	    user = userDao.findByAlias(alias);
	    return user;
		
	}

	public void deleteUser(User user) {
		userDao.delete(user);
		
	}

	public User findByEmail(String email) {
		return userDao.findByEmail(email);
	}

}
