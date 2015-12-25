package com.mgl.drop.game.sprites.thread;

import com.mgl.base.StatusType;
import com.mgl.drop.factory.SoundSingleton;
import com.mgl.drop.game.sprites.SpriteMonsterFat;

public class ThreadFatSound extends Thread {

	SpriteMonsterFat fat;

	public ThreadFatSound(SpriteMonsterFat fat) {
		this.fat = fat;
	}

	public void run(){
		
		try {
			
			if(fat==null){
				return;
			}
			
			Long sleep  = (long) (6f + (Math.random()*1234693343)%6);
			Thread.sleep(sleep*1000);
			
			if(!fat.getStatus().equals(StatusType.NORMAL) || !fat.hasParent()){
				return;
			}
			
			SoundSingleton.getInstance().playSound(fat.getRandomSound());
			
			ThreadFatSound thread = new ThreadFatSound(fat);
			thread.start();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
