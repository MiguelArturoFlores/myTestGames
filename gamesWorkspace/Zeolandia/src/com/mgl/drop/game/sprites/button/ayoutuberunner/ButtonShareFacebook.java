package com.mgl.drop.game.sprites.button.ayoutuberunner;

import java.util.List;

import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;

import com.mgl.base.MyFactory;
import com.mgl.base.MySprite;
import com.mgl.base.SpriteType;
import com.mgl.base.userinfo.UserInfoSingleton;
import com.mgl.drop.game.HUDManagerSingleton;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.hud.FacebookShareHUD;
import com.mgl.drop.game.hud.LooseHUD;
import com.mgl.drop.game.hud.WhatsappShareHUD;
import com.mgl.drop.game.hud.sprites.SpriteMessage;
import com.mgl.drop.game.scene.SceneManagerSingleton;
import com.mgl.zeolandia.R;

public class ButtonShareFacebook extends MySprite{
	
	private LooseHUD looseHUD;
	
	public ButtonShareFacebook(float pX, float pY,
			ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager);
		// TODO Auto-generated constructor stub
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
				
				beginFacebookShare();
				Thread.sleep(1000);

				HUDManagerSingleton.getInstance().removeAndReplaceHud();
				
				SceneManagerSingleton.getInstance().getActivity().onPauseGame();
					
				UserInfoSingleton.getInstance().increaseMoney(100);
				UserInfoSingleton.getInstance().setUserShareFacebook();
				UserInfoSingleton.getInstance().increaseContFacebook();
					
				
				
				if(looseHUD != null){
					looseHUD.resetLevel();
					SpriteMessage message2 = MyFactory.createMessage(SceneManagerSingleton.getInstance().getActivity().getString(R.string.EARN_MONEY)+100,3f);
					HUDManagerSingleton.getInstance().getTop().attachChild(message2);
					message2.setAutoUpdate();
				}
				
				
				
				
				
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	private void beginFacebookShare() {
		try {
			String urlToShare = SceneManagerSingleton.getInstance().getActivity().getString(R.string.FACEBOOK_SHARE_URL);
			
			Intent intent = new Intent(Intent.ACTION_SEND);
			intent.setType("text/plain");
			// intent.putExtra(Intent.EXTRA_SUBJECT, "Foo bar"); // NB: has no effect!
			intent.putExtra(Intent.EXTRA_TEXT, urlToShare);

			// See if official Facebook app is found
			boolean facebookAppFound = false;
			List<ResolveInfo> matches = SceneManagerSingleton.getInstance().getActivity().getPackageManager().queryIntentActivities(intent, 0);
			for (ResolveInfo info : matches) {
			    if (info.activityInfo.packageName.toLowerCase().startsWith("com.facebook.katana")) {
			        intent.setPackage(info.activityInfo.packageName);
			        facebookAppFound = true;
			        break;
			    }
			}

			// As fallback, launch sharer.php in a browser
			if (!facebookAppFound) {
			    String sharerUrl = "https://www.facebook.com/sharer/sharer.php?u=" + urlToShare;
			    intent = new Intent(Intent.ACTION_VIEW, Uri.parse(sharerUrl));
			}

			SceneManagerSingleton.getInstance().getActivity().startActivity(intent);
			SceneManagerSingleton.getInstance().sendGoogleTrack("Share on facebook");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public LooseHUD getLooseHUD() {
		return looseHUD;
	}

	public void setLooseHUD(LooseHUD looseHUD) {
		this.looseHUD = looseHUD;
	}


}
