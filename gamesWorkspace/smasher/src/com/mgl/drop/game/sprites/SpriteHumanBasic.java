package com.mgl.drop.game.sprites;

import java.util.HashMap;

import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.util.Log;

import com.mgl.base.MyAnimateProperty;
import com.mgl.base.MyAnimateSprite;
import com.mgl.base.MySpriteGeneral;
import com.mgl.base.SpriteType;
import com.mgl.base.StatusType;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.factory.SoundSingleton;
import com.mgl.drop.game.behavior.BehaviorVampire;
import com.mgl.drop.game.constant.BehaviorTypeConstant;
import com.mgl.drop.game.constant.State;
import com.mgl.drop.game.constant.ZIndexGame;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.texture.TextureSingleton;
import com.mgl.drop.util.Point;

public class SpriteHumanBasic extends MyAnimateSprite {

	private float maxDistance = 200;
	private float distance = 0;
	private float speed = 20f;
	private float minX;
	private float maxX;
	private boolean botherSound = false;

	private HashMap<Integer, String> poopSoundHash = new HashMap<Integer, String>();
	private HashMap<Integer, String> normalSoundHash = new HashMap<Integer, String>();
	private boolean hasPlayRunSound = false;

	public SpriteHumanBasic(float pX, float pY, ITextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager,
			LevelController level, float minX, float maxX, int spriteSize) {
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager, level,
				spriteSize);
		this.maxX = maxX;
		this.minX = minX;
		status = StatusType.NORMAL;
		hasPlayRunSound = false;

		// initSoundHash();

	}

	private void initSoundHash() {
		try {

			poopSoundHash = new HashMap<Integer, String>();
			for (int i = 1; i <= 5; i++) {
				poopSoundHash.put(Integer.valueOf(i), "fatReact" + i + ".mp3");
			}

			normalSoundHash = new HashMap<Integer, String>();
			for (int i = 1; i <= 5; i++) {
				normalSoundHash.put(Integer.valueOf(i), "fatNormal" + i
						+ ".mp3");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

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

			boolean isAlpha = false;
			// looking for wall
			for (MySpriteGeneral msg : level.getSpriteList()) {
				if (msg.getSpriteType().equals(SpriteType.WALL)) {
					isAlpha = true;
				}

			}

			// looking for fire
			for (MySpriteGeneral msg : level.getSpriteList()) {
				if (msg.getSpriteType().equals(SpriteType.FIRE)) {
					isAlpha = true;
				}

			}

			// looking for trunk
			for (MySpriteGeneral msg : level.getSpriteList()) {
				if (msg.getSpriteType().equals(SpriteType.TRUNK)) {
					isAlpha = true;
				}

			}

			if (isAlpha) {
				this.setAlpha(0.2f);
			} else {
				this.setAlpha(1f);
			}

			if (status.equals(StatusType.NORMAL)) {

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

				if (this.getY() > 200  && spriteNumber < 6 && !hasPlayRunSound) {
					// SoundSingleton.getInstance().playHumanRun();
					SoundSingleton.getInstance().playJulianRun();
					hasPlayRunSound  = true;
				}

				if (this.collidesWith(level.getFloor())) {
					level.removeEntity(this);
					level.getScene().detachChild(this);
					level.getScene().unregisterTouchArea(this);
					if (spriteNumber < 6) {
						SoundSingleton.getInstance().playJulianWin();
					} else {
						SoundSingleton.getInstance().playHumanWin();
					}
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

	private void killHuman() {
		try {

			status = (StatusType.TOUCHED);

			SoundSingleton.getInstance().playSound(getRandomPoopSound());

			level.removeEntity(this);
			level.getScene().unregisterTouchArea(this);
			level.getScene().detachChild(this);
			TextureSingleton texture = TextureSingleton.getInstance();

			String textureName = "humanSmash.png";
			if (spriteNumber < 6) {
				textureName = "julianSmash.png";
			}

			SpriteSmashGeneral smash = new SpriteSmashGeneral(this.getX() + 35,
					this.getY() + this.getHeight() - 10,
					texture.getTextureByName(textureName),
					texture.getVertexBufferObjectManager(), level);

			smash.setZIndex(ZIndexGame.SMASH);

			/*
			 * smash.setWidth(150); smash.setHeight(120); this.setPosition(
			 * this.getX() + this.getWidth() / 2 - smash.getWidth() / 2,
			 * this.getY() + this.getHeight() / 2 - smash.getHeight() / 2);
			 * 
			 * double val = (Math.random() * 1000000) % 100; if (val > 50) {
			 * smash.setRotation(180); smash.setWidth(120);
			 * smash.setHeight(150); this.setPosition( this.getX() +
			 * this.getWidth() / 2 - smash.getWidth() / 2, this.getY() +
			 * this.getHeight() / 2 - smash.getHeight() / 2);
			 * 
			 * }
			 */

			level.getScene().attachChild(smash);
			level.addSpriteToUpdate(smash);

			level.looseLive();
			
			if (spriteNumber < 6) {
				SoundSingleton.getInstance().playJulianDead();
			}else{
				SoundSingleton.getInstance().playHumanDead();
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

			Point currentPoint = new Point(x + getWidth() / 2, y + getHeight()
					/ 2);
			Point pointToMove = new Point(msg.getEntity().getX(), msg
					.getEntity().getY());

			float angle = Point.angleBetween(currentPoint, pointToMove);

			float dx = distance * (float) Math.cos(angle);
			float dy = distance * (float) Math.sin(angle);

			this.setPosition(x + dx, y + dy);

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
		fps = new long[] { 83, 83, 83, 83, 83, 83 };
		long[] fpsAux = new long[] { 83, 83, 83, 83 };

		if (spriteNumber <= 4) {
			stateAnimate.put(State.WALKIN_DOWN, new MyAnimateProperty(0,
					spriteNumber, fpsAux));
		} else {
			stateAnimate.put(State.WALKIN_DOWN, new MyAnimateProperty(0,
					spriteNumber, fps));
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

			killHuman();

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

		fps = new long[] { 100, 100, 100 };
		imageNumber = 3;
		// currentState = State.WALKIN_UP;
		changeAnimateState(State.WALKIN_DOWN, true);
		anime(true);
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

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
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

	public boolean isBotherSound() {
		return botherSound;
	}

	public void setBotherSound(boolean botherSound) {
		this.botherSound = botherSound;
	}

	public HashMap<Integer, String> getPoopSoundHash() {
		return poopSoundHash;
	}

	public void setPoopSoundHash(HashMap<Integer, String> poopSoundHash) {
		this.poopSoundHash = poopSoundHash;
	}

	public HashMap<Integer, String> getNormalSoundHash() {
		return normalSoundHash;
	}

	public void setNormalSoundHash(HashMap<Integer, String> normalSoundHash) {
		this.normalSoundHash = normalSoundHash;
	}

	public boolean isHasPlayRunSound() {
		return hasPlayRunSound;
	}

	public void setHasPlayRunSound(boolean hasPlayRunSound) {
		this.hasPlayRunSound = hasPlayRunSound;
	}

}
