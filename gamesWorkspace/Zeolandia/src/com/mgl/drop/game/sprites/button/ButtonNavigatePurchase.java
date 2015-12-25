package com.mgl.drop.game.sprites.button;

import java.util.ArrayList;

import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MySprite;
import com.mgl.base.SpriteType;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.factory.SoundSingleton;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.scene.SceneManagerSingleton;
import com.mgl.drop.game.scene.SceneManagerSingleton.AllScenes;

public class ButtonNavigatePurchase extends MySprite {

	private ArrayList<MySprite> spriteList;
	private boolean isNext = false;

	public ButtonNavigatePurchase(float pX, float pY,
			ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager,
			ArrayList<MySprite> spriteList, boolean isNext) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager);

		try {
			this.spriteList = spriteList;
			this.isNext = isNext;
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
		// TODO Auto-generated method stub

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

				boolean canIChange = false;
				
				for (MySprite spr : spriteList) {
					if (isNext) {
						if (spr.getX()>MainDropActivity.CAMERA_WIDTH ) {
							canIChange = true;
							break;
						}
					}else{
						
						if (spr.getX()<0 ) {
							canIChange = true;
							break;
						}
					}
				}

				if(!canIChange){
					return true;
				}
				
				SoundSingleton.getInstance().playSound("buttonPress.mp3");
				for (MySprite spr : spriteList) {
					if (isNext) {

						spr.setPosition(spr.getX()
								- MainDropActivity.CAMERA_WIDTH, spr.getY());
					} else {

						spr.setPosition(spr.getX()
								+ MainDropActivity.CAMERA_WIDTH, spr.getY());
					}
				}
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

}
