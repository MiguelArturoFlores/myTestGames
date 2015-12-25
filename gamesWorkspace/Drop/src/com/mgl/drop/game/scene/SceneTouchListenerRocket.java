package com.mgl.drop.game.scene;

import java.util.ArrayList;
import java.util.Stack;

import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.input.touch.TouchEvent;

import android.util.Log;

import com.mgl.drop.game.sprites.SpritePoopRocket;
import com.mgl.drop.util.Point;

public class SceneTouchListenerRocket implements IOnSceneTouchListener {

	private ArrayList<SpritePoopRocket> rocketList;

	public SceneTouchListenerRocket(SpritePoopRocket poop) {
		try {

			rocketList = new ArrayList<SpritePoopRocket>();
			rocketList.add(poop);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {

		try {

			if(rocketList.isEmpty()){
				return false;
			}
			
			switch (pSceneTouchEvent.getAction()) {
			case TouchEvent.ACTION_DOWN:
				
					//rocketList.get(0).setBeginPoint(new Point(pSceneTouchEvent.getX(), pSceneTouchEvent.getY()));
				
				break;
			case TouchEvent.ACTION_MOVE:
				
					

				break;
			case TouchEvent.ACTION_UP:

				SpritePoopRocket rocket = rocketList.get(0);
				
				rocket.setEndPoint(new Point(pSceneTouchEvent.getX(), pSceneTouchEvent.getY()));
				
				rocket.activate();
				rocketList.remove(rocket);
				
				if(rocketList.isEmpty()){
					Log.d("MISIL ", "No hay misiles");
					pScene.setOnSceneTouchListener(null);
				}
				
				break;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;

	}

	public void addRocket(SpritePoopRocket spritePoopRocket) {
		try {

			if (rocketList == null) {
				rocketList = new ArrayList<SpritePoopRocket>();
			}
			rocketList.add(spritePoopRocket);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
