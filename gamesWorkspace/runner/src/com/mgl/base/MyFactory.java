package com.mgl.base;

import android.graphics.Point;

import com.mgl.base.userinfo.PoolObjectSingleton;
import com.mgl.drop.game.constant.CollitionType;
import com.mgl.drop.game.constant.ZIndexGame;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.hud.sprites.SpriteMessage;
import com.mgl.drop.game.sprites.SpriteBackground;
import com.mgl.drop.game.sprites.SpriteSmashGeneral;
import com.mgl.drop.game.sprites.arunner.SpriteADN;
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

	public static SpritePlayer createPlayer(LevelController level) {
		try {

			SpritePlayer player = PoolObjectSingleton.getInstance().getPlayer();
			player.setLevel(level);
			player.setCollitionType(CollitionType.COLLITION_XML);
			player.setXmlName("player.xml");
			player.setMustReload(true);
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
			
			case SpriteTypeConstant.TURBO_BACK:

				SpriteTurboBack turboBack = new SpriteTurboBack(200, 0,
						texture.getTextureByName("turboBack.png"),
						texture.getVertexBufferObjectManager());
				turboBack.setCollitionType(CollitionType.COLLITION_NONE);
				turboBack.setZIndex(ZIndexGame.TURBO_BACK);
				sprite = turboBack;

				break;
			case SpriteTypeConstant.DECORATIVE_SHOT:

				SpriteDecorativeShot decoShot = new SpriteDecorativeShot(200, 0,
						texture.getTextureAnimateByName("powerShot.png"),
						texture.getVertexBufferObjectManager(),controller);
				decoShot.setCollitionType(CollitionType.COLLITION_NONE);
				decoShot.setZIndex(ZIndexGame.FADE);
				sprite = decoShot;

				break;
			
			case SpriteTypeConstant.EYE:

				SpriteBackground eye = new SpriteBackground(200, 0,
						texture.getTextureByName("eye.png"),
						texture.getVertexBufferObjectManager());
				eye.setCollitionType(CollitionType.COLLITION_NONE);
				eye.setZIndex(ZIndexGame.FADE);
				sprite = eye;

				break;
			
			case SpriteTypeConstant.CONTROLL:

				SpriteMoveControll controll = new SpriteMoveControll(200, 0,
						texture.getTextureByName("joystickLimit.png"),
						texture.getVertexBufferObjectManager(),controller);
				//controll.setSize(150, 150);
				controll.setCollitionType(CollitionType.COLLITION_NONE);
				controll.setZIndex(ZIndexGame.FADE);
				sprite = controll;

				break;
			
			case SpriteTypeConstant.CONTROLL_BALL:

				SpriteBackground controllBall = new SpriteBackground(200, 0,
						texture.getTextureByName("joystickBall.png"),
						texture.getVertexBufferObjectManager());
				controllBall.setCollitionType(CollitionType.COLLITION_NONE);
				controllBall.setZIndex(ZIndexGame.FADE);
				sprite = controllBall;

				break;
				
			case SpriteTypeConstant.CHECK_POINT:

				SpriteCheckPoint checkPoint = new SpriteCheckPoint(200, 0,
						texture.getTextureAnimateByName("checkPoint.png"),
						texture.getVertexBufferObjectManager(),controller);
				checkPoint.setSize(100, 100);
				checkPoint.setCollitionType(CollitionType.COLLITION_NONE);
				checkPoint.setZIndex(ZIndexGame.CHECK_POINT);
				sprite = checkPoint;

				break;
			
			case SpriteTypeConstant.ICE_POWER_CIRCLE:

				SpriteIceCircleAnimated iceCircle = new SpriteIceCircleAnimated(200, 0,
						texture.getTextureAnimateByName("powerIceCircleAnimated.png"),
						texture.getVertexBufferObjectManager(),controller);
				iceCircle.setCollitionType(CollitionType.COLLITION_NONE);
				iceCircle.setZIndex(ZIndexGame.ICE_POWER_CIRCLE);
				sprite = iceCircle;

				break;
			case SpriteTypeConstant.ICE:

				SpriteIce ice = new SpriteIce(200, 0,
						texture.getTextureAnimateByName("ice.png"),
						texture.getVertexBufferObjectManager(),controller);
				ice.setCollitionType(CollitionType.COLLITION_NONE);
				ice.setZIndex(ZIndexGame.ICE);
				sprite = ice;

				break;
			
			case SpriteTypeConstant.STAR:

				SpriteStar star = new SpriteStar(200, 0,
						texture.getTextureAnimateByName("bubbleStar.png"),
						texture.getVertexBufferObjectManager(),controller);
				star.setCollitionType(CollitionType.COLLITION_NONE);
				star.setZIndex(ZIndexGame.STAR);
				star.setSize(100, 100);
				sprite = star;

				break;
			
			case SpriteTypeConstant.VULCANO:

				SpriteVulcano vulcano = new SpriteVulcano(200, 0,
						texture.getTextureByName("vulcano.png"),
						texture.getVertexBufferObjectManager());
				vulcano.setCollitionType(CollitionType.COLLITION_XML);
				vulcano.setZIndex(ZIndexGame.VULCANO);
				vulcano.setXmlName("vulcano.xml");
				//vulcano.setSize(15, 15);
				vulcano.setMustReload(false);
				sprite = vulcano;

				break;
			
			case SpriteTypeConstant.BURBLE_VULCANO:

				//SpriteVulcanoBurble burble = new SpriteVulcanoBurble(200, 0,
				//		texture.getTextureAnimateByName("burble.png"),
				//		texture.getVertexBufferObjectManager(),controller);
				SpriteVulcanoBurble burble  = PoolObjectSingleton.getInstance().getBurble();
				burble.init();
				burble.detachSelf();
				burble.setParent(null);
				burble.setCollitionType(CollitionType.COLLITION_NONE);
				burble.setZIndex(ZIndexGame.BURBLE);
				burble.setSize(15, 15);
				sprite = burble;

				break;
			
			case SpriteTypeConstant.ADN:

				SpriteADN adn = new SpriteADN(200, 0,
						texture.getTextureAnimateByName("adn2Animated.png"),
						texture.getVertexBufferObjectManager(),controller);
				adn.setCollitionType(CollitionType.COLLITION_NONE);
				adn.setZIndex(ZIndexGame.ADN);
				adn.setSize(100, 100);
				sprite = adn;

				break;
			
			case SpriteTypeConstant.PLAYER_DEAD:

				SpritePlayerDied dead = PoolObjectSingleton.getInstance().getPlayerDead();
				dead.setLevel(controller);
				//dead.setSize(100, 100);
				dead.setCollitionType(CollitionType.COLLITION_NONE);
				dead.setZIndex(ZIndexGame.FADE);
				sprite = dead;

				break;
				
			case SpriteTypeConstant.WALL_BORDER:

				SpriteBackground wall = new SpriteBackground(200, 0,
						texture.getTextureByName("wall.png"),
						texture.getVertexBufferObjectManager());
				wall.setCollitionType(CollitionType.COLLITION_XML);
				wall.setXmlName("wallBorder.xml");
				wall.setZIndex(ZIndexGame.WALL_BORDER);
				wall.setMustReload(false);
				sprite = wall;

				break;
			case SpriteTypeConstant.PLAYER_BEGIN:

				SpriteReleasePlayer playerBegin = new SpriteReleasePlayer(200, 0,
						texture.getTextureByName("wallBorder.png"),
						texture.getVertexBufferObjectManager());
				playerBegin.setCollitionType(CollitionType.COLLITION_XML);
				playerBegin.setXmlName("suction.xml");
				playerBegin.setFinish(false);
				playerBegin.setZIndex(ZIndexGame.PLAYER_BEGIN);
				playerBegin.setMustReload(true);
				sprite = playerBegin;

				break;
			case SpriteTypeConstant.PLAYER_END:

				SpriteReleasePlayer playerEnd = new SpriteReleasePlayer(200, 0,
						texture.getTextureByName("wallBorder.png"),
						texture.getVertexBufferObjectManager());
				playerEnd.setCollitionType(CollitionType.COLLITION_XML);
				playerEnd.setXmlName("suction.xml");
				playerEnd.setFinish(true);
				playerEnd.setZIndex(ZIndexGame.PLAYER_BEGIN);
				playerEnd.setMustReload(false);
				sprite = playerEnd;

				break;
			case SpriteTypeConstant.METEOR_LEVEL:

				SpriteMeteorLevelSelect meteorLevel = new SpriteMeteorLevelSelect(200, 0,
						texture.getTextureAnimateByName("burble.png"),
						texture.getVertexBufferObjectManager());
				meteorLevel.setSize(100, 100);
				meteorLevel.setCollitionType(CollitionType.COLLITION_NONE);
				meteorLevel.setZIndex(ZIndexGame.METEOR);
				sprite = meteorLevel;

				break;
			case SpriteTypeConstant.METEOR:

				SpriteMeteor meteor = new SpriteMeteor(200, 0,
						texture.getTextureByName("meteor1.png"),
						texture.getVertexBufferObjectManager());
				meteor.setSize(100, 100);
				meteor.setCollitionType(CollitionType.COLLITION_NONE);
				meteor.setZIndex(ZIndexGame.METEOR);
				sprite = meteor;

				break;
			case SpriteTypeConstant.METEOR2:

				SpriteMeteor meteor2 = new SpriteMeteor(200, 0,
						texture.getTextureByName("meteor2.png"),
						texture.getVertexBufferObjectManager());
				meteor2.setSize(100, 100);
				meteor2.setCollitionType(CollitionType.COLLITION_NONE);
				meteor2.setZIndex(ZIndexGame.METEOR);
				sprite = meteor2;

				break;
			case SpriteTypeConstant.METEOR3:

				SpriteMeteor meteor3 = new SpriteMeteor(200, 0,
						texture.getTextureByName("meteor3.png"),
						texture.getVertexBufferObjectManager());
				meteor3.setSize(100, 100);
				meteor3.setCollitionType(CollitionType.COLLITION_NONE);
				meteor3.setZIndex(ZIndexGame.METEOR);
				sprite = meteor3;

				break;

			case SpriteTypeConstant.ENEMY:

				SpriteEnemyBasic enemy = new SpriteEnemyBasic(200, 0,
						texture.getTextureAnimateByName("enemy2.png"),
						texture.getVertexBufferObjectManager(),controller);
				//enemy.setSize(50, 50);
				enemy.setCollitionType(CollitionType.COLLITION_XML);
				enemy.setXmlName("enemy.xml");
				enemy.setZIndex(ZIndexGame.ENEMY);
				sprite = enemy;

				break;
				
			case SpriteTypeConstant.ENEMY_TRIANGLE:

				SpriteEnemyTriangle enemyTriangle = new SpriteEnemyTriangle(200, 0,
						texture.getTextureAnimateByName("enemy3.png"),
						texture.getVertexBufferObjectManager(),controller);
				//enemyTriangle.setSize(50, 50);
				enemyTriangle.setCollitionType(CollitionType.COLLITION_XML);
				enemyTriangle.setXmlName("enemyTriangle.xml");
				enemyTriangle.setZIndex(ZIndexGame.ENEMY);
				sprite = enemyTriangle;

				break;
				
			case SpriteTypeConstant.ENEMY_ROMBE:

				SpriteEnemyRombe enemyRombe = new SpriteEnemyRombe(200, 0,
						texture.getTextureAnimateByName("enemy1.png"),
						texture.getVertexBufferObjectManager(),controller);
				enemyRombe.setCollitionType(CollitionType.COLLITION_XML);
				enemyRombe.setXmlName("enemyRombe.xml");
				enemyRombe.setZIndex(ZIndexGame.ENEMY);
				sprite = enemyRombe;

				break;
				
			case SpriteTypeConstant.ENEMY_ROMBE_BURBLE:

				SpriteEnemyRombeBurble enemyRombeBurble = new SpriteEnemyRombeBurble(200, 0,
						texture.getTextureAnimateByName("enemy.png"),
						texture.getVertexBufferObjectManager(),controller);
				enemyRombeBurble.setCollitionType(CollitionType.COLLITION_XML);
				enemyRombeBurble.setXmlName("enemyRombe.xml");
				enemyRombeBurble.setZIndex(ZIndexGame.ENEMY);
				sprite = enemyRombeBurble;

				break;

			case SpriteTypeConstant.ROOF_BEGIN1:

				SpriteFloor roofBegin1 = new SpriteFloor(200, 0,
						texture.getTextureByName("roofBegin1.png"),
						texture.getVertexBufferObjectManager());
				roofBegin1.setCollitionType(CollitionType.COLLITION_XML);
				roofBegin1.setXmlName("roofBegin1.xml");
				roofBegin1.setZIndex(ZIndexGame.ROOF);
				roofBegin1.setMustReload(false);
				sprite = roofBegin1;

				break;

				
			case SpriteTypeConstant.ROOF_BASE1:

				SpriteFloor roofBase1 = new SpriteFloor(200, 0,
						texture.getTextureByName("roofBase1.png"),
						texture.getVertexBufferObjectManager());
				roofBase1.setCollitionType(CollitionType.COLLITION_XML);
				roofBase1.setXmlName("roofBase1.xml");
				roofBase1.setZIndex(ZIndexGame.ROOF);
				roofBase1.setMustReload(false);
				sprite = roofBase1;

				break;
				
			case SpriteTypeConstant.ROOF_BASE:

				SpriteFloor roofBase = new SpriteFloor(200, 0,
						texture.getTextureByName("roofBase.png"),
						texture.getVertexBufferObjectManager());
				roofBase.setCollitionType(CollitionType.COLLITION_XML);
				roofBase.setXmlName("roofBase.xml");
				roofBase.setZIndex(ZIndexGame.ROOF);
				roofBase.setMustReload(false);
				sprite = roofBase;

				break;
			case SpriteTypeConstant.ROOF_BEGIN:

				SpriteFloor roofBegin = new SpriteFloor(200, 0,
						texture.getTextureByName("roofBegin.png"),
						texture.getVertexBufferObjectManager());
				roofBegin.setCollitionType(CollitionType.COLLITION_XML);
				roofBegin.setXmlName("roofBegin.xml");
				roofBegin.setZIndex(ZIndexGame.ROOF);
				roofBegin.setMustReload(false);
				sprite = roofBegin;

				break;

			case SpriteTypeConstant.ROOF_END:

				SpriteFloor roofEnd = new SpriteFloor(200, 0,
						texture.getTextureByName("roofEnd.png"),
						texture.getVertexBufferObjectManager());
				roofEnd.setCollitionType(CollitionType.COLLITION_XML);
				roofEnd.setXmlName("roofEnd.xml");
				roofEnd.setZIndex(ZIndexGame.ROOF);
				roofEnd.setMustReload(false);
				sprite = roofEnd;

				break;

			case SpriteTypeConstant.TUNEL:

				SpriteTunel tunel = new SpriteTunel(200, 0,
						texture.getTextureByName("tunelBack.png"),
						texture.getVertexBufferObjectManager(),
						controller.getScene());
				tunel.setCollitionType(CollitionType.COLLITION_XML);
				tunel.setXmlName("tunel.xml");
				tunel.setZIndex(ZIndexGame.TUNEL);
				tunel.setMustReload(false);
				sprite = tunel;

				break;

			case SpriteTypeConstant.TUNEL_FRONT:

				SpriteFloor tunelFront = new SpriteFloor(200, 0,
						texture.getTextureByName("tunelFront.png"),
						texture.getVertexBufferObjectManager());
				tunelFront.setCollitionType(CollitionType.COLLITION_XML);
				tunelFront.setXmlName("tunel.xml");
				tunelFront.setZIndex(ZIndexGame.TUNEL_FRONT);
				tunelFront.setMustReload(false);
				sprite = tunelFront;

				break;
				
			case SpriteTypeConstant.BONE4:

				SpriteBackground bone4 = new SpriteBackground(200, 0,
						texture.getTextureByName("bone4.png"),
						texture.getVertexBufferObjectManager());
				bone4.setCollitionType(CollitionType.COLLITION_XML);
				bone4.setXmlName("bone4.xml");
				bone4.setZIndex(ZIndexGame.BONE);
				bone4.setMustReload(false);
				sprite = bone4;

				break;
				
			case SpriteTypeConstant.BONE1:

				SpriteBackground bone1 = new SpriteBackground(200, 0,
						texture.getTextureByName("bone1.png"),
						texture.getVertexBufferObjectManager());
				bone1.setCollitionType(CollitionType.COLLITION_XML);
				bone1.setXmlName("bone1.xml");
				bone1.setZIndex(ZIndexGame.BONE);
				bone1.setMustReload(false);
				sprite = bone1;

				break;
				
			case SpriteTypeConstant.BONE2:

				SpriteBackground bone2 = new SpriteBackground(200, 0,
						texture.getTextureByName("bone2.png"),
						texture.getVertexBufferObjectManager());
				bone2.setCollitionType(CollitionType.COLLITION_XML);
				bone2.setXmlName("bone2.xml");
				bone2.setZIndex(ZIndexGame.BONE);
				bone2.setMustReload(false);
				sprite = bone2;

				break;
			case SpriteTypeConstant.BONE3:

				SpriteBackground bone3 = new SpriteBackground(200, 0,
						texture.getTextureByName("bone3.png"),
						texture.getVertexBufferObjectManager());
				bone3.setCollitionType(CollitionType.COLLITION_XML);
				bone3.setXmlName("bone3.xml");
				bone3.setZIndex(ZIndexGame.BONE);
				bone3.setMustReload(false);
				sprite = bone3;

				break;
			case SpriteTypeConstant.PLATFORM:

				SpritePlatform platform = new SpritePlatform(200, 0,
						texture.getTextureByName("platform.png"),
						texture.getVertexBufferObjectManager());
				platform.setCollitionType(CollitionType.COLLITION_XML);
				platform.setXmlName("platform.xml");
				platform.setZIndex(ZIndexGame.PLATFORM);
				platform.setMustReload(false);
				sprite = platform;

				break;

			case SpriteTypeConstant.PLATFORM_MOBILE:

				SpritePlatformMobile platformm = new SpritePlatformMobile(200, 0,
						texture.getTextureByName("platform.png"),
						texture.getVertexBufferObjectManager());
				platformm.setCollitionType(CollitionType.COLLITION_XML);
				platformm.setXmlName("platformMobile.xml");
				platformm.setTimeToChange(1.5f);
				platformm.setZIndex(ZIndexGame.PLATFORM);
				sprite = platformm;

				break;
			case SpriteTypeConstant.HEDGEHOG4:

				SpriteHedgehog hedgehog4 = new SpriteHedgehog(200, 0,
						texture.getTextureByName("hedgehog4.png"),
						texture.getVertexBufferObjectManager());
				hedgehog4.setCollitionType(CollitionType.COLLITION_XML);
				hedgehog4.setXmlName("hedgehog4.xml");
				hedgehog4.setZIndex(ZIndexGame.HEDGEHOG);
				hedgehog4.setMustReload(false);
				sprite = hedgehog4;

				break;
			case SpriteTypeConstant.HEDGEHOG3:

				SpriteHedgehog hedgehog3 = new SpriteHedgehog(200, 0,
						texture.getTextureByName("hedgehog3.png"),
						texture.getVertexBufferObjectManager());
				hedgehog3.setCollitionType(CollitionType.COLLITION_XML);
				hedgehog3.setXmlName("hedgehog3.xml");
				hedgehog3.setZIndex(ZIndexGame.HEDGEHOG);
				hedgehog3.setMustReload(false);
				sprite = hedgehog3;

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
				hedgehog2.setCollitionType(CollitionType.COLLITION_XML);
				hedgehog2.setXmlName("hedgehog2.xml");
				hedgehog2.setZIndex(ZIndexGame.HEDGEHOG);
				hedgehog2.setMustReload(false);
				sprite = hedgehog2;

				break;

			case SpriteTypeConstant.LAKE:

				SpriteLake lake = new SpriteLake(200, 0,
						texture.getTextureByName("lakeBase.png"),
						texture.getVertexBufferObjectManager(), controller);
				lake.setCollitionType(CollitionType.COLLITION_XML);
				lake.setXmlName("lakeBase.xml");
				lake.setMustReload(false);
				lake.setZIndex(ZIndexGame.LAKE);
				sprite = lake;

				break;
			case SpriteTypeConstant.GEL:

				SpriteGel gel = new SpriteGel(200, 0,
						texture.getTextureByName("gel.png"),
						texture.getVertexBufferObjectManager());
				gel.setXmlName("gel.xml");
				gel.setCollitionType(CollitionType.COLLITION_XML);
				gel.setZIndex(ZIndexGame.GEL);
				sprite = gel;

				break;
			case SpriteTypeConstant.SMASHER:
				SpriteSmasher smash = new SpriteSmasher(500, 0,
						texture.getTextureByName("smasherBone.png"),
						texture.getVertexBufferObjectManager());
				smash.setXmlName("smasherBone.xml");
				smash.setCollitionType(CollitionType.COLLITION_XML);
				sprite = smash;
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
			
			SpritePlayerShot shot = new SpritePlayerShot(0, 0, texture.getTextureAnimateByName("shot.png"), texture.getVertexBufferObjectManager(),controller);
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
			
			return new SpriteMessage(0, 0, texture.getTextureByName("black.jpg"), texture.getVertexBufferObjectManager(), message, timeToShow);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
