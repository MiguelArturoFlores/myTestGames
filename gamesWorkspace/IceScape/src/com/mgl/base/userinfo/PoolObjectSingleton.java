package com.mgl.base.userinfo;


import java.util.LinkedList;
import java.util.Queue;

import com.mgl.base.MySpriteGeneral;
import com.mgl.drop.game.sprites.aicerunner.SpriteSnow;
import com.mgl.drop.game.sprites.aicerunner.SpriteSnowFloor;
import com.mgl.drop.texture.TextureSingleton;

public class PoolObjectSingleton {

	private static PoolObjectSingleton instance = null;

	private Queue<MySpriteGeneral> snowList;
	private Queue<MySpriteGeneral> snowFloorList;

	private PoolObjectSingleton() {
		try {
			TextureSingleton texture = TextureSingleton.getInstance();

			snowList = new LinkedList<MySpriteGeneral>();
			for (int i = 0; i < 200; i++) {
				SpriteSnow snow = new SpriteSnow(0, 0, texture.getTextureByName("snow.png"), texture.getVertexBufferObjectManager());
				snowList.add(snow);
			}

			snowFloorList = new LinkedList<MySpriteGeneral>();
			for (int i = 0; i < 200; i++) {
				SpriteSnowFloor snow = new SpriteSnowFloor(0, 0, texture.getTextureByName("snow.png"), texture.getVertexBufferObjectManager());
				snowFloorList.add(snow);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static PoolObjectSingleton getInstance() {
		try {

			if (instance == null) {
				instance = new PoolObjectSingleton();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return instance;
	}

	public MySpriteGeneral getSnow(){
		try {
			
			MySpriteGeneral spr = snowList.poll();
			snowList.add(spr);
			return spr;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public MySpriteGeneral getSnowFloor(){
		try {
			
			MySpriteGeneral spr = snowFloorList.poll();
			snowFloorList.add(spr);
			return spr;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
