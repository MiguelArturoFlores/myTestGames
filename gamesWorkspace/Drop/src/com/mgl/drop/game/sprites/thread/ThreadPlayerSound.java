package com.mgl.drop.game.sprites.thread;

import com.mgl.drop.game.sprites.SpritePlayer;

public class ThreadPlayerSound extends Thread {

	private SpritePlayer player;
	private boolean mustContinue = true;

	public ThreadPlayerSound(SpritePlayer player) {
		super();
		this.player = player;
	}

	public void run (){
		try {
			
			while(mustContinue){
				
				
				
				Thread.sleep(2000);
				if(mustContinue){
					player.beginFlying();
				}
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public SpritePlayer getPlayer() {
		return player;
	}

	public void setPlayer(SpritePlayer player) {
		this.player = player;
	}

	public boolean isMustContinue() {
		return mustContinue;
	}

	public void setMustContinue(boolean mustContinue) {
		this.mustContinue = mustContinue;
	}
	


}
