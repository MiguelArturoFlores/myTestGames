package com.mgl.social.server.database;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

@Entity
@Table(name = "t_friend")
@Proxy(lazy = false)
public class Friend {
	private Long id;
	private Long idFollowing;
	private Long idFollowed;
	private String date;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdFollowing() {
		return idFollowing;
	}

	public void setIdFollowing(Long idFollowing) {
		this.idFollowing = idFollowing;
	}

	public Long getIdFollowed() {
		return idFollowed;
	}

	public void setIdFollowed(Long idFollowed) {
		this.idFollowed = idFollowed;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
}
