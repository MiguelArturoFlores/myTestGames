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

public class SpriteMonsterZigzag extends MyAnimateSprite {

	private float maxDistance = 200;
	private float distance = 0;
	private Double minX;
	private Double maxX;
	private boolean soundNormal = true;

	private HashMap<Integer, String> poopSoundHash = new HashMap<Integer, String>();
	private HashMap<Integer, String> normalSoundHash = new HashMap<Integer, String>();
	private boolean generateChilds;
	private boolean soundEating = true;

	public SpriteMonsterZigzag(float pX, float pY,
			ITextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager,
			LevelController level, double minX, double maxX) {
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager, level);

		status = StatusType.NORMAL;
		initSoundHash();
		generateChilds = true;

		Double val = ((Math.random() * 10000000) % 2);
		if (val.equals(1D)) {
			speedX = speedX * -1;
		}

		if (speedX > 0) {
			minX = 10;
			maxX = ((Math.random() * 10000000) % (480 - this.getX()));
			if (maxX < 200 && (maxX + this.getX() + 200) < 460) {
				maxX = this.getX() + 200;
			}

		} else {
			maxX = 460;
			minX = ((Math.random() * 10000000) % (this.getX()));
			if ((this.getX() - minX) < 100 || (this.getX() - minX) < 10) {
				minX = 10;
			}
		}

		this.minX = minX;
		this.maxX = maxX;

		// ThreadFatSound thread = new ThreadFatSound(this);
		// thread.start();

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

					SoundSingleton.getInstance().playNormalZigZag();
					soundNormal = false;
				}

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

				SoundSingleton.getInstance().playEatingZigZag();
				soundEating = false;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void killVampire(SpriteType spriteType) {
		try {

			status = (StatusType.TOUCHED);

			//SoundSingleton.getInstance().playSound(getRandomPoopSound());

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
				SoundSingleton.getInstance().playDeadZigZag();

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
				SoundSingleton.getInstance().playDeadZigZag();

			} else {

				if (generateChilds) {

					SpriteMonsterZigzag zigzag1 = (SpriteMonsterZigzag) MyFactory
							.createSprite(SpriteTypeConstant.MONSTER_ZIGZAG,
									level, 0, 0, (int) this.getSpeed(),
									BehaviorTypeConstant.BEHAVIOR_ZIGZAG, 1, 0);
					SpriteMonsterZigzag zigzag2 = (SpriteMonsterZigzag) MyFactory
							.createSprite(SpriteTypeConstant.MONSTER_ZIGZAG,
									level, 0, 0, (int) this.getSpeed(),
									BehaviorTypeConstant.BEHAVIOR_ZIGZAG, -1, 0);

					zigzag1.setSize(90, 100);
					zigzag2.setSize(90, 100);

					zigzag1.setPosition(this.getX() + 50,
							this.getY() + this.getHeight() / 2);
					zigzag2.setPosition(this.getX() - 50, this.getY());

					zigzag1.setSpeedX(200);
					zigzag2.setSpeedX(-200);

					zigzag1.setGenerateChilds(false);
					zigzag2.setGenerateChilds(false);

					level.getScene().registerTouchArea(zigzag1);
					level.getScene().registerTouchArea(zigzag2);

					level.getScene().attachChild(zigzag1);
					level.addSpriteToUpdate(zigzag1);

					level.getScene().attachChild(zigzag2);
					level.addSpriteToUpdate(zigzag2);

				}

				SpriteSmashGeneral smash = new SpriteSmashGeneral(
						this.getX() + 35, this.getY() + this.getHeight() - 10,
						texture.getTextureByName("vampire2Smash.png"),
						texture.getVertexBufferObjectManager(), level);

				smash.setZIndex(ZIndexGame.SMASH);

				smash.setWidth(this.getWidth());
				smash.setHeight(this.getHeight() * 0.6f);
				this.setPosition(
						this.getX() + this.getWidth() / 2 - smash.getWidth()
								/ 2,
						this.getY() + this.getHeight() / 2 - smash.getHeight()
								/ 2);

				level.getScene().attachChild(smash);
				level.addSpriteToUpdate(smash);

				SoundSingleton.getInstance().playSmashNormalSound();

			}

			UserInfoSingleton.getInstance().increaseContZigZag(1);
			level.upScore(ScoreGameConstant.VAMPIRE6,
					this.getX() + this.getWidth() / 2, this.getY());

			if (diamant > 0) {
				generateDiamant();
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
		fps = new long[] { 83, 83, 83, 83, 83, 83 };

		stateAnimate.put(State.WALKIN_RIGHT, new MyAnimateProperty(0, 6, fps));
		stateAnimate.put(State.WALKIN_LEFT, new MyAnimateProperty(0, 6, fps));
		stateAnimate.put(State.POOP_BEGIN, new MyAnimateProperty(0, 6, fps));
		stateAnimate.put(State.POOP_END, new MyAnimateProperty(0, 6, fps));

		stateAnimate.put(State.WALKIN_DOWN, new MyAnimateProperty(0, 6, fps));
		stateAnimate.put(State.WALKIN_UP, new MyAnimateProperty(0, 6, fps));

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

			if (!status.equals(StatusType.NORMAL)) {
				return false;
			}
			killVampire(SpriteType.FINGER);

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

	public Double getMinX() {
		return minX;
	}

	public void setMinX(Double minX) {
		this.minX = minX;
	}

	public Double getMaxX() {
		return maxX;
	}

	public void setMaxX(Double maxX) {
		this.maxX = maxX;
	}

	public float getSpeedX() {
		return speedX;
	}

	public void setSpeedX(float speedX) {
		this.speedX = speedX;
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

	public boolean isGenerateChilds() {
		return generateChilds;
	}

	public void setGenerateChilds(boolean generateChilds) {
		this.generateChilds = generateChilds;
	}

}
