package com.spring.starter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.starter.model.AuthToken;
import com.spring.starter.model.LoginCredentials;
import com.spring.starter.model.User;
import com.spring.starter.service.UserService;

@RestController
@RequestMapping("/login")
public class LoginController {

	private UserService userService;
	
	@Autowired
	ObjectMapper mapper;
	
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@PostMapping
	public ResponseEntity login(@RequestBody LoginCredentials credentials) {
		return userService.login(credentials);
	}
	@PostMapping("/getAccessToken")
	public ResponseEntity getaccesstoken(@RequestBody AuthToken token) throws Exception {
		return userService.getRefreshToken(token.getRefresh_token());
	}
	
}
