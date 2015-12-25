package com.mgl.drop.game.sprites;

import java.util.HashMap;

import org.andengine.audio.sound.Sound;
import org.andengine.engine.camera.Camera;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.util.Log;

import com.mgl.base.MyAnimateProperty;
import com.mgl.base.MyAnimateSprite;
import com.mgl.base.SpriteType;
import com.mgl.base.StatusType;
import com.mgl.drop.factory.SoundSingleton;
import com.mgl.drop.game.constant.State;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.scene.SceneManagerSingleton;
import com.mgl.drop.game.sprites.thread.ThreadPlayerSound;
import com.mgl.drop.texture.TextureSingleton;
import com.mgl.drop.util.Path;
import com.mgl.drop.util.Point;

public class SpritePlayer extends MyAnimateSprite {

	private TextureSingleton texture = TextureSingleton.getInstance();
	private float speed = 100;
	private boolean chasing = true;
	private Path path = null;
	private Point pointToMove = null;
	private float poopTime = 3f;
	private float poopTimeCont = 0;

	private SpritePoopBar poopBar;
	private HashMap<Integer, String> poopSoundHash;
	private Sound soundAleteo;

	private SpriteEndPoint endPoint;
	

	private ThreadPlayerSound thread;
	private float countFlyingSound =0;

