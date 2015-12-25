package com.mgl.drop.game.sprites.arunner;

import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MyAnimateProperty;
import com.mgl.base.MyAnimateSprite;
import com.mgl.base.SpriteType;
import com.mgl.drop.game.constant.State;
import com.mgl.drop.game.controller.LevelController;

public class SpriteIce extends MyAnimateSprite {

	private float contTime = 0;
	private float timeFreeze = 2; 		
	private boolean hasFreeze = false;
	private SpriteEnemy enemy;
	
	public SpriteIce(float pX, float pY, ITextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager,
			LevelController level) {
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager, level);
		contTime = 0;
		hasFreeze = true;
		
	}

	@Override
	public SpriteType getSpriteType() {
		// TODO Auto-generated method stub
		return SpriteType.DECORATIVE;
	}

	@Override
	public void initHashMap() {
		try {

			fps = new long[] { 83, 83, 83 };

			stateAnimate.put(State.WALKIN_RIGHT, new MyAnimateProperty(0, 3,
					fps));
			stateAnimate.put(State.BREAKING, new MyAnimateProperty(3, 3, fps));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void initAnimationParams() {
		try {

			changeAnimateState(State.WALKIN_RIGHT, false);
			anime(false);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void updateAnimated(float dTime, LevelController level) {
		try {
			//this.setColor(100, 0, 0);
			if(enemy!=null && enemy.isKillFreeze()){
				this.changeAnimateState(State.BREAKING, false);
				enemy.killEnemy(SpriteType.FINGER);
				hasFreeze = false;
				enemy = null;
				return;
			}
			
			if(hasFreeze){
				contTime =contTime + dTime;
				enemy.setFreeze(true);
				if(contTime> timeFreeze){
					this.changeAnimateState(State.BREAKING, false);
					hasFreeze = false;
					enemy.setFreeze(false);
				}
			}else{
				if(!isAnimationRunning()){
					this.detachSelf();
					level.removeEntity(this);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public float getContTime() {
		return contTime;
	}

	public void setContTime(float contTime) {
		this.contTime = contTime;
	}

	public float getTimeFreeze() {
		return timeFreeze;
	}

	public void setTimeFreeze(float timeFreeze) {
		this.timeFreeze = timeFreeze;
	}

	public boolean isHasFreeze() {
		return hasFreeze;
	}

	public void setHasFreeze(boolean hasFreeze) {
		this.hasFreeze = hasFreeze;
	}

	public SpriteEnemy getEnemy() {
		return enemy;
	}

	public void setEnemy(SpriteEnemy enemy) {
		this.enemy = enemy;
	}

	
	
}
