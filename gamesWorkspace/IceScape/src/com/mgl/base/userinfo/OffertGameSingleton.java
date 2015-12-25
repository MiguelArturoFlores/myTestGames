package com.mgl.base.userinfo;

import android.util.Log;


import com.mgl.base.publicity.PublicityManagerSingleton;
import com.mgl.base.publicity.ThreadCountActivate;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.game.HUDManagerSingleton;
import com.mgl.drop.game.database.DatabaseDrop;
import com.mgl.drop.game.database.LevelDAO;
import com.mgl.drop.game.database.model.Level;
import com.mgl.drop.game.hud.InformativeHUD;
import com.mgl.drop.game.hud.InformativeSpriteHUD;
import com.mgl.drop.game.hud.OffertTimeHUD;
import com.mgl.drop.game.scene.SceneManagerSingleton;
import com.mgl.drop.game.sprites.SpriteLikeUsFacebook;
import com.mgl.drop.texture.TextureSingleton;
import com.mgl.twitter.TwitterSingleton;

public class OffertGameSingleton {

	private static OffertGameSingleton instance = null;

	private ThreadCountActivate thread;
	
	private ThreadCountActivate threadRate;

	private ThreadCountActivate threadShare;

	private OffertGameSingleton() {
		try {

			Log.d("Inicializo el singelton de publicidad",
					"Inicializo el singelton de publicidad");
			
			
			thread = new ThreadCountActivate(240L, false);
			thread.start();

			threadRate = new ThreadCountActivate(80L, false);
			threadRate.start();

			threadShare = new ThreadCountActivate(120L, true);
			threadShare.start();

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public static OffertGameSingleton getInstance() {
		if (instance == null) {
			instance = new OffertGameSingleton();
		}
		return instance;
	}

	public void showOffert() {
		try {
			if (true) {
				return;
			}
			if (thread == null) {
				Log.d("NULL THREAD", "Thread pubicity was null");
				return;
			}
			if (!thread.isShow()) {
				return;
			}
			HUDManagerSingleton.getInstance().addHUD(new OffertTimeHUD(), true);

			thread.setShow(false);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean canIShowRateMe() {
		try {
			
			if(threadRate.isShow()){
				threadRate.setShow(false);
				return true;
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public void showOffertFreeMoney() {

		try {
			TextureSingleton texture = TextureSingleton.getInstance();
			SceneManagerSingleton.getInstance().sendGoogleTrack("Want MORE MONEY FREE");
			if(UserInfoSingleton.getInstance().showRateme()){
				SceneManagerSingleton.getInstance().sendGoogleTrack("show rate me MORE MONEY FREE");
				System.out.println("show RATE ME");
			}else if(!TwitterSingleton.getInstance().isLoggedIn()){
				System.out.println("show Twitter REWARD FREE");
				TwitterSingleton.getInstance().showTwitterRewardFree();
				SceneManagerSingleton.getInstance().sendGoogleTrack("show twitter MORE MONEY FREE");
				
			}else if(PublicityManagerSingleton.getInstance().hasVideo()){
				System.out.println("show VOIDEO");
				SceneManagerSingleton.getInstance().sendGoogleTrack("show video MORE MONEY FREE");
				PublicityManagerSingleton.getInstance().showVideo();
				
			}else if(UserInfoSingleton.getInstance().shareWhatsapp()){
				SceneManagerSingleton.getInstance().sendGoogleTrack("show share whatsapp MORE MONEY FREE");
				System.out.println("share whatsapp");
			}else if(UserInfoSingleton.getInstance().shareFacebook()){
				SceneManagerSingleton.getInstance().sendGoogleTrack("show share facebook MORE MONEY FREE");
				System.out.println("share facebook");
			}else if(!UserInfoSingleton.getInstance().isHasLike()){
					SpriteLikeUsFacebook face = new SpriteLikeUsFacebook(0, 0,texture.getTextureByName("fb.png") , texture.getVertexBufferObjectManager());
					HUDManagerSingleton.getInstance().addHUD(new InformativeSpriteHUD(face,"Like our Facebook Page" ),true);
			}else{
				HUDManagerSingleton.getInstance().addHUD(new InformativeHUD("Come back Later"),true);
				if(true){
					return;
				}
			}
			
		} catch (Exception e) {

			e.printStackTrace();
		}
		
	}

	public ThreadCountActivate getThreadShare() {
		return threadShare;
	}

	public void setThreadShare(ThreadCountActivate threadShare) {
		this.threadShare = threadShare;
	}

}
