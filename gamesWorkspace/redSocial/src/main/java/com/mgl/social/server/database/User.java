package com.mgl.social.server.database;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "t_user")
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1047904567255761582L;

	private Long id;
	private String name;
	private String lastName;
	private String password;
	private String email;
	private Long idPhoto;
	private String description;
	private String date;
	private Long idCountry;
	private Long recieveMail;
	private Long myLanguage;

	private Long follow;
	private Long following;
	// transient
	private String passwordNew;

	public User() {
	}

	public User(String name, String lastName, String password, String email) {
		super();
		this.name = name;
		this.lastName = lastName;
		this.password = password;
		this.email = email;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return this.id;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Column(unique = true)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getIdPhoto() {
		return idPhoto;
	}

	public void setIdPhoto(Long idPhoto) {
		this.idPhoto = idPhoto;
	}

	@Column(columnDefinition = "TEXT")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Long getIdCountry() {
		return idCountry;
	}

	public void setIdCountry(Long idCountry) {
		this.idCountry = idCountry;
	}

	public Long getFollow() {
		return follow;
	}

	public void setFollow(Long follow) {
		this.follow = follow;
	}

	public Long getFollowing() {
		return following;
	}

	public void setFollowing(Long following) {
		this.following = following;
	}

	@Transient
	public String getPasswordNew() {
		return passwordNew;
	}

	public void setPasswordNew(String passwordNew) {
		this.passwordNew = passwordNew;
	}

	public Long getRecieveMail() {
		return recieveMail;
	}

	public void setRecieveMail(Long recieveMail) {
		this.recieveMail = recieveMail;
	}

	public Long getMyLanguage() {
		return myLanguage;
	}

	public void setMyLanguage(Long myLanguage) {
		this.myLanguage = myLanguage;
	}

}
