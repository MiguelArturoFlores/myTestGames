package com.mgl.drop.game.sprites.aicerunner;

import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MySprite;
import com.mgl.base.SpriteType;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.factory.SoundSingleton;
import com.mgl.drop.game.controller.LevelController;

public class SpriteObjectFalling extends MySprite{

	public boolean remove = false;
	
	public SpriteObjectFalling(float pX, float pY,
			ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager);
		// TODO Auto-generated constructor stub
		remove = false;
	}

	@Override
	public SpriteType getSpriteType() {
		// TODO Auto-generated method stub
		return SpriteType.DANGER;
	}

	@Override
	public void update(float dTime, LevelController level) {
		try {
			
			this.getBody().setLinearVelocity(0, 3);
			
			if(!remove){
				return;
			}
			
			level.removeEntity(this);
			this.detachSelf();
			
			level.increaseScore(1);
			
			addIceExplotion(dTime, level);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void addIceExplotion(float dTime, LevelController level) {
		try {
			
			SpriteIceExplotion ice = new SpriteIceExplotion(0, 0, texture.getTextureAnimateByName("ice.png"), texture.getVertexBufferObjectManager(), level);
			
			ice.setRotationCenter(ice.getWidth()/2, ice.getHeight()/2);
			ice.setRotation(MainDropActivity.getRandomMax(0, 360));
			
			ice.setPosition(this);
			ice.setZIndex(this.getZIndex());
			
			
			level.addSpriteToUpdate(ice);
			level.getScene().attachChild(ice);
			
			SoundSingleton.getInstance().playExplosion();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void collideWithFloor(){
		try {
			
			remove = true;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
