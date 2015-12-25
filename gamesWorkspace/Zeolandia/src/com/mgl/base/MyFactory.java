package com.mgl.base;

import com.google.android.gms.internal.pi;
import com.google.example.games.basegameutils.ButtonLeaderboard;
import com.mgl.base.userinfo.PoolObjectSingleton;
import com.mgl.drop.game.constant.CollitionType;
import com.mgl.drop.game.constant.ItemConstant;
import com.mgl.drop.game.constant.ZIndexGame;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.hud.sprites.SpriteMessage;
import com.mgl.drop.game.model.Item;
import com.mgl.drop.game.sprites.SpriteBackground;
import com.mgl.drop.game.sprites.SpriteBeginPoint;
import com.mgl.drop.game.sprites.SpriteCollitionRpg;
import com.mgl.drop.game.sprites.SpritePortal;
import com.mgl.drop.game.sprites.SpriteSmashGeneral;
import com.mgl.drop.game.sprites.arunner.SpriteADN;
import com.mgl.drop.game.sprites.arunner.SpriteBurble;
import com.mgl.drop.game.sprites.arunner.SpriteCheckPoint;
import com.mgl.drop.game.sprites.arunner.SpriteDecorativeShot;
import com.mgl.drop.game.sprites.arunner.SpriteEnemyBasic;
import com.mgl.drop.game.sprites.arunner.SpriteEnemyRombe;
import com.mgl.drop.game.sprites.arunner.SpriteEnemyRombeBurble;
import com.mgl.drop.game.sprites.arunner.SpriteEnemyTriangle;
import com.mgl.drop.game.sprites.arunner.SpriteFloor;
import com.mgl.drop.game.sprites.arunner.SpriteGel;
import com.mgl.drop.game.sprites.arunner.SpriteHedgehog;
import com.mgl.drop.game.sprites.arunner.SpriteIce;
import com.mgl.drop.game.sprites.arunner.SpriteIceCircleAnimated;
import com.mgl.drop.game.sprites.arunner.SpriteLake;
import com.mgl.drop.game.sprites.arunner.SpriteMeteor;
import com.mgl.drop.game.sprites.arunner.SpriteMeteorLevelSelect;
import com.mgl.drop.game.sprites.arunner.SpriteMoveControll;
import com.mgl.drop.game.sprites.arunner.SpriteObstacleSmash;
import com.mgl.drop.game.sprites.arunner.SpritePlatform;
import com.mgl.drop.game.sprites.arunner.SpritePlatformMobile;
import com.mgl.drop.game.sprites.arunner.SpritePlayer;
import com.mgl.drop.game.sprites.arunner.SpritePlayerDied;
import com.mgl.drop.game.sprites.arunner.SpritePlayerShot;
import com.mgl.drop.game.sprites.arunner.SpriteReleasePlayer;
import com.mgl.drop.game.sprites.arunner.SpriteSmasher;
import com.mgl.drop.game.sprites.arunner.SpriteStar;
import com.mgl.drop.game.sprites.arunner.SpriteTunel;
import com.mgl.drop.game.sprites.arunner.SpriteTurboBack;
import com.mgl.drop.game.sprites.arunner.SpriteVulcano;
import com.mgl.drop.game.sprites.arunner.SpriteVulcanoBurble;
import com.mgl.drop.game.sprites.ayoutuberunner.SpriteColorChange;
import com.mgl.drop.game.sprites.ayoutuberunner.SpriteJump;
import com.mgl.drop.game.sprites.ayoutuberunner.SpriteLikeCoin;
import com.mgl.drop.game.sprites.ayoutuberunner.SpriteLikeCoinDead;
import com.mgl.drop.game.sprites.ayoutuberunner.SpritePlayerCollition;
import com.mgl.drop.game.sprites.ayoutuberunner.SpriteStarGenerator;
import com.mgl.drop.game.sprites.ayoutuberunner.SpriteStarGeometry;
import com.mgl.drop.game.sprites.ayoutuberunner.SpriteTunnel;
import com.mgl.drop.game.sprites.ayoutuberunner.SpriteWinPlayer;
import com.mgl.drop.game.sprites.azeoland.SpriteCharacterBattleFace;
import com.mgl.drop.game.sprites.azeoland.SpriteChestAdventure;
import com.mgl.drop.game.sprites.azeoland.SpriteEnemy;
import com.mgl.drop.game.sprites.azeoland.SpriteEnemyBattle;
import com.mgl.drop.game.sprites.azeoland.SpriteItem;
import com.mgl.drop.game.sprites.azeoland.SpriteNPC;
import com.mgl.drop.game.sprites.azeoland.SpritePlayerAdventure;
import com.mgl.drop.game.sprites.azeoland.SpritePlayerBattle;
import com.mgl.drop.game.sprites.azeoland.SpritePlayerBegin;
import com.mgl.drop.game.sprites.azeoland.SpriteTechniqueAnimation;
import com.mgl.drop.game.sprites.azeoland.SpriteTree;
import com.mgl.drop.game.sprites.azeoland.SpriteTurnSelect;
import com.mgl.drop.game.sprites.button.azeolandia.ButtonDialog;
import com.mgl.drop.game.sprites.button.azeolandia.ButtonPickUp;
import com.mgl.drop.texture.TextureSingleton;
import com.mgl.drop.util.Point;

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

	public static SpritePlayerBattle createPlayerBattle(LevelController level) {
		try {

			SpritePlayerBattle player = new SpritePlayerBattle(0, 0,
					texture.getTextureAnimateByName("zeoBattle.png"),
					texture.getVertexBufferObjectManager(), level);
			player.setZIndex(ZIndexGame.PLAYER);
			return player;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static MySpriteGeneral createTechniqueAnimation(int idSprite,
			LevelController controller) {
		MySpriteGeneral sprite = null;
		try {

			long[] fps;
			SpriteTechniqueAnimation zeoBasic = null;
			switch (idSprite) {
			case SpriteTypeConstant.ZEO_BASIC_ATTACK:

				fps = new long[] { 100, 120, 100};
				
				zeoBasic = new SpriteTechniqueAnimation(
						0, 0, texture.getTextureAnimateByName("zeoBasic.png"),
						texture.getVertexBufferObjectManager(), controller,fps);
				zeoBasic.setZIndex(ZIndexGame.ORDER_OBJECT);
				zeoBasic.setCenterPoint(new Point(30, zeoBasic.getHeight()-7));

				break;
			case SpriteTypeConstant.ZEO_SPECIAL_ATTACK1:

				fps = new long[] { 100, 100, 100,100};
				
				zeoBasic = new SpriteTechniqueAnimation(
						0, 0, texture.getTextureAnimateByName("zeoSpecial1.png"),
						texture.getVertexBufferObjectManager(), controller,fps);
				zeoBasic.setZIndex(ZIndexGame.ORDER_OBJECT);
				zeoBasic.setCenterPoint(new Point(40, zeoBasic.getHeight()-7));

				break;

			}

			sprite = zeoBasic;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sprite;
	}

	public static MySpriteGeneral createObstacle(int idSprite,
			LevelController controller) {
		MySpriteGeneral sprite = null;
		try {

			
			SpriteFloor generic = null;
			SpriteTree genericTree = null;
			
			switch (idSprite) {

			// ---------------ZEOLANDIA-------------------------------------------------------
			case SpriteTypeConstant.PLAYER_BEGIN:

				SpritePlayerBegin playerBegin = new SpritePlayerBegin(0, 0, texture.getTextureByName("playerBegin.png"), texture.getVertexBufferObjectManager());
				playerBegin.setZIndex(ZIndexGame.BACKGROUND1);
				sprite = playerBegin;

				break;
			//-----------------LEVEL1----------------------------------------------------------
			case SpriteTypeConstant.BRIDGE1:

				generic = new SpriteFloor(0, 0, texture.getTextureByName("bridge1.png"), texture.getVertexBufferObjectManager());
				generic.setZIndex(ZIndexGame.BACKGROUND1);
				sprite = generic;

				break;
			case SpriteTypeConstant.BRIDGE2:

				generic = new SpriteFloor(0, 0, texture.getTextureByName("bridge2.png"), texture.getVertexBufferObjectManager());
				generic.setZIndex(ZIndexGame.BACKGROUND1);
				sprite = generic;

				break;
				
			case SpriteTypeConstant.BLUE_SKY:

				generic = new SpriteFloor(0, 0, texture.getTextureByName("blueSky.png"), texture.getVertexBufferObjectManager());
				generic.setZIndex(ZIndexGame.BACKGROUND1);
				sprite = generic;

				break;
	
			case SpriteTypeConstant.MOUNTAIN1:

				generic = new SpriteFloor(0, 0, texture.getTextureByName("mountain1.png"), texture.getVertexBufferObjectManager());
				generic.setZIndex(ZIndexGame.BACKGROUND1);
				sprite = generic;

				break;
				
			case SpriteTypeConstant.MOUNTAIN2:

				generic = new SpriteFloor(0, 0, texture.getTextureByName("mountain2.png"), texture.getVertexBufferObjectManager());
				generic.setZIndex(ZIndexGame.BACKGROUND1);
				sprite = generic;

				break;
			case SpriteTypeConstant.MOUNTAIN3:

				generic = new SpriteFloor(0, 0, texture.getTextureByName("mountain3.png"), texture.getVertexBufferObjectManager());
				generic.setZIndex(ZIndexGame.BACKGROUND1);
				sprite = generic;

				break;

				//-----------------CHEST----------------------------------------------------------
			case SpriteTypeConstant.CHEST:

				SpriteChestAdventure chest = new SpriteChestAdventure(0, 0, texture.getTextureAnimateByName("chest.png"), texture.getVertexBufferObjectManager(),controller);
				chest.setZIndex(ZIndexGame.ORDER_OBJECT);
				sprite = chest;

				break;
			
			//-----------------NPCS-----------------------------------------------------------
			case SpriteTypeConstant.NPC1:

				SpriteNPC npc1 = new SpriteNPC(
						0, 0, texture.getTextureAnimateByName("npc1.png"),
						texture.getVertexBufferObjectManager(), controller);
				npc1.setZIndex(ZIndexGame.ORDER_OBJECT);
				npc1.setMustReload(false);
				sprite = npc1;

				break;
			
			// ---------------FACES----------------------------------------------------------
			case SpriteTypeConstant.ZEO_FACE:

				SpriteCharacterBattleFace zeoFace = new SpriteCharacterBattleFace(
						0, 0, texture.getTextureByName("zeoFace.png"),
						texture.getVertexBufferObjectManager(), controller);
				zeoFace.setZIndex(ZIndexGame.FADE);
				zeoFace.setMustReload(false);
				sprite = zeoFace;

				break;

			// ---------------PORTAL----------------------------------------------------------
			case SpriteTypeConstant.PORTAL:

				SpritePortal portal = new SpritePortal(0, 0,
						texture.getTextureByName("portal.png"),
						texture.getVertexBufferObjectManager(), controller);
				portal.setZIndex(ZIndexGame.ORDER_OBJECT);
				portal.setMustReload(false);
				portal.setAlpha(0.1f);
				sprite = portal;

				break;

			// ---------------TurnArrow-----------------------------------------------------------
			case SpriteTypeConstant.TURN_ARROW:

				SpriteTurnSelect turnArrow = new SpriteTurnSelect(0, 0,
						texture.getTextureAnimateByName("turnArrow.png"),
						texture.getVertexBufferObjectManager(), controller);
				turnArrow.setZIndex(ZIndexGame.FADE);
				turnArrow.setMustReload(false);
				sprite = turnArrow;

				break;

			// ---------------HOUSE-----------------------------------------------------------
			case SpriteTypeConstant.HOUSE:

				SpriteTree house = new SpriteTree(0, 0,
						texture.getTextureByName("house.png"),
						texture.getVertexBufferObjectManager());
				house.setZIndex(ZIndexGame.ORDER_OBJECT);
				house.setMustReload(false);
				house.setIdCollition(SpriteTypeConstant.HOUSE);
				sprite = house;

				break;

			case SpriteTypeConstant.FLOOR3:

				SpriteFloor floor3 = new SpriteFloor(0, 0,
						texture.getTextureByName("floor3.png"),
						texture.getVertexBufferObjectManager());
				floor3.setZIndex(ZIndexGame.BACKGROUND1);
				floor3.setMustReload(false);
				sprite = floor3;

				break;
			case SpriteTypeConstant.FLOOR2:

				SpriteFloor floor2 = new SpriteFloor(0, 0,
						texture.getTextureByName("floor2.png"),
						texture.getVertexBufferObjectManager());
				floor2.setZIndex(ZIndexGame.BACKGROUND1);
				floor2.setMustReload(false);
				sprite = floor2;

				break;
			case SpriteTypeConstant.FLOOR1:

				SpriteFloor floor1 = new SpriteFloor(0, 0,
						texture.getTextureByName("floor1.png"),
						texture.getVertexBufferObjectManager());
				floor1.setZIndex(ZIndexGame.BACKGROUND1);
				floor1.setMustReload(false);
				sprite = floor1;

				break;

			case SpriteTypeConstant.BACKGROUND_BATTLE1:

				SpriteFloor backgroundBattle1 = new SpriteFloor(0, 0,
						texture.getTextureByName("backgroundBattle.png"),
						texture.getVertexBufferObjectManager());
				backgroundBattle1.setZIndex(ZIndexGame.BACKGROUND1);
				sprite = backgroundBattle1;
				break;
			// ---------------ENEMY-----------------------------------------------------------
			case SpriteTypeConstant.ENEMY1:

				SpriteEnemy enemy1 = new SpriteEnemy(0, 0,
						texture.getTextureAnimateByName("enemy1.png"),
						texture.getVertexBufferObjectManager(), controller);
				enemy1.setZIndex(ZIndexGame.FADE);
				sprite = enemy1;
				break;
			case SpriteTypeConstant.ENEMY_BATTLE:

				SpriteEnemyBattle enemyBattle = new SpriteEnemyBattle(0, 0,
						texture.getTextureAnimateByName("enemy1Battle.png"),
						texture.getVertexBufferObjectManager(), controller);
				enemyBattle.setZIndex(ZIndexGame.FADE);
				sprite = enemyBattle;
				break;
			// ---------------ENEMY-----------------------------------------------------------
			// --------------COLLITIONS---------------------------

			case SpriteTypeConstant.COLLITION10:

				SpriteCollitionRpg collition10 = new SpriteCollitionRpg(0, 0,
						texture.getTextureByName("collition10.png"),
						texture.getVertexBufferObjectManager());
				collition10.setZIndex(ZIndexGame.FADE);
				collition10.setMustReload(false);
				sprite = collition10;
				break;
			case SpriteTypeConstant.COLLITION9:

				SpriteCollitionRpg collition9 = new SpriteCollitionRpg(0, 0,
						texture.getTextureByName("collition9.png"),
						texture.getVertexBufferObjectManager());
				collition9.setZIndex(ZIndexGame.FADE);
				collition9.setMustReload(false);
				sprite = collition9;

				break;
			case SpriteTypeConstant.COLLITION8:

				SpriteCollitionRpg collition8 = new SpriteCollitionRpg(0, 0,
						texture.getTextureByName("collition8.png"),
						texture.getVertexBufferObjectManager());
				collition8.setZIndex(ZIndexGame.FADE);
				collition8.setMustReload(false);
				sprite = collition8;
				break;
			case SpriteTypeConstant.COLLITION7:

				SpriteCollitionRpg collition7 = new SpriteCollitionRpg(0, 0,
						texture.getTextureByName("collition7.png"),
						texture.getVertexBufferObjectManager());
				collition7.setZIndex(ZIndexGame.FADE);
				collition7.setMustReload(false);
				sprite = collition7;

				break;
			case SpriteTypeConstant.COLLITION6:

				SpriteCollitionRpg collition6 = new SpriteCollitionRpg(0, 0,
						texture.getTextureByName("collition6.png"),
						texture.getVertexBufferObjectManager());
				collition6.setZIndex(ZIndexGame.FADE);
				collition6.setMustReload(false);
				sprite = collition6;

				break;
			case SpriteTypeConstant.COLLITION5:

				SpriteCollitionRpg collition5 = new SpriteCollitionRpg(0, 0,
						texture.getTextureByName("collition5.png"),
						texture.getVertexBufferObjectManager());
				collition5.setZIndex(ZIndexGame.FADE);
				collition5.setMustReload(false);
				sprite = collition5;

				break;
			case SpriteTypeConstant.COLLITION4:

				SpriteCollitionRpg collition4 = new SpriteCollitionRpg(0, 0,
						texture.getTextureByName("collition4.png"),
						texture.getVertexBufferObjectManager());
				collition4.setZIndex(ZIndexGame.FADE);
				collition4.setMustReload(false);
				sprite = collition4;

				break;
			case SpriteTypeConstant.COLLITION3:

				SpriteCollitionRpg collition3 = new SpriteCollitionRpg(0, 0,
						texture.getTextureByName("collition3.png"),
						texture.getVertexBufferObjectManager());
				collition3.setZIndex(ZIndexGame.FADE);
				collition3.setMustReload(false);
				sprite = collition3;

				break;
			case SpriteTypeConstant.COLLITION2:

				SpriteCollitionRpg collition2 = new SpriteCollitionRpg(0, 0,
						texture.getTextureByName("collition2.png"),
						texture.getVertexBufferObjectManager());
				collition2.setZIndex(ZIndexGame.FADE);
				collition2.setMustReload(false);
				sprite = collition2;

				break;
			case SpriteTypeConstant.COLLITION1:

				SpriteCollitionRpg collition1 = new SpriteCollitionRpg(0, 0,
						texture.getTextureByName("collition1.png"),
						texture.getVertexBufferObjectManager());
				collition1.setZIndex(ZIndexGame.BACKGROUND1);
				collition1.setMustReload(false);
				sprite = collition1;

				break;

			// --------------COLLITIONS---------------------------
			case SpriteTypeConstant.TREE5:

				SpriteTree tree5 = new SpriteTree(0, 0,
						texture.getTextureByName("tree5.png"),
						texture.getVertexBufferObjectManager());
				tree5.setZIndex(ZIndexGame.ORDER_OBJECT);
				tree5.setIdCollition(SpriteTypeConstant.TREE5);
				sprite = tree5;

				break;
			case SpriteTypeConstant.TREE4:

				SpriteTree tree4 = new SpriteTree(0, 0,
						texture.getTextureByName("tree4.png"),
						texture.getVertexBufferObjectManager());
				tree4.setZIndex(ZIndexGame.ORDER_OBJECT);
				tree4.setMustReload(false);
				tree4.setIdCollition(SpriteTypeConstant.TREE4);
				sprite = tree4;

				break;
			case SpriteTypeConstant.TREE3:

				SpriteTree tree3 = new SpriteTree(0, 0,
						texture.getTextureByName("tree3.png"),
						texture.getVertexBufferObjectManager());
				tree3.setZIndex(ZIndexGame.ORDER_OBJECT);
				tree3.setMustReload(false);
				tree3.setIdCollition(SpriteTypeConstant.TREE3);
				sprite = tree3;

				break;
			case SpriteTypeConstant.TREE2:

				SpriteTree tree2 = new SpriteTree(0, 0,
						texture.getTextureByName("tree2.png"),
						texture.getVertexBufferObjectManager());
				tree2.setZIndex(ZIndexGame.ORDER_OBJECT);
				tree2.setMustReload(false);
				tree2.setIdCollition(SpriteTypeConstant.TREE2);
				sprite = tree2;

				break;
			case SpriteTypeConstant.TREE1:

				SpriteTree tree1 = new SpriteTree(0, 0,
						texture.getTextureByName("tree1.png"),
						texture.getVertexBufferObjectManager());
				tree1.setZIndex(ZIndexGame.ORDER_OBJECT);
				tree1.setMustReload(false);
				tree1.setIdCollition(SpriteTypeConstant.TREE1);
				sprite = tree1;

				break;

			case SpriteTypeConstant.GRASS3:

				SpriteFloor grass3 = new SpriteFloor(0, 0,
						texture.getTextureByName("grass3.png"),
						texture.getVertexBufferObjectManager());
				grass3.setZIndex(ZIndexGame.BACKGROUND1);
				grass3.setMustReload(false);
				sprite = grass3;

				break;

			case SpriteTypeConstant.GRASS2:

				SpriteFloor grass2 = new SpriteFloor(0, 0,
						texture.getTextureByName("grass2.png"),
						texture.getVertexBufferObjectManager());
				grass2.setZIndex(ZIndexGame.BACKGROUND1);
				grass2.setMustReload(false);
				sprite = grass2;

				break;

			case SpriteTypeConstant.GRASS1:

				SpriteFloor grass1 = new SpriteFloor(0, 0,
						texture.getTextureByName("grass1.png"),
						texture.getVertexBufferObjectManager());
				grass1.setZIndex(ZIndexGame.BACKGROUND1);
				grass1.setMustReload(false);
				sprite = grass1;

				break;
			// -----------------------------------------------------------------------------------------------------------------
			case SpriteTypeConstant.BEGIN_POINT:

				SpriteBeginPoint beginPoint = new SpriteBeginPoint(200, 0,
						texture.getTextureByName("tunelCover.png"),
						texture.getVertexBufferObjectManager(), controller);
				beginPoint.setCollitionType(CollitionType.COLLITION_NONE);
				beginPoint.setZIndex(ZIndexGame.TUNEL_FRONT);
				beginPoint.setMustReload(false);
				sprite = beginPoint;
				break;

			case SpriteTypeConstant.WIN_LEVEL:

				SpriteWinPlayer winPlayer = new SpriteWinPlayer(200, 0,
						texture.getTextureByName("tunelCover.png"),
						texture.getVertexBufferObjectManager());
				winPlayer.setCollitionType(CollitionType.COLLITION_XML);
				winPlayer.setXmlName("ship.xml");
				winPlayer.setZIndex(ZIndexGame.TUNEL_FRONT);
				sprite = winPlayer;
				break;

			case SpriteTypeConstant.SHIP:

				SpriteBackground ship = new SpriteBackground(200, 0,
						texture.getTextureByName("ship.png"),
						texture.getVertexBufferObjectManager());
				ship.setCollitionType(CollitionType.COLLITION_XML);
				ship.setXmlName("ship.xml");
				ship.setZIndex(ZIndexGame.TUNEL_FRONT);
				sprite = ship;
				break;

			case SpriteTypeConstant.TUNNEL_COVER:

				SpriteBackground cover = new SpriteBackground(200, 0,
						texture.getTextureByName("tunelCover.png"),
						texture.getVertexBufferObjectManager());
				cover.setCollitionType(CollitionType.COLLITION_NONE);
				// cover.setSize(50, 200);
				cover.setZIndex(ZIndexGame.TUNEL_FRONT);
				sprite = cover;
				break;

			case SpriteTypeConstant.STAR_GEOMETRY:

				SpriteStarGeometry starGeometry = (SpriteStarGeometry) PoolObjectSingleton
						.getInstance().getStar();
				starGeometry.reset();
				starGeometry.setLevel(controller);
				sprite = starGeometry;
				break;

			case SpriteTypeConstant.STAR_GENERATOR:

				SpriteStarGenerator starGenerator = new SpriteStarGenerator(
						200, 0, texture.getTextureByName("starGenerator.png"),
						texture.getVertexBufferObjectManager());
				starGenerator.setSize(50, 480);
				starGenerator.setLevel(controller);
				starGenerator.setAlpha(0);
				starGenerator.setCollitionType(CollitionType.COLLITION_NONE);
				starGenerator.setZIndex(ZIndexGame.FADE);
				sprite = starGenerator;
				break;

			case SpriteTypeConstant.TUNNEL:

				SpriteTunnel tunel = new SpriteTunnel(200, 0,
						texture.getTextureByName("tunel.png"),
						texture.getVertexBufferObjectManager());
				// tunel.setSize(50, 200);
				tunel.setCollitionType(CollitionType.COLLITION_NONE);
				tunel.setZIndex(ZIndexGame.TURBO_BACK);
				sprite = tunel;
				break;

			case SpriteTypeConstant.JUMP:

				SpriteJump jump = new SpriteJump(200, 0,
						texture.getTextureByName("jump.png"),
						texture.getVertexBufferObjectManager());
				jump.setSize(50, 50);
				jump.setCollitionType(CollitionType.COLLITION_NONE);
				jump.setZIndex(ZIndexGame.FADE);
				sprite = jump;
				break;

			case SpriteTypeConstant.LIKE_COIN:

				SpriteLikeCoin likeCoin = new SpriteLikeCoin(200, 0,
						texture.getTextureByName("likeCoin.png"),
						texture.getVertexBufferObjectManager());
				likeCoin.setSize(50, 50);
				likeCoin.setCollitionType(CollitionType.COLLITION_NONE);
				likeCoin.setZIndex(ZIndexGame.FADE);
				sprite = likeCoin;
				break;

			case SpriteTypeConstant.LIKE_COIN_DEAD:

				SpriteLikeCoinDead likeCoinDead = new SpriteLikeCoinDead(200,
						0, texture.getTextureAnimateByName("likeCoinDead.png"),
						texture.getVertexBufferObjectManager(), controller);
				likeCoinDead.setSize(50, 50);
				likeCoinDead.setCollitionType(CollitionType.COLLITION_NONE);
				likeCoinDead.setZIndex(ZIndexGame.FADE);
				sprite = likeCoinDead;
				break;

			case SpriteTypeConstant.COLOR_CHANGE:

				SpriteColorChange color = new SpriteColorChange(200, 0,
						texture.getTextureByName("changeColor.png"),
						texture.getVertexBufferObjectManager());
				color.setSize(100, 480);
				color.setAlpha(0);
				color.setCollitionType(CollitionType.COLLITION_NONE);
				color.setZIndex(ZIndexGame.FADE);
				sprite = color;

				break;

			case SpriteTypeConstant.EYE:

				SpriteBackground eye = new SpriteBackground(200, 0,
						texture.getTextureByName("eye.png"),
						texture.getVertexBufferObjectManager());
				eye.setCollitionType(CollitionType.COLLITION_NONE);
				eye.setZIndex(ZIndexGame.FADE);
				sprite = eye;

				break;

			case SpriteTypeConstant.CHECK_POINT:

				SpriteCheckPoint checkPoint = new SpriteCheckPoint(200, 0,
						texture.getTextureByName("checkPoint.png"),
						texture.getVertexBufferObjectManager(), controller);
				checkPoint.setSize(20, 20);
				checkPoint.setCollitionType(CollitionType.COLLITION_NONE);
				checkPoint.setZIndex(ZIndexGame.CHECK_POINT);
				sprite = checkPoint;

				break;

			case SpriteTypeConstant.TRIANGLE1S:

				SpriteHedgehog triangle1S = new SpriteHedgehog(200, 0,
						texture.getTextureByName("pB1.png"),
						texture.getVertexBufferObjectManager());
				triangle1S.setCollitionType(CollitionType.COLLITION_TRIANGLE);
				triangle1S.setZIndex(ZIndexGame.HEDGEHOG);
				triangle1S.setMustReload(false);
				sprite = triangle1S;

				break;

			case SpriteTypeConstant.STAR:
				SpriteStar star = new SpriteStar(200, 0,
						texture.getTextureAnimateByName("bubbleStar.png"),
						texture.getVertexBufferObjectManager(), controller);
				star.setCollitionType(CollitionType.COLLITION_NONE);
				star.setZIndex(ZIndexGame.STAR);
				star.setSize(100, 100);
				sprite = star;

				break;

			case SpriteTypeConstant.PLAYER_DEAD:

				SpritePlayerDied dead = PoolObjectSingleton.getInstance()
						.getPlayerDead();
				dead.setLevel(controller);
				// dead.setSize(100, 100);
				dead.setCollitionType(CollitionType.COLLITION_NONE);
				dead.setZIndex(ZIndexGame.FADE);
				sprite = dead;

				break;

			case SpriteTypeConstant.TRIANGLE5:

				SpriteHedgehog triangle5 = new SpriteHedgehog(200, 0,
						texture.getTextureByName("p5.png"),
						texture.getVertexBufferObjectManager());
				triangle5
						.setCollitionType(CollitionType.COLLITION_TRIANGLEPLUS);
				triangle5.setZIndex(ZIndexGame.HEDGEHOG);
				triangle5.setMustReload(false);
				sprite = triangle5;

				break;

			case SpriteTypeConstant.TRIANGLE4:

				SpriteHedgehog triangle4 = new SpriteHedgehog(200, 0,
						texture.getTextureByName("p4.png"),
						texture.getVertexBufferObjectManager());
				triangle4
						.setCollitionType(CollitionType.COLLITION_TRIANGLEPLUS);
				triangle4.setZIndex(ZIndexGame.HEDGEHOG);
				triangle4.setMustReload(false);
				sprite = triangle4;

				break;

			case SpriteTypeConstant.TRIANGLE3:

				SpriteHedgehog triangle3 = new SpriteHedgehog(200, 0,
						texture.getTextureByName("p3.png"),
						texture.getVertexBufferObjectManager());
				triangle3
						.setCollitionType(CollitionType.COLLITION_TRIANGLEPLUS3);
				triangle3.setZIndex(ZIndexGame.HEDGEHOG);
				triangle3.setMustReload(false);
				sprite = triangle3;

				break;

			case SpriteTypeConstant.TRIANGLE2:

				SpriteHedgehog triangle2 = new SpriteHedgehog(200, 0,
						texture.getTextureByName("p2.png"),
						texture.getVertexBufferObjectManager());
				triangle2
						.setCollitionType(CollitionType.COLLITION_TRIANGLEPLUS);
				triangle2.setZIndex(ZIndexGame.HEDGEHOG);
				triangle2.setMustReload(false);
				sprite = triangle2;

				break;

			case SpriteTypeConstant.TRIANGLE1:

				SpriteHedgehog triangle1 = new SpriteHedgehog(200, 0,
						texture.getTextureByName("p1.png"),
						texture.getVertexBufferObjectManager());
				triangle1.setCollitionType(CollitionType.COLLITION_TRIANGLE);
				triangle1.setZIndex(ZIndexGame.HEDGEHOG);
				triangle1.setMustReload(false);
				sprite = triangle1;

				break;

			case SpriteTypeConstant.LEVEL_GENERAL_FLOOR:

				SpriteFloor levelGeneralFloor = new SpriteFloor(200, 0,
						texture.getTextureByName("backgroundGeneral.png"),
						texture.getVertexBufferObjectManager());
				levelGeneralFloor
						.setCollitionType(CollitionType.COLLITION_RECTANGLE);
				levelGeneralFloor.setZIndex(ZIndexGame.ROOF);
				levelGeneralFloor.setMustReload(false);
				sprite = levelGeneralFloor;

				break;

			case SpriteTypeConstant.FLOOR8:

				SpriteFloor floor8 = new SpriteFloor(200, 0,
						texture.getTextureByName("floor8.png"),
						texture.getVertexBufferObjectManager());
				floor8.setCollitionType(CollitionType.COLLITION_RECTANGLE_GEOMETRY);
				floor8.setZIndex(ZIndexGame.ROOF);
				floor8.setMustReload(false);
				sprite = floor8;

				break;

			case SpriteTypeConstant.FLOOR7:

				SpriteFloor floor7 = new SpriteFloor(200, 0,
						texture.getTextureByName("floor7.png"),
						texture.getVertexBufferObjectManager());
				floor7.setCollitionType(CollitionType.COLLITION_RECTANGLE_GEOMETRY);
				floor7.setZIndex(ZIndexGame.ROOF);
				floor7.setMustReload(false);
				sprite = floor7;

				break;

			case SpriteTypeConstant.FLOOR6:

				SpriteFloor floor6 = new SpriteFloor(200, 0,
						texture.getTextureByName("floor6.png"),
						texture.getVertexBufferObjectManager());
				floor6.setCollitionType(CollitionType.COLLITION_RECTANGLE_GEOMETRY);
				floor6.setZIndex(ZIndexGame.ROOF);
				floor6.setMustReload(false);
				sprite = floor6;

				break;

			case SpriteTypeConstant.PLATFORM5:

				SpriteFloor platform5 = new SpriteFloor(200, 0,
						texture.getTextureByName("platfrom5.png"),
						texture.getVertexBufferObjectManager());
				platform5.setCollitionType(CollitionType.COLLITION_RECTANGLE);
				platform5.setZIndex(ZIndexGame.ROOF);
				platform5.setMustReload(false);
				sprite = platform5;

				break;

			case SpriteTypeConstant.PLATFORM4:

				SpriteFloor platform4 = new SpriteFloor(200, 0,
						texture.getTextureByName("platform4.png"),
						texture.getVertexBufferObjectManager());
				platform4.setCollitionType(CollitionType.COLLITION_RECTANGLE);
				platform4.setZIndex(ZIndexGame.ROOF);
				platform4.setMustReload(false);
				sprite = platform4;

				break;

			case SpriteTypeConstant.PLATFORM3:

				SpriteFloor platform3 = new SpriteFloor(200, 0,
						texture.getTextureByName("platform3.png"),
						texture.getVertexBufferObjectManager());
				platform3.setCollitionType(CollitionType.COLLITION_RECTANGLE);
				platform3.setZIndex(ZIndexGame.ROOF);
				platform3.setMustReload(false);
				sprite = platform3;

				break;

			case SpriteTypeConstant.PLATFORM2:

				SpriteFloor platform2 = new SpriteFloor(200, 0,
						texture.getTextureByName("platform2.png"),
						texture.getVertexBufferObjectManager());
				platform2.setCollitionType(CollitionType.COLLITION_RECTANGLE);
				platform2.setZIndex(ZIndexGame.ROOF);
				platform2.setMustReload(false);
				sprite = platform2;

				break;
			case SpriteTypeConstant.PLATFORM1:

				SpriteFloor platform1 = new SpriteFloor(200, 0,
						texture.getTextureByName("platform1.png"),
						texture.getVertexBufferObjectManager());
				platform1.setCollitionType(CollitionType.COLLITION_RECTANGLE);
				platform1.setZIndex(ZIndexGame.ROOF);
				platform1.setMustReload(false);
				sprite = platform1;

				break;

			case SpriteTypeConstant.FLOOR5:

				SpriteFloor floor5 = new SpriteFloor(200, 0,
						texture.getTextureByName("floor5.png"),
						texture.getVertexBufferObjectManager());
				floor5.setCollitionType(CollitionType.COLLITION_RECTANGLE_GEOMETRY);
				floor5.setZIndex(ZIndexGame.ROOF);
				floor5.setMustReload(false);
				sprite = floor5;

				break;

			case SpriteTypeConstant.FLOOR4:

				SpriteFloor floor4 = new SpriteFloor(200, 0,
						texture.getTextureByName("floor4.png"),
						texture.getVertexBufferObjectManager());
				floor4.setCollitionType(CollitionType.COLLITION_RECTANGLE_GEOMETRY);
				floor4.setZIndex(ZIndexGame.ROOF);
				floor4.setMustReload(false);
				sprite = floor4;

				break;

			case SpriteTypeConstant.HEDGEHOG1:

				SpriteHedgehog hedgehog = new SpriteHedgehog(200, 0,
						texture.getTextureByName("hedgehog1.png"),
						texture.getVertexBufferObjectManager());
				hedgehog.setCollitionType(CollitionType.COLLITION_XML);
				hedgehog.setXmlName("hedgehog1.xml");
				hedgehog.setZIndex(ZIndexGame.HEDGEHOG);
				hedgehog.setMustReload(false);
				sprite = hedgehog;

				break;

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

	public static SpritePlayerShot createShot(LevelController controller) {
		try {

			SpritePlayerShot shot = new SpritePlayerShot(0, 0,
					texture.getTextureAnimateByName("shot.png"),
					texture.getVertexBufferObjectManager(), controller);
			shot.setCollitionType(CollitionType.COLLITION_CIRCULAR);
			shot.setZIndex(ZIndexGame.FADE);

			return shot;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
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

	public static ButtonDialog createDialogButton(LevelController level) {
		try {
			
			ButtonDialog dialog = new ButtonDialog(0, 0, texture.getTextureAnimateByName("cloud.png"), texture.getVertexBufferObjectManager(), level);
			dialog.setAlpha(1);
			dialog.setZIndex(ZIndexGame.FADE);
			return dialog;
			
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

	public static SpriteChestAdventure createChest(Item item, LevelController level){
		try {
		
			SpriteChestAdventure chest = new SpriteChestAdventure(0, 0, texture.getTextureAnimateByName("chest.png"), texture.getVertexBufferObjectManager(),level);
			chest.setItem(item);
			return chest;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static ButtonPickUp createPickUpButton(LevelController level) {
		
		try {
			ButtonPickUp pick = new ButtonPickUp(0, 0, texture.getTextureAnimateByName("cloud.png"), texture.getVertexBufferObjectManager(), level);
			pick.setAlpha(1);
			pick.setZIndex(ZIndexGame.FADE);
			
			return pick;
		} catch (Exception e) {
			
		}
		return null;
	}
	
}
