package com.mgl.drop.game.sprites;

import java.util.HashMap;

import org.andengine.audio.sound.Sound;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.util.Log;

import com.mgl.base.MyAnimateProperty;
import com.mgl.base.MyAnimateSprite;
import com.mgl.base.MySpriteGeneral;
import com.mgl.base.SpriteType;
import com.mgl.base.StatusType;
import com.mgl.drop.factory.SoundSingleton;
import com.mgl.drop.game.constant.State;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.sprites.thread.ThreadRunnerSound;
import com.mgl.drop.texture.TextureSingleton;

public class SpriteMonsterRunner extends MyAnimateSprite {

	private float maxDistance = 200;
	private float distance = 0;
	private float speed = 20f;
	private float minX;
	private float maxX;
	private boolean botherSound = false;

	private HashMap<Integer, String> poopSoundHash = new HashMap<Integer, String>();
	private HashMap<Integer, String> normalSoundHash = new HashMap<Integer, String>();

	public SpriteMonsterRunner(float pX, float pY,
			ITextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager,
			LevelController level, float minX, float maxX) {
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager, level);
		this.maxX = maxX;
		this.minX = minX;
		status = StatusType.NORMAL;

		initSoundHash();

		ThreadRunnerSound thread = new ThreadRunnerSound(this);
		thread.start();
	}

	private void initSoundHash() {
		try {

			poopSoundHash = new HashMap<Integer, String>();
			for (int i = 1; i <= 3; i++) {
				poopSoundHash.put(Integer.valueOf(i), "runnerReact" + i
						+ ".mp3");
			}

			normalSoundHash = new HashMap<Integer, String>();
			for (int i = 1; i <= 5; i++) {
				normalSoundHash.put(Integer.valueOf(i), "runnerNormal" + i
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
			if(val.intValue()==0){
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
			if(val.intValue()==0){
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

			if (status.equals(StatusType.NORMAL)) {

				float distanceTo = dTime * speed;
				distanceTo = dTime * speed;
				this.setPosition(this.getX() + distanceTo, this.getY());

				if (this.getX() >= maxX) {
					speed = speed * -1;
					changeAnimateState(State.WALKIN_LEFT, true);
					distanceTo = dTime * speed;
					this.setPosition(maxX, this.getY());

				} else if (this.getX() <= minX) {
					speed = speed * -1;
					distanceTo = dTime * speed;
					changeAnimateState(State.WALKIN_RIGHT, true);
					this.setPosition(minX, this.getY());
				}

			} else if (status.equals(StatusType.POOPED)) {
				changeAnimateState(State.POOP_BEGIN, false);
				if (!botherSound) {
					botherSound = true;
					// Sound s =
					// SoundSingleton.getInstance().getSound("poopReactOld.mp3");
					SoundSingleton.getInstance()
							.playSound(getRandomPoopSound());

				}
				if (!isAnimationRunning()) {
					changeAnimateState(State.POOP_END, true);

					status = StatusType.FINISHED;
				}
			} else if (status.equals(StatusType.POOPED_END)) {

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void initHashMap() {
		fps = new long[] { 50, 50, 50, 50, 50, 50, 50, 50, 50, 50 };

		stateAnimate.put(State.WALKIN_RIGHT, new MyAnimateProperty(0, 10, fps));
		stateAnimate.put(State.WALKIN_LEFT, new MyAnimateProperty(10, 10, fps));
		stateAnimate.put(State.POOP_BEGIN, new MyAnimateProperty(20, 3,
				new long[] { 200, 300, 200 }));
		stateAnimate.put(State.POOP_END, new MyAnimateProperty(23, 7,
				new long[] { 200, 200, 200, 200, 200, 200, 200 }));

		stateAnimate.put(State.WALKIN_DOWN, new MyAnimateProperty(0, 2,
				new long[] { 200, 200 }));
		stateAnimate.put(State.WALKIN_UP, new MyAnimateProperty(0, 2,
				new long[] { 200, 200 }));

	}

	@Override
	public void initAnimationParams() {

		fps = new long[] { 100, 100, 100 };
		imageNumber = 3;
		// currentState = State.WALKIN_UP;
		changeAnimateState(State.WALKIN_RIGHT, true);
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

	@Override
	public void poop(MySpriteGeneral poop, LevelController controller) {
		try {
			if (poop instanceof SpritePoopRocket) {

				SpriteBigShit spr = new SpriteBigShit(0, 0, TextureSingleton
						.getInstance().getTextureAnimateByName("bigShit.png"),
						TextureSingleton.getInstance()
								.getVertexBufferObjectManager(), level);
				spr.setSize(this.getWidth(), this.getHeight());
				spr.setPosition(this);
				spr.setZIndex(4);
				level.addSpriteToUpdate(spr);
				level.getScene().attachChild(spr);

				// level.removeEntity(this);
				level.getScene().detachChild(this);

			}

			this.setStatus(StatusType.POOPED);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
