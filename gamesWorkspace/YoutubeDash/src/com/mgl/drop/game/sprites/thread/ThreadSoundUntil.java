package com.mgl.drop.game.sprites.thread;

import org.andengine.audio.sound.Sound;

import com.mgl.drop.factory.SoundSingleton;

public class ThreadSoundUntil extends Thread {

	private String name;
	private Float time;

	public ThreadSoundUntil(String name, Float time) {
		super();
		this.name = name;
		this.time = time;
	}

	@Override
	public void run() {
		try {

			if (name == null || name.isEmpty()) {
				return;
			}

			Sound sound = SoundSingleton.getInstance().getSound(name);
			if (sound == null) {

				return;
			}
			//sound.setLooping(true);
			sound.play();

			Thread.sleep(time.longValue()*1000);
			//sound.setVolume(0);
			return;

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
