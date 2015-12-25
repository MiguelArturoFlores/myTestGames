package com.mgl.drop.game.database.model;

public class Trophy {

	
	private Long position;
	private int id;
	private String name;
	private boolean unlock;
	private String textureName;
	private String textName;

	public Trophy() {
	}

	public Trophy(int id, String name, boolean unlock) {
		super();
		this.id = id;
		this.name = name;
		this.unlock = unlock;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isUnlock() {
		return unlock;
	}

	public void setUnlock(boolean unlock) {
		this.unlock = unlock;
	}

	public Long getPosition() {
		return position;
	}

	public void setPosition(Long position) {
		this.position = position;
	}

	public String getTextureName() {
		return textureName;
	}

	public void setTextureName(String textureName) {
		this.textureName = textureName;
	}

	public String getTextName() {
		return textName;
	}

	public void setTextName(String textName) {
		this.textName = textName;
	}

	
	
}
