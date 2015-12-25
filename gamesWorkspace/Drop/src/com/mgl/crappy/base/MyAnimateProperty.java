package com.mgl.crappy.base;

public class MyAnimateProperty {

	private Integer position;
	private Integer quantity;
	private long[] fps;

	

	public MyAnimateProperty(Integer position, Integer quantity, long[] fps) {
		super();
		this.position = position;
		this.quantity = quantity;
		this.fps = fps;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public long[] getFps() {
		return fps;
	}

	public void setFps(long[] fps) {
		this.fps = fps;
	}


	
}