	public SpritePlayer(float pX, float pY, ITextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager,
			LevelController level) {
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager, level);
		try {

			status = StatusType.WAITING;
			initPoopSoundHash();

			thread= null;

			SpriteInvisibleTouch spr = new SpriteInvisibleTouch((0f), (0f),
					200f, 200f, TextureSingleton.getInstance()
							.getTextureByName("point.png"),
					pVertexBufferObjectManager, this);

			this.attachChild((spr));
			spr.setPosition(-60, -20);
			spr.setWidth(150);
			spr.setHeight(100);
			// spr.setAlpha(0.2f);
			spr.setVisible(false);

			level.getScene().registerTouchArea(spr);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initPoopSoundHash() {
		try {

			poopSoundHash = new HashMap<Integer, String>();
			for (int i =1; i <=17; i++){
				poopSoundHash.put(Integer.valueOf(i), "Cagada"+i+".mp3");
			}
			

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void updateAnimated(float dTime, LevelController level) {
		try {

			if (status.equals(StatusType.WAITING)) {
				return;
			} else if (status.equals(StatusType.PLAYING)) {

				if (thread == null) {
					thread = new ThreadPlayerSound(this);
					//thread.start();
				}

				updatePlayerSound(dTime);
				
				String state = getCurrentState();
				updatePlaying(dTime);

				if (state.equals(State.POOPING1)
						|| state.equals(State.POOPING2)
						|| state.equals(State.POOPING3)) {
					poopTimeCont = poopTimeCont + dTime;
					if (poopTimeCont > poopTime || !isAnimationRunning()) {
						canChangeAnimate = true;
						poopTimeCont = 0;
					}
				}

			} else if (status.equals(StatusType.HITED_BY_ROCK)) {
				// level.looseLevelReplay();
				level.looseLevelReplay();

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void updatePlayerSound(float dTime) {
		try {
			
			countFlyingSound = countFlyingSound+dTime;
			if(countFlyingSound>2){
				beginFlying();
				countFlyingSound = 0;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void beginFlying() {
		try {

			//soundAleteo.play();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	
	private void updatePlaying(float dTime) {
		try {
			moveFollowingPath(dTime);

			if (!chasing) {
				return;
			}

			// float width = level.getWidth();
			// float height= endPoint.getY()+endPoint.getHeight()*3;
			float width = endPoint.getX() + endPoint.getWidth() * 3;
			float height = level.getHeigh();

			Camera camera = SceneManagerSingleton.getInstance().getCamera();
			float xStart = this.getX();
			float yStart = this.getY() + 200;

			if (xStart < camera.getWidth() / 2) {
				xStart = camera.getWidth() / 2;

			}
			if (xStart > width - ((camera.getWidth() / 2))) {
				xStart = width - ((camera.getWidth() / 2));

			}
			if (camera.getWidth() - xStart > 0
					&& xStart + 100 < camera.getWidth()) {
				// xStart = camera.getWidth()/2;
			}

			// sad
			if (yStart + camera.getHeight() / 2 > height) {

				yStart = height - camera.getHeight() / 2;

			}

			if (camera.getHeight() > level.getHeigh()) {
				yStart = camera.getCenterY();
			}

			camera.setCenter(xStart, yStart);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void initHashMap() {
		/*
		 * stateAnimate.put(State.WALKIN_DOWN, new MyAnimateProperty(0, 3,new
		 * long[] { 100, 100, 100 })); stateAnimate.put(State.WALKIN_LEFT, new
		 * MyAnimateProperty(3, 3,new long[] { 100, 100, 100 }));
		 * stateAnimate.put(State.WALKIN_RIGHT, new MyAnimateProperty(6, 2,new
		 * long[] { 100,100})); stateAnimate.put(State.WALKIN_UP, new
		 * MyAnimateProperty(9, 3,new long[] { 100, 100, 100 }));
		 */
		stateAnimate.put(State.WALKIN_RIGHT, new MyAnimateProperty(0, 3,
				new long[] { 75, 120, 75 }));
		stateAnimate.put(State.WALKIN_UP, new MyAnimateProperty(0, 3,
				new long[] { 75, 120, 75 }));
		stateAnimate.put(State.WALKIN_DOWN, new MyAnimateProperty(0, 3,
				new long[] { 75, 120, 75 }));
		stateAnimate.put(State.WALKIN_LEFT, new MyAnimateProperty(0, 3,
				new long[] { 75, 120, 75 }));
		stateAnimate.put(State.POOPING1, new MyAnimateProperty(3, 3,
				new long[] { 250, 250, 250 }));
		stateAnimate.put(State.POOPING2, new MyAnimateProperty(6, 3,
				new long[] { 250, 250, 250 }));
		stateAnimate.put(State.POOPING3, new MyAnimateProperty(9, 3,
				new long[] { 250, 250, 250 }));

	}

	@Override
	public void initAnimationParams() {

		fps = new long[] { 100, 100, 100 };
		imageNumber = 3;
		currentState = State.WALKIN_RIGHT;
		anime(true);
	}

	public void moveFollowingPath(float pSecondsElapsed) {

		float x = this.getX();
		float y = this.getY();
		float distance = pSecondsElapsed * speed;

		Point currentPoint = new Point(x + getWidth() / 2, y + getHeight() / 2);

		if (pointToMove == null && path.isEmpty()) {
			return;
		}

		if (!path.isEmpty()) {
			pointToMove = path.peek();
		}

		float disAux = Point.distanceBetween(currentPoint, pointToMove);

		if (distance > disAux) {
			if (!path.isEmpty()) {
				pointToMove = path.pop();
			} else {
				this.setPosition(pointToMove.getX() + getWidth() / 2,
						pointToMove.getY() + getHeight() / 2);
				pointToMove = null;
				return;
			}

		}

		float angle = Point.angleBetween(currentPoint, pointToMove);

		float dx = distance * (float) Math.cos(angle);
		float dy = distance * (float) Math.sin(angle);

		this.setPosition(x + dx, y + dy);

		float dxAux = dx;
		float dyAux = dy;

		if (dxAux < 0) {
			dxAux = dxAux * -1;
		}
		if (dyAux < 0) {
			dxAux = dyAux * -1;
		}

		// greater in X moving left or right;
		if (dxAux > dyAux) {
			if (dx > 0) {
				changeAnimateState(State.WALKIN_UP, true);
			} else {
				changeAnimateState(State.WALKIN_DOWN, true);
			}
		}
		// greater in Y moving up or down
		else {
			if (dy > 0) {
				changeAnimateState(State.WALKIN_RIGHT, true);
			} else {
				changeAnimateState(State.WALKIN_LEFT, true);
			}
		}

	}

	@Override
	public boolean onAreaTouched(final TouchEvent pSceneTouchEvent,
			final float pTouchAreaLocalX, final float pTouchAreaLocalY) {

		switch (pSceneTouchEvent.getAction()) {
		case TouchEvent.ACTION_DOWN:

			try {

				if (status.equals(StatusType.WAITING)
						|| status.equals(StatusType.FINISHED)) {
					return false;
				}

				Log.d("TOCADO", "Player cagando");

				changePigeonRandomPoop();

				if (!poopBar.canIPoop()) {
					Log.d("CAGADAS", "Ya no tengo mas cagadas");
					return false;
				}

				/*
				 * SpritePoop poop = new SpritePoop(this.getX(), this.getY() +
				 * this.getHeight(), texture.getTextureAnimateByName(
				 * "poop.png", 300, 360, 5, 6),
				 * texture.getVertexBufferObjectManager(), level);
				 */

				canChangeAnimate = false;
				MyAnimateSprite poop = poopBar.getPoop();
				if (poop == null) {
					Log.d("CAGADAS", "CAGADA NULA");
					return false;
				}
				if (poop instanceof SpritePoopRocket) {
					SpritePoopRocket rocket = (SpritePoopRocket) poop;
					poop.setSize(70, 70);
					rocket.setSceneTouchListener();
				} else if (poop instanceof SpritePoop) {
					poop.changeAnimateState(State.FALLING_DOWN, true);
					poop.setSize(30, 30);
				} else if (poop instanceof SpritePoopBomb) {
					poop.setSize(30, 30);
				}else if (poop instanceof SpritePoopMultiple) {
					poop.setSize(30, 30);
				}

				poop.setPosition(this.getX() + (poop.getWidth() / 2) - 10,
						this.getY() + 20);

				level.getScene().attachChild(poop);
				level.getScene().registerTouchArea(poop);
				level.addSpriteToUpdate(poop);

				SoundSingleton.getInstance().playSound(
						getRandomPoopSound());
				
				/*Sound sound = SoundSingleton.getInstance().getSound(
						getRandomPoopSound());

				if (sound == null) {
					return false;
				}

				sound.play();*/

			} catch (Exception e) {
				e.printStackTrace();
			}

			break;
		case TouchEvent.ACTION_MOVE:

			break;
		case TouchEvent.ACTION_UP:

			break;
		}
		return true;
	}

	private void changePigeonRandomPoop() {
		try {

			Double val = (Math.random() * 102328764) % 3;

			if (val.intValue() == 0) {
				changeAnimateState(State.POOPING1, false);
			}
			if (val.intValue() == 1) {
				changeAnimateState(State.POOPING2, false);
			}
			if (val.intValue() == 2) {
				changeAnimateState(State.POOPING3, false);
			}

			canChangeAnimate = false;

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private String getRandomPoopSound() {
		try {

			Double val = Math.random() * 100000000f;

			val = val % poopSoundHash.size();
			if(val.intValue() == 0){
				val = 1D;
			}
			String valS = poopSoundHash.get((val.intValue()));
			//String valS = poopSoundHash.get((3));
			Log.d("CAGADA", valS + " val " + Integer.valueOf(val.intValue())
					+ " abcd " + poopSoundHash.size());
			return valS;

		} catch (Exception e) {
			Log.d("CAGADA", "EERROR EN EL VAGADA SOUND");
			return "cagada2.mp3";
		}

	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public Path getPath() {
		return path;
	}

	public void setPath(Path path) {

		this.path = path;
		if (path.isEmpty()) {
			return;
		}
		Point p = path.peek();
		this.setPosition(p.getX() - this.getWidth() / 4,
				p.getY() - this.getHeight() / 2);
	}

	public Point getPointToMove() {
		return pointToMove;
	}

	public void setPointToMove(Point pointToMove) {
		this.pointToMove = pointToMove;
	}

	@Override
	public SpriteType getSpriteType() {

		return SpriteType.PLAYER;
	}

	public boolean isChasing() {
		return chasing;
	}

	public void setChasing(boolean chasing) {
		this.chasing = chasing;
	}

	public SpritePoopBar getPoopBar() {
		return poopBar;
	}

	public void setPoopBar(SpritePoopBar poopBar) {
		this.poopBar = poopBar;
	}

	public float getPoopTime() {
		return poopTime;
	}

	public void setPoopTime(float poopTime) {
		this.poopTime = poopTime;
	}

	public float getPoopTimeCont() {
		return poopTimeCont;
	}

	public void setPoopTimeCont(float poopTimeCont) {
		this.poopTimeCont = poopTimeCont;
	}

	public HashMap<Integer, String> getPoopSoundHash() {
		return poopSoundHash;
	}

	public void setPoopSoundHash(HashMap<Integer, String> poopSoundHash) {
		this.poopSoundHash = poopSoundHash;
	}

	public SpriteEndPoint getEndPoint() {
		return endPoint;
	}

	public void setEndPoint(SpriteEndPoint endPoint) {
		this.endPoint = endPoint;
	}

	public void endFlying() {
		try {

			
			

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
