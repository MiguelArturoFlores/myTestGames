package com.mgl.drop.game.sprites.aicerunner;

import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MySprite;
import com.mgl.base.SpriteType;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.game.controller.LevelController;

public class SpriteSnow extends MySprite{
	
	private float speed = 50;

	private float contTime = 0;
	
	public SpriteSnow(float pX, float pY, ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager);
		// TODO Auto-generated constructor stub
	}

	public void reset(){
		try {
			
			this.detachSelf();
			this.setAlpha(0.6f);
			
			speed = 50;
			
			float val = MainDropActivity.getRandomMax(5, 12);
			
			this.setSize(val, val);
			
			this.setRotationCenter(this.getWidth()/2,this.getHeight()/2);
			
			this.setRotation(MainDropActivity.getRandomMax(0, 180));
	
			speed = speed + MainDropActivity.getRandomMax(0, (int) (speed*0.2));
			
			contTime = 0;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
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
			
			this.setY(this.getY()+distance);
			
			float rotation = this.getRotation();			
			
			if(rotation>360){
				rotation = 0;
			}
			
			rotation = rotation + 1;
			
			this.setRotation(rotation);
			
			
			
			if(this.getY()>280){
				contTime+=dTime;
				if(contTime>0.1){
					this.setAlpha(this.getAlpha() - 0.05f);
					contTime = 0;
				}
				
			}
			
			if(this.getAlpha()<0){
				this.setAlpha(0);
			}
			
			if(this.getY()>450){
				this.detachSelf();
				level.removeEntity(this);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	
}
