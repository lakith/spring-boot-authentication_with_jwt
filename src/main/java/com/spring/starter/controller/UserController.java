package com.spring.starter.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.spring.starter.model.User;
import com.spring.starter.reposatory.UserReposatory;
import com.spring.starter.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	private UserService userService;
	private UserReposatory userReposatory;
	
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@Autowired
	public void setClientRepository(UserReposatory userReposatory) {
		this.userReposatory = userReposatory;
	}

	@PostMapping
	public ObjectNode addUser(@RequestBody User user) {
		return userService.addUser(user);
	}
	
	@GetMapping
	public List<User> getAllUsers() {
		return userReposatory.findAll();
	}
	
	@PutMapping
	public ObjectNode updateUser(@RequestBody User user) {
		return userService.addUser(user);
	}
}
