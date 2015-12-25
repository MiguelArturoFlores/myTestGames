package com.mgl.drop.game.sprites;

import java.util.HashMap;

import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.util.Log;

import com.mgl.base.MyAnimateProperty;
import com.mgl.base.MyAnimateSprite;
import com.mgl.base.MySprite;
import com.mgl.base.MySpriteGeneral;
import com.mgl.base.SpriteType;
import com.mgl.base.StatusType;
import com.mgl.drop.factory.SoundSingleton;
import com.mgl.drop.game.behavior.BehaviorVampire;
import com.mgl.drop.game.constant.BehaviorTypeConstant;
import com.mgl.drop.game.constant.ScoreGameConstant;
import com.mgl.drop.game.constant.State;
import com.mgl.drop.game.constant.ZIndexGame;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.texture.TextureSingleton;
import com.mgl.drop.util.Point;

public class SpriteMonsterWithoutArmor extends MyAnimateSprite {

	private float maxDistance = 200;
	private float distance = 0;
	private float minX;
	private float maxX;
	private boolean soundNormal = true;

	private HashMap<Integer, String> poopSoundHash = new HashMap<Integer, String>();
	private HashMap<Integer, String> normalSoundHash = new HashMap<Integer, String>();
	private boolean soundEating = true;

	public SpriteMonsterWithoutArmor(float pX, float pY,
			ITextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager,
			LevelController level, float minX, float maxX) {
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager, level);
		this.maxX = maxX;
		this.minX = minX;
		status = StatusType.NORMAL;

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

	@Override
	public void updateAnimated(float dTime, LevelController level) {
		try {

			boolean moveNormal = true;
			SpriteDeadBody msg = level.getDeadBody();
			if (msg != null && msg.getStatus().equals(StatusType.NORMAL)) {
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
			if (fire != null) {

				if (fire.collidesWith(this)) {
					killVampire(SpriteType.FIRE);
				}
			}

			// looking for trunk
			SpriteTrunk trunk = level.getTrunk();
			if (trunk != null) {

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

					SoundSingleton.getInstance().playUnArmorNormal();
					soundNormal = false;
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void killVampire(SpriteType spriteType) {
		try {

			status = (StatusType.TOUCHED);

			level.removeEntity(this);
			level.getScene().detachChild(this);
			level.getScene().unregisterTouchArea(this);
			TextureSingleton texture = TextureSingleton.getInstance();

			if (spriteType.equals(SpriteType.FIRE)) {
				SpriteFireDeath fireDeath = new SpriteFireDeath(0, 0,
						texture.getTextureAnimateByName("fireDeath.png"),
						texture.getVertexBufferObjectManager(), level);
				fireDeath.setPosition(this);
				fireDeath.setZIndex(ZIndexGame.SMASH);
				level.getScene().attachChild(fireDeath);
				level.addSpriteToUpdate(fireDeath);

				SoundSingleton.getInstance().playUnarmorDead();

			} else if (spriteType.equals(SpriteType.TRUNK)) {
				SpriteDeathSplash splash = new SpriteDeathSplash(0, 0,
						texture.getTextureByName("death1.png"),
						texture.getVertexBufferObjectManager());
				splash.setPosition(this.getX(), this.getY() + this.getHeight()
						- 20);
				splash.setHeight(10);
				splash.setZIndex(ZIndexGame.SMASH);
				level.getScene().attachChild(splash);
				level.addSpriteToUpdate(splash);

				SoundSingleton.getInstance().playUnarmorDead();

			} else {
				SpriteSmashGeneral smash = new SpriteSmashGeneral(
						this.getX() + 35, this.getY() + this.getHeight() - 10,
						texture.getTextureByName("vampire3Smash.png"),
						texture.getVertexBufferObjectManager(), level);

				smash.setZIndex(ZIndexGame.SMASH);

				smash.setWidth(150);
				smash.setHeight(120);
				this.setPosition(
						this.getX() + this.getWidth() / 2 - smash.getWidth()
								/ 2,
						this.getY() + this.getHeight() / 2 - smash.getHeight()
								/ 2);

				level.getScene().attachChild(smash);
				level.addSpriteToUpdate(smash);

				SoundSingleton.getInstance().playSmashNormalSound();
			}
			level.upScore(ScoreGameConstant.VAMPIRE4,
					this.getX() + this.getWidth() / 2, this.getY());
			if (diamant > 0) {
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

			float xR = (float) ((Math.random() * 12354698) % sprM.getWidth());
			float yR = (float) ((Math.random() * 12354698) % sprM.getHeight());

			Point pointToMove = new Point(msg.getEntity().getX() + xR, msg
					.getEntity().getY() + yR);

			float angle = Point.angleBetween(currentPoint, pointToMove);

			float dx = distance * (float) Math.cos(angle);
			float dy = distance * (float) Math.sin(angle);

			this.setPosition(x + dx, y + dy);

			if (this.collidesWith(sprM) && soundEating) {

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
		fps = new long[] { 83, 83, 83, 83, 83, 83, 83, 83 };

		stateAnimate.put(State.WALKIN_DOWN, new MyAnimateProperty(0, 8, fps));

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
