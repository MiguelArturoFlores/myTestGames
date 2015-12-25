package com.mgl.social.server.database;

import java.io.Serializable;

public class Language implements Serializable{

	private Long id;
	private String namePhoto;
	
	
	
	public Language(Long id, String namePhoto) {
		super();
		this.id = id;
		this.namePhoto = namePhoto;
	}
	
	
	
	@Override
	public boolean equals(Object obj) {

		return this.id.equals(((Language) obj).id);
	}



	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNamePhoto() {
		return namePhoto;
	}
	public void setNamePhoto(String namePhoto) {
		this.namePhoto = namePhoto;
	}
	
	
	
}
