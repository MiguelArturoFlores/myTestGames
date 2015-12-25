package com.mgl.twiitermanager.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

@Entity
@Table(name = "UserInfo")
@Proxy(lazy = false)
public class UserInfo {
	
	private Long id;
	private String email;
	private String twitterAccessToken;
	private String twitterSecretToken;
	private String twitterUserName;
	
	private String language;
	private Long contFacebook;
	private Long contWhatsapp;
	private Long contTwitter;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTwitterAccessToken() {
		return twitterAccessToken;
	}

	public void setTwitterAccessToken(String twitterAccessToken) {
		this.twitterAccessToken = twitterAccessToken;
	}

	public String getTwitterSecretToken() {
		return twitterSecretToken;
	}

	public void setTwitterSecretToken(String twitterSecretToken) {
		this.twitterSecretToken = twitterSecretToken;
	}

	public String getTwitterUserName() {
		return twitterUserName;
	}

	public void setTwitterUserName(String twitterUserName) {
		this.twitterUserName = twitterUserName;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public Long getContFacebook() {
		return contFacebook;
	}

	public void setContFacebook(Long contFacebook) {
		this.contFacebook = contFacebook;
	}

	public Long getContWhatsapp() {
		return contWhatsapp;
	}

	public void setContWhatsapp(Long contWhatsapp) {
		this.contWhatsapp = contWhatsapp;
	}

	public Long getContTwitter() {
		return contTwitter;
	}

	public void setContTwitter(Long contTwitter) {
		this.contTwitter = contTwitter;
	}

}
