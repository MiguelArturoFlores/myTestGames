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
	
	private ThreadCountActivate threadShare;

	private OffertGameSingleton() {
		try {

			Log.d("Inicializo el singelton de publicidad",
					"Inicializo el singelton de publicidad");
			thread = new ThreadCountActivate(240L, false);
			thread.start();

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
	
	public void showOffert(){
		try {
			if(true){return;}
			if(thread ==null){
				Log.d("NULL THREAD", "Thread pubicity was null");
				return;
			}
			if(!thread.isShow()){
				return;
			}
			HUDManagerSingleton.getInstance().addHUD(new OffertTimeHUD(),true);
			
			thread.setShow(false);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean canIShowRateMe() {
		try {
			
			LevelDAO dao = new LevelDAO(SceneManagerSingleton
					.getInstance().getActivity(),
					DatabaseDrop.DB_NAME, null,
					MainDropActivity.DB_VERSION);
			for(Level lvl : dao.loadLevelList()){
				
				if(lvl.getId()<Long.valueOf(MainDropActivity.MIN_LEVEL_TO_RATE) && !lvl.getAvalible()){
					return false;
				}
				
			}
			dao.close();
			
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public void showOffertFreeMoney() {

		try {
			TextureSingleton texture = TextureSingleton.getInstance();
			
			if(!TwitterSingleton.getInstance().isLoggedIn()){
				TwitterSingleton.getInstance().loginToTwitter();
			}else if(!UserInfoSingleton.getInstance().isHasLike()){
				SpriteLikeUsFacebook face = new SpriteLikeUsFacebook(0, 0,texture.getTextureByName("fb.png") , texture.getVertexBufferObjectManager());
				HUDManagerSingleton.getInstance().addHUD(new InformativeSpriteHUD(face,"Like our Facebook Page" ),true);
			}else if(UserInfoSingleton.getInstance().showRateme()){
				
			}else if(PublicityManagerSingleton.getInstance().hasVideo()){
				PublicityManagerSingleton.getInstance().showVideo();
				
			}else{
				HUDManagerSingleton.getInstance().addHUD(new InformativeHUD("Come back Later"),true);
				if(true){
					return;
				}
				if (threadShare == null) {
					Log.d("NULL THREAD", "Thread pubicity was null");
					HUDManagerSingleton.getInstance().addHUD(new InformativeHUD("Come back Later"),true);
					return;
				}
				if (!threadShare.isShow()) {
					HUDManagerSingleton.getInstance().addHUD(new InformativeHUD("Come back Later"),true);
					return;
				}
				//ButtonShareOnFacebook sprFace = new ButtonShareOnFacebook(0, 0,texture.getTextureByName("facebook.png") , texture.getVertexBufferObjectManager());
				
				
				
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
