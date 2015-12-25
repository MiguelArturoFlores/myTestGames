package com.mgl.crappy.base;

import java.util.HashMap;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.util.Log;

import com.mgl.drop.game.constant.State;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.scene.SceneTouchListenerRocket;
import com.mgl.drop.game.sprites.SpriteOvni;
import com.mgl.drop.game.sprites.SpritePoopRocket;
import com.mgl.drop.game.sprites.SpriteRoof;
import com.mgl.drop.texture.TextureSingleton;

public abstract class MyAnimateSprite extends AnimatedSprite implements
		MySpriteGeneral {

	protected StatusType status;
	protected HashMap<String, MyAnimateProperty> stateAnimate;
	protected long[] fps;
	protected String currentState;
	protected int imageNumber;

	protected LevelController level;
	protected float currentTime = 0f;

	protected boolean canChangeAnimate = true;
	protected boolean mustUpdate = true;
	
	protected int type ;

	public MyAnimateSprite(float pX, float pY, ITextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager,
			LevelController level) {
		// center

		super(pX - pTextureRegion.getWidth() / 2, pY
				- pTextureRegion.getHeight() / 2,
				(ITiledTextureRegion) pTextureRegion,
				pVertexBufferObjectManager);
		this.level = level;
		type = SpriteTypeConstant.BACKGROUND;
		// super(pX, pY, pTextureRegion, pVertexBufferObjectManager);

		status = StatusType.NORMAL;

		stateAnimate = new HashMap<String, MyAnimateProperty>();

		initHashMap();

		currentState = new String();
		imageNumber = 0;
		fps = new long[] { 0 };

		initAnimationParams();

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

		}
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
			MySprite spr = (MySprite) sprite;
			
			float x = this.getX()-spr.getX();
			float y = this.getY()-spr.getY();
			
			
			if(spr.getParent() instanceof SpriteOvni){
				SpriteOvni ovni = (SpriteOvni) spr.getParent();
			
				
				x = this.getX()-ovni.getX();
				y = this.getY()-ovni.getY();
				this.detachSelf();
				this.setPosition(x,y);
				spr.attachChild(this);
			}
			if(spr.getParent() instanceof SpriteRoof){
				
				 x = this.getX();
				 y= this.getY();
				 return;
			}
			
			

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
