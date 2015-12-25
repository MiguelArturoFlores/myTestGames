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
import com.mgl.drop.game.hud.LooseHUD;
import com.mgl.drop.game.hud.WhatsappShareHUD;
import com.mgl.drop.game.hud.sprites.SpriteMessage;
import com.mgl.drop.game.scene.SceneManagerSingleton;
import com.mgl.youtuberunner.R;

public class ButtonShareVolontary extends MySprite{
	
	private  WhatsappShareHUD whatsappShareHUD;
	
	private int type;
	
	public static final int WHATSAPP = 0;
	public static final int FACEBOOK = 1;
	public static final int TWITTER = 2;
	
	public ButtonShareVolontary(float pX, float pY,
			ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager, int type) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager);
		this.type = type;
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
				
				switch (type) {
				case WHATSAPP:
						shareWahtsapp();
					break;

				case FACEBOOK:
						shareFacebook();
					break;
				case TWITTER:
					shareTwitter();
				break;
				default:
					break;
				}
				
				
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	private void shareTwitter() {
		// TODO Auto-generated method stub
		
	}

	private void shareFacebook() {
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
			SceneManagerSingleton.getInstance().sendGoogleTrack("Share on facebook volontary");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void shareWahtsapp() {
		try {
			
			String message = SceneManagerSingleton.getInstance().getActivity().getString(R.string.WHATSAPP_SHARE_MESSAGE);
			Intent sendIntent = new Intent();
			sendIntent.setAction(Intent.ACTION_SEND);
			sendIntent.putExtra(Intent.EXTRA_TEXT, message);
			sendIntent.setType("text/plain");
			sendIntent.setPackage("com.whatsapp");
			SceneManagerSingleton.getInstance().getActivity().startActivity(sendIntent);
			
			SceneManagerSingleton.getInstance().sendGoogleTrack("Share on whatsapp Volontary");
			
			
			UserInfoSingleton.getInstance().increaseContWhatsapp();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void setWhatsappHUD(WhatsappShareHUD whatsappShareHUD) {
		try {
			
			this.whatsappShareHUD = whatsappShareHUD;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public WhatsappShareHUD getWhatsappShareHUD() {
		return whatsappShareHUD;
	}

	public void setWhatsappShareHUD(WhatsappShareHUD whatsappShareHUD) {
		this.whatsappShareHUD = whatsappShareHUD;
	}

}
