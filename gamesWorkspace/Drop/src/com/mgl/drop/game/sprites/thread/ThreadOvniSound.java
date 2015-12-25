package com.mgl.drop.game.sprites.thread;

import com.mgl.base.StatusType;
import com.mgl.drop.factory.SoundSingleton;
import com.mgl.drop.game.sprites.SpriteMonsterFat;
import com.mgl.drop.game.sprites.SpriteOvni;

public class ThreadOvniSound  extends Thread {

	SpriteOvni fat;

	public ThreadOvniSound(SpriteOvni fat) {
		this.fat = fat;
	}

	public void run(){
		
		try {
			
			if(fat==null){
				return;
			}
			
			Long sleep  = (long) (3f + (Math.random()*1234693343)%6);
			Thread.sleep(sleep*1000);
			
			if(!fat.getStatus().equals(StatusType.NORMAL) || !fat.hasParent()){
				return;
			}
			
			SoundSingleton.getInstance().playSound("ovniNormal.mp3");
			
			ThreadOvniSound thread = new ThreadOvniSound(fat);
			thread.start();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
