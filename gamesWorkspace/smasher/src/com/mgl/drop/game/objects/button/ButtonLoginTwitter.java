package com.mgl.drop.game.objects.button;

import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MySprite;
import com.mgl.base.SpriteType;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.hud.LooseHUD;
import com.mgl.twitter.TwitterSingleton;

public class ButtonLoginTwitter extends MySprite{

	private LooseHUD looseHUD;
	
	public ButtonLoginTwitter(float pX, float pY,
			ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager);
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
				TwitterSingleton.getInstance().loginToTwitter();
				
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	public LooseHUD getLooseHUD() {
		return looseHUD;
	}

	public void setLooseHUD(LooseHUD looseHUD) {
		this.looseHUD = looseHUD;
	}
	

	
	
}
