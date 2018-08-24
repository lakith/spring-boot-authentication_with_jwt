package com.spring.starter.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class User {

	@Id
    @Column
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Column
	@NotNull
	private String name;
	
	@NotNull
	private String username;
	
	@NotNull
	private String email;
	
	@NotNull
	@JsonIgnore
	private String password;
	
	@Column
	private String appUserRole = "User";
	
	@Column
	@JsonIgnore
	private String refreshToken;
	
	
	public User(@NotNull String name, @NotNull String username, @NotNull String email, @NotNull String password,
			String appUserRole, String refreshToken) {
		super();
		this.name = name;
		this.username = username;
		this.email = email;
		this.password = password;
		this.appUserRole = appUserRole;
		this.refreshToken = refreshToken;
	}

	public User(long id, @NotNull String name, @NotNull String username, @NotNull String email,
			@NotNull String password, String appUserRole, String refreshToken) {
		super();
		this.id = id;
		this.name = name;
		this.username = username;
		this.email = email;
		this.password = password;
		this.appUserRole = appUserRole;
		this.refreshToken = refreshToken;
	}

	public User(@NotNull String name, @NotNull String username, @NotNull String email, @NotNull String password,
			String refreshToken) {
		super();
		this.name = name;
		this.username = username;
		this.email = email;
		this.password = password;
		this.refreshToken = refreshToken;
	}

	public User(long id, @NotNull String name, @NotNull String username, @NotNull String email,
			@NotNull String password, String refreshToken) {
		super();
		this.id = id;
		this.name = name;
		this.username = username;
		this.email = email;
		this.password = password;
		this.refreshToken = refreshToken;
	}

	public User(@NotNull String name, @NotNull String email, @NotNull String password, @NotNull String refreshToken) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
		this.refreshToken = refreshToken;
	}

	public User() {
		super();
	}

	public User(long id, @NotNull String name, @NotNull String email, @NotNull String password,
			@NotNull String refreshToken) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.refreshToken = refreshToken;
	}
	
	
	public String getAppUserRole() {
		return appUserRole;
	}

	public void setAppUserRole(String appUserRole) {
		this.appUserRole = appUserRole;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	
	
}
