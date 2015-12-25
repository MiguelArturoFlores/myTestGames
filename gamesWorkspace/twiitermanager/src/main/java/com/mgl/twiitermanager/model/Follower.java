package com.mgl.twiitermanager.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

@Entity
@Table(name = "Follower")
@Proxy(lazy = false)
public class Follower {

	private Long id;
	private Long idMyUser;
	private Long idFollower;
	private String name;
	private String followDate;
	private Long followMe;
	
	public Follower(){
		
	}
	
	public Follower(Long idMyUser, Long idFollower, String name, String followDate) {
		super();
		this.idMyUser = idMyUser;
		this.idFollower = idFollower;
		this.name = name;
		this.followDate = followDate;
		this.followMe = 0L;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdMyUser() {
		return idMyUser;
	}

	public void setIdMyUser(Long idMyUser) {
		this.idMyUser = idMyUser;
	}

	public Long getIdFollower() {
		return idFollower;
	}

	public void setIdFollower(Long idFollower) {
		this.idFollower = idFollower;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFollowDate() {
		return followDate;
	}

	public void setFollowDate(String followDate) {
		this.followDate = followDate;
	}

	public Long getFollowMe() {
		return followMe;
	}

	public void setFollowMe(Long followMe) {
		this.followMe = followMe;
	}

	
	
}
