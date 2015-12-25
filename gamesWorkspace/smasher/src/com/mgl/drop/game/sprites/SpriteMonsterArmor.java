package com.mgl.drop.game.sprites;

import java.util.HashMap;

import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.util.Log;

import com.mgl.base.MyAnimateProperty;
import com.mgl.base.MyAnimateSprite;
import com.mgl.base.MyFactory;
import com.mgl.base.MySprite;
import com.mgl.base.MySpriteGeneral;
import com.mgl.base.SpriteType;
import com.mgl.base.SpriteTypeConstant;
import com.mgl.base.StatusType;
import com.mgl.base.userinfo.UserInfoSingleton;
import com.mgl.drop.factory.SoundSingleton;
import com.mgl.drop.game.behavior.BehaviorVampire;
import com.mgl.drop.game.constant.BehaviorTypeConstant;
import com.mgl.drop.game.constant.ScoreGameConstant;
import com.mgl.drop.game.constant.State;
import com.mgl.drop.game.constant.ZIndexGame;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.texture.TextureSingleton;
import com.mgl.drop.util.Point;

public class SpriteMonsterArmor extends MyAnimateSprite {

	private float maxDistance = 200;
	private float distance = 0;
	private float minX;
	private float maxX;
	private boolean soundNormal = false;

	private HashMap<Integer, String> poopSoundHash = new HashMap<Integer, String>();
	private HashMap<Integer, String> normalSoundHash = new HashMap<Integer, String>();
	private boolean soundEating = true;

