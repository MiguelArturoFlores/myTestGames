package com.mgl.twiitermanager.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

@Entity
@Table(name = "UserMessaged")
@Proxy(lazy = false)
public class UserMessaged {

	private Long id;
	private Long idUser;
	private Long idMyUser;
	private Long messageConstant;
	private String messageDate;

	public UserMessaged() {

	}

	public UserMessaged(Long idUser, Long idMyUser, Long messageConstant,
			String messageDate) {
		super();
		this.idUser = idUser;
		this.idMyUser = idMyUser;
		this.messageConstant = messageConstant;
		this.messageDate = messageDate;
	}

	public Long getIdUser() {
		return idUser;
	}

	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}

	public Long getIdMyUser() {
		return idMyUser;
	}

	public void setIdMyUser(Long idMyUser) {
		this.idMyUser = idMyUser;
	}

	public Long getMessageConstant() {
		return messageConstant;
	}

	public void setMessageConstant(Long messageConstant) {
		this.messageConstant = messageConstant;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMessageDate() {
		return messageDate;
	}

	public void setMessageDate(String messageDate) {
		this.messageDate = messageDate;
	}

}
