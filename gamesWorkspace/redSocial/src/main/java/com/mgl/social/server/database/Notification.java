package com.mgl.social.server.database;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "t_notification")
public class Notification implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	private Long type;
	private Long idUser;
	private Long idUserGenerator;
	private Long idUpdate;
	private Date date;
	private Long viewed;
	
	//transient
	private String userName;
	private String action;
	private String photo;

	// post

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}

	public Long getIdUser() {
		return idUser;
	}

	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}

	public Long getIdUserGenerator() {
		return idUserGenerator;
	}

	public void setIdUserGenerator(Long idUserGenerator) {
		this.idUserGenerator = idUserGenerator;
	}

	public Long getIdUpdate() {
		return idUpdate;
	}

	public void setIdUpdate(Long idUpdate) {
		this.idUpdate = idUpdate;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Long getViewed() {
		return viewed;
	}

	public void setViewed(Long viewed) {
		this.viewed = viewed;
	}
	
	@Transient
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Transient
	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
	@Transient
	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

}
