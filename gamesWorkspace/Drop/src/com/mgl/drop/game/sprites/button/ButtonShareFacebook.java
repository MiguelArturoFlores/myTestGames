package com.mgl.drop.game.sprites.button;

import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MySprite;
import com.mgl.base.SpriteType;
import com.mgl.base.facebook.FacebookManager;
import com.mgl.drop.MainDropActivity;
import com.mgl.crappypigeon.R;
import com.mgl.drop.game.HUDManagerSingleton;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.hud.InformativeHUD;
import com.mgl.drop.game.scene.SceneManagerSingleton;

public class ButtonShareFacebook extends MySprite {

	public ButtonShareFacebook(float pX, float pY, float pWidth, float pHeight,
			ITextureRegion pTextureRegion,
			VertexBufferObjectManager vertexBufferObjectManager,
			LevelController level) {
		super(pX, pY, pWidth, pHeight, pTextureRegion,
				vertexBufferObjectManager, level);

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

				if (!MainDropActivity.isConnectingToInternet()) {

					InformativeHUD hud = new InformativeHUD(
							SceneManagerSingleton.getInstance().getActivity()
									.getResources()
									.getString(R.string.noInternetConnection));
					HUDManagerSingleton.getInstance().addHUD(hud);

					return false;

				}

				if (FacebookManager.isLoggedIn(SceneManagerSingleton
						.getInstance().getActivity())) {
					FacebookManager.performAction(
							FacebookManager.SHARE_VOLUNTARY, null);
				} else {
					FacebookManager.facebookLogin(SceneManagerSingleton
							.getInstance().getActivity(),
							FacebookManager.SHARE_VOLUNTARY, null);
				}
				
				//FacebookManager.loginfacebook(SceneManagerSingleton.getInstance().getActivity(),FacebookManager.SHARE_TO_UNLOCK,null);

				break;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

}
