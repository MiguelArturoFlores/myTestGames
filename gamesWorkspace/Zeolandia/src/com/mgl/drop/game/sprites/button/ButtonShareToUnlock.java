package com.mgl.drop.game.sprites.button;

import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MySprite;
import com.mgl.base.SpriteType;
import com.mgl.base.facebook.FacebookManager;
import com.mgl.drop.game.HUDManagerSingleton;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.database.model.Level;
import com.mgl.drop.game.scene.SceneManagerSingleton;

public class ButtonShareToUnlock extends MySprite {

	private Level level;

	public ButtonShareToUnlock(int pX, int pY, ITextureRegion textureByName,
			VertexBufferObjectManager vertexBufferObjectManager, Level level) {
		super(pX, pY, textureByName, vertexBufferObjectManager);
		// TODO Auto-generated constructor stub
		this.level = level;
	}

	@Override
	public SpriteType getSpriteType() {

		return null;
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
				
				HUDManagerSingleton.getInstance().removeAndReplaceHud();
				
				//FacebookManager.loginfacebook(SceneManagerSingleton.getInstance().getActivity(),FacebookManager.SHARE_TO_UNLOCK,level);
				
				if(FacebookManager.isLoggedIn(SceneManagerSingleton.getInstance().getActivity())){
					FacebookManager.performAction(FacebookManager.SHARE_TO_UNLOCK,level);
				}else{
					FacebookManager.facebookLogin(SceneManagerSingleton.getInstance().getActivity(),FacebookManager.SHARE_TO_UNLOCK,level);
				}
				
				
				return true;
				
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}
}