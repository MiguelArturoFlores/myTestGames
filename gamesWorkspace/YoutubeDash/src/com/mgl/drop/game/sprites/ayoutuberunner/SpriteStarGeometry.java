package com.mgl.drop.game.sprites.ayoutuberunner;

import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MySprite;
import com.mgl.base.SpriteType;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.game.constant.CollitionType;
import com.mgl.drop.game.controller.LevelController;

public class SpriteStarGeometry extends MySprite{

	private float angle = 0;
	private float timeToDesapear = 1;
	private float contTime = 0;
	private float val = 1;

	public SpriteStarGeometry(float pX, float pY,
			ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager);
		// TODO Auto-generated constructor stub
		setIgnoreUpdate(true);
	}

	@Override
	public SpriteType getSpriteType() {
		// TODO Auto-generated method stub
		return SpriteType.DECORATIVE;
	}

	@Override
	public void reset(){
		try {
			this.detachSelf();
			this.setAlpha(1f);
			contTime = 0;
			timeToDesapear = 1f;
			angle= 0;
			val = 1;
			this.setCollitionType(CollitionType.COLLITION_NONE);
			
		} catch (Exception e) {

		}
		
		
	}
	
	@Override
	public void update(float dTime, LevelController level) {
		try {
			
			setRotationCenter(this.getWidth()/2, this.getHeight()/2);
			angle = angle +4 ;
			
			this.setRotation(angle);
			
			if(angle>360){
				angle=0;
			}
			
			contTime = contTime  +dTime;

			if(val>0){
				
				float alpha = 1 * contTime/timeToDesapear;
				alpha = 1 - alpha;
				this.setAlpha(alpha);
				
				if(this.getAlpha()<=0){
					
					this.setAlpha(1);
					this.detachSelf();
					level.removeEntity(this);
				}
				
				
				
		}else{
				
				float alpha = 1 * contTime/timeToDesapear;
				//alpha = 1 - alpha;
				this.setAlpha(alpha);
				if(this.getAlpha()>=1){
					val = 1;
					this.setAlpha(1);
					//this.detachSelf();
					//level.removeEntity(this);
					contTime = 0;
					this.setTimeToDesapear(MainDropActivity.getRandomMax(50, 100)/100f);
				}
				
			}
		
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public float getAngle() {
		return angle;
	}

	public void setAngle(float angle) {
		this.angle = angle;
	}

	public float getTimeToDesapear() {
		return timeToDesapear;
	}

	public void setTimeToDesapear(float timeToDesapear) {
		this.timeToDesapear = timeToDesapear;
	}

	public float getContTime() {
		return contTime;
	}

	public void setContTime(float contTime) {
		this.contTime = contTime;
	}

	public float getVal() {
		return val;
	}

	public void setVal(float val) {
		this.val = val;
	}

	
}
