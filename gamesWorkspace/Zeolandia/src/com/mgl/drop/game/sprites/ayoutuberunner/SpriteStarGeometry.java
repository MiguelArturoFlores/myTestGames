package com.mgl.drop.game.sprites.ayoutuberunner;

import java.util.ArrayList;

import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MySprite;
import com.mgl.base.SpriteType;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.factory.MyXmlParser;
import com.mgl.drop.game.constant.GameConstants;
import com.mgl.drop.game.controller.LevelController;

public class SpriteStarGeometry extends MySprite{

	private float angle = 0;

	private float timeToDesapear = 1;

	private float contTime = 0;
	
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

	public void reset(){
		try {
			this.detachSelf();
			contTime = 0;
			angle= 0;
			this.setAlpha(1f);
		} catch (Exception e) {

		}
		
		
	}
	
	@Override
	public void update(float dTime, LevelController level) {
		try {
			
			setRotationCenter(this.getWidth()/2, this.getHeight()/2);
			angle = angle +4;
			this.setRotation(angle);
			
			if(angle>360){
				angle=0;
			}
			
			contTime = contTime  +dTime;
			
			float alpha = 1 * contTime/timeToDesapear;
			alpha = 1 - alpha;
			
			this.setAlpha(alpha);
			
			if(this.getAlpha()<=0){
				this.detachSelf();
				level.removeEntity(this);
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

	
}
