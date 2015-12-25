package com.mgl.drop.game.sprites.arunner;

import java.util.ResourceBundle.Control;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.Scene;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.util.Log;

import com.badlogic.gdx.math.Vector2;
import com.mgl.base.MyAnimateProperty;
import com.mgl.base.MyAnimateSprite;
import com.mgl.base.MyFactory;
import com.mgl.base.MySpriteGeneral;
import com.mgl.base.SpriteType;
import com.mgl.base.SpriteTypeConstant;
import com.mgl.base.userinfo.PhysicSingleton;
import com.mgl.base.userinfo.PoolObjectSingleton;
import com.mgl.base.userinfo.UserInfoSingleton;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.factory.SoundSingleton;
import com.mgl.drop.game.constant.CollitionType;
import com.mgl.drop.game.constant.GameConstants;
import com.mgl.drop.game.constant.State;
import com.mgl.drop.game.constant.ZIndexGame;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.scene.SceneManagerSingleton;
import com.mgl.drop.game.sprites.SpriteBackground;
import com.mgl.drop.util.Point;

public class SpritePlayer extends MyAnimateSprite {

	private float MID_EYE_X;
	private float MID_EYE_Y;
	private float RADIUS = 5;

	private boolean reduceSpeed = false;

	private float contSlow = 0f;
	private float timeSlow = 0.1f;
	private Point pointToWin = null;
	private float linearVelocity = 7;
	private float contTimeToAccelerate = 0;
	private float timeToAccelerate = 0.3f;

	private float contTurboBack = 0;
	private float timeTurboBack = 0.05f;

	private float contToDieInGel = 0;
	private float timeToDieInGel = 2;

	private SpriteGel gel;
	private SpriteEnemy enemy;

	private boolean isQuietInGel = false;
	private boolean accelerating = false;
	private float contDistance = 0f;
	private float distanceAccelerate = 0.3f;
	private boolean loose = false;
	private float angle = 0;
	
	private int direcction;
	private boolean burble = false;

	private boolean isHorizontal = true;

	private boolean mustToDie = false;

	private SpriteBackground playerEye;
	private SpriteDecorativeShot decorativeShot;

