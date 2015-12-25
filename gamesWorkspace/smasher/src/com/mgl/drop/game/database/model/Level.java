package com.mgl.drop.game.database.model;

public class Level {

	private Long id;
	private String name;
	private Boolean avalible;
	private int stars;
	private Long helpInfo;

	private Boolean facebook;
	
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

	public Boolean getAvalible() {
		return avalible;
	}

	public void setAvalible(Boolean avalible) {
		this.avalible = avalible;
	}

	public int getStars() {
		return stars;
	}

	public void setStars(int stars) {
		this.stars = stars;
	}

	public Long getHelpInfo() {
		return helpInfo;
	}

	public void setHelpInfo(Long helpInfo) {
		this.helpInfo = helpInfo;
	}

	public Boolean getFacebook() {
		return facebook;
	}

	public void setFacebook(Boolean facebook) {
		this.facebook = facebook;
	}

	
	
}
