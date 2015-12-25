package com.mgl.base.userinfo;

import android.util.Log;








import com.mgl.base.publicity.PublicityManagerSingleton;
import com.mgl.base.publicity.ThreadCountActivate;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.game.HUDManagerSingleton;
import com.mgl.drop.game.database.DatabaseDrop;
import com.mgl.drop.game.database.LevelDAO;
import com.mgl.drop.game.database.model.Level;
import com.mgl.drop.game.hud.FacebookShareHUD;
import com.mgl.drop.game.hud.InformativeHUD;
import com.mgl.drop.game.hud.InformativeSpriteHUD;
import com.mgl.drop.game.hud.OffertTimeHUD;
import com.mgl.drop.game.hud.TwitterLoginHUD;
import com.mgl.drop.game.hud.TwitterShareHUD;
import com.mgl.drop.game.hud.WhatsappShareHUD;
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
			
			
			
			addShareButton();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void addShareButton() {
		try {
			
			if(UserInfoSingleton.getInstance().canShareWhatsapp()){
				HUDManagerSingleton.getInstance().addHUD(new WhatsappShareHUD(1), true);
				return;
			}else if(!TwitterSingleton.getInstance().isLoggedIn()){
				if(thread ==null){
					Log.d("NULL THREAD", "Thread pubicity was null");
					return;
				}
				if(!thread.isShow()){
					return;
				}
				HUDManagerSingleton.getInstance().addHUD(new TwitterLoginHUD(), true);
				thread.setShow(false);
				return;
			}else if(UserInfoSingleton.getInstance().canShareFacebook()){
				HUDManagerSingleton.getInstance().addHUD(new FacebookShareHUD(), true);
				return;
			}else if(TwitterSingleton.getInstance().isLoggedIn()){
				//HUDManagerSingleton.getInstance().addHUD(new TwitterShareHUD(), true);
				return;
			}
			
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
			SceneManagerSingleton.getInstance().sendGoogleTrack(
					"Free Money Click");
			TextureSingleton texture = TextureSingleton.getInstance();
			
			if(!UserInfoSingleton.getInstance().isHasLike()){
				SpriteLikeUsFacebook face = new SpriteLikeUsFacebook(0, 0,texture.getTextureByName("facebook.png") , texture.getVertexBufferObjectManager());
				HUDManagerSingleton.getInstance().addHUD(new InformativeSpriteHUD(face,"Like our Facebook Page" ),true);
			}else if(UserInfoSingleton.getInstance().showRateme()){
				
			}else {
				Log.d("NULL THREAD", "Thread pubicity was null");
				HUDManagerSingleton.getInstance().addHUD(new InformativeHUD("Come back Later"),true);
				
				PublicityManagerSingleton.getInstance().showVideo();
				
				
			}
				
				/*
				if (!threadShare.isShow()) {
					HUDManagerSingleton.getInstance().addHUD(new InformativeHUD("Come back Later"),true);
					return;
				}
				SceneManagerSingleton.getInstance().getActivity().showFacebookLogin();
				//ButtonShareOnFacebook sprFace = new ButtonShareOnFacebook(0, 0,texture.getTextureByName("facebook.png") , texture.getVertexBufferObjectManager());
				ButtonFacebookLogin sprFace = new ButtonFacebookLogin(0, 0,texture.getTextureByName("facebook.png") , texture.getVertexBufferObjectManager());
				HUDManagerSingleton.getInstance().addHUD(new InformativeSpriteHUD(sprFace,"Share on Facebook" ),true);
				*/
				
				
			
			
			
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
