package com.mgl.drop.game.sprites.button.share;

import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MyFactory;
import com.mgl.base.MySprite;
import com.mgl.base.SpriteType;
import com.mgl.drop.game.HUDManagerSingleton;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.hud.sprites.SpriteMessage;
import com.mgl.drop.game.scene.SceneManagerSingleton;
import com.mgl.twitter.TwitterSingleton;

public class ButtonShareTwitter extends MySprite{

	public ButtonShareTwitter(float pX, float pY,
			ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager);
		// TODO Auto-generated constructor stub
	}

	@Override
	public SpriteType getSpriteType() {
		// TODO Auto-generated method stub
		return SpriteType.DECORATIVE;
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
				TwitterSingleton.getInstance().postTweet();
				HUDManagerSingleton.getInstance().removeAndReplaceHud();
				
				SpriteMessage message2 = MyFactory.createMessage(
						SceneManagerSingleton.getInstance().getActivity()
								.getString(com.mgl.smasher.R.string.SHARE_SUCCESSFULL), 3f);
				HUDManagerSingleton.getInstance().getTop()
						.attachChild(message2);
				message2.setAutoUpdate();
				
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	
	
}
