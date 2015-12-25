package com.mgl.drop.factory;

import java.util.ArrayList;
import java.util.HashMap;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.music.MusicManager;
import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.audio.sound.SoundManager;

import android.util.Log;

import com.mgl.drop.MainDropActivity;
import com.mgl.drop.game.HUDManagerSingleton;
import com.mgl.drop.game.hud.InformativeHUD;

public class SoundSingleton {

	private static SoundSingleton instance = null;

	private SoundManager soundManager;
	private MusicManager musicManager;
	private MainDropActivity mainDropActivity;

	private HashMap<String, Sound> hashMap;
	private HashMap<String, Music> hashMapMusic;
	private boolean hasSound = true;
	private boolean playSoundMusic = true;

	private ArrayList<Music> musicPlaying;

	private SoundSingleton(SoundManager soundManager2,
			MainDropActivity mainDropActivity, MusicManager musicManager) {
		try {

			System.out.println("CREO EL SINGLETON DE sonidos");

			this.soundManager = soundManager2;
			this.mainDropActivity = mainDropActivity;
			this.musicManager = musicManager;

			hasSound = true;
			playSoundMusic = true;

			musicPlaying = new ArrayList<Music>();

			SoundFactory.setAssetBasePath("mfx/");
			MusicFactory.setAssetBasePath("music/");

			hashMap = new HashMap<String, Sound>();

			hashMapMusic = new HashMap<String, Music>();

			loadMusicByName("level1.ogg");
			loadMusicByName("level2.ogg");
			loadMusicByName("level3.ogg");
			loadMusicByName("level4.ogg");
			loadMusicByName("level5.ogg");
			loadMusicByName("level6.ogg");
			loadMusicByName("level7.ogg");
			loadMusicByName("level8.ogg");
			loadMusicByName("level9.ogg");
			loadMusicByName("music.ogg");

			loadSoundByName("playerDead.ogg");

			loadSoundByName("winMusic.mp3");
			loadSoundByName("sliderClose.mp3");
			loadSoundByName("sliderOpen.mp3");

			loadSoundByName("buttonPress.mp3");

			// strore
			loadSoundByName("storeSound1.mp3");
			loadSoundByName("storeSound2.mp3");

			// inGame
			loadSoundByName("checkPoint.mp3");

			loadSoundByName("shot1.mp3");
			loadSoundByName("shot2.mp3");

			loadSoundByName("turbo1.mp3");
			loadSoundByName("turbo2.mp3");

			loadSoundByName("burbleSoft1.mp3");
			loadSoundByName("burbleSoft2.mp3");
			loadSoundByName("burbleSoft3.mp3");

			loadSoundByName("burbleStrong1.mp3");
			loadSoundByName("burbleStrong2.mp3");

			loadSoundByName("enemyDie1.mp3");
			loadSoundByName("enemyDie2.mp3");
			loadSoundByName("enemyDie3.mp3");
			loadSoundByName("enemyDie4.mp3");

			loadSoundByName("meteor1.mp3");
			loadSoundByName("meteor2.mp3");
			loadSoundByName("meteor3.mp3");
			loadSoundByName("meteor4.mp3");

			loadSoundByName("icePower1.mp3");
			loadSoundByName("icePower2.mp3");
			loadSoundByName("icePower3.mp3");
			loadSoundByName("icePower4.mp3");

			loadSoundByName("star1.mp3");
			loadSoundByName("star2.mp3");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void loadMusicByName(String name) {
		try {

			Music s = MusicFactory.createMusicFromAsset(musicManager,
					mainDropActivity, name);
			s.setLooping(true);
			hashMapMusic.put(name, s);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void loadSoundByName(String name) {
		try {

			Sound s = SoundFactory.createSoundFromAsset(soundManager,
					mainDropActivity, name);
			hashMap.put(name, s);

		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	public Sound getSoundByName(String name) {
		try {

			Sound s = SoundFactory.createSoundFromAsset(soundManager,
					mainDropActivity, name);

			return s;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public static SoundSingleton getInstance(SoundManager soundManager,
			MainDropActivity mainDropActivity, MusicManager musicManager) {
		try {

			instance = new SoundSingleton(soundManager, mainDropActivity,
					musicManager);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return instance;

	}

	public static SoundSingleton getInstance() {

		return instance;
	}

	public Sound getSound(String name) {
		try {

			// return SoundFactory.createSoundFromAsset(soundManager,
			// mainDropActivity, name);
			Sound s = hashMap.get(name);
			if (!hasSound) {
				s.setVolume(0);
			} else {
				s.setVolume(100);
			}

			return s;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private Music getMusic(String name) {
		try {

			// return SoundFactory.createSoundFromAsset(soundManager,
			// mainDropActivity, name);
			Music s = hashMapMusic.get(name);
			if (!hasSound) {

				s.setVolume(0);
				Log.d("MIUSIca", "NO TIENE");
			} else {
				Log.d("MIUSIca", "TIENE");
				s.setVolume(50);

			}

			s.setVolume(0.5f);
			return hashMapMusic.get(name);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public SoundManager getSoundManager() {
		return soundManager;
	}

	public void setSoundManager(SoundManager soundManager) {
		this.soundManager = soundManager;
	}

	public MainDropActivity getMainDropActivity() {
		return mainDropActivity;
	}

	public void setMainDropActivity(MainDropActivity mainDropActivity) {
		this.mainDropActivity = mainDropActivity;
	}

	public HashMap<String, Sound> getHashMap() {
		return hashMap;
	}

	public void setHashMap(HashMap<String, Sound> hashMap) {
		this.hashMap = hashMap;
	}

	public boolean isHasSound() {
		return hasSound;
	}

	public void playMusic(String name, boolean ignoreSame) {
		try {
			Music m = getMusic(name);

			for (Music mm : musicPlaying) {
				if (m.equals(mm) && !ignoreSame) {
					break;
				}
				mm.seekTo(0);
				mm.pause();
			}

			musicPlaying = new ArrayList<Music>();
			musicPlaying.add(m);

			setSilenceMusic(!hasSound);
			
			if (!hasSound || !playSoundMusic) {
				return;
			}
			
			//HUDManagerSingleton.getInstance().addHUD(new InformativeHUD("play"), true);
			m.play();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void stopMusic(String name) {
		try {

			Music m = getMusic(name);
			m.pause();
			m.seekTo(0);
			musicPlaying.remove(m);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setHasSound(boolean hasSound) {
		try {

			this.hasSound = hasSound;

			for (Music m : musicPlaying) {
				if (hasSound) {
					m.setVolume(100);
					m.play();
				} else {

					m.pause();
					m.seekTo(0);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void setInstance(SoundSingleton instance) {
		SoundSingleton.instance = instance;
	}

	public void playSound(String name) {
		try {

			Sound sound = SoundSingleton.getInstance().getSound(name);

			if (sound == null || !playSoundMusic) {

				return;
			}

			sound.play();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static int getRandomMax(int min, int max) {

		try {

			Double val = min + ((Math.random() * 123456323) % (1 + max - min));
			return val.intValue();

		} catch (Exception e) {

			e.printStackTrace();

		}

		return 1;
	}

	public void playStoreSound() {
		try {

			int val = getRandomMax(1, 2);
			playSound("storeSound" + val + ".mp3");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean isPlaySoundMusic() {
		return playSoundMusic;
	}

	public void setPlaySoundMusic(boolean playSoundMusic) {
		try {

			this.playSoundMusic = playSoundMusic;

			if (playSoundMusic) {
				setHasSound(hasSound);
			} else {
				for (Music m : musicPlaying) {
					m.pause();
					m.seekTo(0);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void stopCurrentMusic() {
		try {

			for (Music mm : musicPlaying) {
				// mm.seekTo(0);
				mm.pause();
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public void playCurrentMusic() {
		try {

			for (Music mm : musicPlaying) {
				// mm.seekTo(0);
				mm.play();
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public void playTrophySound() {

	}

	public void playButtonSound() {
		// TODO Auto-generated method stub

	}

	public void playPlayerDead() {
		try {

			playSound("playerDead.ogg");
		} catch (Exception e) {

		}
	}

	public void playCheckpoint() {
		try {

			playSound("checkPoint.mp3");
		} catch (Exception e) {

		}
	}

	public void playShot() {
		try {

			int val = getRandomMax(1, 2);
			playSound("shot" + val + ".mp3");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void playTurbo() {
		try {

			int val = getRandomMax(1, 2);
			playSound("turbo" + val + ".mp3");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void playBurbleSoft() {
		try {

			int val = getRandomMax(1, 3);
			playSound("burbleSoft" + val + ".mp3");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void playBurbleStrong() {
		try {

			int val = getRandomMax(1, 2);
			playSound("burbleStrong" + val + ".mp3");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void playEnemyDie() {
		try {

			int val = getRandomMax(1, 4);
			playSound("enemyDie" + val + ".mp3");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void playIcePower() {
		try {

			int val = getRandomMax(1, 4);
			playSound("icePower" + val + ".mp3");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void playStar() {
		try {

			int val = getRandomMax(1, 2);
			playSound("star" + val + ".mp3");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void playMeteor() {
		try {

			int val = getRandomMax(1, 4);
			playSound("meteor" + val + ".mp3");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setSilenceMusic(boolean silence) {
		try {

			for (Music m : musicPlaying) {
				if (silence) {
					m.setVolume(0);
					this.hasSound = false;
				} else {
					m.setVolume(1);
					this.hasSound = true;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
