package com.mgl.drop.game.sprites.thread;

import com.mgl.base.StatusType;
import com.mgl.drop.factory.SoundSingleton;
import com.mgl.drop.game.sprites.SpriteMonsterFat;
import com.mgl.drop.game.sprites.SpriteMonsterRunner;

public class ThreadRunnerSound extends Thread {

	SpriteMonsterRunner fat;

	public ThreadRunnerSound(SpriteMonsterRunner fat) {
		this.fat = fat;
	}

	public void run(){
		
		try {
			
			if(fat==null){
				return;
			}
			
			Long sleep  = (long) (10f + (Math.random()*1234693343)%10);
			Thread.sleep(sleep*1000);
			
			if(!fat.getStatus().equals(StatusType.NORMAL) || !fat.hasParent() ){
				return;
			}
			
			SoundSingleton.getInstance().playSound(fat.getRandomSound());
			
			ThreadRunnerSound thread = new ThreadRunnerSound(fat);
			thread.start();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
