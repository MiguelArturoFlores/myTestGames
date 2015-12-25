package com.mgl.base;

import com.google.example.games.basegameutils.ButtonLeaderboard;

import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.hud.sprites.SpriteMessage;
import com.mgl.drop.texture.TextureSingleton;

public class MyFactory {

	public static TextureSingleton texture = TextureSingleton.getInstance();

	public static MySpriteGeneral createSprite(int idSprite,
			LevelController lc, int width, int height, int speed,
			int idBehaviorType, int orientation, int diamant) {

		try {

			MySpriteGeneral sprite = null;

			switch (idSprite) {

			default:
				break;
			}

			MyAnimateSprite spr = (MyAnimateSprite) sprite;
			spr.setDiamant(diamant);
			if (orientation < 0) {

				spr.setSpeedX(spr.getSpeedX() * -1);
				return spr;
			}

			return sprite;

		} catch (Exception e) {
			return null;
		}

	}

	
	public static MySpriteGeneral createObstacle(int idSprite,
			LevelController controller) {
		MySpriteGeneral sprite = null;
		try {

			switch (idSprite) {
			
			case SpriteTypeConstant.BEGIN_POINT:

				break;
			default:
				break;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return sprite;
	}


	public static SpriteMessage createMessage(String message, float timeToShow) {
		try {

			return new SpriteMessage(0, 0,
					texture.getTextureByName("black.jpg"),
					texture.getVertexBufferObjectManager(), message, timeToShow);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static ButtonLeaderboard createButtonLeaderBoard() {
		try {
			
			ButtonLeaderboard leaderboard = new ButtonLeaderboard(0, 0, texture.getTextureByName("googleLeaderboard.png"), texture.getVertexBufferObjectManager());
			return leaderboard;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	

}
