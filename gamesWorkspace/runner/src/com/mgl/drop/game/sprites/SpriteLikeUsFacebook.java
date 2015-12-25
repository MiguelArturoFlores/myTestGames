package com.mgl.drop.game.sprites;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;

import com.mgl.base.MySprite;
import com.mgl.base.SpriteType;
import com.mgl.base.userinfo.OffertGameSingleton;
import com.mgl.base.userinfo.PurchaseSingleton;
import com.mgl.base.userinfo.UserInfoSingleton;
import com.mgl.drop.factory.SoundSingleton;
import com.mgl.drop.game.HUDManagerSingleton;
import com.mgl.drop.game.constant.GameConstants;
import com.mgl.drop.game.constant.GamePurchaseConstant;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.hud.DiamantEarnHUD;
import com.mgl.drop.game.scene.SceneManagerSingleton;

public class SpriteLikeUsFacebook extends MySprite {

	public SpriteLikeUsFacebook(float pX, float pY,
			ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager);
		this.setIgnoreUpdate(true);

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

		switch (pSceneTouchEvent.getAction()) {
		case TouchEvent.ACTION_DOWN:

			SoundSingleton.getInstance().playButtonSound();
			HUDManagerSingleton.getInstance().removeAndReplaceHud();
			
			Intent intent = new Intent(Intent.ACTION_VIEW);
			// Try Google play

			// Market (Google play) app seems not installed, let's try to
			// open a webbrowser
			intent.setData(Uri
					.parse("https://www.facebook.com/IDontRememberGames"));
			if (!MyStartActivity(intent)) {
				// Well if this also fails, we have run out of options,
				// inform the user.
				return true;
			}
			
			
			UserInfoSingleton.getInstance().increaseMoney(GamePurchaseConstant.LIKE_US_FACEBOOK_MONEY);
			
			
			try {
				Thread.sleep((long) 1500d);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			UserInfoSingleton.getInstance().setUserLikeUs();
			DiamantEarnHUD hud = new DiamantEarnHUD(GamePurchaseConstant.LIKE_US_FACEBOOK_MONEY+"");
		    HUDManagerSingleton.getInstance().addHUD(hud,true);
		    
		    SceneManagerSingleton.getInstance().sendGoogleTrack("Like Us On Facebook");
		    
			
			break;
		case TouchEvent.ACTION_MOVE:

			break;
		case TouchEvent.ACTION_UP:

			break;
		}
		return true;
	}

	private boolean MyStartActivity(Intent aIntent) {
		try {
			SceneManagerSingleton.getInstance().getActivity()
					.startActivity(aIntent);
			return true;
		} catch (ActivityNotFoundException e) {
			return false;
		}
	}

}
