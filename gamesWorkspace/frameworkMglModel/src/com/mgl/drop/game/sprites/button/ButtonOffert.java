package com.mgl.drop.game.sprites.button;

import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MyAnimateProperty;
import com.mgl.base.MyAnimateSprite;
import com.mgl.base.SpriteType;
import com.mgl.drop.factory.ObjectFactorySingleton;
import com.mgl.drop.factory.SoundSingleton;
import com.mgl.drop.game.HUDManagerSingleton;
import com.mgl.drop.game.constant.State;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.hud.OffertHUD;
import com.mgl.drop.game.scene.SceneManagerSingleton;
import com.mgl.drop.game.scene.SceneManagerSingleton.AllScenes;
import com.mgl.drop.texture.TextureSingleton;

public class ButtonOffert extends MyAnimateSprite{


	public ButtonOffert(float pX, float pY, ITextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager,
			LevelController level) {
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager, level);
		try {
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public boolean onAreaTouched(final TouchEvent pSceneTouchEvent,
			final float pTouchAreaLocalX, final float pTouchAreaLocalY) {

		switch (pSceneTouchEvent.getAction()) {
		case TouchEvent.ACTION_DOWN:

			break;
		case TouchEvent.ACTION_MOVE:

			break;
		case TouchEvent.ACTION_UP:

			SoundSingleton.getInstance().playSound("buttonPress.mp3");
			HUDManagerSingleton.getInstance().addHUD(new OffertHUD(),true);
			
			return true;

		}
		return true;
	}



	@Override
	public SpriteType getSpriteType() {
		// TODO Auto-generated method stub
		return SpriteType.DECORATIVE;
	}



	@Override
	public void initAnimationParams() {

		try {

			changeAnimateState(State.WALKIN_DOWN, true);
			anime(true);
			
		} catch (Exception e) {

			e.printStackTrace();
		}
	}



	@Override
	public void initHashMap() {
		try {

			fps = new long[] { 83, 83 };

			stateAnimate.put(State.WALKIN_DOWN,
					new MyAnimateProperty(0, 2, fps));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	@Override
	public void updateAnimated(float dTime, LevelController level) {
		
	}

}