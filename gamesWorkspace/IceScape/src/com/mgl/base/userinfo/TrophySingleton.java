package com.mgl.base.userinfo;

import java.util.ArrayList;


import com.google.example.games.basegameutils.GooglePlayGameSingleton;
import com.mgl.base.MySprite;
import com.mgl.base.publicity.ThreadCountActivate;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.game.database.DatabaseDrop;
import com.mgl.drop.game.database.LevelDAO;
import com.mgl.drop.game.database.model.Trophy;
import com.mgl.drop.game.scene.SceneManagerSingleton;
import com.mgl.drop.texture.TextureSingleton;
import com.mgl.twitter.TwitterSingleton;

public class TrophySingleton {

	// constatns
	
	public static int LIKE_US_FACEBOOK = 21;
	public static int RATE_US = 22;
	public static int BUY_DIAMANT = 23;
	public static int BUY_CORPSE = 24;
	public static int BUY_FENCE = 25;
	public static int BUY_FIRE = 26;
	public static int BUY_TRUNK  = 27;
	
	public static int WIN_3_STAR  = 28;
	
	//achievements
	public final static String ACHIEVEMENT_RATE = "CgkInbTf-9cbEAIQCA";
	public final static String ACHIEVEMENT_SHARE_FACEBOOK = "CgkInbTf-9cbEAIQCw";
	public final static String ACHIEVEMENT_SHARE_TWITTER = "CgkInbTf-9cbEAIQDA";
	public final static String ACHIEVEMENT_SHARE_WHATSAPP = "CgkInbTf-9cbEAIQCg";
	public final static String ACHIEVEMENT_LOGIN_TWITTER = "CgkInbTf-9cbEAIQCQ";
	public final static String ACHIEVEMENT_BASIC_LIKE = "CgkInbTf-9cbEAIQDQ";
	public final static String ACHIEVEMENT_MEDIUM_LIKE = "CgkInbTf-9cbEAIQDg";
	public final static String ACHIEVEMENT_SUPER_LIKE = "CgkInbTf-9cbEAIQDw";
	public final static String ACHIEVEMENT_ULTRA_LIKE = "CgkInbTf-9cbEAIQEA";
	
	

	private static TrophySingleton instance = null;
	private LevelDAO dao;
	private ArrayList<MySprite> spriteList;
	private TextureSingleton texture = TextureSingleton.getInstance();

	private ThreadCountActivate thread;
	
	private TrophySingleton() {
		try {

			dao = new LevelDAO(SceneManagerSingleton.getInstance()
					.getActivity(), DatabaseDrop.DB_NAME, null,
					MainDropActivity.DB_VERSION);

			loadTrophyList();

			thread = new ThreadCountActivate(60l, true);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void loadTrophyList() {
		try {

			if(true){
				return;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static TrophySingleton getInstance() {
		try {
			if (instance == null) {
				instance = new TrophySingleton();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return instance;
	}

	public ArrayList<MySprite> getSpriteList() {
		try {
			loadTrophyList();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return spriteList;
	}

	public void validateTrophyAcomplish(){
		try {
			
			if(true){
				return;
			}
				
			
			if(!thread.isShow()){
				return;
			}
			
			thread.setShow(true);
			
			if(UserInfoSingleton.getInstance().getStarRating()>0){
				GooglePlayGameSingleton.getInstance().unlockAchievement(ACHIEVEMENT_RATE);
			}
			if(UserInfoSingleton.getInstance().getContShareFacebook()>0){
				GooglePlayGameSingleton.getInstance().unlockAchievement(ACHIEVEMENT_SHARE_FACEBOOK);
			}
			if(UserInfoSingleton.getInstance().getContShareTwitter()>0){
				GooglePlayGameSingleton.getInstance().unlockAchievement(ACHIEVEMENT_SHARE_TWITTER);
			}
			if(UserInfoSingleton.getInstance().getContShareWhatsapp()>0){
				GooglePlayGameSingleton.getInstance().unlockAchievement(ACHIEVEMENT_SHARE_WHATSAPP);
			}
			if(TwitterSingleton.getInstance().isLoggedIn()){
				GooglePlayGameSingleton.getInstance().unlockAchievement(ACHIEVEMENT_LOGIN_TWITTER);
			}
			if(UserInfoSingleton.getInstance().getTotalLike()>100){
				//HUDManagerSingleton.getInstance().addHUD(new InformativeHUD(""+UserInfoSingleton.getInstance().getTotalLike()), true);
				GooglePlayGameSingleton.getInstance().unlockAchievement(ACHIEVEMENT_BASIC_LIKE);
			}
			if(UserInfoSingleton.getInstance().getTotalLike()>1000){
				//HUDManagerSingleton.getInstance().addHUD(new InformativeHUD(""+UserInfoSingleton.getInstance().getTotalLike()), true);
				GooglePlayGameSingleton.getInstance().unlockAchievement(ACHIEVEMENT_MEDIUM_LIKE);
			}
			if(UserInfoSingleton.getInstance().getTotalLike()>10000){
				//HUDManagerSingleton.getInstance().addHUD(new InformativeHUD(""+UserInfoSingleton.getInstance().getTotalLike()), true);
				GooglePlayGameSingleton.getInstance().unlockAchievement(ACHIEVEMENT_SUPER_LIKE);
			}
			if(UserInfoSingleton.getInstance().getTotalLike()>100000){
				//HUDManagerSingleton.getInstance().addHUD(new InformativeHUD(""+UserInfoSingleton.getInstance().getTotalLike()), true);
				GooglePlayGameSingleton.getInstance().unlockAchievement(ACHIEVEMENT_ULTRA_LIKE);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
