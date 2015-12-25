package com.mgl.base;

import android.graphics.Point;


import com.google.example.games.basegameutils.ButtonLeaderboard;
import com.mgl.base.userinfo.PoolObjectSingleton;
import com.mgl.drop.game.constant.CollitionType;
import com.mgl.drop.game.constant.ItemConstant;
import com.mgl.drop.game.constant.ZIndexGame;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.hud.sprites.SpriteMessage;
import com.mgl.drop.game.model.Item;
import com.mgl.drop.game.sprites.SpriteBackground;
import com.mgl.drop.game.sprites.SpriteCollitionRpg;
import com.mgl.drop.game.sprites.SpritePortal;
import com.mgl.drop.game.sprites.SpriteSmashGeneral;
import com.mgl.drop.game.sprites.arunner.SpriteDecorativeShot;
import com.mgl.drop.game.sprites.arunner.SpriteFloor;
import com.mgl.drop.game.sprites.arunner.SpriteGel;
import com.mgl.drop.game.sprites.arunner.SpriteHedgehog;
import com.mgl.drop.game.sprites.arunner.SpriteLake;
import com.mgl.drop.game.sprites.arunner.SpriteMeteorLevelSelect;
import com.mgl.drop.game.sprites.arunner.SpriteObstacleSmash;
import com.mgl.drop.game.sprites.arunner.SpritePlatform;
import com.mgl.drop.game.sprites.arunner.SpritePlayerDied;
import com.mgl.drop.game.sprites.arunner.SpriteReleasePlayer;
import com.mgl.drop.game.sprites.arunner.SpriteSmasher;
import com.mgl.drop.game.sprites.arunner.SpriteTurboBack;
import com.mgl.drop.game.sprites.ayoutuberunner.SpriteLikeCoinDead;
import com.mgl.drop.game.sprites.ayoutuberunner.SpritePlayerCollition;
import com.mgl.drop.game.sprites.ayoutuberunner.SpriteStarGeometry;
import com.mgl.drop.game.sprites.azeoland.SpriteEnemy;
import com.mgl.drop.game.sprites.azeoland.SpriteItem;
import com.mgl.drop.game.sprites.azeoland.SpritePlayerAdventure;
import com.mgl.drop.game.sprites.azeoland.SpriteTree;
import com.mgl.drop.game.sprites.azeoland.SpriteTurnSelect;
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

	public static SpritePlayerCollition createPlayerCollition() {
		try {

			SpritePlayerCollition player = new SpritePlayerCollition(0, 0,
					texture.getTextureByName("floor1.png"),
					texture.getVertexBufferObjectManager());
			player.setZIndex(ZIndexGame.PLAYER);
			return player;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static SpritePlayerAdventure createPlayer(LevelController level) {
		try {

			SpritePlayerAdventure player = new SpritePlayerAdventure(0, 0,
					texture.getTextureAnimateByName("zeo.png"),
					texture.getVertexBufferObjectManager(), level);
			player.setZIndex(ZIndexGame.PLAYER);
			return player;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}



	public static MySpriteGeneral createObstacle(int idSprite,
			LevelController controller) {
		MySpriteGeneral sprite = null;
		try {

			switch (idSprite) {

			// ---------------ZEOLANDIA-------------------------------------------------------
			//-----------------NPCS-----------------------------------------------------------
			case SpriteTypeConstant.HEDGEHOG2:

				SpriteHedgehog hedgehog2 = new SpriteHedgehog(200, 0,
						texture.getTextureByName("hedgehog2.png"),
						texture.getVertexBufferObjectManager());
				hedgehog2.setCollitionType(CollitionType.COLLITION_CIRCULAR);
				// hedgehog2.setXmlName("hedgehog2.xml");
				hedgehog2.setZIndex(ZIndexGame.HEDGEHOG);// /////////////////////999
				hedgehog2.setMustReload(false);
				sprite = hedgehog2;

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

			ButtonLeaderboard leaderboard = new ButtonLeaderboard(0, 0,
					texture.getTextureByName("googleLeaderboard.png"),
					texture.getVertexBufferObjectManager());
			return leaderboard;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static SpriteItem createItem(Item item){
		try {
		
			SpriteItem itemSpr = new SpriteItem(0, 0, texture.getTextureByName(item.getTextureName()), texture.getVertexBufferObjectManager());
			itemSpr.setItem(item);
			return itemSpr;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
