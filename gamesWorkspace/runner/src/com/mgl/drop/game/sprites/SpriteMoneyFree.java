package com.mgl.drop.game.sprites;

import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MySprite;
import com.mgl.base.SpriteType;
import com.mgl.base.userinfo.OffertGameSingleton;
import com.mgl.drop.factory.SoundSingleton;
import com.mgl.drop.game.controller.LevelController;

public class SpriteMoneyFree extends MySprite {

	public SpriteMoneyFree(float pX, float pY,
			ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager);
		try {

			this.setIgnoreUpdate(true);
			
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	@Override
	public SpriteType getSpriteType() {
		return SpriteType.DECORATIVE;
	}

	@Override
	public void update(float dTime, LevelController level) {

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

				SoundSingleton.getInstance().playButtonSound();
				OffertGameSingleton.getInstance().showOffertFreeMoney();
				
				break;
			default:

				break;

				
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

}
