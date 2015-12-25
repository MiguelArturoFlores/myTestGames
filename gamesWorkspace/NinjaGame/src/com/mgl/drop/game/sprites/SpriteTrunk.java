package com.mgl.drop.game.sprites;

import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MyAnimateProperty;
import com.mgl.base.MyAnimateSprite;
import com.mgl.base.SpriteType;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.game.constant.State;
import com.mgl.drop.game.constant.ZIndexGame;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.texture.TextureSingleton;

public class SpriteTrunk extends MyAnimateSprite {

	private int contRolling = 0;
	private int totalrolling ;
	
	private SpriteBackground collideShape;

	private int speed =300;

	public SpriteTrunk(float pX, float pY, ITextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager,
			LevelController level) {
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager, level);
		contRolling = 0;
		totalrolling = 2;
		
		
	}

	@Override
	public SpriteType getSpriteType() {

		return SpriteType.TRUNK;
	}

	@Override
	public boolean collidesWith(org.andengine.entity.shape.IShape pOtherShape) {
		try {
			
			return collideShape.collidesWith(pOtherShape);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public void setCollisionShape(){
		try {
			
			float percentage = 0.3f;
			
			TextureSingleton texture = TextureSingleton.getInstance();
			collideShape = new SpriteBackground(0, 0, texture.getTextureByName("black.jpg"),texture.getVertexBufferObjectManager());
			collideShape.setWidth(this.getWidth());
			collideShape.setHeight(this.getHeight()*percentage);
			collideShape.setPosition(0,this.getHeight()-this.getHeight()*percentage);
			collideShape.setAlpha(0);
			this.attachChild(collideShape);
			
//			xToMove = this.getX();
//			yToMove = this.getY();
//			totalHeight = this.getHeight();
//			
//			this.setY(yToMove +this.getHeight());
//			this.setHeight(0);
//
//			currentHeight = 0;
//			
//			status = StatusType.APPEARING;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void initAnimationParams() {
		try {
			changeAnimateState(State.TRUNK_ROLLING, true);
			anime(true);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void initHashMap() {
		fps = new long[] { 83, 83, 83, 83, 83 };

		stateAnimate
				.put(State.TRUNK_ROLLING, new MyAnimateProperty(0, 5, fps));

	}

	
	
	@Override
	public void updateAnimated(float dTime, LevelController level) {
		try {

			if(contRolling>=totalrolling){
				this.detachSelf();
				level.removeEntity(this);
			}
			
			int distance = (int) (speed * dTime);
			
			this.setY(this.getY()-distance);
			
			
			if(this.getY()<-this.getHeight() ){
				contRolling++;
				this.setY(MainDropActivity.CAMERA_HEIGHT);
			}
			

		} catch (Exception e) {
			e.printStackTrace();
		}

	}


}