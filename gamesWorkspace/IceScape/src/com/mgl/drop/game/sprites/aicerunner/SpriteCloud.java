package com.mgl.drop.game.sprites.aicerunner;

import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MySprite;
import com.mgl.base.SpriteType;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.game.controller.LevelController;

public class SpriteCloud extends MySprite{
	
	private float speed = 22;

	public SpriteCloud(float pX, float pY, ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager);
		// TODO Auto-generated constructor stub
		speed = speed + MainDropActivity.getRandomMax(0, (int) (speed*0.2));
	}

	@Override
	public SpriteType getSpriteType() {
		// TODO Auto-generated method stub
		return SpriteType.DECORATIVE;
	}

	@Override
	public void update(float dTime, LevelController level) {
		try {
			float distance = dTime * speed;
			
			this.setX(this.getX()+distance);
			
			if(this.getX() > MainDropActivity.getCAMERA_WIDTH()){
				level.removeEntity(this);
				this.detachSelf();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	
}
