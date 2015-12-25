package com.mgl.drop.game.scene;

import java.util.ArrayList;


import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.scene.Scene;

import com.mgl.base.MySpriteGeneral;
import com.mgl.drop.game.entity.arunner.EntityIntroAnimator;
import com.mgl.drop.texture.TextureSingleton;

public class SceneIntro extends Scene {
	
	private float time = 0;
	private ArrayList<MySpriteGeneral> spriteList;
	
	private TextureSingleton texture = TextureSingleton.getInstance();
	
	public SceneIntro() {
		try {
			
			spriteList = new ArrayList<MySpriteGeneral>();
			
			EntityIntroAnimator entity = new EntityIntroAnimator(this);
			spriteList.add(entity);

			updateScene();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void updateScene() {
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
					if (time >= 0.03f) {
						for(MySpriteGeneral spr : spriteList){
							spr.update(time, null);
						}
						time = 0;
					}
					// level.update(pSecondsElapsed, level);
					// game.update(pSecondsElapsed);
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
}
