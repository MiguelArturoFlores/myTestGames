package com.mgl.drop.game.sprites.arunner;

import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MySprite;
import com.mgl.base.SpriteType;
import com.mgl.drop.game.controller.LevelController;

public class SpriteSmasher extends MySprite{

	private float distanceToMove = 200;
	private float distanceMoved = 0;
	private float speed = 80;
	private float speedFIX = 80;
	private float speedACCELERATE = 120;
	
	public SpriteSmasher(float pX, float pY,
			ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager);
	}

	@Override
	public SpriteType getSpriteType() {
		// TODO Auto-generated method stub
		return SpriteType.SMASHER;
	}

	@Override
	public void update(float dTime, LevelController level) {
		try {

			float distance = speed * dTime;
			float distanceAux = 0;
			
			if (speed < 0) {
				distanceAux = distance * -1;
				speed = speedFIX*-1;
			} else {
				distanceAux = distance;
				speed = speedACCELERATE;
			}

			distanceMoved = distanceMoved + distanceAux;
			LevelController.setBodyPixelPositionOld(this.getX(), this.getY()
					+ distance, getBody().getAngle(), this.getWidth(),
					this.getHeight(), body);

			if (distanceMoved > distanceToMove) {

				speed = speed * -1;
				distanceMoved = 0;

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

}
