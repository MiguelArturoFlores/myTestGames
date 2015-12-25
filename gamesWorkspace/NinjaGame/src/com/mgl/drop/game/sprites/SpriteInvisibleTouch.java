package com.mgl.drop.game.sprites;

import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MySprite;
import com.mgl.base.SpriteType;
import com.mgl.drop.game.controller.LevelController;

public class SpriteInvisibleTouch extends MySprite {

	public SpriteInvisibleTouch(float pX, float pY, float pWidth,
			float pHeight, ITextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager, Sprite spr) {
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager);

		this.spr = spr;
		this.setIgnoreUpdate(true);

	}

	public Sprite spr;

	@Override
	public boolean onAreaTouched(final TouchEvent pSceneTouchEvent,
			final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
		try {
			if(spr==null){
				return true;
			}
			spr.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX,
					pTouchAreaLocalY);

			

		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public SpriteType getSpriteType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(float dTime, LevelController level) {
		// TODO Auto-generated method stub

	}

}
