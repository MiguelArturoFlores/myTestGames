package com.mgl.drop.game.sprites.button.ayoutuberunner;

import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MyFactory;
import com.mgl.base.MySprite;
import com.mgl.base.SpriteType;
import com.mgl.base.SpriteTypeConstant;
import com.mgl.drop.game.constant.ZIndexGame;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.sprites.SpriteBackground;
import com.mgl.drop.game.sprites.arunner.SpriteCheckPoint;

public class ButtonGenerateCheckPoint extends MySprite {

	private boolean freeze = false;
	private float timeToReload = 3.5f;
	private float contTime = timeToReload;
	private SpriteBackground reloadBar;
	private float angle = 0;
	private float maxAngle = 180;

	public ButtonGenerateCheckPoint(float pX, float pY, ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager,
			LevelController level) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager,level);
		try {

			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onAreaTouched(final TouchEvent pSceneTouchEvent,
			final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
		try {

			switch (pSceneTouchEvent.getAction()) {
			case TouchEvent.ACTION_DOWN:

				break;
			case TouchEvent.ACTION_MOVE:

				break;
			case TouchEvent.ACTION_UP:
				SpriteCheckPoint check = (SpriteCheckPoint) MyFactory.createObstacle(SpriteTypeConstant.CHECK_POINT, level);
				check.setPosition(level.getPlayer());
				check.setZIndex(ZIndexGame.CHECK_POINT);
				level.getScene().attachChild(check);
				level.setCheckPoint(check);
				
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public SpriteType getSpriteType() {
		// TODO Auto-generated method stub
		return SpriteType.DECORATIVE;
	}

	@Override
	public void update(float dTime, LevelController level) {

		
	}


	
}