package com.mgl.base;

import org.andengine.entity.IEntity;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.badlogic.gdx.physics.box2d.Body;
import com.mgl.drop.game.constant.CollitionType;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.texture.TextureSingleton;

public abstract class MySprite extends Sprite implements MySpriteGeneral {

	protected float currentTime = 0f;
	protected Body body;
	protected LevelController level;
	protected StatusType status;
	protected TextureSingleton texture = TextureSingleton.getInstance();
	protected float time = 0f;
	protected boolean mustUpdate = true;
	protected String xmlName;
	protected int collitionType = CollitionType.COLLITION_NONE;
	protected boolean isTouchable = true;
	protected float myAngle= 0;
	protected boolean physicAdd = false;
	protected boolean mustReload = true;

	public MySprite(float pX, float pY, float pWidth, float pHeight,
			ITextureRegion pTextureRegion,
			VertexBufferObjectManager vertexBufferObjectManager,
			LevelController level) {
		super(pX, pY, pWidth, pHeight, pTextureRegion,
				vertexBufferObjectManager);
		status = StatusType.NORMAL;
		isTouchable = false;
		mustReload = true;
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
	public void setMustUpdate(boolean mustUpdate) {
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

	@Override
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

	@Override
	public Body getBody() {
		return body;
	}

	@Override
	public void setBody(Body body) {
		this.body = body;
	}

	@Override
	public int getCollitionType() {
		return collitionType;
	}

	@Override
	public String getXmlName() {
		return xmlName;
	}

	public void setXmlName(String xmlName) {
		this.xmlName = xmlName;
	}

	public void setCollitionType(int collitionType) {
		this.collitionType = collitionType;
	}

	@Override
	public void setXmlParameter(String parameter) {

	}

	public TextureSingleton getTexture() {
		return texture;
	}

	public void setTexture(TextureSingleton texture) {
		this.texture = texture;
	}

	public boolean isTouchable() {
		return isTouchable;
	}

	public void setTouchable(boolean isTouchable) {
		this.isTouchable = isTouchable;
	}

	public boolean isMustUpdate() {
		return mustUpdate;
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
