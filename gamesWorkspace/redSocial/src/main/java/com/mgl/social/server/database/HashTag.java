package com.mgl.social.server.database;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_hashtag")
public class HashTag implements Serializable{

	private Long id;
	private String name;
	private Long idUpdate;

	// post

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

	public Long getIdUpdate() {
		return idUpdate;
	}

	public void setIdUpdate(Long idUpdate) {
		this.idUpdate = idUpdate;
	}

	
}
