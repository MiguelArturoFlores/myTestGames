package com.mgl.base.userinfo;

import java.util.ArrayList;

import com.google.example.games.basegameutils.GooglePlayGameSingleton;
import com.mgl.base.MySprite;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.game.database.DatabaseDrop;
import com.mgl.drop.game.database.LevelDAO;
import com.mgl.drop.game.database.model.PlayerDB;
import com.mgl.drop.game.database.model.Score;
import com.mgl.drop.game.hud.sprites.SpriteScore;
import com.mgl.drop.game.scene.SceneManagerSingleton;
import com.mgl.drop.texture.TextureSingleton;

public class ScoreSingleton {

	private static ScoreSingleton instance = null;

	private TextureSingleton texture = TextureSingleton.getInstance();

	private ArrayList<MySprite> spriteList;

	private LevelDAO dao;

	private ScoreSingleton() {
		try {

			dao = new LevelDAO(SceneManagerSingleton.getInstance()
					.getActivity(), DatabaseDrop.DB_NAME, null,
					MainDropActivity.DB_VERSION);

			loadScoreList();

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	private void loadScoreList() {
		try {

			ArrayList<Score> scoreList = dao.loadScoreList();
			spriteList = new ArrayList<MySprite>();

			for (Score s : scoreList) {

				SpriteScore score = new SpriteScore(0, 0,
						texture.getTextureByName("buttonTextureRed.png"),
						texture.getVertexBufferObjectManager(),
						s.getPosition(), s.getScore());
				spriteList.add(score);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static ScoreSingleton getInstance() {
		if (instance == null) {
			instance = new ScoreSingleton();
		}
		return instance;
	}

	public ArrayList<MySprite> getSpriteList() {
		try {
			loadScoreList();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return spriteList;
	}

	public void setSpriteList(ArrayList<MySprite> spriteList) {
		this.spriteList = spriteList;
	}

	public void newScore(int score) {
		try {

			ArrayList<Score> scoreList = dao.loadScoreList();
			boolean found = false;
			for (Score s : scoreList) {
				if (s.getScore().intValue() < score) {
					found = true;
					Long scoreAux = s.getScore();
					s.setScore(Long.valueOf(score));
					score = scoreAux.intValue();
				}
			}

			if (!found) {
				return;
			}

			dao.newScore(scoreList);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void addPlayerScore(int contLikeCoin,boolean submitScore) {
		try {

			int playerSelect = UserInfoSingleton.getInstance()
					.getPlayerSelected();
			PlayerDB playerDB = dao.loadPlayerById(playerSelect);
			playerDB.setLikesQuantity(playerDB.getLikesQuantity()
					+ contLikeCoin);
			
			dao.updatePlayerDB(playerDB.getId(), playerDB.getLikesQuantity());
			dao.updateUserInfo("money", UserInfoSingleton.getInstance().getMoney()+contLikeCoin);
			
			if(submitScore){
				GooglePlayGameSingleton.getInstance().submitScore(playerDB.getLikesQuantity(),PoolObjectSingleton.getInstance().getLeaderboardName(playerSelect));
				GooglePlayGameSingleton.getInstance().submitScore(playerDB.getLikesQuantity());
			}
			
			

			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/*public void submitLeaderBoardScoreFromPlayer(SpritePlayer player) {
		try {

			
			
			
			
			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

}
