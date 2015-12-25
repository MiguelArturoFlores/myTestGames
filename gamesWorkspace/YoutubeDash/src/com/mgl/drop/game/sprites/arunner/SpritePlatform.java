package com.mgl.drop.game.sprites.arunner;

import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MySprite;
import com.mgl.base.SpriteType;
import com.mgl.drop.game.controller.LevelController;

public class SpritePlatform extends MySprite{

	private float timeToChange = 0;
	private boolean horizontal = true;
	
	private float speed = 100; 
	private float contDistance = 0;
	private int direction=1;
	
	public SpritePlatform(float pX, float pY,
			ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager);
		try {
			
		} catch (Exception e) {
			
		}
	}

	@Override
	public SpriteType getSpriteType() {
		// TODO Auto-generated method stub
		return SpriteType.PLATFORM;
	}

	@Override
	public void update(float dTime, LevelController level) {
		try {
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public float getTimeToChange() {
		return timeToChange;
	}

	public void setTimeToChange(float timeToChange) {
		this.timeToChange = timeToChange;
	}

	public boolean isHorizontal() {
		return horizontal;
	}

	public void setHorizontal(boolean horizontal) {
		this.horizontal = horizontal;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public float getContDistance() {
		return contDistance;
	}

	public void setContDistance(float contDistance) {
		this.contDistance = contDistance;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	
	
	
	

}
