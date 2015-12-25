package com.mgl.drop.game.database.model;

public class MyPurchase {

	private String textureName;

	private float price;
	private int quantity;
	private int id;
	private String token;
	private float newPrice;

	public MyPurchase(String textureName, float price, int quantity, int id,
			String token) {
		super();
		this.textureName = textureName;
		this.price = price;
		this.quantity = quantity;
		this.id = id;
		this.token = token;

	}

	public MyPurchase(String textureName, float price, float newPrice,
			int quantity, int id, String token) {
		super();
		this.textureName = textureName;
		this.price = price;
		this.quantity = quantity;
		this.id = id;
		this.token = token;
		this.newPrice = newPrice;

	}

	public String getTextureName() {
		return textureName;
	}

	public void setTextureName(String textureName) {
		this.textureName = textureName;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public float getNewPrice() {
		return newPrice;
	}

	public void setNewPrice(float newPrice) {
		this.newPrice = newPrice;
	}

}
