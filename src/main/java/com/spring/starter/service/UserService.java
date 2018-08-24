package com.spring.starter.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.spring.starter.config.ApiParameters;
import com.spring.starter.jwt.JwtGenerator;
import com.spring.starter.model.AuthToken;
import com.spring.starter.model.LoginCredentials;
import com.spring.starter.model.User;
import com.spring.starter.reposatory.UserReposatory;

@Service
public class UserService {
	
	private UserReposatory userReposatory;
	
	@Autowired
	ObjectMapper mapper;

	@Autowired
	public void setClientRepository(UserReposatory userReposatory) {
		this.userReposatory = userReposatory;
	}
	
	public ResponseEntity getValiedUser(LoginCredentials credentials) {
		User user = userReposatory.validateUser(credentials.getUsername(), credentials.getPassword());
		return new ResponseEntity(user,HttpStatus.OK);
	}
	
	public ObjectNode addUser(User user) {
		userReposatory.save(user);
		ObjectNode objectNode = mapper.createObjectNode();
		objectNode.put("Message", "User Addition Successfull");
		return objectNode;
	}
	
	 public ResponseEntity getRefreshToken(String refresh_token) throws Exception {
	        User resUser = userReposatory.findByRefreshToken(refresh_token);

	        if (resUser == null) {
	            throw new Exception("Invalid refresh_token");
	        }

	        String accessToken = createJWtWithOutPrefix(resUser);
	        String refreshTokenNew = createRefreshToken(resUser);
	        AuthToken authToken = new AuthToken();
	        authToken.setAccess_token(accessToken);
	        authToken.setRefresh_token(refreshTokenNew);
	        return new ResponseEntity(authToken,HttpStatus.OK);
	    }
	
	public ResponseEntity login(LoginCredentials loginCredentials) {
		User valideUser = userReposatory.validateUser(loginCredentials.getUsername(), loginCredentials.getPassword());
	
		if(valideUser == null) {
			return new ResponseEntity("Invalid login Credentials!", HttpStatus.UNAUTHORIZED);
		}
		String acessToken = createJWtWithOutPrefix(valideUser);
		String refreshToken = createRefreshToken(valideUser);
		AuthToken authToken = new AuthToken();
		authToken.setAccess_token(acessToken);
		authToken.setRefresh_token(refreshToken);
		return new ResponseEntity(authToken,HttpStatus.ACCEPTED);
	}
	
	private String createJWtWithOutPrefix(User user) {
		List<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<>();
		grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_"+user.getAppUserRole()));
		String accessToken = JwtGenerator.genarateAccessJWT(user.getName(), user.getUsername(), grantedAuthorities, ApiParameters.JWT_EXPIRATION, ApiParameters.JWT_SECRET);
		return accessToken;
	}
	
	private String createRefreshToken(User user) {
		 List<SimpleGrantedAuthority> grantedAuthorityList = new ArrayList<>();
	     grantedAuthorityList.add(new SimpleGrantedAuthority("ROLE_" + user.getAppUserRole()));
	     String refreshToken = JwtGenerator.generateRefreshToken(user.getName(), user.getUsername(),grantedAuthorityList,ApiParameters.REFRESH_TOKEN_EXPIRATION,ApiParameters.JWT_SECRET);
	     userReposatory.updateRefreshToken(user.getUsername(), refreshToken);
	     return refreshToken;
	}
	
}