	public SpriteMonsterArmor(float pX, float pY,
			ITextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager,
			LevelController level, float minX, float maxX) {
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager, level);
		this.maxX = maxX;
		this.minX = minX;
		status = StatusType.NORMAL;

		
		// ThreadFatSound thread = new ThreadFatSound(this);
		// thread.start();

	}

	

	private String getRandomPoopSound() {
		try {

			Double val = Math.random() * 100000000f;

			val = val % poopSoundHash.size();
			if (val.intValue() == 0) {
				val = 1D;
			}
			String valS = poopSoundHash.get((val.intValue()));

			return valS;

		} catch (Exception e) {
			Log.d("CAGADA", "EERROR EN EL VAGADA SOUND");
			return "cagada2.mp3";
		}

	}

	public String getRandomSound() {
		try {

			Double val = Math.random() * 100000000f;

			val = val % normalSoundHash.size();
			if (val.intValue() == 0) {
				val = 1D;
			}
			String valS = normalSoundHash.get((val.intValue()));

			return valS;

		} catch (Exception e) {
			Log.d("CAGADA", "EERROR EN EL VAGADA SOUND");
			return "cagada2.mp3";
		}

	}

	@Override
	public void updateAnimated(float dTime, LevelController level) {
		try {

			boolean moveNormal = true;
			
			SpriteDeadBody msg = level.getDeadBody();
			if (msg != null
					&& msg.getStatus().equals(StatusType.NORMAL)) {
				float prevSpeed = speed;
				speed = speed * 2.50f;
				moveToFood(msg, dTime);
				speed = prevSpeed;
				moveNormal = false;

			}

			// looking for wall
			SpriteWall wall = level.getWall();
			if (wall != null) {

				if (this.getY() + this.getHeight() > wall.getY()
						+ wall.getHeight()) {
					this.setZIndex(ZIndexGame.VAMPIRE_THORW_WALL);
				}
				if (wall.collidesWith(this)) {
					moveNormal = false;
				}
			}

			// looking for fire
			SpriteFire fire = level.getFire();
			if (fire != null ) {

				if (fire.collidesWith(this)) {
					killVampire(SpriteType.FIRE);
				}
			}

			// looking for trunk
			SpriteTrunk trunk = level.getTrunk();
			if (trunk != null ) {

				if (trunk.collidesWith(this)) {
					killVampire(SpriteType.TRUNK);
				}
			}
			
			if (status.equals(StatusType.NORMAL) && moveNormal) {

				switch (behavior) {
				case BehaviorTypeConstant.BEHAVIOR_NORMAL:
					BehaviorVampire.updateNormal(dTime, level, this);
					break;
				case BehaviorTypeConstant.BEHAVIOR_ZIGZAG:
					BehaviorVampire.updateZigZag(dTime, level, this);
					break;
				case BehaviorTypeConstant.BEHAVIOR_ACCELERATE:
					BehaviorVampire.updateAccelerate(dTime, level, this);
					break;
				case BehaviorTypeConstant.BEHAVIOR_NORMAL_ZIGZAG:
					BehaviorVampire.updateNormalZigZag(dTime, level, this);
					break;
				default:
					BehaviorVampire.updateNormal(dTime, level, this);
					break;
				}

				if (this.getY() > 20 && soundNormal) {

					SoundSingleton.getInstance().playVampireNormalSound();
					soundNormal = false;
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void validateZIndex() {
		try {

			for (MySpriteGeneral msg : level.getSpriteList()) {
				if (msg.getSpriteType().equals(SpriteType.OBJETIVE)) {
					if (msg instanceof MyAnimateSprite) {
						MyAnimateSprite animateSpr = (MyAnimateSprite) msg;
						animateSpr.collidesWith(this);
						if (this.getY() + this.getHeight() > animateSpr.getY()
								+ animateSpr.getHeight()) {
							this.setZIndex(ZIndexGame.VAMPIRE_PLUS);
						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void killVampire(SpriteType trunk) {
		try {

			status = (StatusType.TOUCHED);

			SoundSingleton.getInstance().playArmorSmash();

			level.removeEntity(this);
			level.getScene().unregisterTouchArea(this);
			level.getScene().detachChild(this);
			TextureSingleton texture = TextureSingleton.getInstance();

			SpriteMonsterWithoutArmor sprGeneral = (SpriteMonsterWithoutArmor) MyFactory
					.createSprite(SpriteTypeConstant.MONSTER_WITHOUT_ARMOR,
							level, 1, 1, Float.valueOf(speed).intValue(),
							behavior,1,0);
			
			sprGeneral.setPosition(this.getX(),this.getY());

			level.getScene().attachChild(sprGeneral);
			level.getScene().registerTouchArea(sprGeneral);
			level.addSpriteToUpdate(sprGeneral);

			SpriteArmorCrash armorCrash = new SpriteArmorCrash(0, 0,
					texture.getTextureAnimateByName("armorSplash.png"),
					texture.getVertexBufferObjectManager(), level);
			armorCrash.setPosition(this.getX()+ this.getWidth() / 2 - armorCrash.getWidth()/2
					/ 2, this.getY());
			armorCrash.setZIndex(ZIndexGame.SMASH);
			level.getScene().attachChild(armorCrash);
			level.addSpriteToUpdate(armorCrash);

			UserInfoSingleton.getInstance().increaseContArmor(1);
			level.upScore(ScoreGameConstant.VAMPIRE3, this.getX()+this.getWidth()/2, this.getY());

			if(diamant>0){
				generateDiamant();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void moveToFood(MySpriteGeneral msg, float pSecondsElapsed) {
		try {

			float x = this.getX();
			float y = this.getY();
			float distance = pSecondsElapsed * speed;

			MySprite sprM = (MySprite) msg;

			Point currentPoint = new Point(x + getWidth() / 2, y + getHeight()
					/ 2);
			
			float xR = (float) ((Math.random()*12354698)%sprM.getWidth());
			float yR = (float) ((Math.random()*12354698)%sprM.getHeight());
			
			Point pointToMove = new Point(msg.getEntity().getX()+xR, msg
					.getEntity().getY()+yR);

			float angle = Point.angleBetween(currentPoint, pointToMove);

			float dx = distance * (float) Math.cos(angle);
			float dy = distance * (float) Math.sin(angle);

			this.setPosition(x + dx, y + dy);

			if(this.collidesWith(sprM) && soundEating ){

					SoundSingleton.getInstance().playEatingUnarmor();
					soundEating = false;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void poop(MySpriteGeneral poop, LevelController controller) {
		try {

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initHashMap() {
		try {

			fps = new long[] { 83, 83, 83, 83, 83, 83, 83, 83 };

			stateAnimate.put(State.WALKIN_DOWN,
					new MyAnimateProperty(0, 8, fps));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public boolean onAreaTouched(final TouchEvent pSceneTouchEvent,
			final float pTouchAreaLocalX, final float pTouchAreaLocalY) {

		switch (pSceneTouchEvent.getAction()) {
		case TouchEvent.ACTION_DOWN:

			if (!status.equals(StatusType.NORMAL)) {
				return false;
			}

			killVampire(SpriteType.FINGER);

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

	@Override
	public void initAnimationParams() {

		try {

			changeAnimateState(State.WALKIN_DOWN, true);
			anime(true);

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	@Override
	public SpriteType getSpriteType() {

		return SpriteType.OBJETIVE;
	}

	public float getMaxDistance() {
		return maxDistance;
	}

	public void setMaxDistance(float maxDistance) {
		this.maxDistance = maxDistance;
	}

	public float getDistance() {
		return distance;
	}

	public void setDistance(float distance) {
		this.distance = distance;
	}

	public float getMinX() {
		return minX;
	}

	public void setMinX(float minX) {
		this.minX = minX;
	}

	public float getMaxX() {
		return maxX;
	}

	public void setMaxX(float maxX) {
		this.maxX = maxX;
	}

}
