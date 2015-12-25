package com.mgl.base.userinfo;

import java.util.ArrayList;
import java.util.HashMap;

import com.mgl.base.MySprite;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.game.database.DatabaseDrop;
import com.mgl.drop.game.database.LevelDAO;
import com.mgl.drop.game.database.model.Score;
import com.mgl.drop.game.database.model.Trophy;
import com.mgl.drop.game.hud.sprites.SpriteScore;
import com.mgl.drop.game.scene.SceneManagerSingleton;
import com.mgl.drop.game.sprites.SpriteTrophy;
import com.mgl.drop.texture.TextureSingleton;

public class TrophySingleton {

	// constatns
	public final static int TROPHY_KILL10_BASIC = 1;
	public final static int TROPHY_KILL10_BAT = 2;
	public final static int TROPHY_KILL10_HOLE = 3;
	public final static int TROPHY_KILL10_ARMOR = 4;
	public final static int TROPHY_KILL10_ZIGZAG = 5;
	
	public final static int TROPHY_KILL100_BASIC = 6;
	public final static int TROPHY_KILL100_BAT = 7;
	public final static int TROPHY_KILL100_HOLE = 8;
	public final static int TROPHY_KILL100_ARMOR = 9;
	public final static int TROPHY_KILL100_ZIGZAG = 10;
	
	public final static int TROPHY_KILL1000_BASIC = 11;
	public final static int TROPHY_KILL1000_BAT = 12;
	public final static int TROPHY_KILL1000_HOLE = 13;
	public final static int TROPHY_KILL1000_ARMOR = 14;
	public final static int TROPHY_KILL1000_ZIGZAG = 15;
	
	public final static int TROPHY_KILL5000_BASIC = 16;
	public final static int TROPHY_KILL5000_BAT = 17;
	public final static int TROPHY_KILL5000_HOLE = 18;
	public final static int TROPHY_KILL5000_ARMOR = 19;
	public final static int TROPHY_KILL5000_ZIGZAG = 20;
	
	public final static int LIKE_US_FACEBOOK = 21;
	public final static int RATE_US = 22;
	public final static int BUY_DIAMANT = 23;
	public final static int BUY_CORPSE = 24;
	public final static int BUY_FENCE = 25;
	public final static int BUY_FIRE = 26;
	public final static int BUY_TRUNK  = 27;
	
	public final static int WIN_3_STAR  = 28;
	
	//achievements
	public final static String ACHIEVEMENT_10_BASIC = "CgkIiPvFta8TEAIQAw";
	public final static String ACHIEVEMENT_100_BASIC = "CgkIiPvFta8TEAIQBA";
	public final static String ACHIEVEMENT_1000_BASIC = " CgkIiPvFta8TEAIQBg";
	public final static String ACHIEVEMENT_5000_BASIC = "CgkIiPvFta8TEAIQBw";
	
	public final static String ACHIEVEMENT_10_BAT = "CgkIiPvFta8TEAIQCA";
	public final static String ACHIEVEMENT_100_BAT = "CgkIiPvFta8TEAIQCQ";
	public final static String ACHIEVEMENT_1000_BAT = "CgkIiPvFta8TEAIQCg";
	public final static String ACHIEVEMENT_5000_BAT = "CgkIiPvFta8TEAIQCw";
	
	public final static String ACHIEVEMENT_10_MAIDEN = "CgkIiPvFta8TEAIQDA";
	public final static String ACHIEVEMENT_100_MAIDEN = "CgkIiPvFta8TEAIQDQ";
	public final static String ACHIEVEMENT_1000_MAIDEN = "CgkIiPvFta8TEAIQDg";
	public final static String ACHIEVEMENT_5000_MAIDEN = "CgkIiPvFta8TEAIQDw";
	
	public final static String ACHIEVEMENT_10_ARMOR = "CgkIiPvFta8TEAIQEA";
	public final static String ACHIEVEMENT_100_ARMOR = "CgkIiPvFta8TEAIQEQ";
	public final static String ACHIEVEMENT_1000_ARMOR = "CgkIiPvFta8TEAIQEg";
	public final static String ACHIEVEMENT_5000_ARMOR = "CgkIiPvFta8TEAIQEw";
	 												  	