	public SpritePlayer(float pX, float pY, ITextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager,
			LevelController level) {
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager, level);
		try {
			pointToWin = null;
			contTimeToAccelerate = 0;
			contTimeToAccelerate = 0;
			contToDieInGel = 0;
			loose = false;
			isQuietInGel = false;
			gel = null;
			enemy = null;
			burble = false;
			mustToDie = false;

			playerEye = (SpriteBackground) MyFactory.createObstacle(
					SpriteTypeConstant.EYE, level);
			// playerEye.setPosition(this.getWidth()/3 - playerEye.getWidth()/2,
			// this.getHeight()/3 - playerEye.getHeight()/2);
			playerEye.setPosition(42 - playerEye.getWidth() / 2,
					40 - playerEye.getHeight() / 2);
			MID_EYE_X = playerEye.getX();
			MID_EYE_Y = playerEye.getY();

			this.attachChild(playerEye);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public SpriteType getSpriteType() {
		return SpriteType.PLAYER;
	}

	@Override
	public void initHashMap() {
		try {

			fps = new long[] { 83, 83, 83, 83, 83, 83, 83, 83, 83 };

			stateAnimate.put(State.WALKIN_RIGHT, new MyAnimateProperty(0, 9,
					fps));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void initAnimationParams() {
		try {

			changeAnimateState(State.WALKIN_RIGHT, true);
			anime(true);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void updateAnimated(float dTime, LevelController level) {
		try {

			if (pointToWin != null) {
				updateMovingToWin();
				return;
			}

			if (enemy != null) {
				verifyEnemyKill(dTime, level);
			}

			if (loose) {
				this.detachSelf();
				level.removeEntity(this);
				SoundSingleton.getInstance().playPlayerDead();
				return;
			}

			contTimeToAccelerate = contTimeToAccelerate + dTime;

			Camera cam = SceneManagerSingleton.getInstance().getCamera();
			if (this.getX() + this.getWidth() < cam.getCenterX()
					- MainDropActivity.CAMERA_WIDTH / 2 - 50
					|| this.getY() > cam.getCenterY()
							+ MainDropActivity.CAMERA_HEIGHT / 2 + 50
					|| mustToDie) {
				level.looseLevelReplay();
				// level.setCheckPoint(null);
			}

			contSlow = contSlow + dTime;
			if (contSlow >= timeSlow) {
				updateSlow();
				contSlow = 0;
			}

			if (isQuietInGel) {
				updateQuietInGel(dTime, level);
				return;
			}

			if (accelerating) {
				updateAccelerating(dTime, level);
			}

			decorativeShot.update(dTime, level);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void verifyEnemyKill(float dTime, LevelController level) {
		try {
			if (enemy == null) {
				return;
			}

			if (enemy instanceof SpriteEnemyTriangle) {

				level.looseLevelReplay();

			} else if (enemy instanceof SpriteEnemyRombe) {

				level.looseLevelReplay();

			} else if (enemy instanceof SpriteEnemyRombe) {

				level.looseLevelReplay();
			}

			enemy = null;
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void updateQuietInGel(float dTime, LevelController level) {
		try {

			if (gel == null) {
				return;
			}

			contToDieInGel = contToDieInGel + dTime;
			if (contToDieInGel > timeToDieInGel) {
				// showLoosing(level);
				contToDieInGel = 0;
				mustToDie = true;
				return;
			}

			float angle = Point.angleBetween(
					new Point(this.getX(), this.getY()), new Point(gel.getX()
							+ gel.getWidth() / 2, gel.getY() + gel.getHeight()
							/ 2));

			float dx = linearVelocity / 5 * (float) Math.cos(angle);
			float dy = linearVelocity / 2 * (float) Math.sin(angle);

			this.getBody().setLinearVelocity(dx, dy);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void updateAccelerating(float dTime, LevelController level) {
		try {

			contTurboBack = contTurboBack + dTime;
			if (contTurboBack > timeTurboBack) {
				contTurboBack = 0;
				SpriteTurboBack turbo = (SpriteTurboBack) MyFactory
						.createObstacle(SpriteTypeConstant.TURBO_BACK, level);
				turbo.setSize(this.getWidth(), this.getHeight());
				turbo.setPosition(this);
				turbo.setZIndex(ZIndexGame.TURBO_BACK);
				level.addSpriteToUpdate(turbo);
				level.getScene().attachChild(turbo);
			}

			contDistance = contDistance + dTime;
			if (contDistance > distanceAccelerate) {
				accelerating = false;
				contDistance = 0;
				// this.getBody().setLinearVelocity(new Vector2(0f, 0f));
				return;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void updateMovingToWin() {
		try {

			float angle = Point.angleBetween(
					new Point(this.getX(), this.getY()), pointToWin);

			float dx = linearVelocity * 10 * (float) Math.cos(angle);
			float dy = linearVelocity * 10 * (float) Math.sin(angle);

			this.getBody().setLinearVelocity(dx, dy);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void updateSlow() {
		try {

			if (reduceSpeed) {

				Vector2 vector = this.getBody().getLinearVelocity();
				if (vector.x < 0) {
					vector.x = vector.x + 1f;
					if (vector.x <= 0) {
						vector.x = 0;

					}
				}
				if (vector.x > 0) {
					vector.x = vector.x - 1f;
					if (vector.x <= 0) {
						vector.x = 0;

					}
				}

				this.getBody().setLinearVelocity(vector);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onAreaTouched(final TouchEvent pSceneTouchEvent,
			final float pTouchAreaLocalX, final float pTouchAreaLocalY) {

		Log.d("TOQUYE", "TOQUE");
		switch (pSceneTouchEvent.getAction()) {
		case TouchEvent.ACTION_DOWN:

			getBody().applyLinearImpulse(new Vector2(0, 20),
					getBody().getWorldCenter());

			break;
		case TouchEvent.ACTION_MOVE:

			break;
		case TouchEvent.ACTION_UP:

			break;
		default:

			break;

		}

		return true;
	}

	public void slowForContact(boolean slow) {
		try {

			reduceSpeed = slow;

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void setPositionToWin(Point point) {
		try {

			pointToWin = point;

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void manageSceneTouchAccelerate(int direcction, boolean isHorizontal) {
		try {

			this.direcction = direcction;
			this.isHorizontal = isHorizontal;

			if (pointToWin != null) {
				return;
			}
			if (contTimeToAccelerate < timeToAccelerate) {
				return;
			}
			contTimeToAccelerate = 0;
			accelerating = true;

			/*
			 * this.getBody().applyLinearImpulse( new
			 * Vector2(GameConstants.PLAYER_SPEEDX * 2 * 3 direcction, 0 *(-5f *
			 * 12)), this.getBody().getWorldCenter());
			 */
			generateBurbles();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void generateBurbles() {
		try {

			try {

				SoundSingleton.getInstance().playBurbleStrong();

				for (int i = 0; i < MainDropActivity.getRandomMax(6, 8); i++) {
					SpriteVulcanoBurble burble = (SpriteVulcanoBurble) MyFactory
							.createObstacle(SpriteTypeConstant.BURBLE_VULCANO,
									level);

					float x = MainDropActivity.getRandomMax(
							(int) (this.getX() - 20),
							(int) (this.getX() + this.getWidth() - 20));
					float y = MainDropActivity.getRandomMax(
							(int) (this.getY() + 60),
							(int) (this.getY() + 60 + 25));

					float w = MainDropActivity.getRandomMax(7, 20);
					// float h = MainDropActivity.getRandomMax(5,20);
					burble.setSize(w, w);

					burble.setPosition(x, y);
					burble.setDirection(SpriteVulcanoBurble.DRECTION_UP);
					burble.setActive(false);
					burble.setZIndex(ZIndexGame.BURBLE);
					burble.setDistance(50);
					level.getScene().attachChild(burble);
					level.addSpriteToUpdate(burble);

				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void manageSceneTouch() {
		try {

			generateBurbles();

			if (pointToWin != null) {
				return;
			}

			float up = this.getBody().getMass() * 6;
			float right = this.getBody().getMass() * 0.7f;
			float xVel = this.getBody().getLinearVelocity().x;
			float yVel = this.getBody().getLinearVelocity().y;

			// player.getBody().setLinearVelocity(right/2, up/4*-1);
			this.getBody().setLinearVelocity(GameConstants.PLAYER_SPEEDX,
					GameConstants.PLAYER_SPEEDY);
			// player.getBody().applyLinearImpulse(new
			// Vector2(GameConstants.PLAYER_SPEEDX *2,-5f*15),
			// player.getBody().getWorldCenter());
			if (xVel < right / 2f) {
				// player.getBody().applyLinearImpulse(new Vector2(0.7f * right,
				// 0),
				// player.getBody().getWorldCenter());

			}

			if (yVel > up * -1) {
				// player.getBody().applyLinearImpulse(new Vector2(0, up * -1),
				// player.getBody().getWorldCenter());
			}

			// player.getBody().applyForce(new Vector2(0, -50),
			// player.getBody().getWorldCenter());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void shotToEnemy(LevelController level) {
		try {

			if (isQuietInGel) {
				return;
			}

			if (true) {
				shotToEnemyEye(level);
				return;
			}

			SoundSingleton.getInstance().playShot();

			createShotAnimation();

			SpritePlayerShot shot = MyFactory.createShot(level);
			shot.setZIndex(ZIndexGame.SHOT);

			shot.setSize(30, 30);
			shot.setCollitionType(CollitionType.COLLITION_CIRCULAR);
			PhysicSingleton.getInstance().loadSpriteInWorldXML(shot);

			float x = this.getX() + this.getWidth() * 1.3f;

			if (level.getControll().isRight()) {
				shot.setPointToMove(new Point(x + shot.getDistanceToMove(),
						this.getY()));
			} else {
				x = this.getX() - 20;
				shot.setPointToMove(new Point(x - shot.getDistanceToMove(),
						this.getY()));
			}
			shot.setY(this.getY() + this.getHeight() / 2);

			LevelController.setBodyPixelPositionOld(x, shot.getY(), 0,
					shot.getWidth(), shot.getHeight(), shot.getBody());

			level.getScene().attachChild(shot);
			level.addSpriteToUpdate(shot);

			if (true) {
				return;
			}

			float distaceMid = Point.distanceBetween(new Point(0, 0),
					new Point(this.getWidth() / 2, this.getHeight() / 2)) + 5;

			Point pointCenter = level.getControll().getPointCenter();
			Point pointBall = level.getControll().getPointBall();

			float angle = Point.angleBetween(pointCenter, pointBall);
			float dx = (12000) * (float) Math.cos(angle);
			float dy = (12000) * (float) Math.sin(angle);

			float dxAux = (distaceMid) * (float) Math.cos(angle);
			float dyAux = (distaceMid) * (float) Math.sin(angle);

			// shot.setY(this.getY()+this.getHeight()/2 + dyAux);
			// shot.setX(this.getX() + this.getWidth()/2+ dxAux);

			shot.setPointToMove(new Point(shot.getX() + dx, shot.getY() + dy));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void shotToEnemyEye(LevelController level) {
		try {

			SoundSingleton.getInstance().playShot();

			createShotAnimation();

			SpritePlayerShot shot = MyFactory.createShot(level);
			shot.setZIndex(ZIndexGame.SHOT);

			shot.setSize(30, 30);
			shot.setCollitionType(CollitionType.COLLITION_CIRCULAR);
			float anglePI = (float) (angle * 180/Math.PI);
			
			shot.setRotationCenter(shot.getWidth()/2,shot.getHeight()/2);
			shot.setRotation(anglePI);
			
			PhysicSingleton.getInstance().loadSpriteInWorldXML(shot);
			
			
			float distaceMid = Point.distanceBetween(new Point(0, 0),
					new Point(this.getWidth() / 2, this.getHeight() / 2)) + 5;

			//Point pointCenter = level.getControll().getPointCenter();
			//Point pointBall = new Point(playerEye.getX(),playerEye.getY()); 
			//float angle = Point.angleBetween(pointCenter, pointBall);
			
			float dx = (12000) * (float) Math.cos(angle);
			float dy = (12000) * (float) Math.sin(angle);

			float dxAux = (distaceMid) * (float) Math.cos(angle);
			float dyAux = (distaceMid) * (float) Math.sin(angle);

			shot.setY(this.getY()+this.getHeight()/2 + dyAux);
			shot.setX(this.getX() + this.getWidth()/2+ dxAux);

			shot.setPointToMove(new Point(shot.getX() + dx, shot.getY() + dy));

			//anglePI = 120;		
			
			LevelController.setBodyPixelPositionOld(shot.getX(), shot.getY(), angle,
					shot.getWidth(), shot.getHeight(), shot.getBody());

			//shot.getBody().setTransform(shot.getX(), shot.getY(), angle);
			level.getScene().attachChild(shot);
			level.addSpriteToUpdate(shot);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void createShotAnimation() {
		try {

			if (decorativeShot == null) {
				decorativeShot = (SpriteDecorativeShot) MyFactory
						.createObstacle(SpriteTypeConstant.DECORATIVE_SHOT,
								level);
			}

			if (decorativeShot.hasParent()) {
				return;
			}

			decorativeShot.init();
			decorativeShot.setSize(this.getWidth() * 1.3f,
					this.getHeight() * 1.3f);
			decorativeShot.setPosition(
					this.getWidth() / 2 - decorativeShot.getWidth() / 2,
					this.getHeight() / 2 - decorativeShot.getHeight() / 2);

			this.attachChild(decorativeShot);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void showLoosing(LevelController controller) {
		try {

			SpritePlayerDied dead = (SpritePlayerDied) MyFactory
					.createObstacle(SpriteTypeConstant.PLAYER_DEAD, controller);
			dead.setPosition(this.getX(), this.getY());
			controller.addSpriteToUpdate(dead);
			controller.getScene().attachChild(dead);

			loose = true;

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void setQuietInGel(SpriteGel gel) {
		try {
			isQuietInGel = true;
			this.gel = gel;

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public boolean isLoose() {
		return loose;
	}

	public void setLoose(boolean loose) {
		this.loose = loose;
	}

	public void setCollitionWithEnemy(Object userData) {
		try {

			enemy = (SpriteEnemy) userData;
			if (enemy.isFreeze() && accelerating) {
				enemy.killFreezeEnemy();
				enemy = null;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void shotFreeze() {
		try {

			SoundSingleton.getInstance().playIcePower();

			level.getInvisiblePointToFollow().shake(1, 5);

			SpriteIceCircleAnimated iceCircle = (SpriteIceCircleAnimated) MyFactory
					.createObstacle(SpriteTypeConstant.ICE_POWER_CIRCLE, level);
			iceCircle.setPosition(this);
			iceCircle.setSize(this.getWidth(), this.getHeight());
			level.addSpriteToUpdate(iceCircle);
			level.getScene().attachChild(iceCircle);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void moveInDirection(Point pointCenter, Point pointBall,
			float percentage) {
		try {
			if (isQuietInGel) {
				return;
			}
			angle = Point.angleBetween(pointCenter, pointBall);

			
			
			float speedAcc = 0;

			if (accelerating) {
				speedAcc = linearVelocity * 1.5f;
			}

			float dx = (linearVelocity + speedAcc) * (float) Math.cos(angle);
			float dy = (linearVelocity + speedAcc) * (float) Math.sin(angle);

			dx = dx * percentage / 100f;
			dy = dy * percentage / 100f;

			if (!burble && pointToWin == null) {
				this.getBody().setLinearVelocity(dx, dy);
			}
			burble = false;

			float dxAux = 5 * (float) Math.cos(angle);
			float dyAux = 5 * (float) Math.sin(angle);

			if (dxAux > 5) {
				dxAux = 5;
			}
			if (dxAux < -5) {
				dxAux = -5;
			}

			if (dyAux > 5) {
				dyAux = 5;
			}
			if (dyAux < -5) {
				dyAux = -5;
			}

			float xA = playerEye.getX() + dxAux;
			float yA = playerEye.getY() + dyAux;

			if (playerEye.getX() + dxAux > MID_EYE_X + RADIUS) {
				xA = (MID_EYE_X + RADIUS);
			}
			if (playerEye.getX() + dxAux < MID_EYE_X - RADIUS) {
				xA = (MID_EYE_X - RADIUS);
			}

			if (playerEye.getY() + dyAux > MID_EYE_Y + RADIUS) {
				yA = (MID_EYE_Y + RADIUS);
			}
			if (playerEye.getY() + dyAux < MID_EYE_Y - RADIUS) {
				yA = (MID_EYE_Y - RADIUS);
			}

			playerEye.setPosition(xA, yA);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setBurble(boolean b) {
		this.burble = b;

	}

	public void desaccelerate() {
		try {
			accelerating = false;

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void accelerate(float contTime) {
		try {
			if (contTimeToAccelerate < timeToAccelerate) {
				return;
			}

			distanceAccelerate = contTime;
			contTimeToAccelerate = 0;
			if (accelerating == false) {
				SoundSingleton.getInstance().playTurbo();
			}

			accelerating = true;
			generateBurbles();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void slowReleaseButton() {
		try {

			this.getBody().setLinearVelocity(
					this.getBody().getLinearVelocity().x * 0.2f,
					this.getBody().getLinearVelocity().y);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
