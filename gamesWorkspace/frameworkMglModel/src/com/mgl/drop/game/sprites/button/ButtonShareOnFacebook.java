package com.mgl.drop.game.sprites.button;

import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MySprite;
import com.mgl.base.SpriteType;
import com.mgl.base.facebook.FacebookManager;
import com.mgl.base.userinfo.OffertGameSingleton;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.factory.SoundSingleton;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.scene.SceneManagerSingleton;

public class ButtonShareOnFacebook extends MySprite{

	public ButtonShareOnFacebook(float pX, float pY,
			ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager);

		try {
			
		} catch (Exception e) {
			e.printStackTrace();
		}
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
				
				MainDropActivity act = SceneManagerSingleton.getInstance().getActivity();
				act.onClickPostPhoto();
				SoundSingleton.getInstance().playSound("buttonPress.mp3");
				//FacebookManager.facebookLoginAux(SceneManagerSingleton.getInstance().getActivity(), FacebookManager.SHARE_VOLUNTARY, null);
				
				
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
