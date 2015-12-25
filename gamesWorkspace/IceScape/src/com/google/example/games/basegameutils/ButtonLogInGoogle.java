package com.google.example.games.basegameutils;

import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MySprite;
import com.mgl.base.SpriteType;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.scene.SceneManagerSingleton;

public class ButtonLogInGoogle extends MySprite{

	
	private boolean isLogin;
	private GooglePlayButtonBar buttonBar;
	
	public ButtonLogInGoogle(float pX, float pY,
			ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager,boolean isLogin) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager);
		this.isLogin = isLogin;
		
		
		
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

				// if (contTime >= timeToReload) {
				SceneManagerSingleton.getInstance().getActivity().runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						try {
							GooglePlayGameSingleton.getInstance().init(SceneManagerSingleton.getInstance().getActivity());
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
				
				
				if(isLogin){
					GooglePlayGameSingleton.getInstance().login(SceneManagerSingleton.getInstance().getActivity());
					buttonBar.reloadButtons(true);
				}else{
					GooglePlayGameSingleton.getInstance().logout(SceneManagerSingleton.getInstance().getActivity());
					buttonBar.reloadButtons(false);
				}
				
				
				// contTime = timeToReload;
				// }

				break;
			case TouchEvent.ACTION_MOVE:

				break;
			case TouchEvent.ACTION_UP:

				

				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	public boolean isLogin() {
		return isLogin;
	}

	public void setLogin(boolean isLogin) {
		this.isLogin = isLogin;
	}

	public GooglePlayButtonBar getButtonBar() {
		return buttonBar;
	}

	public void setButtonBar(GooglePlayButtonBar buttonBar) {
		this.buttonBar = buttonBar;
	}

	
	
}
