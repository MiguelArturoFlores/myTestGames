package com.mgl.drop.game.sprites.aicerunner;

import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.mgl.base.MyAnimateProperty;
import com.mgl.base.userinfo.PhysicSingleton;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.factory.SoundSingleton;
import com.mgl.drop.game.constant.CollitionType;
import com.mgl.drop.game.constant.State;
import com.mgl.drop.game.constant.ZIndexGame;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.sprites.SpritePlayerBase;
import com.mgl.drop.texture.TextureSingleton;

public class SpritePlayer extends SpritePlayerBase {

	private static final int MAX_SPEED = 10;

	private SpritePlayer playerReflex;

	public SpritePlayer(float pX, float pY, ITextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager,
			LevelController level) {
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager, level);
		try {

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void createReflex() {
		try {

			TextureSingleton texture = TextureSingleton.getInstance();

			playerReflex = new SpritePlayer(0, 0,
					texture.getTextureAnimateByName("player.png"),
					texture.getVertexBufferObjectManager(), level);
			playerReflex.setSize(60, 90);
			playerReflex.setPosition(this);

			playerReflex.setZIndex(ZIndexGame.PLAYER);

			level.getScene().attachChild(playerReflex);
			playerReflex.setFlippedVertical(true);

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

			fps = new long[] { 83, 83, 83, 83, 83, 83, 83 };

			stateAnimate.put(State.WALKIN_RIGHT, new MyAnimateProperty(0, 7,
					fps));
			stateAnimate.put(State.WALKIN_LEFT, new MyAnimateProperty(10, 7,
					fps));
			stateAnimate.put(State.IDLE, new MyAnimateProperty(9, 4,
					new long[] { 83, 83, 83, 83 }));

			stateAnimate.put(State.DIYING, new MyAnimateProperty(22, 3,
					new long[] { 83, 83, 83 }));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateAnimated(float dTime, LevelController level) {
		try {
			
			updateReflex(dTime, level);

			if (updateDying(dTime, level)) {
				return;
			}

			updateMooving(dTime, level);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void updateReflex(float dTime, LevelController level) {
		try {
			
			playerReflex.setPosition(this.getX(), this.getY()+this.getHeight());
			playerReflex.setAlpha(0.5f);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean updateDying(float dTime, LevelController level) {
		try {

			if (currentState.equals(State.DIYING)) {
				if (!isAnimationRunning()) {
					level.looseLevel();
				}
				return true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	private void updateMooving(float dTime, LevelController level) {
		try {

			if (currentState.equals(State.WALKIN_LEFT)) {

				if (getBody().getLinearVelocity().x < -MAX_SPEED) {
					// max velocity
					return;
				}

				getBody().setLinearVelocity(
						getBody().getLinearVelocity().x - 0.5f,
						getBody().getLinearVelocity().y);

			}
			if (currentState.equals(State.WALKIN_RIGHT)) {

				if (getBody().getLinearVelocity().x > MAX_SPEED) {
					// max velocity
					return;
				}

				getBody().setLinearVelocity(
						getBody().getLinearVelocity().x + 0.5f,
						getBody().getLinearVelocity().y);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void mooveRight() {
		try {
			
			SoundSingleton.getInstance().playMoveRight();
			
			if (currentState.equals(State.DIYING)) {
				return;
			}
			playerReflex.changeAnimateState(State.WALKIN_RIGHT, false);
			changeAnimateState(State.WALKIN_RIGHT, false);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void mooveLeft() {
		try {

			SoundSingleton.getInstance().playMoveLeft();
			
			if (currentState.equals(State.DIYING)) {
				return;
			}
			playerReflex.changeAnimateState(State.WALKIN_LEFT, false);
			changeAnimateState(State.WALKIN_LEFT, false);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void releaseMovement() {
		try {

			if (currentState.equals(State.DIYING)) {
				return;
			}

			changeAnimateState(State.IDLE, true);
			playerReflex.changeAnimateState(State.IDLE, true);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void looseLive() {
		try {

			changeAnimateState(State.DIYING, false);
			playerReflex.changeAnimateState(State.DIYING, false);
			getBody().setLinearVelocity(0, getBody().getLinearVelocity().y);
			
			SoundSingleton.getInstance().died();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
