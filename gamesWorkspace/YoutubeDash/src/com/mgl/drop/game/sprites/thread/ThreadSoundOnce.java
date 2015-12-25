package com.mgl.drop.game.sprites.thread;

import com.mgl.drop.factory.SoundSingleton;

public class ThreadSoundOnce extends Thread {
	
	private String name;
	private Float time;
	
	public ThreadSoundOnce(String name, Float time) {
		super();
		this.name = name;
		this.time = time;
	}
	
	@Override
	public void run(){
		try {
			
			Thread.sleep(time.longValue()*1000);
			if(name==null || name.isEmpty()){
				return;
			}
			
			SoundSingleton.getInstance().playSound(name);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
