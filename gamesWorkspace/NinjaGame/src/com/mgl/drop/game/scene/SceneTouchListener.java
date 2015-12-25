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

	private boolean battle = false;
	
	public SceneTouchListener(LevelController levelController) {

		this.controller = levelController;
		battle = false;
		
	}

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		try {
			switch (pSceneTouchEvent.getAction()) {
			case TouchEvent.ACTION_DOWN:
				if(battle){
					//controller.manageSceneTouchBattle(pSceneTouchEvent.getX(),pSceneTouchEvent.getY());
					return true;
				}
				controller.manageSceneTouch(pSceneTouchEvent.getX(),pSceneTouchEvent.getY());
				break;
			case TouchEvent.ACTION_MOVE:

				break;
			case TouchEvent.ACTION_UP:
				
				break;
			default:

				break;

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	public LevelController getController() {
		return controller;
	}

	public void setController(LevelController controller) {
		this.controller = controller;
	}

	public Point getP1() {
		return p1;
	}

	public void setP1(Point p1) {
		this.p1 = p1;
	}

	public Point getP2() {
		return p2;
	}

	public void setP2(Point p2) {
		this.p2 = p2;
	}

	public boolean isBattle() {
		return battle;
	}

	public void setBattle(boolean battle) {
		this.battle = battle;
	}

	
	
}
