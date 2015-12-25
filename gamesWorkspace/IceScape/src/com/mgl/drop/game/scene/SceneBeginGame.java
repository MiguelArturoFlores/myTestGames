package com.mgl.drop.game.scene;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.scene.Scene;

import com.mgl.drop.game.controller.LevelController;

public class SceneBeginGame extends Scene{

	private LevelController level;
	
	public SceneBeginGame(){
		try {
			
			level = new LevelController(this);
			
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
					// System.out.println("this is the time elapsed MAIN SCENE: "+time);
					//if (time >= 0.01) {
						//Log.d("Seconds", pSecondsElapsed+"  "+time);
						level.update(pSecondsElapsed);
					//}
					// level.update(pSecondsElapsed, level);
					// game.update(pSecondsElapsed);
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
}
