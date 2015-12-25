package com.mgl.drop.game.database.model;

public class PlayerDB {

	private Integer id;
	private String name;
	private int likesQuantity;
	private int stars;
	private String texture;
	private String channelName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLikesQuantity() {
		return likesQuantity;
	}

	public void setLikesQuantity(int likesQuantity) {
		this.likesQuantity = likesQuantity;
	}

	public int getStars() {
		return stars;
	}

	public void setStars(int stars) {
		this.stars = stars;
	}

	public String getTexture() {
		return texture;
	}

	public void setTexture(String texture) {
		this.texture = texture;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

}
