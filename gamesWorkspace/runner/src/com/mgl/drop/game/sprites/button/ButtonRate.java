package com.mgl.drop.game.sprites.button;

import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;

import com.mgl.base.MySprite;
import com.mgl.base.SpriteType;
import com.mgl.base.userinfo.UserInfoSingleton;
import com.mgl.drop.factory.SoundSingleton;
import com.mgl.drop.game.HUDManagerSingleton;
import com.mgl.drop.game.constant.GamePurchaseConstant;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.hud.DiamantEarnHUD;
import com.mgl.drop.game.hud.InformativeHUD;
import com.mgl.drop.game.scene.SceneManagerSingleton;
import com.mgl.drop.game.scene.SceneManagerSingleton.AllScenes;

public class ButtonRate extends MySprite {
	
	private boolean isRealRate = false;
	private int starNumber = 1;

	public ButtonRate(float pX, float pY, ITextureRegion pTextureRegion,
			VertexBufferObjectManager vertexBufferObjectManager, int starNumber) {
		super(pX, pY, pTextureRegion, vertexBufferObjectManager);
		isRealRate = false;
		this.starNumber = starNumber;
	}

	@Override
	public SpriteType getSpriteType() {
		// TODO Auto-generated method stub
		return SpriteType.DECORATIVE;
	}

	@Override
	public void update(float dTime, LevelController level) {
		try {

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public boolean onAreaTouched(final TouchEvent pSceneTouchEvent,
			final float pTouchAreaLocalX, final float pTouchAreaLocalY) {

		switch (pSceneTouchEvent.getAction()) {
		case TouchEvent.ACTION_DOWN:

			SoundSingleton.getInstance().playButtonSound();
			UserInfoSingleton.getInstance().setUserRate();
			UserInfoSingleton.getInstance().setStar(starNumber);
			SceneManagerSingleton.getInstance().sendGoogleTrack("Rate Us "+starNumber);
			
			if(isRealRate){
				realRate();
				return true;
			}
			
			HUDManagerSingleton.getInstance().removeAndReplaceHud();
			HUDManagerSingleton.getInstance().addHUD(new InformativeHUD("Thanks you so much!!!"), true);;

			break;
		case TouchEvent.ACTION_MOVE:

			break;
		case TouchEvent.ACTION_UP:

			break;
		}
		return true;
	}

	private void realRate() {
		try {
			
			Intent intent = new Intent(Intent.ACTION_VIEW);
			// Try Google play
			intent.setData(Uri.parse("market://details?id=com.mgl.runner"));
			if (!MyStartActivity(intent)) {
				// Market (Google play) app seems not installed, let's try to
				// open a webbrowser
				intent.setData(Uri
						.parse("https://play.google.com/store/apps/details?id=com.mgl.runner"));
				if (!MyStartActivity(intent)) {
					// Well if this also fails, we have run out of options,
					// inform the user.
					return ;

				}
			}

			HUDManagerSingleton.getInstance().removeAndReplaceHud();

			try {
				Thread.sleep((long) 1500d);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			UserInfoSingleton.getInstance().increaseMoney(
					GamePurchaseConstant.RATE_US_GOOGLE_PLAY);
			DiamantEarnHUD hud = new DiamantEarnHUD(
					GamePurchaseConstant.RATE_US_GOOGLE_PLAY + "");
			HUDManagerSingleton.getInstance().addHUD(hud, true);

			
			this.detachSelf();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
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

	public boolean isRealRate() {
		return isRealRate;
	}

	public void setRealRate(boolean isRealRate) {
		this.isRealRate = isRealRate;
	}

}
