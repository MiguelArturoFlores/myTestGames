package com.mgl.drop.game.scene;

import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.input.touch.TouchEvent;

import com.mgl.drop.MainDropActivity;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.util.Point;

public class SceneTouchListener implements IOnSceneTouchListener {

	private LevelController controller;
	private Point p1;
	private Point p2;

	public SceneTouchListener(LevelController levelController) {

		this.controller = levelController;

	}

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		try {
			switch (pSceneTouchEvent.getAction()) {
			case TouchEvent.ACTION_DOWN:
				
				if(pSceneTouchEvent.getX()>MainDropActivity.CAMERA_WIDTH/2){
					controller.getPlayer().mooveRight();
				}else{
					controller.getPlayer().mooveLeft();
				}
				
				break;
			case TouchEvent.ACTION_MOVE:

				break;
			case TouchEvent.ACTION_UP:
				
				controller.getPlayer().releaseMovement(); 
				
				break;
			default:

				break;

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

}
