package com.mgl.drop.game.sprites.thread;

import com.mgl.drop.game.sprites.SpriteMonsterKid;

public class ThreadReloadShoot extends Thread{

	private SpriteMonsterKid monster ;
	public ThreadReloadShoot(SpriteMonsterKid monster){
		try {
			
			this.monster=monster;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void run(){
		try {
			
			Thread.sleep(5000);
			monster.setCanShoot(true);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
