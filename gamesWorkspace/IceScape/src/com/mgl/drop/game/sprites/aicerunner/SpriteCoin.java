package com.mgl.drop.game.sprites.aicerunner;

import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.mgl.base.MyAnimateProperty;
import com.mgl.base.MyAnimateSprite;
import com.mgl.base.SpriteType;
import com.mgl.base.userinfo.PhysicSingleton;
import com.mgl.drop.factory.SoundSingleton;
import com.mgl.drop.game.constant.CollitionType;
import com.mgl.drop.game.constant.State;
import com.mgl.drop.game.constant.ZIndexGame;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.sprites.SpriteBackground;
import com.mgl.drop.texture.TextureSingleton;

public class SpriteCoin extends MyAnimateSprite {

	private SpriteBackground light;
	private boolean removed = false;
	
	private float distance = 0;
	private boolean isUp = true;
	
	private float speed = 30;

	public SpriteCoin(float pX, float pY, ITextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager,
			LevelController level) {
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager, level);
		try {

			light = new SpriteBackground(0, 0, TextureSingleton.getInstance()
					.getTextureByName("light.png"), TextureSingleton
					.getInstance().getVertexBufferObjectManager());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean remove = false;
	private boolean collect = false;

	@Override
	public SpriteType getSpriteType() {
		// TODO Auto-generated method stub
		return SpriteType.COIN;
	}

	public void collectCoin() {
		try {

			collect = true;
			remove = true;

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void destroyCoin() {
		try {

			remove = true;
			collect = false;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void createLight(LevelController level) {
		try {

			light.setPosition(
					this.getX() + this.getWidth() / 2 - light.getWidth() / 2, -150);

			level.getScene().attachChild(light);
			light.setZIndex(this.getZIndex() - 1);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initAnimationParams() {
		try {

			changeAnimateState(State.IDLE, false);
			anime(true);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void initHashMap() {
		try {

			stateAnimate.put(State.IDLE, new MyAnimateProperty(0, 20,
					new long[] { 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83,
							83, 83, 83, 83, 83, 83, 83, 83, 83 }));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateAnimated(float dTime, LevelController level) {
		try {

			if (body != null) {
				updateWithBody(dTime, level);
				return;
			}
			
			distance = distance + dTime * speed;
			
			if(isUp){
				this.setY(this.getY() - (dTime * speed));
			}else{
				this.setY(this.getY() - (dTime * speed));
			}
			
			if(distance>10){
				distance = 0;
				isUp = !isUp;
			}
			
			if (this.collidesWith(level.getPlayer())) {
				level.collectCoin();
				level.removeEntity(this);
				this.detachSelf();
				light.detachSelf();
				SoundSingleton.getInstance().playEat();
				return;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void updateWithBody(float dTime, LevelController level) {
		try {

			if (!remove) {
				return;
			}

			light.detachSelf();
			
			level.removeEntity(this);
			this.detachSelf();
			
			if(collect){
				level.collectCoin();
				SoundSingleton.getInstance().playEat();
			}else{
				
				SpriteCoin falling   = new SpriteCoin(0,0, TextureSingleton.getInstance().getTextureAnimateByName("fish.png"), TextureSingleton.getInstance().getVertexBufferObjectManager(),level);
				falling.setSize(80, 80);
				falling.setPosition(this);
				falling.setZIndex(ZIndexGame.PLAYER);
				level.getScene().attachChild(falling);
				level.addSpriteToUpdate(falling);
				
			}
			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
