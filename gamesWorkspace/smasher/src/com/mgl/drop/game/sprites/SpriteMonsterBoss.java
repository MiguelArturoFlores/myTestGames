package com.mgl.drop.game.sprites;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.util.Log;

import com.mgl.base.MyAnimateProperty;
import com.mgl.base.MyAnimateSprite;
import com.mgl.base.MyEntitySmasher;
import com.mgl.base.MyFactory;
import com.mgl.base.MySpriteGeneral;
import com.mgl.base.SpriteType;
import com.mgl.base.SpriteTypeConstant;
import com.mgl.base.StatusType;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.factory.SoundSingleton;
import com.mgl.drop.game.behavior.BehaviorVampire;
import com.mgl.drop.game.constant.BehaviorTypeConstant;
import com.mgl.drop.game.constant.ScoreGameConstant;
import com.mgl.drop.game.constant.State;
import com.mgl.drop.game.constant.ZIndexGame;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.entity.Behavior;
import com.mgl.drop.texture.TextureSingleton;
import com.mgl.drop.util.Point;

public class SpriteMonsterBoss extends MyAnimateSprite {

	private float maxDistance = 200;
	private float distance = 0;
	private float minX;
	private float maxX;

	private float xToMove = 0;
	private float yToMove = 0;

	private boolean botherSound = false;
	private float timeToMove = 2;
	private float contTime = 0;

	private float timeToMonsters = 2;
	private float contTimeMonsters = 0;

	private SpriteLife life;
	private SpriteBossHit bossHit;
	
	private float contAppear = 0;
	private float timeToAppear = 0.1f;
	
	private float contSound = 0f;
	private float minTimeSound = 2f;

	private HashMap<Integer, String> poopSoundHash = new HashMap<Integer, String>();
	private HashMap<Integer, String> normalSoundHash = new HashMap<Integer, String>();

	private float contChange = 0;
	private float timeToChange = 3;
	private SpriteBossHitted bossHitted;
	private boolean hasSound = false;

