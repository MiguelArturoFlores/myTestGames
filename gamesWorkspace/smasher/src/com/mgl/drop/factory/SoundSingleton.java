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

			loadMusicByName("music.mp3");
			loadMusicByName("backgroundSound1.mp3");
			loadMusicByName("bossMusic.mp3");
			
			loadSoundByName("laught.mp3");
			loadSoundByName("finalWave.mp3");
			
			loadSoundByName("looseMusic.mp3");
			loadSoundByName("winMusic.mp3");
			loadSoundByName("sliderClose.mp3");
			loadSoundByName("sliderOpen.mp3");

			loadSoundByName("buttonPress.mp3");
			
			//without power
			loadSoundByName("out1.mp3");
			loadSoundByName("out2.mp3");
			loadSoundByName("out3.mp3");
			loadSoundByName("out4.mp3");
			
			//trophy
			loadSoundByName("Trofeo1.mp3");
			loadSoundByName("Trofeo2.mp3");
			loadSoundByName("Trofeo3.mp3");
			
			//diamant sound
			loadSoundByName("Diamante_1.mp3");
			loadSoundByName("Diamante_2.mp3");
			loadSoundByName("Diamante_3.mp3");
			loadSoundByName("Diamante_4.mp3");
			loadSoundByName("Diamante_5.mp3");

			
			// buttonSound
			loadSoundByName("buttonSound1.mp3");
			loadSoundByName("buttonSound2.mp3");
			loadSoundByName("buttonSound3.mp3");
			loadSoundByName("buttonSound4.mp3");
			loadSoundByName("buttonSound5.mp3");

			// sm,ashg
			loadSoundByName("smash1.mp3");
			loadSoundByName("smash2.mp3");
			loadSoundByName("smash3.mp3");
			loadSoundByName("smash4.mp3");
			
			//julian
			loadSoundByName("julian1.mp3");
			loadSoundByName("julian2.mp3");
			loadSoundByName("julianRun1.mp3");
			loadSoundByName("julianRun2.mp3");
			loadSoundByName("julianWin1.mp3");
			loadSoundByName("julianWin2.mp3");
			

			// normal Vampire
			loadSoundByName("normalV1.mp3");
			loadSoundByName("normalV2.mp3");
			// dead
			loadSoundByName("Vampiro_Muerto1.mp3");
			loadSoundByName("Vampiro_Muerto2.mp3");
			loadSoundByName("Vampiro_Muerto3.mp3");
			loadSoundByName("Vampiro_Muerto4.mp3");
			loadSoundByName("Vampiro_Muerto5.mp3");

			// human win
			loadSoundByName("Win_humana1.mp3");
			loadSoundByName("Win_humana2.mp3");
			loadSoundByName("Win_humana3.mp3");
			loadSoundByName("Win_humana4.mp3");
			loadSoundByName("Win_humana5.mp3");
			
			//human run 
			loadSoundByName("humanRun1.mp3");
			loadSoundByName("humanRun2.mp3");
			loadSoundByName("humanRun3.mp3");
			loadSoundByName("humanRun4.mp3");
			loadSoundByName("humanRun5.mp3");

			// dead human
			loadSoundByName("Humana_muerta1.mp3");
			loadSoundByName("Humana_muerta2.mp3");
			loadSoundByName("Humana_muerta3.mp3");
			loadSoundByName("Humana_muerta4.mp3");
			loadSoundByName("Humana_muerta5.mp3");

			// armor normal sound
			loadSoundByName("Gordo_CaballeroV_Normal1.mp3");
			loadSoundByName("Gordo_CaballeroV_Normal2.mp3");
			loadSoundByName("Gordo_CaballeroV_Normal3.mp3");
			loadSoundByName("Gordo_CaballeroV_Normal4.mp3");
			loadSoundByName("Gordo_CaballeroV_Normal5.mp3");
			loadSoundByName("Gordo_CaballeroV_Normal6.mp3");
			loadSoundByName("Gordo_CaballeroV_Normal7.mp3");
			loadSoundByName("Gordo_CaballeroV_Normal8.mp3");
			loadSoundByName("Gordo_CaballeroV_Normal9.mp3");
			loadSoundByName("Gordo_CaballeroV_Normal10.mp3");
			loadSoundByName("Gordo_CaballeroV_Normal11.mp3");

			// gordo normal
			loadSoundByName("Gordo_Normal1.mp3");
			loadSoundByName("Gordo_Normal2.mp3");
			loadSoundByName("Gordo_Normal3.mp3");
			loadSoundByName("Gordo_Normal4.mp3");
			loadSoundByName("Gordo_Normal5.mp3");
			loadSoundByName("Gordo_Normal6.mp3");
			loadSoundByName("Gordo_Normal7.mp3");
			loadSoundByName("Gordo_Normal8.mp3");
			loadSoundByName("Gordo_Normal9.mp3");
			loadSoundByName("Gordo_Normal10.mp3");
			loadSoundByName("Gordo_Normal11.mp3");

			// gordo dead
			loadSoundByName("Gordo_Muerto1.mp3");
			loadSoundByName("Gordo_Muerto2.mp3");
			loadSoundByName("Gordo_Muerto3.mp3");
			loadSoundByName("Gordo_Muerto4.mp3");
			loadSoundByName("Gordo_Muerto5.mp3");
			loadSoundByName("Gordo_Muerto6.mp3");
			loadSoundByName("Gordo_Muerto7.mp3");
			loadSoundByName("Gordo_Muerto8.mp3");
			loadSoundByName("Gordo_Muerto9.mp3");

			// vampieresa normal

			loadSoundByName("Vampiresa_Normal1.mp3");
			loadSoundByName("Vampiresa_Normal2.mp3");
			loadSoundByName("Vampiresa_Normal3.mp3");
			loadSoundByName("Vampiresa_Normal4.mp3");
			loadSoundByName("Vampiresa_Normal5.mp3");
			loadSoundByName("Vampiresa_Normal6.mp3");

			// vampiresa dead
			loadSoundByName("Vampiresa_Muerta1.mp3");
			loadSoundByName("Vampiresa_Muerta2.mp3");
			loadSoundByName("Vampiresa_Muerta3.mp3");
			loadSoundByName("Vampiresa_Muerta4.mp3");
			loadSoundByName("Vampiresa_Muerta5.mp3");
			loadSoundByName("Vampiresa_Muerta6.mp3");

			// vampire hole
			loadSoundByName("Vampiro_Cubierto_Normal1.mp3");
			loadSoundByName("Vampiro_Cubierto_Normal2.mp3");
			loadSoundByName("Vampiro_Cubierto_Normal3.mp3");
			loadSoundByName("Vampiro_Cubierto_Normal4.mp3");
			loadSoundByName("Vampiro_Cubierto_Normal5.mp3");
			loadSoundByName("Vampiro_Cubierto_Normal6.mp3");

			// vampire hole dead

			loadSoundByName("Vampiro_Cubierto_Muerto1.mp3");
			loadSoundByName("Vampiro_Cubierto_Muerto2.mp3");
			loadSoundByName("Vampiro_Cubierto_Muerto3.mp3");
			loadSoundByName("Vampiro_Cubierto_Muerto4.mp3");

			// eating fat
			loadSoundByName("Gordo_comiendo1.mp3");
			loadSoundByName("Gordo_comiendo2.mp3");
			loadSoundByName("Gordo_comiendo3.mp3");
			loadSoundByName("Gordo_comiendo4.mp3");
			loadSoundByName("Gordo_comiendo5.mp3");
			loadSoundByName("Gordo_comiendo6.mp3");

			// vampiresa comiendo
			loadSoundByName("Vampiresa_Comiendo1.mp3");
			loadSoundByName("Vampiresa_Comiendo2.mp3");
			loadSoundByName("Vampiresa_Comiendo3.mp3");
			loadSoundByName("Vampiresa_Comiendo4.mp3");
			loadSoundByName("Vampiresa_Comiendo5.mp3");
			loadSoundByName("Vampiresa_Comiendo6.mp3");

			// normal bat eating
			loadSoundByName("Vampiro_Comiendo1.mp3");
			loadSoundByName("Vampiro_Comiendo2.mp3");
			loadSoundByName("Vampiro_Comiendo3.mp3");
			loadSoundByName("Vampiro_Comiendo4.mp3");
			loadSoundByName("Vampiro_Comiendo5.mp3");
			loadSoundByName("Vampiro_Comiendo6.mp3");
			loadSoundByName("Vampiro_Comiendo7.mp3");
			loadSoundByName("Vampiro_Comiendo8.mp3");

			// bat eating
			loadSoundByName("VampiroMurcie_comiendo1.mp3");
			loadSoundByName("VampiroMurcie_comiendo2.mp3");
			loadSoundByName("VampiroMurcie_comiendo3.mp3");
			loadSoundByName("VampiroMurcie_comiendo4.mp3");
			loadSoundByName("VampiroMurcie_comiendo5.mp3");
			loadSoundByName("VampiroMurcie_comiendo6.mp3");

			// armor
			loadSoundByName("armor1.mp3");
			loadSoundByName("armor2.mp3");

			// normal bat
			loadSoundByName("normalMurcielago1.mp3");
			loadSoundByName("normalMurcielago2.mp3");
			loadSoundByName("normalMurcielago3.mp3");
			loadSoundByName("normalMurcielago4.mp3");
			loadSoundByName("normalMurcielago5.mp3");
			
			//boss hit
			loadSoundByName("bossHit1.mp3");
			loadSoundByName("bossHit2.mp3");
			loadSoundByName("bossHit3.mp3");
			loadSoundByName("bossHit4.mp3");
			loadSoundByName("bossHit5.mp3");
			loadSoundByName("bossHit6.mp3");
			loadSoundByName("bossHit7.mp3");

			//boss Normal
			loadSoundByName("bossNormal1.mp3");
			loadSoundByName("bossNormal.mp3");
			loadSoundByName("bossNormal3.mp3");
			loadSoundByName("bossNormal4.mp3");
			loadSoundByName("bossNormal5.mp3");
			
			// powers
			loadSoundByName("trunk1.mp3");
			loadSoundByName("trunk2.mp3");

			loadSoundByName("fire1.mp3");

			loadSoundByName("wall1.mp3");
			loadSoundByName("wall2.mp3");
			loadSoundByName("wall3.mp3");

			loadSoundByName("deadBody1.mp3");
			loadSoundByName("deadBody2.mp3");

			// strore
			loadSoundByName("storeSound1.mp3");
			loadSoundByName("storeSound2.mp3");

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

	public void playSmashNormalSound() {
		try {

			int val = getRandomMax(1, 4);
			playSound("smash" + val + ".mp3");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void playVampireNormalSound() {
		try {

			int val = getRandomMax(1, 2);
			playSound("normalV" + val + ".mp3");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void playVampireBatSound() {
		try {

			int val = getRandomMax(1, 5);
			playSound("normalMurcielago" + val + ".mp3");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	

	public void playBossNormalSound() {
		try {

			int val = getRandomMax(1, 5);
			playSound("bossNormal" + val + ".mp3");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public void playBossHitSound() {
		try {

			int val = getRandomMax(1, 7);
			playSound("bossHit" + val + ".mp3");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void playDiamantSound() {
		try {

			int val = getRandomMax(1, 5);
			playSound("Diamante_" + val + ".mp3");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void playTrunkSound() {
		try {

			int val = getRandomMax(1, 2);
			playSound("trunk" + val + ".mp3");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void playFireSound() {
		try {

			int val = getRandomMax(1, 1);
			playSound("fire" + val + ".mp3");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void playWallSound() {
		try {

			int val = getRandomMax(1, 3);
			playSound("wall" + val + ".mp3");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void playDeadBodySound() {
		try {

			int val = getRandomMax(1, 2);
			playSound("deadBody" + val + ".mp3");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void playButtonSound() {
		try {

			int val = getRandomMax(1, 5);
			playSound("buttonSound" + val + ".mp3");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void playStoreSound() {
		try {

			int val = getRandomMax(1, 2);
			playSound("storeSound" + val + ".mp3");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void playVampireBasicDead() {
		try {

			int val = getRandomMax(1, 5);
			playSound("Vampiro_Muerto" + val + ".mp3");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void playHumanWin() {
		try {

			int val = getRandomMax(1, 5);
			playSound("Win_humana" + val + ".mp3");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void playJulianWin() {
		try {

			int val = getRandomMax(1, 2);
			playSound("julianWin" + val + ".mp3");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void playJulianDead() {
		try {

			int val = getRandomMax(1, 2);
			playSound("julian" + val + ".mp3");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void playJulianRun() {
		try {

			int val = getRandomMax(1, 2);
			playSound("julianRun" + val + ".mp3");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void playHumanRun() {
		try {

			int val = getRandomMax(1, 5);
			playSound("humanRun" + val + ".mp3");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void playHumanDead() {

		try {

			int val = getRandomMax(1, 5);
			playSound("Humana_muerta" + val + ".mp3");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void playArmorNormal() {

		try {

			int val = getRandomMax(1, 11);
			playSound("Gordo_CaballeroV_Normal" + val + ".mp3");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void playArmorSmash() {

		try {

			int val = getRandomMax(1, 2);
			playSound("armor" + val + ".mp3");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void playUnArmorNormal() {

		try {

			int val = getRandomMax(1, 11);
			playSound("Gordo_Normal" + val + ".mp3");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void playUnarmorDead() {

		try {

			int val = getRandomMax(1, 9);
			playSound("Gordo_Muerto" + val + ".mp3");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void playNormalZigZag() {

		try {

			int val = getRandomMax(1, 6);
			playSound("Vampiresa_Normal" + val + ".mp3");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void playDeadZigZag() {

		try {

			int val = getRandomMax(1, 6);
			playSound("Vampiresa_Muerta" + val + ".mp3");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void playNormalHole() {

		try {

			int val = getRandomMax(1, 6);
			playSound("Vampiro_Cubierto_Normal" + val + ".mp3");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void playDeadHole() {

		try {

			int val = getRandomMax(1, 4);
			playSound("Vampiro_Cubierto_Muerto" + val + ".mp3");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void playEatingBasic() {

		try {
			int val = getRandomMax(1, 8);
			playSound("Vampiro_Comiendo" + val + ".mp3");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void playEatingUnarmor() {

		try {
			int val = getRandomMax(1, 6);
			playSound("Gordo_comiendo" + val + ".mp3");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void playEatingZigZag() {

		try {
			int val = getRandomMax(1, 6);
			playSound("Vampiresa_Comiendo" + val + ".mp3");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void playEatingBat() {

		try {
			int val = getRandomMax(1, 6);
			playSound("VampiroMurcie_comiendo" + val + ".mp3");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void playTrophySound() {

		try {
			int val = getRandomMax(1, 3);
			playSound("Trofeo" + val + ".mp3");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void playWithoutPower() {

		try {
			int val = getRandomMax(1, 5);
			playSound("out" + val + ".mp3");

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
	
	

	
}
