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
import com.mgl.drop.game.sprites.aninja.SpriteDialog;
import com.mgl.drop.game.sprites.aninja.SpriteDoor;
import com.mgl.drop.game.sprites.aninja.SpriteDoorActivator;
import com.mgl.drop.game.sprites.aninja.SpriteEnemy;
import com.mgl.drop.game.sprites.aninja.SpriteEnemyLight;
import com.mgl.drop.game.sprites.aninja.SpriteEnemySamurai;
import com.mgl.drop.game.sprites.aninja.SpriteFireDecorative;
import com.mgl.drop.game.sprites.aninja.SpriteKey;
import com.mgl.drop.game.sprites.aninja.SpriteLever;
import com.mgl.drop.game.sprites.aninja.SpritePlayerAdventure;
import com.mgl.drop.game.sprites.aninja.SpriteSmoke;
import com.mgl.drop.game.sprites.aninja.SpriteVictory;
import com.mgl.drop.game.sprites.aninja.SpriteWhisthle;
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
import com.mgl.drop.game.sprites.azeoland.SpriteItem;
import com.mgl.drop.game.sprites.azeoland.SpriteTree;
import com.mgl.drop.game.sprites.azeoland.SpriteTurnSelect;
import com.mgl.drop.game.sprites.ninja.SpritePlayer;
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
			
			SpriteTree generic;
			SpriteFloor genericFloor;
			SpriteFireDecorative fireDeco;
			
			switch (idSprite) {
			
			case SpriteTypeConstant.SMOKE:
				SpriteSmoke smoke = new SpriteSmoke(0, 0, texture.getTextureAnimateByName("smoke.png"), texture.getVertexBufferObjectManager(), controller);
				smoke.setZIndex(ZIndexGame.FADE);
				sprite = smoke;
				
				break;

			case SpriteTypeConstant.KEY:
				SpriteKey key = new SpriteKey(0, 0, texture.getTextureByName("key.png"), texture.getVertexBufferObjectManager());
				key.setZIndex(ZIndexGame.FADE);
				sprite = key;
				
				break;
				
			// ---------------TRAINING-------------------------------------------------------
			case SpriteTypeConstant.DIALOG:
				
				SpriteDialog dialog = new SpriteDialog(0, 0, texture.getTextureByName("c2.png"), texture.getVertexBufferObjectManager());
				dialog.setZIndex(ZIndexGame.ORDER_OBJECT);
				dialog.setAlpha(0);
				sprite = dialog;
				break;
				
			case SpriteTypeConstant.VICTORY:
				
				SpriteVictory victory = new SpriteVictory(0, 0, texture.getTextureByName("c1.png"), texture.getVertexBufferObjectManager(),controller);
				victory.setZIndex(ZIndexGame.ORDER_OBJECT);
				victory.setAlpha(0);
				sprite = victory;
				break;
				
			case SpriteTypeConstant.WHISTHLE:
				
				SpriteWhisthle whistle = new SpriteWhisthle(0, 0, texture.getTextureAnimateByName("whisthle.png"), texture.getVertexBufferObjectManager(),controller);
				whistle.setZIndex(ZIndexGame.ORDER_OBJECT);
				sprite = whistle;
				break;
				
			case SpriteTypeConstant.LEVER:
				
				SpriteLever lever = new SpriteLever(0, 0, texture.getTextureByName("trainingLever.png"), texture.getVertexBufferObjectManager(),controller);
				lever.setZIndex(ZIndexGame.ORDER_OBJECT);
				sprite = lever;
				break;
				
			case SpriteTypeConstant.DOOR_ACTIVATOR:
				
				SpriteDoorActivator doorActivator = new SpriteDoorActivator(0, 0, texture.getTextureByName("c1.png"), texture.getVertexBufferObjectManager());
				doorActivator.setZIndex(ZIndexGame.ORDER_OBJECT);
				sprite = doorActivator;
				break;
			
			case SpriteTypeConstant.DOOR:
				
				SpriteDoor door = new SpriteDoor(0, 0, texture.getTextureByName("door.png"), texture.getVertexBufferObjectManager(), controller);
				door.setZIndex(ZIndexGame.ORDER_OBJECT);
				sprite = door;
				break;
				
			case SpriteTypeConstant.LEVEL_GATE:
				
				SpriteDoor doorWin = new SpriteDoor(0, 0, texture.getTextureByName("levelGate.png"), texture.getVertexBufferObjectManager(), controller);
				doorWin.setZIndex(ZIndexGame.ORDER_OBJECT);
				sprite = doorWin;
				break;
				
			case SpriteTypeConstant.WIN_COLLITION:
				
				SpritePortal portal = new SpritePortal(0, 0, texture.getTextureByName("winC.png"), texture.getVertexBufferObjectManager(), controller);
				portal.setZIndex(ZIndexGame.ORDER_OBJECT);
				sprite = portal;
				break;
				
			case SpriteTypeConstant.FIREA:
				
				fireDeco = new SpriteFireDecorative(0, 0, texture.getTextureByName("fireA.png"), texture.getVertexBufferObjectManager(), controller);
				fireDeco.setZIndex(ZIndexGame.ORDER_OBJECT);
				sprite = fireDeco;
				break;
			case SpriteTypeConstant.FIREB:
				
				fireDeco = new SpriteFireDecorative(0, 0, texture.getTextureByName("fireB.png"), texture.getVertexBufferObjectManager(), controller);
				fireDeco.setZIndex(ZIndexGame.ORDER_OBJECT);
				sprite = fireDeco;
				break;
				
			case SpriteTypeConstant.TORCH:
				
				fireDeco = new SpriteFireDecorative(0, 0, texture.getTextureByName("torch.png"), texture.getVertexBufferObjectManager(), controller);
				fireDeco.setZIndex(ZIndexGame.ORDER_OBJECT);
				sprite = fireDeco;
				break;
				

			case SpriteTypeConstant.ARC_ROOF:
				SpriteFloor arc = new SpriteFloor(0, 0,
						texture.getTextureByName("arcRoof.png"),
						texture.getVertexBufferObjectManager());
				arc.setZIndex(ZIndexGame.ARC_ROOF);
				sprite = arc;

				break;
			case SpriteTypeConstant.BOX:
				generic = new SpriteTree(0, 0,
						texture.getTextureByName("box.png"),
						texture.getVertexBufferObjectManager());
				generic.setZIndex(ZIndexGame.ORDER_OBJECT);
				sprite = generic;
				break;
			case SpriteTypeConstant.STAIRS_TRAINING:
				genericFloor = new SpriteFloor(0, 0,
						texture.getTextureByName("stairs.png"),
						texture.getVertexBufferObjectManager());
				genericFloor.setZIndex(ZIndexGame.BACKGROUND2);
				sprite = genericFloor;
				break;
			case SpriteTypeConstant.STAIRS_WALL:
				genericFloor = new SpriteFloor(0, 0,
						texture.getTextureByName("stairsWall.png"),
						texture.getVertexBufferObjectManager());
				genericFloor.setZIndex(ZIndexGame.BACKGROUND1);
				sprite = genericFloor;
				break;
				
			case SpriteTypeConstant.BACKGROUND_TRAINING:
				SpriteFloor genericF = new SpriteFloor(0, 0,
						texture.getTextureByName("backgroundTraining.png"),
						texture.getVertexBufferObjectManager());
				genericF.setZIndex(ZIndexGame.BACKGROUND1);
				sprite = genericF;

				break;
				
			case SpriteTypeConstant.ENEMY_TRAINING:
				SpriteEnemy enemyTraining = new SpriteEnemy(0, 0,
						texture.getTextureAnimateByName("enemyTraining.png"),
						texture.getVertexBufferObjectManager(),controller);
				enemyTraining.setZIndex(ZIndexGame.BACKGROUND1);
				sprite = enemyTraining;

				break;
			
			//======================DUNGEON===================================================
			
			case SpriteTypeConstant.ENEMY_SAMURAI:
				SpriteEnemySamurai enemySamurai = new SpriteEnemySamurai(0, 0,
						texture.getTextureAnimateByName("enemy3.png"),
						texture.getVertexBufferObjectManager(),controller);
				enemySamurai.setZIndex(ZIndexGame.BACKGROUND1);
				sprite = enemySamurai;

				break;
				
			case SpriteTypeConstant.ENEMY_LIGHT:
				SpriteEnemyLight enemyLight= new SpriteEnemyLight(0, 0,
						texture.getTextureAnimateByName("enemy2.png"),
						texture.getVertexBufferObjectManager(),controller);
				enemyLight.setZIndex(ZIndexGame.BACKGROUND1);
				sprite = enemyLight;

				break;

				
			case SpriteTypeConstant.BACKGROUND_DUNGEON:
				genericFloor = new SpriteFloor(0, 0,
						texture.getTextureByName("backgroundDungeon.png"),
						texture.getVertexBufferObjectManager());
				genericFloor.setZIndex(ZIndexGame.BACKGROUND1);
				sprite = genericFloor;

				break;
				
			case SpriteTypeConstant.BOX_D:
				generic = new SpriteTree(0, 0,
						texture.getTextureByName("boxD.png"),
						texture.getVertexBufferObjectManager());
				generic.setZIndex(ZIndexGame.ORDER_OBJECT);
				sprite = generic;

				break;
				
			case SpriteTypeConstant.FLAG_DUNGEON1:
				generic = new SpriteTree(0, 0,
						texture.getTextureByName("flagD1.png"),
						texture.getVertexBufferObjectManager());
				generic.setZIndex(ZIndexGame.ORDER_OBJECT);
				sprite = generic;

				break;
				
			case SpriteTypeConstant.FLAG_DUNGEON2:
				generic = new SpriteTree(0, 0,
						texture.getTextureByName("flagD2.png"),
						texture.getVertexBufferObjectManager());
				generic.setZIndex(ZIndexGame.ORDER_OBJECT);
				sprite = generic;

				break;
				
			case SpriteTypeConstant.FLAG_DUNGEON3:
				generic = new SpriteTree(0, 0,
						texture.getTextureByName("flagD3.png"),
						texture.getVertexBufferObjectManager());
				generic.setZIndex(ZIndexGame.ORDER_OBJECT);
				sprite = generic;

				break;
				
			case SpriteTypeConstant.COLUMN_DUNGEON_D1:
				generic = new SpriteTree(0, 0,
						texture.getTextureByName("columnD1.png"),
						texture.getVertexBufferObjectManager());
				generic.setZIndex(ZIndexGame.ORDER_OBJECT);
				sprite = generic;

				break;
				
			case SpriteTypeConstant.COLUMN_DUNGEON_D2:
				generic = new SpriteTree(0, 0,
						texture.getTextureByName("columnD2.png"),
						texture.getVertexBufferObjectManager());
				generic.setZIndex(ZIndexGame.ORDER_OBJECT);
				sprite = generic;

				break;
				
			case SpriteTypeConstant.COLUMN_DUNGEON_D3:
				generic = new SpriteTree(0, 0,
						texture.getTextureByName("columnD3.png"),
						texture.getVertexBufferObjectManager());
				generic.setZIndex(ZIndexGame.ORDER_OBJECT);
				sprite = generic;

				break;
				
				
			case SpriteTypeConstant.CORPSE:
				generic = new SpriteTree(0, 0,
						texture.getTextureByName("corpse.png"),
						texture.getVertexBufferObjectManager());
				generic.setZIndex(ZIndexGame.ORDER_OBJECT);
				sprite = generic;

				break;
				
			case SpriteTypeConstant.JAIL1:
				generic = new SpriteTree(0, 0,
						texture.getTextureByName("jail1.png"),
						texture.getVertexBufferObjectManager());
				generic.setZIndex(ZIndexGame.ORDER_OBJECT);
				sprite = generic;

				break;
				
			case SpriteTypeConstant.JAIL2:
				generic = new SpriteTree(0, 0,
						texture.getTextureByName("jail2.png"),
						texture.getVertexBufferObjectManager());
				generic.setZIndex(ZIndexGame.ORDER_OBJECT);
				sprite = generic;

				break;

			case SpriteTypeConstant.STAIRS_DUNGEON:
				genericFloor = new SpriteFloor(0, 0,
						texture.getTextureByName("stairsD.png"),
						texture.getVertexBufferObjectManager());
				genericFloor.setZIndex(ZIndexGame.ORDER_OBJECT);
				sprite = genericFloor;

				break;

			case SpriteTypeConstant.TUNNEL1:
				genericFloor = new SpriteFloor(0, 0,
						texture.getTextureByName("tunelClosed.png"),
						texture.getVertexBufferObjectManager());
				genericFloor.setZIndex(ZIndexGame.ORDER_OBJECT);
				sprite = genericFloor;

				break;
				
			case SpriteTypeConstant.TUNNEL2:
				genericFloor = new SpriteFloor(0, 0,
						texture.getTextureByName("tunelOpen.png"),
						texture.getVertexBufferObjectManager());
				genericFloor.setZIndex(ZIndexGame.ORDER_OBJECT);
				sprite = genericFloor;

				break;

			case SpriteTypeConstant.WALLD:
				genericFloor = new SpriteFloor(0, 0,
						texture.getTextureByName("wallD.png"),
						texture.getVertexBufferObjectManager());
				genericFloor.setZIndex(ZIndexGame.ORDER_OBJECT);
				sprite = genericFloor;

				break;
				
			case SpriteTypeConstant.WALLD1:
				generic = new SpriteTree(0, 0,
						texture.getTextureByName("wD1.png"),
						texture.getVertexBufferObjectManager());
				generic.setZIndex(ZIndexGame.ORDER_OBJECT);
				sprite = generic;

				break;
				
			case SpriteTypeConstant.WALLD2:
				generic = new SpriteTree(0, 0,
						texture.getTextureByName("wD2.png"),
						texture.getVertexBufferObjectManager());
				generic.setZIndex(ZIndexGame.ORDER_OBJECT);
				sprite = generic;

				break;
				
			case SpriteTypeConstant.WALLD3:
				generic = new SpriteTree(0, 0,
						texture.getTextureByName("wD3.png"),
						texture.getVertexBufferObjectManager());
				generic.setZIndex(ZIndexGame.ORDER_OBJECT);
				sprite = generic;

				break;
				
			case SpriteTypeConstant.WALLD4:
				generic = new SpriteTree(0, 0,
						texture.getTextureByName("wD4.png"),
						texture.getVertexBufferObjectManager());
				generic.setZIndex(ZIndexGame.ORDER_OBJECT);
				sprite = generic;

				break;
				
			case SpriteTypeConstant.WALLD5:
				generic = new SpriteTree(0, 0,
						texture.getTextureByName("wD5.png"),
						texture.getVertexBufferObjectManager());
				generic.setZIndex(ZIndexGame.ORDER_OBJECT);
				sprite = generic;

				break;
				
			case SpriteTypeConstant.WALLD6:
				generic = new SpriteTree(0, 0,
						texture.getTextureByName("wD6.png"),
						texture.getVertexBufferObjectManager());
				generic.setZIndex(ZIndexGame.ORDER_OBJECT);
				sprite = generic;

				break;
				
			case SpriteTypeConstant.WALLD7:
				generic = new SpriteTree(0, 0,
						texture.getTextureByName("wD7.png"),
						texture.getVertexBufferObjectManager());
				generic.setZIndex(ZIndexGame.ORDER_OBJECT);
				sprite = generic;

				break;
				
			case SpriteTypeConstant.WALLD8:
				generic = new SpriteTree(0, 0,
						texture.getTextureByName("wD8.png"),
						texture.getVertexBufferObjectManager());
				generic.setZIndex(ZIndexGame.ORDER_OBJECT);
				sprite = generic;

				break;
				
			case SpriteTypeConstant.WALLD9:
				generic = new SpriteTree(0, 0,
						texture.getTextureByName("wD9.png"),
						texture.getVertexBufferObjectManager());
				generic.setZIndex(ZIndexGame.ORDER_OBJECT);
				sprite = generic;

				break;
				
				
			//======================FLAG==============================
			case SpriteTypeConstant.FLAG1:
				generic = new SpriteTree(0, 0,
						texture.getTextureByName("flag1.png"),
						texture.getVertexBufferObjectManager());
				generic.setZIndex(ZIndexGame.ORDER_OBJECT);
				sprite = generic;

				break;
			case SpriteTypeConstant.FLAG2:
				generic = new SpriteTree(0, 0,
						texture.getTextureByName("flag2.png"),
						texture.getVertexBufferObjectManager());
				generic.setZIndex(ZIndexGame.ORDER_OBJECT);
				sprite = generic;

				break;
			case SpriteTypeConstant.FLAG3:
				genericFloor = new SpriteFloor(0, 0,
						texture.getTextureByName("flag3.png"),
						texture.getVertexBufferObjectManager());
				genericFloor.setZIndex(ZIndexGame.FLAG);
				sprite = genericFloor;

				break;
				//=======================COLUMN====================================
			case SpriteTypeConstant.COLUMN1:
				generic = new SpriteTree(0, 0,
						texture.getTextureByName("column1.png"),
						texture.getVertexBufferObjectManager());
				generic.setZIndex(ZIndexGame.ORDER_OBJECT);
				sprite = generic;

				break;
			case SpriteTypeConstant.COLUMN2:
				generic = new SpriteTree(0, 0,
						texture.getTextureByName("column2.png"),
						texture.getVertexBufferObjectManager());
				generic.setZIndex(ZIndexGame.ORDER_OBJECT);
				sprite = generic;

				break;
			case SpriteTypeConstant.COLUMN3:
				generic = new SpriteTree(0, 0,
						texture.getTextureByName("column3.png"),
						texture.getVertexBufferObjectManager());
				generic.setZIndex(ZIndexGame.ORDER_OBJECT);
				sprite = generic;

				break;
				//===========================Wall==========================================
			case SpriteTypeConstant.W1:
				generic = new SpriteTree(0, 0,
						texture.getTextureByName("W1.png"),
						texture.getVertexBufferObjectManager());
				generic.setZIndex(ZIndexGame.ORDER_OBJECT);
				sprite = generic;

				break;
			case SpriteTypeConstant.W2:
				generic = new SpriteTree(0, 0,
						texture.getTextureByName("W2.png"),
						texture.getVertexBufferObjectManager());
				generic.setZIndex(ZIndexGame.ORDER_OBJECT);
				sprite = generic;

				break;
			case SpriteTypeConstant.W3:
				generic = new SpriteTree(0, 0,
						texture.getTextureByName("W3.png"),
						texture.getVertexBufferObjectManager());
				generic.setZIndex(ZIndexGame.ORDER_OBJECT);
				sprite = generic;

				break;
			case SpriteTypeConstant.W4:
				generic = new SpriteTree(0, 0,
						texture.getTextureByName("W4.png"),
						texture.getVertexBufferObjectManager());
				generic.setZIndex(ZIndexGame.ORDER_OBJECT);
				sprite = generic;

				break;
			case SpriteTypeConstant.W5:
				generic = new SpriteTree(0, 0,
						texture.getTextureByName("W5.png"),
						texture.getVertexBufferObjectManager());
				generic.setZIndex(ZIndexGame.ORDER_OBJECT);
				sprite = generic;

				break;
			case SpriteTypeConstant.W6:
				generic = new SpriteTree(0, 0,
						texture.getTextureByName("W6.png"),
						texture.getVertexBufferObjectManager());
				generic.setZIndex(ZIndexGame.ORDER_OBJECT);
				sprite = generic;

				break;
			case SpriteTypeConstant.W7:
				generic = new SpriteTree(0, 0,
						texture.getTextureByName("W7.png"),
						texture.getVertexBufferObjectManager());
				generic.setZIndex(ZIndexGame.ORDER_OBJECT);
				sprite = generic;

				break;
			case SpriteTypeConstant.W8:
				generic = new SpriteTree(0, 0,
						texture.getTextureByName("W8.png"),
						texture.getVertexBufferObjectManager());
				generic.setZIndex(ZIndexGame.ORDER_OBJECT);
				sprite = generic;

				break;
			case SpriteTypeConstant.W9:
				generic = new SpriteTree(0, 0,
						texture.getTextureByName("W9.png"),
						texture.getVertexBufferObjectManager());
				generic.setZIndex(ZIndexGame.ORDER_OBJECT);
				sprite = generic;

				break;
			case SpriteTypeConstant.W10:
				generic = new SpriteTree(0, 0,
						texture.getTextureByName("W10.png"),
						texture.getVertexBufferObjectManager());
				generic.setZIndex(ZIndexGame.ORDER_OBJECT);
				sprite = generic;

				break;
			// ---------------NINJA-------------------------------------------------------
			case SpriteTypeConstant.NINJA:

				SpritePlayer ninja = new SpritePlayer(0, 0,
						texture.getTextureAnimateByName("playerC1.png"),
						texture.getVertexBufferObjectManager(),controller);
				sprite = ninja;

				break;

			
			// ---------------COLLITION---------------------------------------------------
			case SpriteTypeConstant.COLLITION_SOFT:

				SpriteCollitionRpg collition10 = new SpriteCollitionRpg(0, 0,
						texture.getTextureByName("c1.png"),
						texture.getVertexBufferObjectManager());
				collition10.setZIndex(ZIndexGame.FADE);
				collition10.setMustReload(false);
				collition10.setSoftCollition(true);
				sprite = collition10;

				break;
			case SpriteTypeConstant.COLLITION4:

				SpriteCollitionRpg collition4 = new SpriteCollitionRpg(0, 0,
						texture.getTextureByName("c4.png"),
						texture.getVertexBufferObjectManager());
				collition4.setZIndex(ZIndexGame.FADE);
				collition4.setMustReload(false);
				sprite = collition4;

				break;
			case SpriteTypeConstant.COLLITION3:

				SpriteCollitionRpg collition3 = new SpriteCollitionRpg(0, 0,
						texture.getTextureByName("c3.png"),
						texture.getVertexBufferObjectManager());
				collition3.setZIndex(ZIndexGame.FADE);
				collition3.setMustReload(false);
				sprite = collition3;

				break;
			case SpriteTypeConstant.COLLITION2:

				SpriteCollitionRpg collition2 = new SpriteCollitionRpg(0, 0,
						texture.getTextureByName("c2.png"),
						texture.getVertexBufferObjectManager());
				collition2.setZIndex(ZIndexGame.FADE);
				collition2.setMustReload(false);
				sprite = collition2;

				break;
			case SpriteTypeConstant.COLLITION1:

				SpriteCollitionRpg collition1 = new SpriteCollitionRpg(0, 0,
						texture.getTextureByName("c1.png"),
						texture.getVertexBufferObjectManager());
				collition1.setZIndex(ZIndexGame.BACKGROUND1);
				collition1.setMustReload(false);
				sprite = collition1;

				break;
			
			// ---------------floor---------------------------------------------------	
			case SpriteTypeConstant.FLOOR1:
				SpriteFloor floor = new SpriteFloor(0, 0,
						texture.getTextureByName("floor.png"),
						texture.getVertexBufferObjectManager());
				floor.setZIndex(ZIndexGame.BACKGROUND1);
				floor.setMustReload(false);
				sprite = floor;

				break;
			case SpriteTypeConstant.FLOOR2:
				SpriteFloor floor2 = new SpriteFloor(0, 0,
						texture.getTextureByName("floor2.png"),
						texture.getVertexBufferObjectManager());
				floor2.setZIndex(ZIndexGame.BACKGROUND1);
				sprite = floor2;

				break;
			// ---------------Floor V---------------------------------------------------	
			
			case SpriteTypeConstant.FLOORV3:
				SpriteTree floorV3 = new SpriteTree(0, 0,
						texture.getTextureByName("roofV3.png"),
						texture.getVertexBufferObjectManager());
				sprite = floorV3;

				break;
				
			case SpriteTypeConstant.FLOORV2:
				SpriteTree floorV2 = new SpriteTree(0, 0,
						texture.getTextureByName("roofV2.png"),
						texture.getVertexBufferObjectManager());
				sprite = floorV2;

				break;
				
			case SpriteTypeConstant.FLOORV1:
				SpriteTree floorV1 = new SpriteTree(0, 0,
						texture.getTextureByName("roofV1.png"),
						texture.getVertexBufferObjectManager());
				sprite = floorV1;

				break;
				
				//=================================dojo==================================
			case SpriteTypeConstant.FLOORVD2:
				SpriteTree floorVD2 = new SpriteTree(0, 0,
						texture.getTextureByName("roofVD2.png"),
						texture.getVertexBufferObjectManager());
				sprite = floorVD2;

				break;
				
			case SpriteTypeConstant.FLOORVD1:
				SpriteTree floorVD1 = new SpriteTree(0, 0,
						texture.getTextureByName("roofVD1.png"),
						texture.getVertexBufferObjectManager());
				sprite = floorVD1;

				break;
				
			// ---------------Floor H---------------------------------------------------
			case SpriteTypeConstant.FLOORH3:
				SpriteTree floorG3 = new SpriteTree(0, 0,
						texture.getTextureByName("wall3.png"),
						texture.getVertexBufferObjectManager());
				sprite = floorG3;

				break;
				
			case SpriteTypeConstant.FLOORH2:
				SpriteTree floorH2 = new SpriteTree(0, 0,
						texture.getTextureByName("wall2.png"),
						texture.getVertexBufferObjectManager());
				sprite = floorH2;

				break;
				
			case SpriteTypeConstant.FLOORH1:
				SpriteTree floorH1 = new SpriteTree(0, 0,
						texture.getTextureByName("wall1.png"),
						texture.getVertexBufferObjectManager());
				sprite = floorH1;

				break;

				//==================dojo==============================================================
				
			case SpriteTypeConstant.FLOORHD5:
				SpriteTree floorGD5 = new SpriteTree(0, 0,
						texture.getTextureByName("wallD5.png"),
						texture.getVertexBufferObjectManager());
				sprite = floorGD5;

				break;
				
			case SpriteTypeConstant.FLOORHD4:
				SpriteTree floorHD4 = new SpriteTree(0, 0,
						texture.getTextureByName("wallD4.png"),
						texture.getVertexBufferObjectManager());
				sprite = floorHD4;

				break;
				
			case SpriteTypeConstant.FLOORHD3:
				SpriteTree floorGD3 = new SpriteTree(0, 0,
						texture.getTextureByName("wallD3.png"),
						texture.getVertexBufferObjectManager());
				sprite = floorGD3;

				break;
				
			case SpriteTypeConstant.FLOORHD2:
				SpriteTree floorHD2 = new SpriteTree(0, 0,
						texture.getTextureByName("wallD2.png"),
						texture.getVertexBufferObjectManager());
				sprite = floorHD2;

				break;
				
			case SpriteTypeConstant.FLOORHD1:
				SpriteTree floorHD1 = new SpriteTree(0, 0,
						texture.getTextureByName("wallD1.png"),
						texture.getVertexBufferObjectManager());
				sprite = floorHD1;

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
