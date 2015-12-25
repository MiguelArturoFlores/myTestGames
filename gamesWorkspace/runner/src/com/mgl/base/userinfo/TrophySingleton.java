package com.mgl.base.userinfo;

import java.util.ArrayList;

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
	public static int TROPHY_KILL10_BASIC = 1;
	public static int TROPHY_KILL10_BAT = 2;
	public static int TROPHY_KILL10_HOLE = 3;
	public static int TROPHY_KILL10_ARMOR = 4;
	public static int TROPHY_KILL10_ZIGZAG = 5;
	
	public static int TROPHY_KILL100_BASIC = 6;
	public static int TROPHY_KILL100_BAT = 7;
	public static int TROPHY_KILL100_HOLE = 8;
	public static int TROPHY_KILL100_ARMOR = 9;
	public static int TROPHY_KILL100_ZIGZAG = 10;
	
	public static int TROPHY_KILL1000_BASIC = 11;
	public static int TROPHY_KILL1000_BAT = 12;
	public static int TROPHY_KILL1000_HOLE = 13;
	public static int TROPHY_KILL1000_ARMOR = 14;
	public static int TROPHY_KILL1000_ZIGZAG = 15;
	
	public static int TROPHY_KILL5000_BASIC = 16;
	public static int TROPHY_KILL5000_BAT = 17;
	public static int TROPHY_KILL5000_HOLE = 18;
	public static int TROPHY_KILL5000_ARMOR = 19;
	public static int TROPHY_KILL5000_ZIGZAG = 20;
	
	public static int LIKE_US_FACEBOOK = 21;
	public static int RATE_US = 22;
	public static int BUY_DIAMANT = 23;
	public static int BUY_CORPSE = 24;
	public static int BUY_FENCE = 25;
	public static int BUY_FIRE = 26;
	public static int BUY_TRUNK  = 27;
	
	public static int WIN_3_STAR  = 28;
	
	
	

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

}
