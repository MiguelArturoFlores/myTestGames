package com.mgl.drop.game.sprites;

import java.util.ArrayList;

import org.andengine.engine.camera.SmoothCamera;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.util.Log;

import com.mgl.drop.factory.SoundSingleton;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.database.model.Level;
import com.mgl.drop.game.scene.SceneManagerSingleton;
import com.mgl.drop.game.scene.SceneManagerSingleton.AllScenes;

public class SpriteZoomCamera extends Sprite {

	private boolean isIn = true;

	private LevelController level;
	
	public SpriteZoomCamera(float pX, float pY,
			ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager, boolean isIn, LevelController level) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager);
		this.isIn = isIn;
		this.level = level;
		
		
	}

	@Override
	public boolean onAreaTouched(final TouchEvent pSceneTouchEvent,
			final float pTouchAreaLocalX, final float pTouchAreaLocalY) {

		switch (pSceneTouchEvent.getAction()) {
		case TouchEvent.ACTION_DOWN:

			
			
			
			
			if(isIn){
				
				SceneManagerSingleton.getInstance().zoomIn(level);
				SoundSingleton.getInstance().playSound("zoomIn.mp3");
				
				
			}else{
				
				SceneManagerSingleton.getInstance().zoomOut(level);
				SoundSingleton.getInstance().playSound("zoomOut.mp3");
				
			}
			
			
			
			break;
		case TouchEvent.ACTION_MOVE:

			break;
		case TouchEvent.ACTION_UP:

			break;
		}
		return true;
	}

	
}
