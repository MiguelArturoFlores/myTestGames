package com.mgl.drop.game.database.model;

public class Level {

	private Long id;
	private String name;
	private Boolean avalible;
	private int stars;
	private Long helpInfo;
	private int position;

	private Boolean facebook;
	private int minStar;
	
	private float red;
	private float green;
	private float blue;
	
	private float percentage;
	
	private boolean isLast = false;
	
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

	public int getMinStar() {
		return minStar;
	}

	public void setMinStar(int minStar) {
		this.minStar = minStar;
	}

	public float getRed() {
		return red;
	}

	public void setRed(float red) {
		this.red = red;
	}

	public float getGreen() {
		return green;
	}

	public void setGreen(float green) {
		this.green = green;
	}

	public float getBlue() {
		return blue;
	}

	public void setBlue(float blue) {
		this.blue = blue;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public float getPercentage() {
		return percentage;
	}

	public void setPercentage(float percentage) {
		this.percentage = percentage;
	}

	public boolean isLast() {
		return isLast;
	}

	public void setLast(boolean isLast) {
		this.isLast = isLast;
	}
	
	

	
}
