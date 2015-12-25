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
			playSoundMusic= true;
			
			musicPlaying = new ArrayList<Music>();
			
			SoundFactory.setAssetBasePath("mfx/");
			MusicFactory.setAssetBasePath("music/");

			hashMap = new HashMap<String, Sound>();

			hashMapMusic = new HashMap<String, Music>();

			loadMusicByName("music.ogg");
			loadMusicByName("backgroundSound1.ogg");
			
			loadSoundByName("playerRelease.mp3");
			
			loadSoundByName("winMusic.mp3");
			loadSoundByName("sliderClose.mp3");
			loadSoundByName("sliderOpen.mp3");

			loadSoundByName("buttonPress1.mp3");
			loadSoundByName("buttonPress2.mp3");
			
			// strore
			
			loadSoundByName("adn.mp3");
			
			//inGame
			loadSoundByName("playerDead1.mp3");
			loadSoundByName("playerDead2.mp3");
			
			loadSoundByName("checkpoint1.mp3");
			loadSoundByName("checkpoint2.mp3");
			loadSoundByName("checkpoint3.mp3");
			
			loadSoundByName("shot1.mp3");
			loadSoundByName("shot2.mp3");
			
			loadSoundByName("shot1.mp3");
			loadSoundByName("shot2.mp3");
			
			loadSoundByName("turbo1.mp3");
			loadSoundByName("turbo2.mp3");

			loadSoundByName("burbles.mp3");
			
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
			
			loadSoundByName("metallicCollition.mp3");
			loadSoundByName("virusCollition.mp3");
			
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

	public void playMusic(String name,boolean ignoreSame) {
		try {

			Music m = getMusic(name);
			
			for(Music mm : musicPlaying){
				if(m.equals(mm) && !ignoreSame){return;}
				mm.seekTo(0);
				mm.pause();
			}
			
			musicPlaying = new ArrayList<Music>();
			musicPlaying.add(m);
			
			if(!hasSound || !playSoundMusic){
				return;
			}
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
				if(hasSound){
					m.setVolume(100);
					m.play();
				}else{
					
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

	public  void playSound(String name) {
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

			if(playSoundMusic){
				setHasSound(hasSound);
			}
			else{
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
			
			for(Music mm : musicPlaying){
				mm.seekTo(0);
				mm.pause();
			}
			
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public void playTrophySound() {
		
	}

	public void playButtonSound() {
		try {
			int val = getRandomMax(1, 2);
			playSound("buttonPress"+val+".mp3");
		} catch (Exception e) {
		
		}
	}
	

	public void playPlayerDead(){
		try {
			int val = getRandomMax(1, 2);
			playSound("playerDead"+val+".mp3");
		} catch (Exception e) {

		}
	}
	
	public void playCheckpoint(){
		try {
			int val = getRandomMax(1, 3);
			playSound("checkpoint"+val+".mp3");
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

			playSound("burbles.mp3");

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

	public void playADN() {
		try {
			playSound("adn.mp3");
		} catch (Exception e) {

		}
	}

	public void playPlayerRelease() {
		try {
			playSound("playerRelease.mp3");
		} catch (Exception e) {

		}
		
	}

	public void playMetallicCollition() {
		
		try {
			playSound("metallicCollition.mp3");
		} catch (Exception e) {

		}
	}

	public void playVirusCollition() {
		
		try {
			playSound("virusCollition.mp3");
		} catch (Exception e) {

		}
	}
	
}
