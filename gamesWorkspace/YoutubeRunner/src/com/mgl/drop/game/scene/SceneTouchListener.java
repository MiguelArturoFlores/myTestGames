package com.mgl.drop.game.scene;

import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.input.touch.TouchEvent;

import android.util.Log;

import com.mgl.base.StatusType;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.factory.SoundSingleton;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.entity.EntityScore;
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
				controller.getPlayer().jump();
				break;
			case TouchEvent.ACTION_MOVE:

				break;
			case TouchEvent.ACTION_UP:
				controller.getPlayer().stopJump();;
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
