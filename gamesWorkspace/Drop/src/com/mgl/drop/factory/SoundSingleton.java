package com.mgl.drop.factory;

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

	private SoundSingleton(SoundManager soundManager2,
			MainDropActivity mainDropActivity, MusicManager musicManager) {
		try {

			System.out.println("CREO EL SINGLETON DE sonidos");

			this.soundManager = soundManager2;
			this.mainDropActivity = mainDropActivity;
			this.musicManager = musicManager;
			
			hasSound = true;

			SoundFactory.setAssetBasePath("mfx/");
			MusicFactory.setAssetBasePath("music/");

			hashMap = new HashMap<String, Sound>();
			
			hashMapMusic = new HashMap<String, Music>();
			
			loadMusicByName("music.mp3");
			loadMusicByName("backgroundSound.mp3");
			

			for(int i =1; i<=17;i++){
				loadSoundByName("Cagada"+i+".mp3");
			}
			
			//fat
			for(int i =1; i<=5;i++){
				loadSoundByName("fatReact"+i+".mp3");
			}
			
			for(int i =1; i<=5;i++){
				loadSoundByName("fatNormal"+i+".mp3");
			}
			
			//runner
			for(int i =1; i<=3;i++){
				loadSoundByName("runnerReact"+i+".mp3");
			}
			
			for(int i =1; i<=5;i++){
				loadSoundByName("runnerNormal"+i+".mp3");
			}
			
			//old
			for(int i =1; i<=3;i++){
				loadSoundByName("oldReact"+i+".mp3");
			}
			
			for(int i =1; i<=7;i++){
				loadSoundByName("oldNormal"+i+".mp3");
			}
			//kid
			for(int i =1; i<=4;i++){
				loadSoundByName("kidReact"+i+".mp3");
			}
			
			for(int i =1; i<=4;i++){
				loadSoundByName("kidNormal"+i+".mp3");
			}
			
			loadSoundByName("winMusic.mp3");
			loadSoundByName("looseMusic.mp3");
			
			loadSoundByName("star1.mp3");
			loadSoundByName("star2.mp3");
			loadSoundByName("star3.mp3");
			
			loadSoundByName("tictac.mp3");
			
			loadSoundByName("sliderOpen.mp3");
			loadSoundByName("sliderClose.mp3");
			
			loadSoundByName("zoomIn.mp3");
			loadSoundByName("zoomOut.mp3");
			
			loadSoundByName("liga.mp3");
			loadSoundByName("throwingRock.mp3");
			
			loadSoundByName("buttonPress.mp3");
			loadSoundByName("explosion.mp3");
			loadSoundByName("flyingRocket.mp3");
			loadSoundByName("poopReactOld.mp3");
			
			
			loadSoundByName("justinNormal.mp3");
			loadSoundByName("justinPoop.mp3");
			loadSoundByName("justinPoop1.mp3");
			loadSoundByName("justinPoop2.mp3");
			
			loadSoundByName("mileyNormal.mp3");
			loadSoundByName("mileyPoop.mp3");
			loadSoundByName("mileyPoop1.mp3");
			loadSoundByName("mileyPoop2.mp3");
			
			loadSoundByName("rockExplosion.mp3");
			
			loadSoundByName("split.mp3");
			
			loadSoundByName("ovniExploding.mp3");
			loadSoundByName("ovniNormal.mp3");
			
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

			instance = new SoundSingleton(soundManager, mainDropActivity, musicManager);

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
			if(!hasSound){
				s.setVolume(0);
			}else{
				s.setVolume(100);
			}
			
			return s;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public Music getMusic(String name) {
		try {

			// return SoundFactory.createSoundFromAsset(soundManager,
			// mainDropActivity, name);
			Music s = hashMapMusic.get(name);
			if(!hasSound){
				
				s.setVolume(0);
				Log.d("MIUSIca","NO TIENE");
			}else{
				Log.d("MIUSIca","TIENE");
				s.setVolume(100);
				
			}
			
			//s.setVolume(0);
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

	public void setHasSound(boolean hasSound) {
		this.hasSound = hasSound;
	}

	public static void setInstance(SoundSingleton instance) {
		SoundSingleton.instance = instance;
	}

	public static void playSound(String name){
		try {
			
			Sound sound = SoundSingleton.getInstance().getSound(name);
			
			if(sound==null){
			
				return ;
			}
			
			sound.play();
			
		} catch (Exception e) {
			e.printStackTrace() 
			;
		}
	}
	
}
