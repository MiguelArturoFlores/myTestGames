package com.mgl.social.server.database;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "t_message")
public class Message implements Serializable {

	private Long id;
	private String message;
	private Long idReciever;
	private Long idSendUser;
	private Long idConversation; // is the id from the first message
	private Date date;
	private Long active;

	private String photo;
	private String userName;
	private boolean showMessage = false;
	private ArrayList<Message> conversationList;

	private String dateStr;
	
	// post

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Long getIdSendUser() {
		return idSendUser;
	}

	public void setIdSendUser(Long idSendUser) {
		this.idSendUser = idSendUser;
	}

	public Long getIdConversation() {
		return idConversation;
	}

	public void setIdConversation(Long idConversation) {
		this.idConversation = idConversation;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Long getIdReciever() {
		return idReciever;
	}

	public void setIdReciever(Long idReciever) {
		this.idReciever = idReciever;
	}

	@Transient
	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	@Transient
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Transient
	public boolean isShowMessage() {
		return showMessage;
	}

	public void setShowMessage(boolean showMessage) {
		this.showMessage = showMessage;
	}

	@Transient
	public ArrayList<Message> getConversationList() {
		return conversationList;
	}

	public void setConversationList(ArrayList<Message> conversationList) {
		this.conversationList = conversationList;
	}

	public Long getActive() {
		return active;
	}

	public void setActive(Long active) {
		this.active = active;
	}
	@Transient
	public String getDateStr() {
		return dateStr;
	}

	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}

}
