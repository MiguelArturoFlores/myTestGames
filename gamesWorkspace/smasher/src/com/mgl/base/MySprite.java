package com.mgl.base;

import org.andengine.entity.IEntity;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.texture.TextureSingleton;

public abstract class MySprite extends Sprite implements MySpriteGeneral {

	protected float currentTime = 0f;
	protected LevelController level;
	protected StatusType status;
	protected TextureSingleton texture = TextureSingleton.getInstance();
	protected float time = 0f;
	protected boolean mustUpdate= true;

	public MySprite(float pX, float pY, float pWidth, float pHeight,
			ITextureRegion pTextureRegion,
			VertexBufferObjectManager vertexBufferObjectManager,
			LevelController level) {
		super(pX, pY, pWidth, pHeight, pTextureRegion,
				vertexBufferObjectManager);
		status = StatusType.NORMAL;

		this.level = level;
	}

	public MySprite(float pX, float pY, ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager);
		status = StatusType.NORMAL;
	}

	public MySprite(float pX, float pY, ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager,
			LevelController level) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager);
		status = StatusType.NORMAL;
		this.level = level;
	}

	
	@Override
	public void setMustUpdate(boolean mustUpdate){
		this.mustUpdate = mustUpdate;
	}
	
	@Override
	public abstract void update(float dTime, LevelController level);

	public float getCurrentTime() {
		return currentTime;
	}

	public void setCurrentTime(float currentTime) {
		this.currentTime = currentTime;
	}

	public LevelController getLevel() {
		return level;
	}

	public void setLevel(LevelController level) {
		this.level = level;
	}

	@Override
	public StatusType getStatus() {
		return status;
	}

	@Override
	public void setStatus(StatusType status) {
		this.status = status;
	}

	@Override
	public void poop(MySpriteGeneral poop, LevelController level) {
		try {
			this.setStatus(StatusType.POOPED);
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
		return getTouchArea();
	}

}
