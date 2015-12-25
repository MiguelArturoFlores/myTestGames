package com.mgl.base.userinfo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

import com.mgl.base.MyFactory;
import com.mgl.base.MySpriteGeneral;
import com.mgl.base.SpriteTypeConstant;
import com.mgl.drop.game.constant.GameConstants;
import com.mgl.drop.game.sprites.SpriteBloodInHud;
import com.mgl.drop.game.sprites.arunner.SpriteBurble;
import com.mgl.drop.game.sprites.arunner.SpritePlayer;
import com.mgl.drop.game.sprites.arunner.SpritePlayerDied;
import com.mgl.drop.game.sprites.arunner.SpritePlayerModel;
import com.mgl.drop.game.sprites.arunner.SpriteVulcanoBurble;
import com.mgl.drop.texture.TextureSingleton;

public class PoolObjectSingleton {

	private static PoolObjectSingleton instance = null;

	private Queue<SpriteVulcanoBurble> burbleList;

	private PoolObjectSingleton() {
		try {
			TextureSingleton texture = TextureSingleton.getInstance();

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

	public SpritePlayerModel getPlayerModelSelected() {
		try {
			TextureSingleton texture = TextureSingleton.getInstance();
			String textureName;

			int players = UserInfoSingleton.getInstance().getPlayerSelected();

			switch (players) {
			case GameConstants.PLAYER_A:

				textureName = "virusAModel.png";
				break;
			case GameConstants.PLAYER_B:

				textureName = "virusBModel.png";
				break;
			case GameConstants.PLAYER_C:

				textureName = "virusCModel.png";
				break;

			default:
				textureName = "virusAModel.png";
				break;
			}

			return new SpritePlayerModel(0, 0,
					texture.getTextureAnimateByName(textureName),
					texture.getVertexBufferObjectManager(), null);

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

				textureName = "virusA.png";
				break;
			case GameConstants.PLAYER_B:

				textureName = "virusB.png";
				break;
			case GameConstants.PLAYER_C:

				textureName = "virusC.png";
				break;

			default:
				textureName = "virusA.png";
				break;
			}

			return new SpritePlayer(0, 0,
					texture.getTextureAnimateByName(textureName),
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

			switch (players) {
			case GameConstants.PLAYER_A:

				textureName = "virusADead.png";
				break;
			case GameConstants.PLAYER_B:

				textureName = "virusBDead.png";
				break;
			case GameConstants.PLAYER_C:

				textureName = "virusCDead.png";
				break;

			default:
				textureName = "virusADead.png";

				break;
			}

			return new SpritePlayerDied(200, 0,
					texture.getTextureByName(textureName),
					texture.getVertexBufferObjectManager(), null);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

}
