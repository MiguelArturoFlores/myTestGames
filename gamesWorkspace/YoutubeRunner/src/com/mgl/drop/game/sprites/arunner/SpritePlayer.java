package com.mgl.drop.game.sprites.arunner;

import java.util.ResourceBundle.Control;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.Scene;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.color.Color;

import android.hardware.SensorManager;
import android.util.Log;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.mgl.base.MyAnimateProperty;
import com.mgl.base.MyAnimateSprite;
import com.mgl.base.MyFactory;
import com.mgl.base.MySprite;
import com.mgl.base.MySpriteGeneral;
import com.mgl.base.SpriteType;
import com.mgl.base.SpriteTypeConstant;
import com.mgl.base.StatusType;
import com.mgl.base.userinfo.PhysicSingleton;
import com.mgl.base.userinfo.PoolObjectSingleton;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.factory.SoundSingleton;
import com.mgl.drop.game.constant.CollitionType;
import com.mgl.drop.game.constant.GameConstants;
import com.mgl.drop.game.constant.State;
import com.mgl.drop.game.constant.ZIndexGame;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.scene.SceneManagerSingleton;
import com.mgl.drop.game.sprites.SpriteBackground;
import com.mgl.drop.game.sprites.ayoutuberunner.SpriteWake;
import com.mgl.drop.util.Point;

public class SpritePlayer extends MySprite {

	private float MID_EYE_X;
	private float MID_EYE_Y;
	private float RADIUS = 5;
	
	private float contWake = 0;
	private float wakeTime = 0f;
	private boolean win = true;
	private boolean soundMusic = true;

	private boolean reduceSpeed = false;
	private boolean isShip = false;
	private boolean fly = false;
	
	private float currentSpeedShip = 0;

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

	private int direcction;
	private boolean burble = false;

	private boolean isHorizontal = true;

	private boolean mustToDie = false;

	// private SpriteBackground playerEye;
	private SpriteDecorativeShot decorativeShot;

	private boolean jumping = false;
	private boolean jumpingContinously = false;
	private boolean canJump = false;

	private Point pointJump = null;
	private float jumpDistance = 95;
	private float jumpDistanceLong = 230;
	private float speedJump = 525/32f;
	private float speedJumpFall = speedJump;
	private float speedJumpLong = 766/32f;    //jumpdiDistanceJump*150/200;
	private float speedFly = 350/32f;
	private float speedX = 500 / 32f;

	private float contRotate = 0;
	private float rotateTime = 0.7f;
	private float maxAngle = 0;
	private float angle = 0;
	private boolean rotating = false;
	private float stepRotate = 90;

	private StatusType prevStatus;
	private float  gravity = 1;

	//rotation on ship
	private SpritePlayer player;
	private SpriteBackground ship;
	
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

			this.status = StatusType.NORMAL;
			prevStatus = this.status;

			SpriteBackground frame = new SpriteBackground(0, 0,
					texture.getTextureByName("frame.png"),
					texture.getVertexBufferObjectManager());
			frame.setSize(50, 50);
			this.attachChild(frame);
			frame.setZIndex(-1);

			this.sortChildren();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public SpriteType getSpriteType() {
		return SpriteType.PLAYER;
	}

	/*
	 * @Override public void initHashMap() { try {
	 * 
	 * fps = new long[] { 41, 41};
	 * 
	 * stateAnimate.put(State.WALKIN_RIGHT, new MyAnimateProperty(0, 2, fps));
	 * 
	 * } catch (Exception e) { e.printStackTrace(); }
	 * 
	 * }
	 * 
	 * @Override public void initAnimationParams() { try {
	 * 
	 * changeAnimateState(State.WALKIN_RIGHT, true); anime(true);
	 * 
	 * } catch (Exception e) { e.printStackTrace(); }
	 * 
	 * }
	 */

