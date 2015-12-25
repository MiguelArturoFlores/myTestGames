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
			Log.d("TOQUYE", "TOQUE");
			switch (pSceneTouchEvent.getAction()) {
			case TouchEvent.ACTION_DOWN:
				p1 = new Point(pSceneTouchEvent.getX(), pSceneTouchEvent.getY());
				break;
			case TouchEvent.ACTION_MOVE:

				break;
			case TouchEvent.ACTION_UP:
				p2 = new Point(pSceneTouchEvent.getX(), pSceneTouchEvent.getY());
				if(p1==null){
					p1 = new Point(pSceneTouchEvent.getX(), pSceneTouchEvent.getY());
					return true;
				}
				if (Point.distanceBetween(p1, p2) > 100) {
					
					float x = p1.getX() -p2.getX();
					float y = p1.getY() - p2.getY();
	
					if(x<0){
						x=x*-1;
					}
					if(y<0){
						y=y*-1;
					}
					boolean isHorizontal = true;
					if(y>x){
						isHorizontal = false;
					}
					
					if(p1.getX()>=p2.getX()){	
						controller.manageSceneTouchAccelerate(-1,isHorizontal);
					}else{
						controller.manageSceneTouchAccelerate(1,isHorizontal);
					}
				} else {
					controller.manageSceneTouch();
				}

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
