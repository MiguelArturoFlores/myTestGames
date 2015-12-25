package com.mgl.drop.game.sprites;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MySprite;
import com.mgl.base.SpriteType;
import com.mgl.drop.game.controller.LevelController;

public class SpriteBlackBackground extends MySprite {

	private float contTime = 0f;
	private float timeToAction = 1.5f;
	private boolean isPaused = false;

	public SpriteBlackBackground(float pX, float pY,
			ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager,
			LevelController controller) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager,
				controller);
	}

	@Override
	public SpriteType getSpriteType() {
		// TODO Auto-generated method stub
		return SpriteType.DECORATIVE;
	}

	@Override
	public void update(float dTime, LevelController level) {
		try {

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setAutoUpdate() {
		try {

			try {

				this.registerUpdateHandler(new IUpdateHandler() {
					@Override
					public void reset() {
					}

					@Override
					public void onUpdate(float pSecondsElapsed) {
						// HERE IS THE GAME LOOP
						time += pSecondsElapsed;

						try {

							level.setMustUpdate(false);

							contTime += pSecondsElapsed;

							float alpha = contTime * 1 / timeToAction;
							alpha = 1 - alpha;

							if (alpha <= 0) {
								alpha = 0;
								getSprite().detachSelf();
								level.setMustUpdate(true);
							}

							// if(alpha>=1){
							// alpha = 1;
							// }

							getSprite().setAlpha(alpha);
							if (contTime >= timeToAction) {
								// level.setMustUpdate(isPaused);
							}

						} catch (Exception e) {
							e.printStackTrace();
						}

					}

				});

			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public SpriteBlackBackground getSprite() {
		return this;
	}

	public float getContTime() {
		return contTime;
	}

	public void setContTime(float contTime) {
		this.contTime = contTime;
	}

	public float getTimeToAction() {
		return timeToAction;
	}

	public void setTimeToAction(float timeToAction) {
		this.timeToAction = timeToAction;
	}

	public boolean isPaused() {
		return isPaused;
	}

	public void setPaused(boolean isPaused) {
		this.isPaused = isPaused;
	}

}
