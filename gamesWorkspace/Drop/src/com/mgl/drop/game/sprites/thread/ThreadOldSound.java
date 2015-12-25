package com.mgl.drop.game.sprites.thread;

import com.mgl.base.StatusType;
import com.mgl.drop.factory.SoundSingleton;
import com.mgl.drop.game.sprites.SpriteMonsterBasic;
import com.mgl.drop.game.sprites.SpriteMonsterFat;

public class ThreadOldSound extends Thread {

	SpriteMonsterBasic fat;

	public ThreadOldSound(SpriteMonsterBasic fat) {
		this.fat = fat;
	}

	public void run(){
		
		try {
			
			if(fat==null){
				return;
			}
			
			Long sleep  = (long) (8f + (Math.random()*1234693343)%8);
			Thread.sleep(sleep*1000);
			
			if(!fat.getStatus().equals(StatusType.NORMAL) || !fat.hasParent()){
				return;
			}
			
			SoundSingleton.getInstance().playSound(fat.getRandomSound());
			
			ThreadOldSound thread = new ThreadOldSound(fat);
			thread.start();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}