package com.mgl.social.server.database;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;


@Entity
@Table(name = "t_share")
@Proxy(lazy = false)
public class Share {
	private Long id;
	
	private Long idUser;
	private Date date;

	private Long idUpdate;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
	@Column(name = "idUser")
	public Long getIdUser() {
		return idUser;
	}

	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Long getIdUpdate() {
		return idUpdate;
	}

	public void setIdUpdate(Long idUpdate) {
		this.idUpdate = idUpdate;
	}

	
	
}
