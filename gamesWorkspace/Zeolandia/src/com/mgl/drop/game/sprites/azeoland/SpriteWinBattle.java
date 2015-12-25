package com.mgl.drop.game.sprites.azeoland;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.text.Text;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MySprite;
import com.mgl.base.SpriteType;
import com.mgl.base.TextFactory;
import com.mgl.base.userinfo.PlayerSingleton;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.scene.SceneManagerSingleton;
import com.mgl.drop.game.sprites.SpriteBlackBackground;

public class SpriteWinBattle extends MySprite {

	private float contTime = 0f;
	private float timeToAction = 1.5f;
	private boolean isPaused = false;
	
	private int experience = 0;

	public SpriteWinBattle(float pX, float pY,
			ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager,
			LevelController controller) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager,
				controller);

		try {
			this.setAlpha(0);
			this.setSize(MainDropActivity.CAMERA_WIDTH,MainDropActivity.CAMERA_HEIGHT);
			Text text = TextFactory.getWinBattleText(texture.getmFont1());
			text.setPosition(MainDropActivity.CAMERA_WIDTH/2 -text.getWidth()/2,MainDropActivity.CAMERA_HEIGHT/2 -text.getHeight()/2);
			this.attachChild(text);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
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
							//alpha = 1 - alpha;

							if (alpha >= 1) {
								alpha = 1;
							}

							// if(alpha>=1){
							// alpha = 1;
							// }

							getSprite().setAlpha(alpha);
							if (contTime >= timeToAction) {
								
								getSprite().detachSelf();
								SceneManagerSingleton.getInstance().goBackToAdventureMode();
								
								PlayerSingleton.getInstance().increaseExperience(experience);
								
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

	public SpriteWinBattle getSprite() {
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

	public void setExperience(int experience) {
		this.experience = experience;
		
	}

}
