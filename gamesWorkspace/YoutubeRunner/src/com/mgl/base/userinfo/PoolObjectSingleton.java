package com.mgl.base.userinfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

import com.google.example.games.basegameutils.GooglePlayGameSingleton;
import com.mgl.base.MyFactory;
import com.mgl.base.MySpriteGeneral;
import com.mgl.base.SpriteTypeConstant;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.game.constant.CollitionType;
import com.mgl.drop.game.constant.GameConstants;
import com.mgl.drop.game.constant.ZIndexGame;
import com.mgl.drop.game.database.DatabaseDrop;
import com.mgl.drop.game.database.LevelDAO;
import com.mgl.drop.game.database.model.PlayerDB;
import com.mgl.drop.game.scene.SceneManagerSingleton;
import com.mgl.drop.game.sprites.SpriteBloodInHud;
import com.mgl.drop.game.sprites.arunner.SpriteBurble;
import com.mgl.drop.game.sprites.arunner.SpritePlayer;
import com.mgl.drop.game.sprites.arunner.SpritePlayerDied;
import com.mgl.drop.game.sprites.arunner.SpritePlayerModel;
import com.mgl.drop.game.sprites.arunner.SpriteStar;
import com.mgl.drop.game.sprites.arunner.SpriteVulcanoBurble;
import com.mgl.drop.game.sprites.ayoutuberunner.SpriteStarGeometry;
import com.mgl.drop.texture.TextureSingleton;

public class PoolObjectSingleton {

	private static PoolObjectSingleton instance = null;

	private Queue<SpriteVulcanoBurble> burbleList;
	private Queue<MySpriteGeneral> starList;

	private PoolObjectSingleton() {
		try {
			TextureSingleton texture = TextureSingleton.getInstance();

			starList = new LinkedList<MySpriteGeneral>();
			for (int i = 0; i < 500; i++) {
				SpriteStarGeometry starGeometry = new SpriteStarGeometry(200,
						0, texture.getTextureByName("backStar.png"),
						texture.getVertexBufferObjectManager());
				starGeometry.setSize(50, 480);
				starGeometry.setCollitionType(CollitionType.COLLITION_NONE);
				starGeometry.setZIndex(ZIndexGame.BONE);
				starList.add(starGeometry);
			}

			burbleList = new LinkedList<SpriteVulcanoBurble>();
			for (int i = 0; i < 500; i++) {
				SpriteVulcanoBurble burble = new SpriteVulcanoBurble(200, 0,
						texture.getTextureAnimateByName("burble.png"),
						texture.getVertexBufferObjectManager(), null);
				burbleList.add(burble);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static PoolObjectSingleton getInstance() {
		try {

			if (instance == null) {
				instance = new PoolObjectSingleton();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return instance;
	}

	public SpriteVulcanoBurble getBurble() {
		try {

			SpriteVulcanoBurble sprite = burbleList.poll();
			burbleList.add(sprite);

			return sprite;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public MySpriteGeneral getStar() {
		try {

			MySpriteGeneral sprite = starList.poll();
			starList.add(sprite);

			return sprite;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public SpritePlayer getPlayer() {

		try {
			TextureSingleton texture = TextureSingleton.getInstance();
			String textureName;

			int players = UserInfoSingleton.getInstance().getPlayerSelected();

			switch (players) {
			case GameConstants.PLAYER_A:

				textureName = "character1.png";
				break;
			case GameConstants.PLAYER_B:

				textureName = "character2.png";
				break;
			case GameConstants.PLAYER_C:

				textureName = "character3.png";
				break;
			case GameConstants.PLAYER_D:

				textureName = "character4.png";
				break;

			case GameConstants.PLAYER_E:

				textureName = "character5.png";
				break;
			case GameConstants.PLAYER_F:

				textureName = "character6.png";
				break;

			case GameConstants.PLAYER_G:

				textureName = "character7.png";
				break;

			case GameConstants.PLAYER_H:

				textureName = "character8.png";
				break;
			case GameConstants.PLAYER_I:

				textureName = "character9.png";
				break;
			case GameConstants.PLAYER_J:

				textureName = "character10.png";
				break;
			case GameConstants.PLAYER_K:

				textureName = "character11.png";
				break;

			default:
				textureName = "character1.png";
				break;
			}

			return new SpritePlayer(0, 0,
					texture.getTextureByName(textureName),
					texture.getVertexBufferObjectManager(), null);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public SpritePlayerDied getPlayerDead() {

		try {
			TextureSingleton texture = TextureSingleton.getInstance();
			String textureName;

			int players = UserInfoSingleton.getInstance().getPlayerSelected();

			textureName = "dead.png";

			return new SpritePlayerDied(200, 0,
					texture.getTextureByName(textureName),
					texture.getVertexBufferObjectManager(), null);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	public ArrayList<MySpriteGeneral> getPlayerList() {

		ArrayList<MySpriteGeneral> sprList = new ArrayList<MySpriteGeneral>();

		try {

			LevelDAO dao = new LevelDAO(SceneManagerSingleton.getInstance()
					.getActivity(), DatabaseDrop.DB_NAME, null,
					MainDropActivity.DB_VERSION);
			ArrayList<PlayerDB> playerList = dao.loadPlayerList();
			
			TextureSingleton texture = TextureSingleton.getInstance();
			boolean shuffle = true;
			for (PlayerDB player : playerList) {

				SpritePlayerModel spr = new SpritePlayerModel(0, 0,
						texture.getTextureByName(player.getTexture()),
						texture.getVertexBufferObjectManager(), null);
				spr.setPlayer(player);
				spr.setLeaderboardName(getLeaderboardName(player.getId()));
				sprList.add(spr);
				
				if(player.getLikesQuantity()>0){
					shuffle = false;
				}	
			}
			
			if(shuffle){
				Collections.shuffle(sprList);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return sprList;
	}

	public String getLeaderboardName(Integer id) {
		try {

			switch (id) {
			case GameConstants.PLAYER_A:
				return GooglePlayGameSingleton.LEADERBOARD_ALEXX;
			
			case GameConstants.PLAYER_B:
				return GooglePlayGameSingleton.LEADERBOARD_DROSS;
			
			case GameConstants.PLAYER_C:
				return GooglePlayGameSingleton.LEADERBOARD_FERNANFLOO;

			case GameConstants.PLAYER_D:
				return GooglePlayGameSingleton.LEADERBOARD_LUZU;

			case GameConstants.PLAYER_E:
				return GooglePlayGameSingleton.LEADERBOARD_RUBIUS;
			
			case GameConstants.PLAYER_F:
				return GooglePlayGameSingleton.LEADERBOARD_STAXX;

			case GameConstants.PLAYER_G:
				return GooglePlayGameSingleton.LEADERBOARD_VEGETTA777;

			case GameConstants.PLAYER_H:
				return GooglePlayGameSingleton.LEADERBOARD_WILLIREX;

			case GameConstants.PLAYER_I:
				return GooglePlayGameSingleton.LEADERBOARD_ADAM;

			case GameConstants.PLAYER_J:
				return GooglePlayGameSingleton.LEADERBOARD_MANGEL;
			
			case GameConstants.PLAYER_K:
				return GooglePlayGameSingleton.LEADERBOARD_GERMAN;

			default:
				break;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return GooglePlayGameSingleton.getInstance().LEADERBOARD_GENERAL;
	}

}
