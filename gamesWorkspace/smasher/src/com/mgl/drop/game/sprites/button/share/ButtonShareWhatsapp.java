package com.mgl.drop.game.sprites.button.share;

import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.content.Intent;

import com.mgl.base.MyFactory;
import com.mgl.base.MySprite;
import com.mgl.base.SpriteType;
import com.mgl.base.userinfo.UserInfoSingleton;
import com.mgl.drop.game.HUDManagerSingleton;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.hud.sprites.SpriteMessage;
import com.mgl.drop.game.scene.SceneManagerSingleton;
import com.mgl.smasher.R;

public class ButtonShareWhatsapp extends MySprite {

	public ButtonShareWhatsapp(float pX, float pY,
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
				String message = SceneManagerSingleton.getInstance()
						.getActivity()
						.getString(R.string.WHATSAPP_SHARE_MESSAGE);
				Intent sendIntent = new Intent();
				sendIntent.setAction(Intent.ACTION_SEND);
				sendIntent.putExtra(Intent.EXTRA_TEXT, message);
				sendIntent.setType("text/plain");
				sendIntent.setPackage("com.whatsapp");
				SceneManagerSingleton.getInstance().getActivity()
						.startActivity(sendIntent);
				Thread.sleep(1500);
				SceneManagerSingleton.getInstance().sendGoogleTrack(
						"Share on whatsapp");

				UserInfoSingleton.getInstance().increaseContWhatsapp();

				UserInfoSingleton.getInstance().increaseMoney(50);

				UserInfoSingleton.getInstance().setUserShareWhatsapp();

				SpriteMessage message2 = MyFactory.createMessage(
						SceneManagerSingleton.getInstance().getActivity()
								.getString(com.mgl.smasher.R.string.EARN_MONEY) + 50, 3f);
				HUDManagerSingleton.getInstance().getTop()
						.attachChild(message2);
				message2.setAutoUpdate();
				
				HUDManagerSingleton.getInstance().removeAndReplaceHud(); 

				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

}
