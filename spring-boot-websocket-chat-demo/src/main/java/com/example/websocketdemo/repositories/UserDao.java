package com.example.websocketdemo.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.websocketdemo.entities.User;


@Transactional
public interface UserDao extends JpaRepository<User, Long>{

	User findByEmailAndPassword(String email, String password);
	User findByFirstName(String firstName);
	List<User> findAll();
	@SuppressWarnings("unchecked")
	User saveAndFlush(User user);
	void delete(User user);
	User findByAlias(String alias);
	User findByEmail(String email);
}