	@Override
	public void update(float dTime, LevelController level) {
		try {
			
			if(soundMusic){
				soundMusic = false;
				//SoundSingleton.getInstance().playMusic("level4"+".ogg",true);
			}

			body.applyForce(new Vector2(0,-SensorManager.GRAVITY_EARTH * gravity), new Vector2(body.getWorldCenter()));
			
			switch (this.getStatus()) {
			case NORMAL:
				updateNormal(dTime, level);
				break;
			case JUMP_LONG:
				updateJumpLong(dTime, level);
				break;
			case SHIP:
				updateShip(dTime, level);
				break;
			default:
				break;
			}

			if (loose) {
				return;
			}

			if(!this.status.equals(StatusType.SHIP)){
				if (!canJump) {
					if (!rotating) {
						rotate();
						rotating = true;
					}
				}
				updateRotating(dTime, level);
			}
			//updateLeavingWake(dTime,level);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void updateShip(float dTime, LevelController level) {
		try {
			
			if(gravity>0){
				if(fly){
					
					if(this.getBody().getLinearVelocity().y<-10){
						
					}else
						getBody().applyLinearImpulse(new Vector2(0, -5),
								getBody().getWorldCenter());
					
				}else{
					if(this.getBody().getLinearVelocity().y>10){
						
					}else
					
						getBody().applyLinearImpulse(new Vector2(0, 5),
								getBody().getWorldCenter());
				}
			}else{
				if(!fly){
					
					if(this.getBody().getLinearVelocity().y<-10){
						
					}else
						getBody().applyLinearImpulse(new Vector2(0, -5),
								getBody().getWorldCenter());
					
				}else{
					if(this.getBody().getLinearVelocity().y>10){
						
					}else
					
						getBody().applyLinearImpulse(new Vector2(0, 5),
								getBody().getWorldCenter());
				}
			}
			updateRotatingShip(dTime,level);
			
			updateLeavingWake(dTime, level);
			
			this.getBody().setLinearVelocity(speedX,
					this.getBody().getLinearVelocity().y);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void updateRotatingShip(float dTime, LevelController level) {
		try {
			float angle = Point.angleBetween(pointJump,new Point(this.getX(), this.getY()));
			
			angle = (float) (angle *180 / Math.PI) ;
			
			this.setRotation(angle);
			
			
			pointJump = new Point(this.getX(), this.getY());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void updateLeavingWake(float dTime, LevelController level) {
		try {
			
			contWake = contWake + dTime;
			if(contWake < wakeTime){
				return;
			}
			
			contWake = 0;
			
			
			SpriteWake wake = new SpriteWake(this.getX()-50, this.getY(), texture.getTextureByName("frame.png"),texture.getVertexBufferObjectManager());
			wake.setZIndex(this.getZIndex()-1);
			wake.setSize(50,50);
			wake.setRotationCenter(wake.getWidth()/2, wake.getHeight()/2);
			wake.setRotation(this.getRotation());

			float angle = Point.angleBetween(pointJump,new Point(this.getX(), this.getY()));
			wake.setPosition((float)(wake.getX()+wake.getWidth()*Math.cos(angle)),(float)(wake.getY()+wake.getHeight()*Math.sin(angle))); 
			level.addSpriteToUpdate(wake);
			level.getScene().attachChild(wake);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void updateJumpLong(float dTime, LevelController level) {
		try {
			updateLeavingWake(dTime, level);
			
			if (jumping) {
				canJump = false;
				//if (Point.distanceBetween(pointJump, new Point(this.getX(),
				//		this.getY())) > jumpDistance) {
				if(Math.abs(pointJump.getY()-this.getY())>=jumpDistanceLong){
					jumping = false;
					speedJumpFall = Math.abs(pointJump.getY()-this.getY())*speedX*32/125f;
					speedJumpFall = speedJumpFall/32f;
					// rotate();

					// this.getBody().setLinearVelocity(this.getBody().getLinearVelocity().x
					// , speedJump*0.8f);
				} else {
					//float speed = (jumpDistanceLong- Math.abs(pointJump.getY()-this.getY()))*speedX*32/125f;
					//speed = speed / 32f;
					this.getBody().setLinearVelocity(
							this.getBody().getLinearVelocity().x,
							speedJumpLong * -1* gravity);
				}
			} else {
				this.getBody().setLinearVelocity(
						this.getBody().getLinearVelocity().x, speedJumpFall*gravity);
			}

			if (!rotating && !canJump) {
				// rotating = true;
			}

			if (rotating) {
				// updateRotating(dTime,level);
			}

			this.getBody().setLinearVelocity(speedX,
					this.getBody().getLinearVelocity().y);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void updateNormal(float dTime, LevelController level) {
		try {

			if (canJump && !jumping && jumpingContinously) {
				jumping = true;
				// rotate();
			}
			if (jumping) {
				canJump = false;
				
				//if (Point.distanceBetween(pointJump, new Point(this.getX(),
				//		this.getY())) > jumpDistance) {
				if(Math.abs(pointJump.getY()-this.getY())>=jumpDistance){
					jumping = false;
					speedJumpFall = Math.abs(pointJump.getY()-this.getY())*speedX*32/100f;
					speedJumpFall = speedJumpFall/32f;
					// rotate();

					// this.getBody().setLinearVelocity(this.getBody().getLinearVelocity().x
					// , speedJump*0.8f);
				} else {
					this.getBody().setLinearVelocity(
							this.getBody().getLinearVelocity().x,
							speedJumpFall * -1* gravity);
				}
			} else {
				this.getBody().setLinearVelocity(
						this.getBody().getLinearVelocity().x, speedJumpFall*gravity);
			}

			if (!rotating && !canJump) {
				// rotating = true;
			}

			if (rotating) {
				// updateRotating(dTime,level);
			}

			this.getBody().setLinearVelocity(speedX,
					this.getBody().getLinearVelocity().y);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void updateRotating(float dTime, LevelController level) {
		try {	

			contRotate = contRotate + dTime;
			angle = angle + stepRotate * contRotate / rotateTime;// maxAngle *
																	// contRotate/rotateTime;
			// angle = maxAngle + angle;
			// angle = 30;
			this.setRotation(angle);
			if (angle >= maxAngle) {
				rotating = false;
				angle = maxAngle;
				this.setRotation(maxAngle);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void rotate() {
		try {

			contRotate = 0;
			rotating = true;

			angle = maxAngle;
			maxAngle = maxAngle + stepRotate;

			if (maxAngle > 360) {
				maxAngle = stepRotate;
				angle = 0;
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
			dead.setPosition(
					this.getX() + this.getWidth() / 2 - dead.getWidth() / 2,
					this.getY() + this.getHeight() / 2 - dead.getHeight() / 2);
			controller.addSpriteToUpdate(dead);
			controller.getScene().attachChild(dead);

			this.detachSelf();
			controller.removeEntity(this);
			SoundSingleton.getInstance().playPlayerDead();

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
			float angle = Point.angleBetween(pointCenter, pointBall);

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

	public void jump() {
		try {

			// this.getBody().setLinearVelocity(this.getBody().getLinearVelocity().x
			// , this.getBody().getLinearVelocity().y - 15);
			// jumping = true;
			//
			
			//this.status = StatusType.NORMAL;
			if(!jumping){
				pointJump = new Point(this.getX(), this.getY());	
			}	
			
			jumpingContinously = true;
			
			

			fly = true;
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void stopJump() {
		try {
			jumpingContinously = false;
			fly = false;
			// pointJump = new Point(this.getX(), this.getY());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void canJump(boolean b) {
		try {
			pointJump = new Point(this.getX(), this.getY());
			canJump = true;
			
			if(this.status.equals(StatusType.JUMP_LONG)){
				this.status = StatusType.NORMAL;
			}
			// SoundSingleton.getInstance().playShot();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void initData() {
		try {
			this.setRotationCenter(this.getWidth() / 2, this.getHeight() / 2);
			win = false; 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void jumpLong() {
		try {
			this.status = StatusType.JUMP_LONG;
			jumping = true;
			pointJump = new Point(this.getX(), this.getY());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void contactWithFloor(boolean b) {
		try {

			canJump = true;
			pointJump = new Point(this.getX(), this.getY());
			switch (this.status) {
			case JUMP_LONG:
				 //this.status = StatusType.NORMAL;
				break;

			default:
				break;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void changeGravity() {
		try {
			
			
			
			this.gravity  = gravity *-1;
			jumping = false;
			
			
			if(gravity<0){
				level.reloadCollition(this,"playerInverse.xml");
				ship.setFlippedVertical(true);
				ship.setPosition(-3,0);
				player.setFlippedVertical(true);
				player.setPosition(2,5);
				
			}else{
				level.reloadCollition(this,"player.xml");
				ship.setFlippedVertical(false);
				player.setFlippedVertical(false);
				player.setPosition(2,-5);
				ship.setPosition(-3, 5);
			}
			
			Vector2 grav = PhysicSingleton.getInstance().getPhysicsWorld().getGravity();
			grav.y = grav.y * -1;
			PhysicSingleton.getInstance().getPhysicsWorld().setGravity(grav);
			
			this.setZIndex(ZIndexGame.PLAYER);
			
			
			//speedJump = speedJump * gravity;
			//speedJumpFall = speedJumpFall * gravity;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void validateCollition(MySprite floor) {
		try {
			
			if(gravity>0){
				if(floor.getY()>this.getY()){
					if(this.getY()+this.getHeight() > floor.getY()+floor.getHeight()*0.02f){
						level.looseLevelReplay();
						}
				}else{
					level.looseLevelReplay();
				}
			}else{

				if(floor.getY()<this.getY()){
					if(this.getY() < floor.getY()+floor.getHeight()*0.98f){
						level.looseLevelReplay();
						}
				}else{
					level.looseLevelReplay();
				}
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void transformOnShip() {
		try {
			
			this.getBody().setLinearVelocity(this.getBody().getLinearVelocity().x, 0);
			
			currentSpeedShip = 0; 
			
			isShip = !isShip;
			
			if(isShip){
				this.setAlpha(0f);
				this.setRotation(0);
				this.detachChildren();
				
				player = PoolObjectSingleton.getInstance().getPlayer();
				player.setSize(this.getWidth(), this.getHeight());
				player.detachChildren();
				player.setPosition(2,-5);
				//player.setFlippedVertical(true);
				this.attachChild(player);
				
				ship = (SpriteBackground) MyFactory.createObstacle(SpriteTypeConstant.SHIP, level);
				this.attachChild(ship);
				//ship.setFlippedVertical(true);
				ship.setPosition(-3, 5);
				this.status = StatusType.SHIP;
				
			}else{
				this.setAlpha(1);
				this.detachChildren();
				
				SpriteBackground frame = new SpriteBackground(0, 0,
						texture.getTextureByName("frame.png"),
						texture.getVertexBufferObjectManager());
				frame.setSize(50, 50);
				this.attachChild(frame);
				frame.setZIndex(-1);

				this.sortChildren();
				
				this.status = StatusType.NORMAL;
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void setWin(boolean b) {
		this.win = b;
	}

	public boolean isWin() {
		return win;
	}


}
