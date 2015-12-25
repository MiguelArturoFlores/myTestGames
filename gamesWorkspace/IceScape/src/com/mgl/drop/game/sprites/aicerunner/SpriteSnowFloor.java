package com.mgl.drop.game.sprites.aicerunner;

import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MySprite;
import com.mgl.base.SpriteType;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.game.controller.LevelController;

public class SpriteSnowFloor extends MySprite {

	private float speed = 20;

	private float timeToLive = 1;
	private float contTime = 0;

	private float contTimeSmall = 0;
	
	public SpriteSnowFloor(float pX, float pY,
			ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager);
		// TODO Auto-generated constructor stub
	}

	public void reset() {
		try {

			this.detachSelf();
			this.setAlpha(0.6f);

			speed = 20;

			float val = MainDropActivity.getRandomMax(15, 20);

			this.setSize(val, val);

			this.setRotationCenter(this.getWidth() / 2, this.getHeight() / 2);

			this.setRotation(MainDropActivity.getRandomMax(0, 180));

			speed = speed
					+ MainDropActivity.getRandomMax(0, (int) (speed * 0.2));

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

			this.setY(this.getY() - distance);

			contTime += dTime;

			float rotation = this.getRotation();

			if (rotation > 360) {
				rotation = 0;
			}

			rotation = rotation + 1;

			this.setRotation(rotation);

			contTimeSmall +=dTime;
			
			if(contTimeSmall > 0.2f){
				contTimeSmall = 0 ;
				this.setSize(this.getWidth()*0.80f, this.getHeight()*0.80f);
			}
			
			if (contTime < timeToLive) {
				return;
			}

			this.setAlpha(this.getAlpha() - 0.02f);

			if (this.getAlpha() < 0) {
				level.removeEntity(this);
				this.detachSelf();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}