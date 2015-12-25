package com.mgl.base;

public class MyObjectGeneral {

	public float x;
	public float y;
	public int width;
	public int height;
	public int idSprite;
	public boolean isActive;
	public float time;
	private int speed;
	private int idBehaviorType;
	private int orientation;
	private int diamant;

	public MyObjectGeneral() {

	}

	public MyObjectGeneral(float x, float y, int idSprite, boolean isActive,
			float time, int width, int height) {
		super();
		this.x = x;
		this.y = y;
		this.idSprite = idSprite;
		this.isActive = isActive;
		this.time = time;
		this.width =width;
		this.height = height;
	}

	public MyObjectGeneral(float x, float y, int idSprite, boolean isActive,
			float time) {
		super();
		this.x = x;
		this.y = y;
		this.idSprite = idSprite;
		this.isActive = isActive;
		this.time = time;
		
	}
	
	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public int getIdSprite() {
		return idSprite;
	}

	public void setIdSprite(int idSprite) {
		this.idSprite = idSprite;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public float getTime() {
		return time;
	}

	public void setTime(float time) {
		this.time = time;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String print() {

		return new String("x "+x+" y"+ y+" time "+time);
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getIdBehaviorType() {
		return idBehaviorType;
	}

	public void setIdBehaviorType(int idBehaviorType) {
		this.idBehaviorType = idBehaviorType;
	}

	public int getOrientation() {
		return orientation;
	}

	public void setOrientation(int orientation) {
		this.orientation = orientation;
	}

	public int getDiamant() {
		return diamant;
	}

	public void setDiamant(int diamant) {
		
		this.diamant = diamant;
	}


	
	
}
