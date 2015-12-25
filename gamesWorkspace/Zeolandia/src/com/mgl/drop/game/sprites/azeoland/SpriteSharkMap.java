package com.mgl.drop.game.sprites.azeoland;

import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.SpriteType;
import com.mgl.drop.game.controller.LevelController;

public class SpriteSharkMap extends SpriteAnimator{

	private float speed=15;
	private float contDistance=0;
	private int direction = 1;
	private float distanceToChange = 20;
	
	public SpriteSharkMap(float pX, float pY, ITextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager,
			LevelController level, long[] fps) {
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager, level, fps);
		// TODO Auto-generated constructor stub
	}

	@Override
	public SpriteType getSpriteType() {
		// TODO Auto-generated method stub
		return SpriteType.DECORATIVE;
	}

	@Override
	public void updateAnimated(float dTime, LevelController level) {
		try {
			
			float distance = speed * dTime;
			
			contDistance +=distance;
			
			distance =distance*direction;
			
			this.setX(this.getX() + distance);

			if(contDistance>distanceToChange){
				direction = direction*-1;
				contDistance = 0;
				if(direction>=0){
					setFlippedHorizontal(false);
				}else{
					setFlippedHorizontal(true);
				}
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public float getDistanceToChange() {
		return distanceToChange;
	}

	public void setDistanceToChange(float distanceToChange) {
		this.distanceToChange = distanceToChange;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}
	

	

}
