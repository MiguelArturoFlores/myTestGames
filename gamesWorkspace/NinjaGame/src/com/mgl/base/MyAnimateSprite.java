package com.mgl.base;

import java.util.HashMap;

import org.andengine.entity.IEntity;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.badlogic.gdx.physics.box2d.Body;
import com.mgl.drop.game.constant.CollitionType;
import com.mgl.drop.game.constant.GameConstants;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.texture.TextureSingleton;

public abstract class MyAnimateSprite extends AnimatedSprite implements
		MySpriteGeneral, Comparable<MyAnimateSprite> {

	protected StatusType status;
	protected HashMap<String, MyAnimateProperty> stateAnimate;
	protected Body body;
	protected long[] fps;
	protected String currentState;
	protected int imageNumber;
	protected boolean isTouchable = true;
	protected boolean physicAdd = false;

	protected LevelController level;
	protected float currentTime = 0f;
	protected float time = 0f;

	protected boolean canChangeAnimate = true;
	protected boolean mustUpdate = true;

	protected int type;
	protected int behavior;
	protected float speed;
	protected float speedX;
	protected int diamant;
	protected String xmlName;
	protected int collitionType = CollitionType.COLLITION_NONE;
	protected float myAngle = 0;
	protected boolean mustReload = true;

	TextureSingleton texture = TextureSingleton.getInstance();

	public MyAnimateSprite(float pX, float pY, ITextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager,
			LevelController level) {
		// center

		super(pX - pTextureRegion.getWidth() / 2, pY
				- pTextureRegion.getHeight() / 2,
				(ITiledTextureRegion) pTextureRegion,
				pVertexBufferObjectManager);
		try {
			diamant = 0;
			mustReload = true;
			this.level = level;
			behavior = type = SpriteTypeConstant.BACKGROUND;
			this.speedX = 150;

			/*
			 * if((Math.random()*1000000)%100 > 50) this.speedX = 150; else{
			 * this.speedX = -150; }
			 */

			// super(pX, pY, pTextureRegion, pVertexBufferObjectManager);

			status = StatusType.NORMAL;

			stateAnimate = new HashMap<String, MyAnimateProperty>();

			initHashMap();

			currentState = new String();
			imageNumber = 0;
			fps = new long[] { 0 };

			initAnimationParams();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public abstract void initAnimationParams();

	public abstract void initHashMap();

	public void anime(boolean loop) {
		try {
			imageNumber = stateAnimate.get(currentState).getQuantity();
			fps = stateAnimate.get(currentState).getFps();
			animate(fps, stateAnimate.get(currentState).getPosition(),
					stateAnimate.get(currentState).getPosition() + imageNumber
							- 1, loop);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void anime(long[] ls, int state, int numberImages) {
		try {
			animate(ls, state, state + numberImages - 1, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void changeAnimateState(String animateState, boolean loop) {
		if (!canChangeAnimate) {
			return;
		}
		if (!currentState.equals(animateState)) {
			currentState = animateState;
			anime(loop);
		}else{
			if(!isAnimationRunning()){
				anime(loop);
			}
		}
	}
	
	public void reanimateState(String animateState, boolean loop) {
			currentState = animateState;
			anime(loop);

	}

	public abstract void updateAnimated(float dTime, LevelController level);

	public void update(float dTime, LevelController level) {
		try {
			if (!mustUpdate) {
				return;
			}
			updateAnimated(dTime, level);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public int compareTo(MyAnimateSprite another) {

		if (this.getY() + this.getHeight() > another.getY()
				+ another.getHeight()) {
			return 1;
		}
		if (this.getY() + this.getHeight() < another.getY()
				+ another.getHeight()) {
			return -1;
		}
		return 0;

	}

	@Override
	public StatusType getStatus() {
		return status;
	}

	@Override
	public void setStatus(StatusType status) {
		this.status = status;
	}

	public HashMap<String, MyAnimateProperty> getStateAnimate() {
		return stateAnimate;
	}

	public void setStateAnimate(HashMap<String, MyAnimateProperty> stateAnimate) {
		this.stateAnimate = stateAnimate;
	}

	public long[] getFps() {
		return fps;
	}

	public void setFps(long[] fps) {
		this.fps = fps;
	}

	public String getCurrentState() {
		return currentState;
	}

	public void setCurrentState(String currentState) {
		this.currentState = currentState;
	}

	public int getImageNumber() {
		return imageNumber;
	}

	public void setImageNumber(int imageNumber) {
		this.imageNumber = imageNumber;
	}

	public LevelController getLevel() {
		return level;
	}

	public void setLevel(LevelController level) {
		this.level = level;
	}

	public float getCurrentTime() {
		return currentTime;
	}

	public void setCurrentTime(float currentTime) {
		this.currentTime = currentTime;
	}

	public boolean isCanChangeAnimate() {
		return canChangeAnimate;
	}

	public void setCanChangeAnimate(boolean canChangeAnimate) {
		this.canChangeAnimate = canChangeAnimate;
	}

	public boolean isMustUpdate() {
		return mustUpdate;
	}

	public void setMustUpdate(boolean mustUpdate) {
		this.mustUpdate = mustUpdate;
	}

	@Override
	public void poop(MySpriteGeneral poop, LevelController controller) {
		try {
			this.setStatus(StatusType.POOPED);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void addToTerrain(MySpriteGeneral sprite) {
		try {

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public float getTime() {
		return time;
	}

	public void setTime(float time) {
		this.time = time;
	}

	@Override
	public IEntity getEntity() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public ITouchArea getTouchArea() {
		// TODO Auto-generated method stub
		return this;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getBehavior() {
		return behavior;
	}

	public void setBehavior(int behavior) {
		this.behavior = behavior;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;

	}

	public float getSpeedX() {
		return speedX;
	}

	public void setSpeedX(float speedX) {
		this.speedX = speedX;
	}

	public int getDiamant() {
		return diamant;
	}

	public void setDiamant(int diamant) {
		this.diamant = diamant;
	}

	public Body getBody() {
		return body;
	}

	public void setBody(Body body) {
		this.body = body;
	}

	public int getCollitionType() {
		return collitionType;
	}

	public String getXmlName() {
		return xmlName;
	}

	public void setXmlName(String xmlName) {
		this.xmlName = xmlName;
	}

	public void setCollitionType(int collitionType) {
		this.collitionType = collitionType;
	}

	public void setXmlParameter(String parameter) {

	}

	public boolean isTouchable() {
		return isTouchable;
	}

	public void setTouchable(boolean isTouchable) {
		this.isTouchable = isTouchable;
	}

	public TextureSingleton getTexture() {
		return texture;
	}

	public void setTexture(TextureSingleton texture) {
		this.texture = texture;
	}

	public float getMyAngle() {
		return myAngle;
	}

	public void setMyAngle(float myAngle) {
		this.myAngle = myAngle;
	}

	public boolean isPhysicAdd() {
		return physicAdd;
	}

	public void setPhysicAdd(boolean physicAdd) {
		this.physicAdd = physicAdd;
	}

	public boolean isMustReload() {
		return mustReload;
	}

	public void setMustReload(boolean mustReload) {
		this.mustReload = mustReload;
	}

}