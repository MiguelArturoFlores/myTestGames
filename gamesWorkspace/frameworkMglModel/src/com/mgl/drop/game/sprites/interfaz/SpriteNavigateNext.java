package com.mgl.drop.game.sprites.interfaz;

import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.util.Log;

import com.mgl.base.MySprite;
import com.mgl.base.SpriteType;
import com.mgl.drop.factory.SoundSingleton;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.scene.SelectLevelScene;

public class SpriteNavigateNext extends MySprite {

	private SelectLevelScene selectLevelScene;
	private boolean plus = true;

	public SpriteNavigateNext(float pX, float pY,
			ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager,
			SelectLevelScene selectLevelScenel, boolean plus) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager);
		this.selectLevelScene = selectLevelScenel;
		this.plus = plus;
	}

	@Override
	public boolean onAreaTouched(final TouchEvent pSceneTouchEvent,
			final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
		try {
			
		
		switch (pSceneTouchEvent.getAction()) {
		case TouchEvent.ACTION_DOWN:

			if(plus){
				selectLevelScene.setCurrentPage(selectLevelScene.getCurrentPage()+1);
				SoundSingleton.getInstance().playSound("sliderOpen.mp3");
				Log.d("PLUS","plus");
			}else{
				selectLevelScene.setCurrentPage(selectLevelScene.getCurrentPage()-1);
				SoundSingleton.getInstance().playSound("sliderClose.mp3");
				Log.d("minus","minus");
			}
			
			
			selectLevelScene.navigate();
			
			break;
		case TouchEvent.ACTION_MOVE:

			break;
		case TouchEvent.ACTION_UP:

			break;

		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public SpriteType getSpriteType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(float dTime, LevelController level) {
		// TODO Auto-generated method stub

	}

}