	public SpriteMonsterBoss(float pX, float pY, ITextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager,
			LevelController level, float minX, float maxX) {
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager, level);
		try {

			this.maxX = maxX;
			this.minX = minX;
			status = StatusType.NORMAL;
			
			TextureSingleton texture = TextureSingleton.getInstance();
			
			bossHitted =  new SpriteBossHitted(0, 0, texture.getTextureByName("bossHit.png"), texture.getVertexBufferObjectManager());

			
			life = new SpriteLife(0, 0,
					texture.getTextureByName("lifeBlack.png"),
					texture.getVertexBufferObjectManager(), this, level);
			life.setPosition(
					MainDropActivity.CAMERA_WIDTH - life.getWidth(),
					MainDropActivity.CAMERA_HEIGHT/2 -  life.getHeight()/2);
			level.getScene().attachChild(life);
			level.addSpriteToUpdate(life);

			bossHit = new SpriteBossHit(0, 0,
					texture.getTextureAnimateByName("pointToHit.png"),
					texture.getVertexBufferObjectManager(), level, 1, this,
					life);

			
			bossHit.generateRandomPositionToHit();

			this.attachChild(bossHit);
			level.addSpriteToUpdate(bossHit);
			level.getScene().registerTouchArea(bossHit);

			// ThreadFatSound thread = new ThreadFatSound(this);
			// thread.start();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void unregisterHit() {
		try {

			level.getScene().unregisterTouchArea(bossHit);

		} catch (Exception e) {
			e.printStackTrace();
		}
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

			if(!hasSound){
				hasSound = true;
				SoundSingleton.getInstance().playMusic("bossMusic.mp3",false);
				SoundSingleton.getInstance().playSound("lught.mp3");
			}
			
			contSound += dTime;
			if(contSound > minTimeSound){
				contSound = 0;
				SoundSingleton.getInstance().playBossNormalSound();
			}
			
			
			level.getScene().unregisterTouchArea(this);

			updateHitPoint(dTime, level);

			boolean moveNormal = true;

			if (status.equals(StatusType.DESAPEARING) && moveNormal) {
				contAppear += dTime;
				if(contAppear > timeToAppear){
					status = StatusType.NORMAL;
					setAlpha(1);
					bossHitted.detachSelf();
					level.removeEntity(bossHitted);
					
				}
				return;
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
				case BehaviorTypeConstant.BEHAVIOR_BOSS:
					BehaviorVampire.updateNormalBoss(dTime, level, this);
					contTime = timeToMove;
					break;
				case BehaviorTypeConstant.BEHAVIOR_BOSS_WAITING:
					updateBossWaiting(dTime, level);

					break;
				default:
					BehaviorVampire.updateNormal(dTime, level, this);
					break;
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void updateBossWaiting(float dTime, LevelController level) {
		try {

			float x = this.getX();
			float y = this.getY();
			float distance = dTime * speed;

			Point currentPoint = new Point(x + getWidth() / 2, y + getHeight()
					/ 2);
			contTime = contTime + dTime;
			if (contTime > timeToMove) {
				contTime = 0;
				float xAux = (float) ((Math.random() * 100000000) % (MainDropActivity.CAMERA_WIDTH - 50));
				float yAux = (float) ((Math.random() * 100000000) % (MainDropActivity.CAMERA_HEIGHT / 2));

				xToMove = xAux;

				yToMove = yAux;

			}

			contTimeMonsters = contTimeMonsters + dTime;
			if (contTimeMonsters > timeToMonsters) {
				contTimeMonsters = 0;
				generateMonsters(level);
			}

			Point pointToMove = new Point(xToMove, yToMove);

			float angle = Point.angleBetween(currentPoint, pointToMove);

			float dx = distance * (float) Math.cos(angle);
			float dy = distance * (float) Math.sin(angle);

			this.setPosition(x + dx, y + dy);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void generateMonsters(LevelController level) {
		try {

			ArrayList<MyEntitySmasher> monsList = generateRandomHumanVampire(3,
					3);

			for (MyEntitySmasher smash : monsList) {

				MySpriteGeneral monster = MyFactory.createSprite(
						smash.getIdSprite(), level, 0, 0, smash.getSpeed(),
						smash.getIdBehaviorType(), 1, 0);
				level.addSpriteToUpdate(monster);
				monster.getEntity().setPosition(smash.getX(),
						smash.getY() - (smash.getSpeed() * smash.getTime()));
				level.getScene().attachChild(monster.getEntity());
				level.getScene().registerTouchArea(monster.getTouchArea());

			}
			TextureSingleton texture = TextureSingleton.getInstance();
			if (diamant > 0) {
				generateDiamant();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private Stack<Float> generateTimeStack(float quantity, float time) {
		Stack<Float> timeStack = new Stack<Float>();
		try {

			for (int i = 0; i < quantity; i++) {
				float timeG = (float) ((Math.random() * 1000000) % time);
				timeStack.add(timeG);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return timeStack;
	}

	private ArrayList<MyEntitySmasher> generateRandomHumanVampire(int quantity,
			float time) {

		ArrayList<MyEntitySmasher> spriteList = new ArrayList<MyEntitySmasher>();
		try {

			Stack<Float> timeStack = generateTimeStack(quantity, time);

			while (!timeStack.isEmpty()) {
				float valX = (float) (((Math.random() * 100000) % 310) + 10);
				// int speed = 130;

				MyEntitySmasher smasher = new MyEntitySmasher(valX, -100,
						getRandomVampireByLevel(), false, timeStack.pop());
				smasher.setSpeed(150);
				smasher.setIdBehaviorType(BehaviorTypeConstant.BEHAVIOR_NORMAL);
				spriteList.add(smasher);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return spriteList;

	}

	public int getRandomVampireByLevel() {
		try {

			HashMap<Integer, Integer> monsterTypeList = new HashMap<Integer, Integer>();

			monsterTypeList.put(0, SpriteTypeConstant.MONSTER_ARMOR);
			monsterTypeList.put(1, SpriteTypeConstant.MONSTER_BASIC);
			monsterTypeList.put(2, SpriteTypeConstant.MONSTER_BAT);
			monsterTypeList.put(3, SpriteTypeConstant.MONSTER_HOLE);
			monsterTypeList.put(4, SpriteTypeConstant.MONSTER_ZIGZAG);

			Integer randomId = (int) ((Math.random() * 1000000) % monsterTypeList
					.size());

			return monsterTypeList.get(randomId);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return SpriteTypeConstant.MONSTER_BASIC;
	}

	private void updateHitPoint(float dTime, LevelController level) {
		try {

			if (bossHit == null) {
				return;
			}

			contChange = contChange + dTime;
			if (contChange < timeToChange) {
				return;
			}

			contChange = 0;
			// bossHit.generateRandomPositionToHit();

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
		try {
			
			SoundSingleton.getInstance().playBossHitSound();
			status = StatusType.DESAPEARING;
			bossHitted.setPosition(0,0);
			bossHitted.setSize(this.getWidth(), this.getHeight());
			bossHitted.detachSelf();
			this.attachChild(bossHitted);
			level.addSpriteToUpdate(bossHitted);
			this.setAlpha(0);
			contAppear = 0;
			bossHitted.resetConts();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
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

	public void killBoss() {
		try {

			SoundSingleton.getInstance().playMusic("backgroundSound1.mp3",true);
			TextureSingleton texture = TextureSingleton.getInstance();

			SpriteSmashGeneral smash = new SpriteSmashGeneral(this.getX() + 35,
					this.getY() + this.getHeight() - 10,
					texture.getTextureByName("bossSmash.png"),
					texture.getVertexBufferObjectManager(), level);

			smash.setZIndex(ZIndexGame.SMASH);

			smash.setWidth(this.getWidth());
			smash.setHeight(this.getHeight() / 2);
			smash.setPosition(
					this.getX() + this.getWidth() / 2 - smash.getWidth() / 2,
					this.getY() + this.getHeight() / 2 - smash.getHeight() / 4);

			level.getScene().attachChild(smash);
			level.addSpriteToUpdate(smash);

			level.getScene().detachChild(this);
			level.removeEntity(this);
			level.getScene().unregisterTouchArea(bossHit);

			level.getScene().detachChild(life);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
