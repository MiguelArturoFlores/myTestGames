package com.mgl.drop.game.sprites.button.ayoutuberunner;

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
import com.mgl.drop.game.hud.InformativeHUD;
import com.mgl.drop.game.hud.LooseHUD;
import com.mgl.drop.game.hud.WhatsappShareHUD;
import com.mgl.drop.game.hud.sprites.SpriteMessage;
import com.mgl.drop.game.scene.SceneManagerSingleton;
import com.mgl.twitter.TwitterSingleton;
import com.mgl.youtuberdash.R;

public class ButtonShareWhatsapp extends MySprite{
	
	private  WhatsappShareHUD whatsappShareHUD;
	private LooseHUD looseHUD;
	
	public ButtonShareWhatsapp(float pX, float pY,
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
				String message = SceneManagerSingleton.getInstance().getActivity().getString(R.string.WHATSAPP_SHARE_MESSAGE);
				Intent sendIntent = new Intent();
				sendIntent.setAction(Intent.ACTION_SEND);
				sendIntent.putExtra(Intent.EXTRA_TEXT, message);
				sendIntent.setType("text/plain");
				sendIntent.setPackage("com.whatsapp");
				SceneManagerSingleton.getInstance().getActivity().startActivity(sendIntent);
				Thread.sleep(1500);
				SceneManagerSingleton.getInstance().sendGoogleTrack("Share on whatsapp");
				
				
				UserInfoSingleton.getInstance().increaseContWhatsapp();
				SceneManagerSingleton.getInstance().getActivity().onPauseGame();
				if(whatsappShareHUD!=null){
					whatsappShareHUD.increaseCont();
					
					UserInfoSingleton.getInstance().increaseMoney(150);
					
					UserInfoSingleton.getInstance().setUserShareWhatsapp();
					
					SpriteMessage message2 = MyFactory.createMessage(SceneManagerSingleton.getInstance().getActivity().getString(R.string.EARN_MONEY)+150,3f); 
					HUDManagerSingleton.getInstance().getTop().attachChild(message2);
					message2.setAutoUpdate();
					
				}else if(looseHUD!=null){
					UserInfoSingleton.getInstance().increaseMoney(150);
					UserInfoSingleton.getInstance().setUserShareWhatsapp();
					
					SpriteMessage message2 = MyFactory.createMessage(SceneManagerSingleton.getInstance().getActivity().getString(R.string.EARN_MONEY)+150,3f); 
					looseHUD.resetLevel();
					
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

	public LooseHUD getLooseHUD() {
		return looseHUD;
	}

	public void setLooseHUD(LooseHUD looseHUD) {
		this.looseHUD = looseHUD;
	}

	
	
}
