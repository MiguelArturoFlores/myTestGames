package com.mgl.base;

import android.graphics.Point;

import com.google.example.games.basegameutils.ButtonLeaderboard;
import com.mgl.base.userinfo.PoolObjectSingleton;
import com.mgl.drop.game.constant.CollitionType;
import com.mgl.drop.game.constant.ZIndexGame;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.hud.sprites.SpriteMessage;
import com.mgl.drop.game.sprites.SpriteBackground;
import com.mgl.drop.game.sprites.SpriteBeginPoint;
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
import com.mgl.drop.game.sprites.ayoutuberunner.SpriteColorChange;
import com.mgl.drop.game.sprites.ayoutuberunner.SpriteJump;
import com.mgl.drop.game.sprites.ayoutuberunner.SpriteLikeCoin;
import com.mgl.drop.game.sprites.ayoutuberunner.SpriteLikeCoinDead;
import com.mgl.drop.game.sprites.ayoutuberunner.SpritePlayerCollition;
import com.mgl.drop.game.sprites.ayoutuberunner.SpriteStarGenerator;
import com.mgl.drop.game.sprites.ayoutuberunner.SpriteStarGeometry;
import com.mgl.drop.game.sprites.ayoutuberunner.SpriteTunnel;
import com.mgl.drop.game.sprites.ayoutuberunner.SpriteWinPlayer;
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
			
			SpritePlayerCollition player = new SpritePlayerCollition(0, 0, texture.getTextureByName("floor1.png"), texture.getVertexBufferObjectManager());
			player.setSize(50, 50);
			player.setCollitionType(CollitionType.COLLITION_XML);
			player.setXmlName("playerCollition.xml");
			player.setMustReload(true);
			player.setZIndex(ZIndexGame.PLAYER);
			return player;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static SpritePlayer createPlayer(LevelController level) {
		try {

			SpritePlayer player = PoolObjectSingleton.getInstance().getPlayer();
			player.setLevel(level);
			player.setSize(50, 50);
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
			
			case SpriteTypeConstant.BEGIN_POINT:

				SpriteBeginPoint beginPoint = new SpriteBeginPoint(200, 0,
						texture.getTextureByName("tunelCover.png"),
						texture.getVertexBufferObjectManager(),controller);
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
				//cover.setSize(50, 200);
				cover.setZIndex(ZIndexGame.TUNEL_FRONT);
				sprite = cover;
				break;


			case SpriteTypeConstant.STAR_GEOMETRY:

				SpriteStarGeometry starGeometry = (SpriteStarGeometry) PoolObjectSingleton.getInstance().getStar();
				//SpriteStarGeometry starGeometry = new SpriteStarGeometry(200,
				//		0, texture.getTextureByName("backStar.png"),
				//		texture.getVertexBufferObjectManager());
				starGeometry.reset();
				starGeometry.setLevel(controller);
				sprite = starGeometry;
				break;

			
			case SpriteTypeConstant.STAR_GENERATOR:

				SpriteStarGenerator starGenerator = new SpriteStarGenerator(200, 0,
						texture.getTextureByName("starGenerator.png"),
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
				//tunel.setSize(50, 200);
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

				SpriteLikeCoinDead likeCoinDead = new SpriteLikeCoinDead(200, 0,
						texture.getTextureAnimateByName("likeCoinDead.png"),
						texture.getVertexBufferObjectManager(),controller);
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
				triangle5.setCollitionType(CollitionType.COLLITION_TRIANGLEPLUS);
				triangle5.setZIndex(ZIndexGame.HEDGEHOG);
				triangle5.setMustReload(false);
				sprite = triangle5;

				break;
				
			case SpriteTypeConstant.TRIANGLE4:

				SpriteHedgehog triangle4 = new SpriteHedgehog(200, 0,
						texture.getTextureByName("p4.png"),
						texture.getVertexBufferObjectManager());
				triangle4.setCollitionType(CollitionType.COLLITION_TRIANGLEPLUS);
				triangle4.setZIndex(ZIndexGame.HEDGEHOG);
				triangle4.setMustReload(false);
				sprite = triangle4;

				break;

				
			case SpriteTypeConstant.TRIANGLE3:

				SpriteHedgehog triangle3 = new SpriteHedgehog(200, 0,
						texture.getTextureByName("p3.png"),
						texture.getVertexBufferObjectManager());
				triangle3.setCollitionType(CollitionType.COLLITION_TRIANGLEPLUS3);
				triangle3.setZIndex(ZIndexGame.HEDGEHOG);
				triangle3.setMustReload(false);
				sprite = triangle3;

				break;

				
			case SpriteTypeConstant.TRIANGLE2:

				SpriteHedgehog triangle2 = new SpriteHedgehog(200, 0,
						texture.getTextureByName("p2.png"),
						texture.getVertexBufferObjectManager());
				triangle2.setCollitionType(CollitionType.COLLITION_TRIANGLEPLUS);
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
				levelGeneralFloor.setCollitionType(CollitionType.COLLITION_RECTANGLE);
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
				
			case SpriteTypeConstant.FLOOR3:

				SpriteFloor floor3 = new SpriteFloor(200, 0,
						texture.getTextureByName("floor3.png"),
						texture.getVertexBufferObjectManager());
				floor3.setCollitionType(CollitionType.COLLITION_RECTANGLE_GEOMETRY);
				floor3.setZIndex(ZIndexGame.ROOF);
				floor3.setMustReload(false);
				sprite = floor3;

				break;
				
			case SpriteTypeConstant.FLOOR2:

				SpriteFloor floor2 = new SpriteFloor(200, 0,
						texture.getTextureByName("floor2.png"),
						texture.getVertexBufferObjectManager());
				floor2.setCollitionType(CollitionType.COLLITION_RECTANGLE_GEOMETRY);
				floor2.setZIndex(ZIndexGame.ROOF);
				floor2.setMustReload(false);
				sprite = floor2;

				break;
			case SpriteTypeConstant.FLOOR1:

				SpriteFloor floor1 = new SpriteFloor(200, 0,
						texture.getTextureByName("floor1.png"),
						texture.getVertexBufferObjectManager());
				floor1.setCollitionType(CollitionType.COLLITION_RECTANGLE_GEOMETRY);
				floor1.setZIndex(ZIndexGame.ROOF);
				floor1.setMustReload(false);
				sprite = floor1;

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
			
			ButtonLeaderboard leaderboard = new ButtonLeaderboard(0, 0, texture.getTextureByName("googleLeaderboard.png"), texture.getVertexBufferObjectManager());
			return leaderboard;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	

}
