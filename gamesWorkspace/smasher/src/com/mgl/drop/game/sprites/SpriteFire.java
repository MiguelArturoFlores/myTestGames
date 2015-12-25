package com.mgl.drop.game.sprites;

import org.andengine.entity.modifier.MoveModifier;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.google.android.gms.internal.iz;
import com.mgl.base.MyAnimateProperty;
import com.mgl.base.MyAnimateSprite;
import com.mgl.base.SpriteType;
import com.mgl.drop.game.constant.State;
import com.mgl.drop.game.constant.ZIndexGame;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.texture.TextureSingleton;

public class SpriteFire extends MyAnimateSprite {

	private float timeToDesapear = 5f;
	private float contTime = 0;

	private SpriteBackground collideShape;
	private SpriteBackground background;

	public SpriteFire(float pX, float pY, ITextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager,
			LevelController level) {
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager, level);

	}

	public void setCollisionShape() {
		try {

			float percentage = 0.4f;

			TextureSingleton texture = TextureSingleton.getInstance();
			collideShape = new SpriteBackground(0, 0,
					texture.getTextureByName("black.jpg"),
					texture.getVertexBufferObjectManager());
			collideShape.setWidth(this.getWidth());
			collideShape.setHeight(this.getHeight() * percentage);
			collideShape.setPosition(0, this.getHeight() - this.getHeight()
					* percentage);
			collideShape.setAlpha(0f);
			this.attachChild(collideShape);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean collidesWith(org.andengine.entity.shape.IShape pOtherShape) {
		try {

			return collideShape.collidesWith(pOtherShape);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public SpriteType getSpriteType() {

		return SpriteType.FIRE;
	}

	@Override
	public void initAnimationParams() {
		try {
			changeAnimateState(State.DISPLAYED_FIRE, true);
			anime(true);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void initHashMap() {
		fps = new long[] { 83, 83, 83, 83, 83, 83, 83, 83 };

		stateAnimate
				.put(State.DISPLAYED_FIRE, new MyAnimateProperty(0, 8, fps));

	}

	@Override
	public void updateAnimated(float dTime, LevelController level) {
		try {

			contTime += dTime;
			if (contTime < timeToDesapear)
				return;

			background.detachSelf();
			level.removeEntity(background);

			this.detachSelf();
			level.removeEntity(this);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void addBackground() {
		try {
			TextureSingleton texture = TextureSingleton.getInstance();
			background = new SpriteBackground(0, this.getHeight(), texture
					.getInstance().getTextureByName("fireFloor.png"),
					texture.getVertexBufferObjectManager());
			level.getScene().attachChild(background);
			level.addSpriteToUpdate(background);
			background.setPosition(0, this.getY() +87);
			background.setZIndex(ZIndexGame.FIRE_FLOOR);
			background.registerEntityModifier(new MoveModifier(0.4f, background.getWidth()*-1, background.getX(),
					background.getY(), background.getY()));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