	public final static String ACHIEVEMENT_10_HOLE = "CgkIiPvFta8TEAIQFA";
	public final static String ACHIEVEMENT_100_HOLE = "CgkIiPvFta8TEAIQFQ";
	public final static String ACHIEVEMENT_1000_HOLE = "CgkIiPvFta8TEAIQFg";
	public final static String ACHIEVEMENT_5000_HOLE = "CgkIiPvFta8TEAIQFw";
	
	//leaderboards
	public final static String LEADERBOARD_GENERAL = "CgkIiPvFta8TEAIQBQ";
			
	
	private static HashMap<Integer, String> pairTrophyHash;
	private static TrophySingleton instance = null;
	private LevelDAO dao;
	private ArrayList<MySprite> spriteList;
	private TextureSingleton texture = TextureSingleton.getInstance();

	private TrophySingleton() {
		try {

			dao = new LevelDAO(SceneManagerSingleton.getInstance()
					.getActivity(), DatabaseDrop.DB_NAME, null,
					MainDropActivity.DB_VERSION);

			loadTrophyList();
			initPairHash();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initPairHash() {
		try {
			
			pairTrophyHash = new HashMap<Integer, String>();
			
			pairTrophyHash.put(TROPHY_KILL10_BASIC, ACHIEVEMENT_10_BASIC);
			pairTrophyHash.put(TROPHY_KILL100_BASIC, ACHIEVEMENT_100_BASIC);
			pairTrophyHash.put(TROPHY_KILL1000_BASIC, ACHIEVEMENT_1000_BASIC);
			pairTrophyHash.put(TROPHY_KILL5000_BASIC, ACHIEVEMENT_5000_BASIC);
			
			//bat
			pairTrophyHash.put(TROPHY_KILL10_BAT, ACHIEVEMENT_10_BAT);
			pairTrophyHash.put(TROPHY_KILL100_BAT, ACHIEVEMENT_100_BAT);
			pairTrophyHash.put(TROPHY_KILL1000_BAT, ACHIEVEMENT_1000_BAT);
			pairTrophyHash.put(TROPHY_KILL5000_BAT, ACHIEVEMENT_5000_BAT);
			
			//ARMOR
			pairTrophyHash.put(TROPHY_KILL10_ARMOR, ACHIEVEMENT_10_ARMOR);
			pairTrophyHash.put(TROPHY_KILL100_ARMOR, ACHIEVEMENT_100_ARMOR);
			pairTrophyHash.put(TROPHY_KILL1000_ARMOR, ACHIEVEMENT_1000_ARMOR);
			pairTrophyHash.put(TROPHY_KILL5000_ARMOR, ACHIEVEMENT_5000_ARMOR);
			
			//ZIGZAG
			pairTrophyHash.put(TROPHY_KILL10_ZIGZAG, ACHIEVEMENT_10_MAIDEN);
			pairTrophyHash.put(TROPHY_KILL100_ZIGZAG, ACHIEVEMENT_100_MAIDEN);
			pairTrophyHash.put(TROPHY_KILL1000_ZIGZAG, ACHIEVEMENT_1000_MAIDEN);
			pairTrophyHash.put(TROPHY_KILL5000_ZIGZAG, ACHIEVEMENT_5000_MAIDEN);
			
			//HOLE
			pairTrophyHash.put(TROPHY_KILL10_HOLE, ACHIEVEMENT_10_HOLE);
			pairTrophyHash.put(TROPHY_KILL100_HOLE, ACHIEVEMENT_100_HOLE);
			pairTrophyHash.put(TROPHY_KILL1000_HOLE, ACHIEVEMENT_1000_HOLE);
			pairTrophyHash.put(TROPHY_KILL5000_HOLE, ACHIEVEMENT_5000_HOLE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void loadTrophyList() {
		try {

			ArrayList<Trophy> trophyList = dao.loadTrophyList();
			spriteList = new ArrayList<MySprite>();

			for (Trophy s : trophyList) {

				SpriteTrophy score = new SpriteTrophy(0, 0,
						texture.getTextureByName("buttonTextureRed.png"),
						texture.getVertexBufferObjectManager(), s);
				spriteList.add(score);
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

	public static String getString(int idTrophy) {

		try {
			
			return pairTrophyHash.get(idTrophy);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
}
