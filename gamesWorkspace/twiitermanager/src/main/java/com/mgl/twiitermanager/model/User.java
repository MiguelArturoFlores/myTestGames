package com.mgl.twiitermanager.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

@Entity
@Table(name = "myUser")
@Proxy(lazy = false)
public class User {

	private Long id;
	private Long idUser;
	private String name;
	private String accessToken;
	private String tokenSecret;

	public User(){
		
	}
	
	

	public User(Long idUser, String name, String accessToken, String tokenSecret) {
		super();
		this.idUser = idUser;
		this.name = name;
		this.accessToken = accessToken;
		this.tokenSecret = tokenSecret;
	}



	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getIdUser() {
		return idUser;
	}

	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getTokenSecret() {
		return tokenSecret;
	}

	public void setTokenSecret(String tokenSecret) {
		this.tokenSecret = tokenSecret;
	}
	
	

}
