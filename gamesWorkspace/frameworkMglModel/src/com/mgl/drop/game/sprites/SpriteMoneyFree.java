package com.mgl.drop.game.sprites;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MySprite;
import com.mgl.base.SpriteType;
import com.mgl.base.userinfo.OffertGameSingleton;
import com.mgl.drop.factory.SoundSingleton;
import com.mgl.drop.game.controller.LevelController;

public class SpriteMoneyFree extends MySprite {

	
	private float time = 0;
	private float timeToChange = 0.8f;
	private float aplhaSign = -1;
	
	public SpriteMoneyFree(float pX, float pY,
			ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager);
		try {

			//this.setIgnoreUpdate(true);

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	@Override
	public SpriteType getSpriteType() {
		return SpriteType.DECORATIVE;
	}

	@Override
	public void update(float dTime, LevelController level) {

	}

	@Override
	public boolean onAreaTouched(final TouchEvent pSceneTouchEvent,
			final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
		try {
			switch (pSceneTouchEvent.getAction()) {
			case TouchEvent.ACTION_DOWN:

				break;
			case TouchEvent.ACTION_MOVE:

				break;
			case TouchEvent.ACTION_UP:

				SoundSingleton.getInstance().playSound("buttonPress.mp3");
				OffertGameSingleton.getInstance().showOffertFreeMoney();

				break;
			default:

				break;

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	public void setAutoUpdate() {
		try {

			this.registerUpdateHandler(new IUpdateHandler() {
				@Override
				public void reset() {
				}

				@Override
				public void onUpdate(float pSecondsElapsed) {
					// HERE IS THE GAME LOOP
					time += pSecondsElapsed;
					// System.out.println("this is the time elapsed MAIN SCENE: "+time);
					if(time > timeToChange){
						time = 0;
						aplhaSign = aplhaSign * - 1;
					}
					
					getSprite().setAlpha(getSprite().getAlpha() + 0.05f*aplhaSign );
					
					if(getSprite().getAlpha()>1){
						getSprite().setAlpha(1);
						time = 0;
						aplhaSign = aplhaSign * - 1;
					}
					if(getSprite().getAlpha()<0.6f){
						getSprite().setAlpha(0.6f);
						time = 0;
						aplhaSign = aplhaSign * - 1;
					}

				}

			});

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public SpriteMoneyFree getSprite(){
			return this;
	}

}
