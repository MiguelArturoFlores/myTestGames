package com.mgl.drop.game.sprites.thread;

import com.mgl.base.StatusType;

import com.mgl.drop.game.sprites.SpriteMonsterBasic;

public class ThreadShowUmbrella extends Thread {

	private SpriteMonsterBasic monster;
	private double time;

	public ThreadShowUmbrella(SpriteMonsterBasic monster, double seconds) {
		this.monster = monster;
		this.time = seconds;
	}

	public void run() {
		try {

			Thread.sleep((long) (time * 1000));
			monster.setCanShowUmbrella(true);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
