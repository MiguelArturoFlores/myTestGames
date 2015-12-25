package com.mgl.drop.game.sprites.button;

import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.mgl.base.MySprite;
import com.mgl.base.SpriteType;
import com.mgl.base.userinfo.UserInfoSingleton;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.factory.SoundSingleton;
import com.mgl.drop.game.HUDManagerSingleton;
import com.mgl.drop.game.constant.GamePurchaseConstant;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.hud.DiamantEarnHUD;
import com.mgl.drop.game.scene.SceneManagerSingleton;

public class ButtonFacebookLogin extends MySprite {

	private LoginButton loginButton;

	public ButtonFacebookLogin(float pX, float pY,
			ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager);
		try {

			loginButton = new LoginButton(SceneManagerSingleton.getInstance()
					.getActivity());
			loginButton.setWidth(300);
			loginButton.setHeight(100);
			loginButton
					.setUserInfoChangedCallback(new LoginButton.UserInfoChangedCallback() {
						
						@Override
						public void onUserInfoFetched(GraphUser user) {
							// HelloFacebookSampleActivity.this.user = user;
							SceneManagerSingleton.getInstance().getActivity()
									.updateUIFacebook();
							// It's possible that we were waiting for this.user
							// to be populated in order to post a
							// status update.
							
							
							SceneManagerSingleton.getInstance().getActivity()
									.handlePendingAction();
							
//							try {
//								Thread.sleep(2000);
//							} catch (InterruptedException e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//							}
//							SceneManagerSingleton.getInstance().getActivity()
//							.onClickPostPhoto();	

						}
					});
			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public SpriteType getSpriteType() {
		// TODO Auto-generated method stub
		return SpriteType.DECORATIVE;
	}

	@Override
	public void update(float dTime, LevelController level) {
		//

	}

	public boolean onAreaTouched(final TouchEvent pSceneTouchEvent,
			final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
		try {
			switch (pSceneTouchEvent.getAction()) {
			case TouchEvent.ACTION_DOWN:

				break;
			case TouchEvent.ACTION_MOVE:

				break;
			case TouchEvent.ACTION_UP:

				com.facebook.Session session = com.facebook.Session
						.getActiveSession();
				boolean enableButtons = (session != null && session.isOpened());

				if(enableButtons){
					SceneManagerSingleton.getInstance().getActivity()
					.onClickPostPhoto();
					
					return true;
				}
				SceneManagerSingleton.getInstance().getActivity().pendingActionPostPhoto();
				loginButton.performClick();

				HUDManagerSingleton.getInstance().removeAndReplaceHud();
				try {
					Thread.sleep((long) 2200d);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				DiamantEarnHUD hud = new DiamantEarnHUD(GamePurchaseConstant.LOGIN_ON_FACEBOOK_MONEY+"");
			    HUDManagerSingleton.getInstance().addHUD(hud,true);
			    
			    SceneManagerSingleton.getInstance().sendGoogleTrack("LOGIN Us On Facebook");
				
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
