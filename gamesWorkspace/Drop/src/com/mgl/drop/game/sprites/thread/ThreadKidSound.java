package com.mgl.drop.game.sprites.thread;

import com.mgl.base.StatusType;
import com.mgl.drop.factory.SoundSingleton;
import com.mgl.drop.game.sprites.SpriteMonsterBasic;
import com.mgl.drop.game.sprites.SpriteMonsterKid;

public class ThreadKidSound extends Thread {

	SpriteMonsterKid fat;

	public ThreadKidSound(SpriteMonsterKid fat) {
		this.fat = fat;
	}

	public void run(){
		
		try {
			
			if(fat==null){
				return;
			}
			
			Long sleep  = (long) (4f + (Math.random()*1234693343)%3);
			Thread.sleep(sleep*1000);
			
			if(!fat.getStatus().equals(StatusType.NORMAL) || !fat.hasParent()){
				return;
			}
			
			SoundSingleton.getInstance().playSound(fat.getRandomSound());
			
			ThreadKidSound thread = new ThreadKidSound(fat);
			thread.start();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
