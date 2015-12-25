package com.mgl.crappy.base;

import org.andengine.audio.sound.Sound;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.drop.factory.SoundSingleton;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.texture.TextureSingleton;

public abstract class MySprite extends Sprite implements MySpriteGeneral {

	protected float currentTime = 0f;
	protected LevelController level;
	protected StatusType status;
	protected TextureSingleton texture = TextureSingleton.getInstance();
	
	public MySprite(float pX, float pY, float pWidth, float pHeight,
			ITextureRegion pTextureRegion,
			VertexBufferObjectManager vertexBufferObjectManager, LevelController level) {
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
			VertexBufferObjectManager pVertexBufferObjectManager, LevelController level) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager);
		status = StatusType.NORMAL;
		this.level = level;
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
		
	

	
	
	
	
}
