package com.mgl.drop.game.entity;

import java.util.ArrayList;

import org.andengine.entity.IEntity;
import org.andengine.entity.scene.ITouchArea;

import android.util.Log;

import com.google.android.gms.games.internal.GamesConstants;
import com.mgl.base.MyAnimateSprite;
import com.mgl.base.MyEntity;
import com.mgl.base.MyEntitySmasher;
import com.mgl.base.MyFactory;
import com.mgl.base.MySprite;
import com.mgl.base.MySpriteGeneral;
import com.mgl.base.SpriteType;
import com.mgl.base.SpriteTypeConstant;
import com.mgl.base.StatusType;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.game.constant.BehaviorTypeConstant;
import com.mgl.drop.game.constant.GameConstants;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.sprites.SpriteHumanBasic;

public class Wave extends MyEntity {

	private ArrayList<MyEntitySmasher> spriteList;
	private ArrayList<Behavior> behaviorList;
	private Class noparams[] = {};

	private Long id;
	private Long beginTime;
	private float time = 0;

	private boolean isActive;
	private boolean showChangeWave = true;
	private boolean finish = false;
	private boolean last = false;

	private int duration;

	private String textToChange;

	public Wave() {
		try {
			spriteList = new ArrayList<MyEntitySmasher>();
			behaviorList = new ArrayList<Behavior>();
			isActive = false;
			id = -1L;

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(float dTime, LevelController level) {
		try {
			time = time + dTime;

			ArrayList<MyEntitySmasher> sprToRemove = new ArrayList<MyEntitySmasher>();

			for (MyEntitySmasher ms : spriteList) {
				if (time >= ms.getTime() && !ms.isActive()) {
					MySpriteGeneral spr = MyFactory.createSprite(
							ms.getIdSprite(), level, 60, 80, ms.getSpeed(),
							ms.getIdBehaviorType(), ms.getOrientation(),
							ms.getDiamant());
					ms.setActive(true);

					spr.getEntity().setPosition(ms.getX(), ms.getY());

					level.addSpriteToUpdate(spr);
					level.getScene().attachChild(spr.getEntity());
					level.getScene().registerTouchArea(spr.getTouchArea());

					if (showChangeWave) {
						level.getLevelHud().changeWaveText(textToChange);
						showChangeWave = !showChangeWave;
					}

				}
				if (ms.isActive) {
					sprToRemove.add(ms);
				}
			}

			spriteList.removeAll(sprToRemove);

			for (MyEntitySmasher ms : spriteList) {
				if (!ms.isActive()) {
					return;

				}
			}

			finish = true;

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void generateRandomWave(int contWave) {
		try {

			int monsterPerSecond = 2;
			ArrayList<Long> monsterTypeList = new ArrayList<Long>();
			int waveTime = 10;

			if (contWave <= 4) {
				
				int num = MainDropActivity.getRandomMax(3, 5);
				
				this.setSpriteList((ArrayList<MyEntitySmasher>) this.getClass().getMethod("generateWaveLevel"+num, noparams).invoke(this, null));
				
				//generateWaveLevel3();
				//generateWaveLevel4();
				//generateWaveLevel5();
				

			} else if (contWave <= 10) {
				generateWaveLevel6();
				generateWaveLevel7();
				generateWaveLevel8();
				generateWaveLevel9();
				generateWaveLevel10();
				
			} else if (contWave <= 16) {
				generateWaveLevel11();
				generateWaveLevel12();
				generateWaveLevel13();
				generateWaveLevel14();
				generateWaveLevel15();
				generateWaveLevel16();
			}else {
				
				generateWaveLevel14();
				generateWaveLevel15();
				generateWaveLevel16();
				generateWaveLevel17();
				generateWaveLevel18();
				generateWaveLevel19();
				generateWaveLevel21();
				generateWaveLevel22();
				generateWaveLevel23();
				generateWaveLevel24();
				generateWaveLevel25();
				generateWaveLevel26();
				generateWaveLevel27();
				generateWaveLevel28();
				generateWaveLevel29();
				generateWaveLevel30();
				generateWaveLevel31();
				generateWaveLevel32();
				
			}
			

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void generateRandomWaveAux(int difficulty) {
		try {

			int monsterPerSecond = 2;
			ArrayList<Long> monsterTypeList = new ArrayList<Long>();
			int waveTime = 10;

			switch (difficulty) {
			case GameConstants.DIFFICULTY_EASY:

				monsterTypeList.add(Long
						.valueOf(SpriteTypeConstant.MONSTER_BASIC));
				monsterTypeList.add(Long
						.valueOf(SpriteTypeConstant.MONSTER_ZIGZAG));
				monsterTypeList.add(Long
						.valueOf(SpriteTypeConstant.MONSTER_BAT));
				monsterTypeList.add(Long
						.valueOf(SpriteTypeConstant.MONSTER_ARMOR));
				monsterTypeList.add(Long
						.valueOf(SpriteTypeConstant.MONSTER_HOLE));
				monsterTypeList.add(Long
						.valueOf(SpriteTypeConstant.MONSTER_WITHOUT_ARMOR));

				break;

			default:
				break;
			}

			int second = 0;
			for (int i = 0; i < waveTime; i++) {
				for (int j = 0; j < monsterPerSecond; j++) {
					float x = (float) ((Math.random() * 1000000) % (MainDropActivity.CAMERA_WIDTH - 80));
					float y = -120;
					float time = (float) (Float.valueOf(second) + ((Math
							.random() * 1000000) % 10) / 10);
					int spriteType;

					do {

						spriteType = (int) (((Math.random() * 10000000) % 9) + 1);
					} while (!monsterTypeList
							.contains(Long.valueOf(spriteType))
							&& !spriteList.isEmpty());

					MyEntitySmasher entity = new MyEntitySmasher(x, y,
							spriteType, false, time);
					entity.setSpeed(150);
					spriteList.add(entity);
				}
				second++;
			}

			MyEntitySmasher entity = new MyEntitySmasher(0, -200,
					SpriteTypeConstant.HUMAN_BASIC, false, 2);
			entity.setSpeed(150);
			spriteList.add(entity);

			entity = new MyEntitySmasher(300, -200,
					SpriteTypeConstant.HUMAN_BASIC, false, 6);
			entity.setSpeed(150);
			spriteList.add(entity);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public SpriteType getSpriteType() {

		return SpriteType.DECORATIVE;
	}

	@Override
	public void setStatus(StatusType status) {
		// TODO Auto-generated method stub

	}

	@Override
	public StatusType getStatus() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void poop(MySpriteGeneral poop, LevelController level) {

	}

	public ArrayList<MyEntitySmasher> getSpriteList() {
		return spriteList;
	}

	public void setSpriteList(ArrayList<MyEntitySmasher> spriteList) {
		try {
			if (this.spriteList == null || this.spriteList.isEmpty()) {
				this.spriteList = spriteList;
			} else {
				this.spriteList.addAll(spriteList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public Long getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Long beginTime) {
		this.beginTime = beginTime;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {

		this.isActive = isActive;
	}

	public float getTime() {
		return time;
	}

	public void setTime(float time) {
		this.time = time;
	}

	@Override
	public IEntity getEntity() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ITouchArea getTouchArea() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isShowChangeWave() {
		return showChangeWave;
	}

	public void setShowChangeWave(boolean showChangeWave) {
		this.showChangeWave = showChangeWave;
	}

	public String getTextToChange() {
		return textToChange;
	}

	public void setTextToChange(String textToChange) {
		this.textToChange = textToChange;
	}

	public boolean isFinish() {
		return finish;
	}

	public void setFinish(boolean finish) {
		this.finish = finish;
	}

	public boolean isLast() {
		return last;
	}

	public void setLast(boolean last) {
		this.last = last;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ArrayList<Behavior> getBehaviorList() {
		return behaviorList;
	}

	public void setBehaviorList(ArrayList<Behavior> behaviorList) {
		this.behaviorList = behaviorList;
	}

	public void generateSmashSprites() {
		try {
			System.out.println("generating smash sprites");
			spriteList = new ArrayList<MyEntitySmasher>();
			for (Behavior b : behaviorList) {
				System.out.println("wave " + id + " " + b.getId());
				spriteList.addAll(b.generateSmashSprites(beginTime));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void setMustUpdate(boolean mustUpdate) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateChild(float dTime, LevelController level) {
		// TODO Auto-generated method stub

	}

	public void finishMonsters() {
		try {

			for (MyEntitySmasher ms : spriteList) {
				ms.setActive(true);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<Wave> generateWaveByLevel(Long idLevel) {
		ArrayList<Wave> waveList = new ArrayList<Wave>();

		try {

			switch (idLevel.intValue()) {
			case 3:
				waveList = generateWaveLevel3();

				break;
			case 4:
				waveList = generateWaveLevel4();

				break;
			case 5:
				waveList = generateWaveLevel5();

				break;
			case 6:
				waveList = generateWaveLevel6();

				break;

			case 7:
				waveList = generateWaveLevel7();

				break;
			case 8:
				waveList = generateWaveLevel8();

				break;
			case 9:
				waveList = generateWaveLevel9();

				break;
			case 10:
				waveList = generateWaveLevel10();

				break;
			case 11:
				waveList = generateWaveLevel11();

				break;
			case 12:
				waveList = generateWaveLevel12();

				break;
			case 13:
				waveList = generateWaveLevel13();

				break;
			case 14:
				waveList = generateWaveLevel14();

				break;

			case 15:
				waveList = generateWaveLevel15();

				break;

			case 16:
				waveList = generateWaveLevel16();

				break;

			case 17:
				waveList = generateWaveLevel17();

				break;

			case 18:
				waveList = generateWaveLevel18();

				break;

			case 19:
				waveList = generateWaveLevel19();

				break;

			case 20:
				waveList = generateWaveLevel20();

				break;
			case 21:
				waveList = generateWaveLevel21();

				break;

			case 22:
				waveList = generateWaveLevel22();

				break;
			case 23:
				waveList = generateWaveLevel23();

				break;
			case 24:
				waveList = generateWaveLevel24();

				break;
			case 25:
				waveList = generateWaveLevel25();

				break;
			case 26:
				waveList = generateWaveLevel26();

				break;
			case 27:
				waveList = generateWaveLevel27();

				break;
			case 28:
				waveList = generateWaveLevel28();

				break;
			case 29:
				waveList = generateWaveLevel29();

				break;

			case 30:
				waveList = generateWaveLevel30();

				break;

			case 31:
				waveList = generateWaveLevel31();

				break;

			case 32:
				waveList = generateWaveLevel32();

				break;

			default:
				break;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return waveList;
	}

	public static ArrayList<Wave> generateWaveByLevelBETA(Long idLevel) {
		ArrayList<Wave> waveList = new ArrayList<Wave>();

		try {

			switch (idLevel.intValue()) {
			case 3:
				waveList = generateWaveLevel3();

				break;
			case 4:
				waveList = generateWaveLevel4();

				break;
			case 5:
				waveList = generateWaveLevel5();

				break;
			case 6:
				waveList = generateWaveLevel6();

				break;

			case 7:
				waveList = generateWaveLevel7();

				break;
			case 8:
				waveList = generateWaveLevel13();

				break;
			case 9:
				waveList = generateWaveLevel14();

				break;
			case 10:
				waveList = generateWaveLevel15();

				break;
			default:
				break;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return waveList;
	}

	public static ArrayList<Wave> generateWaveLevel32() {
		ArrayList<Wave> waveList = new ArrayList<Wave>();
		try {

			Long i = 0l;
			Wave wave = new Wave();
			wave.setBeginTime(0l);
			wave.setDuration(0);

			// creating WAVE---------------------------------------------

			wave = generateWaveRandomRectangles(i,
					wave.getBeginTime() + wave.getDuration(), 300, 300, ""
							+ SpriteTypeConstant.MONSTER_BAT, 15,
					BehaviorTypeConstant.BEHAVIOR_ZIGZAG_NORMAL, 5);
			i++;
			waveList.add(wave);

			wave = generateWaveZigZagMid(i,
					wave.getBeginTime() + wave.getDuration(), 300, 300, ""
							+ SpriteTypeConstant.MONSTER_ZIGZAG, 15, 4);
			i++;
			waveList.add(wave);

			wave = generateWaveLeftFastRightSlow(i,
					wave.getBeginTime() + wave.getDuration(), 350, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC + ","
							+ SpriteTypeConstant.MONSTER_BAT, 15, 12,
					BehaviorTypeConstant.BEHAVIOR_ACCELERATE);
			i++;
			waveList.add(wave);

			wave = generateWaveEquis(i,
					wave.getBeginTime() + wave.getDuration(), 350, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC);
			i++;
			waveList.add(wave);

			wave = generateWaveROMBO(i,
					wave.getBeginTime() + wave.getDuration(), 350, 300, ""
							+ SpriteTypeConstant.MONSTER_HOLE);
			i++;
			waveList.add(wave);

			wave = generateWaveROMBO(i,
					wave.getBeginTime() + wave.getDuration(), 350, 300, ""
							+ SpriteTypeConstant.MONSTER_BAT);
			i++;
			waveList.add(wave);

			wave = generateWaveRightFastLeftSlow(i,
					wave.getBeginTime() + wave.getDuration(), 350, 300, ""
							+ SpriteTypeConstant.MONSTER_ARMOR + ","
							+ SpriteTypeConstant.MONSTER_HOLE, 15, 12,
					BehaviorTypeConstant.BEHAVIOR_ACCELERATE);
			i++;
			waveList.add(wave);

			wave = generateWaveROMBO(i,
					wave.getBeginTime() + wave.getDuration(), 350, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC);
			i++;
			waveList.add(wave);

			// final wave
			wave = generateWaveRandomRectangles(i,
					wave.getBeginTime() + wave.getDuration(), 350, 300, ""
							+ SpriteTypeConstant.MONSTER_ARMOR + ","
							+ SpriteTypeConstant.MONSTER_HOLE, 15,
					BehaviorTypeConstant.BEHAVIOR_ACCELERATE, 8);
			i++;
			waveList.add(wave);

			wave = generateWaveBoss(i,
					wave.getBeginTime() + wave.getDuration(), 200, 300, ""
							+ SpriteTypeConstant.MONSTER_ARMOR);
			i++;
			waveList.add(wave);

		} catch (Exception e) {

			e.printStackTrace();

		}
		return waveList;
	}

	public static ArrayList<Wave> generateWaveLevel31() {
		ArrayList<Wave> waveList = new ArrayList<Wave>();
		try {

			Long i = 0l;
			Wave wave = new Wave();
			wave.setBeginTime(0l);
			wave.setDuration(0);

			// creating WAVE---------------------------------------------
			wave = generateWaveZigZagMid(i,
					wave.getBeginTime() + wave.getDuration(), 300, 300, ""
							+ SpriteTypeConstant.MONSTER_ZIGZAG, 15, 2);
			i++;
			waveList.add(wave);

			wave = generateWaveV(i, wave.getBeginTime() + wave.getDuration(),
					300, 300, "" + SpriteTypeConstant.MONSTER_BASIC, 20);
			i++;
			waveList.add(wave);

			wave = generateWaveEquis(i,
					wave.getBeginTime() + wave.getDuration(), 300, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC);
			i++;
			waveList.add(wave);

			wave = generateWaveZigZagMid(i,
					wave.getBeginTime() + wave.getDuration(), 300, 300, ""
							+ SpriteTypeConstant.MONSTER_ZIGZAG, 15, 2);
			i++;

			wave = generateWaveZigZagMid(i,
					wave.getBeginTime() + wave.getDuration(), 280, 300, ""
							+ SpriteTypeConstant.MONSTER_ZIGZAG, 15, 2);
			i++;

			wave = generateWaveROMBO(i,
					wave.getBeginTime() + wave.getDuration(), 200, 300, ""
							+ SpriteTypeConstant.MONSTER_ARMOR);
			i++;
			waveList.add(wave);

			wave = generateWaveRightFastLeftSlow(i,
					wave.getBeginTime() + wave.getDuration(), 450, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC + ","
							+ SpriteTypeConstant.MONSTER_HOLE, 80, 8,
					BehaviorTypeConstant.BEHAVIOR_ZIGZAG);
			i++;
			waveList.add(wave);

			wave = generateWaveZigZagMid(i,
					wave.getBeginTime() + wave.getDuration(), 350, 300, ""
							+ SpriteTypeConstant.MONSTER_ARMOR, 15, 10);
			i++;
			waveList.add(wave);

			wave = generateWave2C2CT(i,
					wave.getBeginTime() + wave.getDuration(), 250, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC + ","
							+ SpriteTypeConstant.MONSTER_BAT, 25);
			i++;
			waveList.add(wave);

			wave = generateWaveZigZagMid(i,
					wave.getBeginTime() + wave.getDuration(), 350, 300, ""
							+ SpriteTypeConstant.MONSTER_ARMOR, 15, 10);
			i++;
			waveList.add(wave);

			wave = generateWaveLeftFastRightSlow(i,
					wave.getBeginTime() + wave.getDuration(), 350, 300, ""
							+ SpriteTypeConstant.MONSTER_BAT + ","
							+ SpriteTypeConstant.MONSTER_HOLE, 15, 10,
					BehaviorTypeConstant.BEHAVIOR_ACCELERATE);
			i++;
			waveList.add(wave);

			wave = generateWaveLeftFastRightSlow(i,
					wave.getBeginTime() + wave.getDuration(), 350, 300, ""
							+ SpriteTypeConstant.MONSTER_BAT + ","
							+ SpriteTypeConstant.MONSTER_BASIC, 15, 10,
					BehaviorTypeConstant.BEHAVIOR_ZIGZAG);
			i++;
			waveList.add(wave);

			wave = generateWaveRandomRectangles(i,
					wave.getBeginTime() + wave.getDuration(), 350, 300, ""
							+ SpriteTypeConstant.MONSTER_ARMOR + ","
							+ SpriteTypeConstant.MONSTER_HOLE, 15,
					BehaviorTypeConstant.BEHAVIOR_ACCELERATE, 8);
			i++;
			waveList.add(wave);

		} catch (Exception e) {

			e.printStackTrace();

		}
		return waveList;
	}

	public static ArrayList<Wave> generateWaveLevel30() {
		ArrayList<Wave> waveList = new ArrayList<Wave>();
		try {

			Long i = 0l;
			Wave wave = new Wave();
			wave.setBeginTime(0l);
			wave.setDuration(0);

			// creating WAVE---------------------------------------------
			wave = generateWave2C2CT(i,
					wave.getBeginTime() + wave.getDuration(), 350, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC, 12);
			i++;
			waveList.add(wave);

			wave = generateWavePVHHuman(i,
					wave.getBeginTime() + wave.getDuration(), 280, 300, ""
							+ SpriteTypeConstant.MONSTER_ARMOR);
			i++;
			waveList.add(wave);

			wave = generateWaveRandomRectangles(i,
					wave.getBeginTime() + wave.getDuration() + 2, 280, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC, 20,
					BehaviorTypeConstant.BEHAVIOR_ACCELERATE, 5);
			i++;
			waveList.add(wave);

			wave = generateWaveZIGZAG(i,
					wave.getBeginTime() + wave.getDuration(), 280, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC + ","
							+ SpriteTypeConstant.MONSTER_ARMOR);
			i++;
			waveList.add(wave);

			wave = generateWavePiramid(i,
					wave.getBeginTime() + wave.getDuration(), 320, 300, ""
							+ SpriteTypeConstant.MONSTER_HOLE, 15,
					BehaviorTypeConstant.BEHAVIOR_ACCELERATE);
			i++;
			waveList.add(wave);

			wave = generateWaveRandomRectangles(i,
					wave.getBeginTime() + wave.getDuration() + 3, 320, 300, ""
							+ SpriteTypeConstant.MONSTER_HOLE, 20,
					BehaviorTypeConstant.BEHAVIOR_NORMAL_ZIGZAG, 5);
			i++;
			waveList.add(wave);

			wave = generateWaveRightFastLeftSlow(i,
					wave.getBeginTime() + wave.getDuration(), 450, 300, ""
							+ SpriteTypeConstant.MONSTER_WITHOUT_ARMOR, 0, 8,
					BehaviorTypeConstant.BEHAVIOR_ZIGZAG);
			i++;
			waveList.add(wave);

			wave = generateWaveEquis(i,
					wave.getBeginTime() + wave.getDuration(), 350, 300, ""
							+ SpriteTypeConstant.MONSTER_ARMOR);
			i++;
			waveList.add(wave);

			wave = generateWaveLeftFastRightSlow(i,
					wave.getBeginTime() + wave.getDuration(), 450, 300, ""
							+ SpriteTypeConstant.MONSTER_HOLE, 0, 8,
					BehaviorTypeConstant.BEHAVIOR_ZIGZAG);
			i++;
			waveList.add(wave);

			wave = generateWaveRandomRectangles(i,
					wave.getBeginTime() + wave.getDuration(), 320, 300, ""
							+ SpriteTypeConstant.MONSTER_HOLE + ","
							+ SpriteTypeConstant.MONSTER_BAT, 18,
					BehaviorTypeConstant.BEHAVIOR_ACCELERATE, 6);
			i++;
			waveList.add(wave);

		} catch (Exception e) {

			e.printStackTrace();

		}
		return waveList;
	}

	public static ArrayList<Wave> generateWaveLevel29() {
		ArrayList<Wave> waveList = new ArrayList<Wave>();
		try {

			Long i = 0l;
			Wave wave = new Wave();
			wave.setBeginTime(0l);
			wave.setDuration(0);

			// creating WAVE---------------------------------------------
			wave = generateWaveDIAGONALHuman(i,
					wave.getBeginTime() + wave.getDuration(), 350, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC);
			i++;
			waveList.add(wave);

			wave = generateWavePiramid(i,
					wave.getBeginTime() + wave.getDuration(), 280, 300, ""
							+ SpriteTypeConstant.MONSTER_ARMOR, 0,
					BehaviorTypeConstant.BEHAVIOR_NORMAL);
			i++;
			waveList.add(wave);

			wave = generateWaveROMBOHuman(i,
					wave.getBeginTime() + wave.getDuration(), 280, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC);
			i++;
			waveList.add(wave);

			wave = generateWaveZIGZAG(i,
					wave.getBeginTime() + wave.getDuration(), 280, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC + ","
							+ SpriteTypeConstant.MONSTER_ARMOR);
			i++;
			waveList.add(wave);

			wave = generateWaveDiamant(i,
					wave.getBeginTime() + wave.getDuration(), 280, 300, ""
							+ SpriteTypeConstant.MONSTER_HOLE);
			i++;
			waveList.add(wave);

			wave = generateWaveRandomRectangles(i,
					wave.getBeginTime() + wave.getDuration(), 280, 300, ""
							+ SpriteTypeConstant.MONSTER_HOLE, 20, 5,
					BehaviorTypeConstant.BEHAVIOR_NORMAL_ZIGZAG);
			i++;
			waveList.add(wave);

			wave = generateWave2C2CT(i,
					wave.getBeginTime() + wave.getDuration(), 280, 300, ""
							+ SpriteTypeConstant.MONSTER_BAT, 25);
			i++;
			waveList.add(wave);

			wave = generateWaveRightFastLeftSlow(i,
					wave.getBeginTime() + wave.getDuration(), 450, 300, ""
							+ SpriteTypeConstant.MONSTER_WITHOUT_ARMOR, 0, 8,
					BehaviorTypeConstant.BEHAVIOR_ZIGZAG);
			i++;
			waveList.add(wave);

			wave = generateWaveEquis(i,
					wave.getBeginTime() + wave.getDuration(), 350, 300, ""
							+ SpriteTypeConstant.MONSTER_ARMOR);
			i++;
			waveList.add(wave);

			wave = generateWaveRandomRectangles(i,
					wave.getBeginTime() + wave.getDuration(), 280, 300, ""
							+ SpriteTypeConstant.MONSTER_HOLE + ","
							+ SpriteTypeConstant.MONSTER_BAT, 20, 5,
					BehaviorTypeConstant.BEHAVIOR_NORMAL_ZIGZAG);
			i++;
			waveList.add(wave);

			wave = generateWaveLeftFastRightSlow(i,
					wave.getBeginTime() + wave.getDuration(), 450, 300, ""
							+ SpriteTypeConstant.MONSTER_HOLE, 0, 8,
					BehaviorTypeConstant.BEHAVIOR_ZIGZAG);
			i++;
			waveList.add(wave);

		} catch (Exception e) {

			e.printStackTrace();

		}
		return waveList;
	}

	public static ArrayList<Wave> generateWaveLevel28() {
		ArrayList<Wave> waveList = new ArrayList<Wave>();
		try {

			Long i = 0l;
			Wave wave = new Wave();
			wave.setBeginTime(0l);
			wave.setDuration(0);

			// creating WAVE---------------------------------------------
			wave = generateWaveHPNZ(i,
					wave.getBeginTime() + wave.getDuration(), 270, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC);
			i++;
			waveList.add(wave);

			wave = generateWaveRandom(i,
					wave.getBeginTime() + wave.getDuration(), 280, 300, ""
							+ SpriteTypeConstant.MONSTER_BAT + ","
							+ SpriteTypeConstant.MONSTER_HOLE);
			i++;
			waveList.add(wave);

			wave = generateWaveZigZagMid(i,
					wave.getBeginTime() + wave.getDuration(), 280, 300, ""
							+ SpriteTypeConstant.MONSTER_HOLE + ","
							+ SpriteTypeConstant.MONSTER_BAT, 12, 10);
			i++;
			waveList.add(wave);

			wave = generateWaveRandom(i,
					wave.getBeginTime() + wave.getDuration(), 280, 300, ""
							+ SpriteTypeConstant.MONSTER_HOLE);
			i++;
			waveList.add(wave);

			wave = generateWaveRightFastLeftSlow(i,
					wave.getBeginTime() + wave.getDuration() + 4, 280, 300, ""
							+ SpriteTypeConstant.MONSTER_ARMOR + ","
							+ SpriteTypeConstant.MONSTER_HOLE, 20, 12,
					BehaviorTypeConstant.BEHAVIOR_NORMAL_ZIGZAG);
			i++;
			waveList.add(wave);

			wave = generateWaveDiamantHuman(i,
					wave.getBeginTime() + wave.getDuration() + 4, 280, 300, ""
							+ SpriteTypeConstant.MONSTER_ARMOR + ","
							+ SpriteTypeConstant.MONSTER_HOLE);
			i++;
			waveList.add(wave);

			wave = generateWaveLeftFastRightSlow(i,
					wave.getBeginTime() + wave.getDuration() + 4, 280, 300, ""
							+ SpriteTypeConstant.MONSTER_BAT + ","
							+ SpriteTypeConstant.MONSTER_ARMOR, 20, 12,
					BehaviorTypeConstant.BEHAVIOR_NORMAL_ZIGZAG);
			i++;
			waveList.add(wave);

			wave = generateWaveRandomRectangles(i,
					wave.getBeginTime() + wave.getDuration(), 280, 300, ""
							+ SpriteTypeConstant.MONSTER_HOLE + ","
							+ SpriteTypeConstant.MONSTER_BAT, 12,
					BehaviorTypeConstant.BEHAVIOR_ACCELERATE, 4);
			i++;
			waveList.add(wave);

			wave = generateWaveZIGZAGHuman(i,
					wave.getBeginTime() + wave.getDuration(), 280, 300, ""
							+ SpriteTypeConstant.MONSTER_HOLE + ","
							+ SpriteTypeConstant.MONSTER_BAT);
			i++;
			waveList.add(wave);

		} catch (Exception e) {

			e.printStackTrace();

		}
		return waveList;
	}

	public static ArrayList<Wave> generateWaveLevel27() {
		ArrayList<Wave> waveList = new ArrayList<Wave>();
		try {

			Long i = 0l;
			Wave wave = new Wave();
			wave.setBeginTime(0l);
			wave.setDuration(0);

			// creating WAVE---------------------------------------------
			wave = generateWaveRandom(i,
					wave.getBeginTime() + wave.getDuration(), 270, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC + ","
							+ SpriteTypeConstant.MONSTER_HOLE);
			i++;
			waveList.add(wave);

			wave = generateWaveROMBOHuman(i,
					wave.getBeginTime() + wave.getDuration(), 300, 300, ""
							+ SpriteTypeConstant.MONSTER_ARMOR);
			i++;
			waveList.add(wave);

			wave = generateWaveEquis(i,
					wave.getBeginTime() + wave.getDuration() + 4, 270, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC + ","
							+ SpriteTypeConstant.MONSTER_BAT);
			i++;
			waveList.add(wave);

			wave = generateWaveHPNZ(i, wave.getBeginTime() + wave.getDuration()
					+ 4, 270, 300, "" + SpriteTypeConstant.MONSTER_BASIC + ","
					+ SpriteTypeConstant.MONSTER_ARMOR);
			i++;
			waveList.add(wave);

			wave = generateWaveRightFastLeftSlow(i,
					wave.getBeginTime() + wave.getDuration(), 350, 300, ""
							+ SpriteTypeConstant.MONSTER_HOLE + ","
							+ SpriteTypeConstant.MONSTER_BAT, 10, 18,
					BehaviorTypeConstant.BEHAVIOR_ACCELERATE);
			i++;
			waveList.add(wave);

			wave = generateWaveLeftFastRightSlow(i,
					wave.getBeginTime() + wave.getDuration(), 350, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC + ","
							+ SpriteTypeConstant.MONSTER_ARMOR, 10, 14,
					BehaviorTypeConstant.BEHAVIOR_ACCELERATE);
			i++;
			waveList.add(wave);

			wave = generateWaveZigZagMid(i,
					wave.getBeginTime() + wave.getDuration(), 650, 300, ""
							+ SpriteTypeConstant.MONSTER_ARMOR, 100, 2);
			i++;
			waveList.add(wave);

			wave = generateWaveEquisHuman(i,
					wave.getBeginTime() + wave.getDuration(), 270, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC + ","
							+ SpriteTypeConstant.MONSTER_BAT);
			i++;
			waveList.add(wave);

			wave = generateWaveDiamant(i,
					wave.getBeginTime() + wave.getDuration(), 250, 300, ""
							+ SpriteTypeConstant.MONSTER_HOLE + ","
							+ SpriteTypeConstant.MONSTER_BAT);
			i++;
			waveList.add(wave);

			wave = generateWavePiramid(i,
					wave.getBeginTime() + wave.getDuration(), 350, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC + ","
							+ SpriteTypeConstant.MONSTER_BAT + ","
							+ SpriteTypeConstant.MONSTER_ZIGZAG, 15,
					BehaviorTypeConstant.BEHAVIOR_NORMAL_ZIGZAG);
			i++;
			waveList.add(wave);

		} catch (Exception e) {

			e.printStackTrace();

		}
		return waveList;
	}

	public static ArrayList<Wave> generateWaveLevel26() {
		ArrayList<Wave> waveList = new ArrayList<Wave>();
		try {

			Long i = 0l;
			Wave wave = new Wave();
			wave.setBeginTime(0l);
			wave.setDuration(0);

			// creating WAVE---------------------------------------------
			wave = generateWaveROMBOHuman(i,
					wave.getBeginTime() + wave.getDuration(), 300, 300, ""
							+ SpriteTypeConstant.MONSTER_BAT);
			i++;
			waveList.add(wave);

			wave = generateWaveRandom(i,
					wave.getBeginTime() + wave.getDuration(), 350, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC);
			i++;
			waveList.add(wave);

			wave = generateWaveDIAGONAL(i,
					wave.getBeginTime() + wave.getDuration() + 4, 300, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC + ","
							+ SpriteTypeConstant.MONSTER_BAT);
			i++;
			waveList.add(wave);

			wave = generateWavePVZ(i, wave.getBeginTime() + wave.getDuration()
					+ 4, 300, 300, "" + SpriteTypeConstant.MONSTER_BASIC + ","
					+ SpriteTypeConstant.MONSTER_ARMOR);
			i++;
			waveList.add(wave);

			wave = generateWaveEquis(i,
					wave.getBeginTime() + wave.getDuration(), 350, 300, ""
							+ SpriteTypeConstant.MONSTER_HOLE);
			i++;
			waveList.add(wave);

			wave = generateWaveLeftFastRightSlow(i,
					wave.getBeginTime() + wave.getDuration(), 350, 300, ""
							+ SpriteTypeConstant.MONSTER_HOLE + ","
							+ SpriteTypeConstant.MONSTER_ARMOR, 10, 14,
					BehaviorTypeConstant.BEHAVIOR_ACCELERATE);
			i++;
			waveList.add(wave);

			wave = generateWaveZigZagMid(i,
					wave.getBeginTime() + wave.getDuration(), 350, 300, ""
							+ SpriteTypeConstant.MONSTER_ZIGZAG, 10, 2);
			i++;
			waveList.add(wave);

			wave = generateWaveEquisHuman(i,
					wave.getBeginTime() + wave.getDuration(), 250, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC + ","
							+ SpriteTypeConstant.MONSTER_BAT);
			i++;
			waveList.add(wave);

			wave = generateWaveHHH(i, wave.getBeginTime() + wave.getDuration(),
					320, 300, "" + SpriteTypeConstant.MONSTER_HOLE + ","
							+ SpriteTypeConstant.MONSTER_BAT);
			i++;
			waveList.add(wave);

			wave = generateWaveLeftFastRightSlow(i,
					wave.getBeginTime() + wave.getDuration(), 350, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC + ","
							+ SpriteTypeConstant.MONSTER_BAT + ","
							+ SpriteTypeConstant.MONSTER_ZIGZAG, 10, 16,
					BehaviorTypeConstant.BEHAVIOR_NORMAL_ZIGZAG);
			i++;
			waveList.add(wave);

		} catch (Exception e) {

			e.printStackTrace();

		}
		return waveList;
	}

	public static ArrayList<Wave> generateWaveLevel25() {
		ArrayList<Wave> waveList = new ArrayList<Wave>();
		try {

			Long i = 0l;
			Wave wave = new Wave();
			wave.setBeginTime(0l);
			wave.setDuration(0);

			// creating WAVE---------------------------------------------
			wave = generateWavePZRHuman(i,
					wave.getBeginTime() + wave.getDuration(), 270, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC);
			i++;
			waveList.add(wave);

			wave = generateWaveRandom(i,
					wave.getBeginTime() + wave.getDuration(), 350, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC + ","
							+ SpriteTypeConstant.MONSTER_HOLE);
			i++;
			waveList.add(wave);

			wave = generateWaveROMBO(i,
					wave.getBeginTime() + wave.getDuration() + 4, 270, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC + ","
							+ SpriteTypeConstant.MONSTER_BAT);
			i++;
			waveList.add(wave);

			wave = generateWaveRandom(i,
					wave.getBeginTime() + wave.getDuration(), 350, 300, ""
							+ SpriteTypeConstant.MONSTER_HOLE);
			i++;
			waveList.add(wave);

			wave = generateWaveLeftFastRightSlow(i,
					wave.getBeginTime() + wave.getDuration(), 350, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC + ","
							+ SpriteTypeConstant.MONSTER_BAT, 10, 16,
					BehaviorTypeConstant.BEHAVIOR_NORMAL_ZIGZAG);
			i++;
			waveList.add(wave);

			wave = generateWaveZIGZAGHuman(i,
					wave.getBeginTime() + wave.getDuration() + 2, 250, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC + ","
							+ SpriteTypeConstant.MONSTER_BAT);
			i++;
			waveList.add(wave);

			wave = generateWaveRVP(i, wave.getBeginTime() + wave.getDuration()
					+ 2, 270, 300, "" + SpriteTypeConstant.MONSTER_BASIC + ","
					+ SpriteTypeConstant.MONSTER_BAT);
			i++;
			waveList.add(wave);

			wave = generateWaveZIGZAG(i,
					wave.getBeginTime() + wave.getDuration(), 350, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC + ","
							+ SpriteTypeConstant.MONSTER_BAT);
			i++;
			waveList.add(wave);

			wave = generateWaveDiamant(i,
					wave.getBeginTime() + wave.getDuration(), 350, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC + ","
							+ SpriteTypeConstant.MONSTER_BAT);
			i++;
			waveList.add(wave);

			wave = generateWaveRightFastLeftSlow(i,
					wave.getBeginTime() + wave.getDuration(), 350, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC + ","
							+ SpriteTypeConstant.MONSTER_BAT, 10, 16,
					BehaviorTypeConstant.BEHAVIOR_NORMAL_ZIGZAG);
			i++;
			waveList.add(wave);

		} catch (Exception e) {

			e.printStackTrace();

		}
		return waveList;
	}

	public static ArrayList<Wave> generateWaveLevel24() {
		ArrayList<Wave> waveList = new ArrayList<Wave>();
		try {

			Long i = 0l;
			Wave wave = new Wave();
			wave.setBeginTime(0l);
			wave.setDuration(0);

			// creating WAVE---------------------------------------------

			wave = generateWaveRandomRectangles(i,
					wave.getBeginTime() + wave.getDuration(), 270, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC + ","
							+ SpriteTypeConstant.MONSTER_HOLE + ","
							+ SpriteTypeConstant.MONSTER_ARMOR, 12,
					BehaviorTypeConstant.BEHAVIOR_NORMAL_ZIGZAG, 6);
			i++;
			waveList.add(wave);

			wave = generateWaveDiamant(i,
					wave.getBeginTime() + wave.getDuration(), 280, 300, ""
							+ SpriteTypeConstant.MONSTER_BAT);
			i++;
			waveList.add(wave);

			wave = generateWaveEquisHuman(i,
					wave.getBeginTime() + wave.getDuration(), 310, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC);
			i++;
			waveList.add(wave);

			wave = generateWavePVZ(i, wave.getBeginTime() + wave.getDuration(),
					310, 300, "" + SpriteTypeConstant.MONSTER_BASIC);
			i++;
			waveList.add(wave);

			wave = generateWaveHPNZHuman(i,
					wave.getBeginTime() + wave.getDuration(), 310, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC);
			i++;
			waveList.add(wave);

			wave = generateWaveZIGZAGHuman(i,
					wave.getBeginTime() + wave.getDuration(), 310, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC);
			i++;
			waveList.add(wave);

			wave = generateWaveRandom(i,
					wave.getBeginTime() + wave.getDuration(), 310, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC + ","
							+ SpriteTypeConstant.MONSTER_BAT);
			i++;
			waveList.add(wave);

			wave = generateWavePZR(i, wave.getBeginTime() + wave.getDuration(),
					310, 300, "" + SpriteTypeConstant.MONSTER_ARMOR);
			i++;
			waveList.add(wave);

		} catch (Exception e) {

			e.printStackTrace();

		}
		return waveList;
	}

	public static ArrayList<Wave> generateWaveLevel23() {
		ArrayList<Wave> waveList = new ArrayList<Wave>();
		try {

			Long i = 0l;
			Wave wave = new Wave();
			wave.setBeginTime(0l);
			wave.setDuration(0);

			// creating WAVE---------------------------------------------

			wave = generateWaveZigZagMid(i,
					wave.getBeginTime() + wave.getDuration(), 450, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC, 0, 12);
			i++;
			waveList.add(wave);

			wave = generateWaveRightFastLeftSlow(i, 0l, 250, 300, ""
					+ SpriteTypeConstant.MONSTER_ARMOR, 15, 8,
					BehaviorTypeConstant.BEHAVIOR_NORMAL);

			wave = generateWaveRightFastLeftSlow(i,
					wave.getBeginTime() + wave.getDuration(), 350, 300, ""
							+ SpriteTypeConstant.MONSTER_BAT, 15, 8,
					BehaviorTypeConstant.BEHAVIOR_NORMAL);
			i++;
			waveList.add(wave);

			wave = generateWaveV(i, wave.getBeginTime() + wave.getDuration(),
					300, 300, "" + SpriteTypeConstant.MONSTER_BAT + ","
							+ SpriteTypeConstant.MONSTER_BASIC, 5);
			i++;
			waveList.add(wave);

			wave = generateWaveRVP(i, wave.getBeginTime() + wave.getDuration(),
					290, 300, "" + SpriteTypeConstant.MONSTER_BAT + ","
							+ SpriteTypeConstant.MONSTER_BASIC);
			i++;
			waveList.add(wave);

			wave = generateWaveZigZagMid(i,
					wave.getBeginTime() + wave.getDuration(), 270, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC, 0, 6);
			i++;
			waveList.add(wave);

			wave = generateWaveLeftFastRightSlow(i,
					wave.getBeginTime() + wave.getDuration() + 2, 280, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC, 12, 8,
					BehaviorTypeConstant.BEHAVIOR_ACCELERATE);
			i++;
			waveList.add(wave);

			wave = generateWaveRightFastLeftSlow(i,
					wave.getBeginTime() + wave.getDuration(), 280, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC, 12, 8,
					BehaviorTypeConstant.BEHAVIOR_ZIGZAG_NORMAL);
			i++;
			waveList.add(wave);

			wave = generateWaveRandomRectangles(i,
					wave.getBeginTime() + wave.getDuration(), 400, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC, 12,
					BehaviorTypeConstant.BEHAVIOR_NORMAL_ZIGZAG, 4);
			i++;
			waveList.add(wave);

		} catch (Exception e) {

			e.printStackTrace();

		}
		return waveList;
	}

	public static ArrayList<Wave> generateWaveLevel22() {
		ArrayList<Wave> waveList = new ArrayList<Wave>();
		try {

			Long i = 0l;
			Wave wave = new Wave();
			wave.setBeginTime(0l);
			wave.setDuration(0);

			// creating WAVE---------------------------------------------

			wave = generateWaveRandomRectangles(i,
					wave.getBeginTime() + wave.getDuration(), 270, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC, 10,
					BehaviorTypeConstant.BEHAVIOR_NORMAL, 5);
			i++;
			waveList.add(wave);

			wave = generateWaveZigZagMid(i,
					wave.getBeginTime() + wave.getDuration(), 250, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC + ","
							+ SpriteTypeConstant.MONSTER_HOLE, 15, 8);
			i++;
			waveList.add(wave);

			wave = generateWaveCasita(i,
					wave.getBeginTime() + wave.getDuration(), 250, 300, ""
							+ SpriteTypeConstant.MONSTER_ARMOR, 15);
			i++;
			waveList.add(wave);

			wave = generateWaveRightFastLeftSlow(i,
					wave.getBeginTime() + wave.getDuration(), 250, 300, ""
							+ SpriteTypeConstant.MONSTER_ARMOR, 15, 10,
					BehaviorTypeConstant.BEHAVIOR_ZIGZAG_NORMAL);
			i++;
			waveList.add(wave);

			wave = generateWaveLeftFastRightSlow(i,
					wave.getBeginTime() + wave.getDuration(), 280, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC, 15, 10,
					BehaviorTypeConstant.BEHAVIOR_ZIGZAG_NORMAL);
			i++;
			waveList.add(wave);

			wave = generateWaveRandomRectangles(i,
					wave.getBeginTime() + wave.getDuration(), 270, 300, ""
							+ SpriteTypeConstant.MONSTER_HOLE, 10,
					BehaviorTypeConstant.BEHAVIOR_NORMAL, 4);
			i++;
			waveList.add(wave);

			wave = generateWaveZigZagMid(i,
					wave.getBeginTime() + wave.getDuration(), 350, 300, ""
							+ SpriteTypeConstant.MONSTER_HOLE, 10, 10);
			i++;
			waveList.add(wave);

		} catch (Exception e) {

			e.printStackTrace();

		}
		return waveList;
	}

	public static ArrayList<Wave> generateWaveLevel17() {
		ArrayList<Wave> waveList = new ArrayList<Wave>();
		try {

			Long i = 0l;
			Wave wave = new Wave();
			wave.setBeginTime(0l);
			wave.setDuration(0);

			// creating WAVE---------------------------------------------

			wave = generateWaveDIAGONALHuman(i,
					wave.getBeginTime() + wave.getDuration(), 270, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC);
			i++;
			waveList.add(wave);

			wave = generateWaveEquisHuman(i,
					wave.getBeginTime() + wave.getDuration(), 270, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC);
			i++;
			waveList.add(wave);

			wave = generateWaveHHHHuman(i,
					wave.getBeginTime() + wave.getDuration(), 270, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC);
			i++;
			waveList.add(wave);

			wave = generateWaveHPNZ(i,
					wave.getBeginTime() + wave.getDuration(), 270, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC);
			i++;
			waveList.add(wave);

			wave = generateWaveZigZagMid(i,
					wave.getBeginTime() + wave.getDuration(), 270, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC, 15, 20);
			i++;
			waveList.add(wave);

			wave = generateWaveEquisHuman(i,
					wave.getBeginTime() + wave.getDuration(), 320, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC);
			i++;
			waveList.add(wave);

			wave = generateWaveDIAGONALHuman(i,
					wave.getBeginTime() + wave.getDuration(), 270, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC);
			i++;
			waveList.add(wave);

			wave = generateWaveHPNZ(i,
					wave.getBeginTime() + wave.getDuration(), 270, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC);
			i++;
			waveList.add(wave);

		} catch (Exception e) {

			e.printStackTrace();

		}
		return waveList;
	}

	public static ArrayList<Wave> generateWaveLevel20() {
		ArrayList<Wave> waveList = new ArrayList<Wave>();
		try {

			Long i = 0l;
			Wave wave = new Wave();
			wave.setBeginTime(0l);
			wave.setDuration(0);

			// creating WAVE---------------------------------------------

			wave = generateWaveRandomRectangles(i,
					wave.getBeginTime() + wave.getDuration(), 270, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC, 10,
					BehaviorTypeConstant.BEHAVIOR_NORMAL, 6);
			i++;
			waveList.add(wave);

			wave = generateWaveZigZagMid(i,
					wave.getBeginTime() + wave.getDuration(), 250, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC + ","
							+ SpriteTypeConstant.MONSTER_HOLE, 15, 8);
			i++;
			waveList.add(wave);

			wave = generateWaveCasita(i,
					wave.getBeginTime() + wave.getDuration(), 250, 300, ""
							+ SpriteTypeConstant.MONSTER_ARMOR, 15);
			i++;
			waveList.add(wave);

			wave = generateWavePVZ(i, wave.getBeginTime() + wave.getDuration(),
					250, 300, "" + SpriteTypeConstant.MONSTER_BAT);
			i++;
			waveList.add(wave);

			wave = generateWaveRandomHuman(i,
					wave.getBeginTime() + wave.getDuration(), 250, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC + ","
							+ SpriteTypeConstant.MONSTER_BAT, 20);
			i++;
			waveList.add(wave);

			wave = generateWaveDiamantHuman(i,
					wave.getBeginTime() + wave.getDuration(), 250, 300, ""
							+ SpriteTypeConstant.MONSTER_ARMOR + ","
							+ SpriteTypeConstant.MONSTER_BASIC);
			i++;
			waveList.add(wave);

			wave = generateWaveLeftFastRightSlow(i,
					wave.getBeginTime() + wave.getDuration(), 250, 300, ""
							+ SpriteTypeConstant.MONSTER_ARMOR, 15, 10,
					BehaviorTypeConstant.BEHAVIOR_ZIGZAG_NORMAL);
			i++;
			waveList.add(wave);
		} catch (Exception e) {

			e.printStackTrace();

		}
		return waveList;
	}

	public static ArrayList<Wave> generateWaveLevel19() {
		ArrayList<Wave> waveList = new ArrayList<Wave>();
		try {

			Long i = 0l;
			Wave wave = new Wave();
			wave.setBeginTime(0l);
			wave.setDuration(0);

			// creating WAVE---------------------------------------------
			wave = generateWaveZigZagMid(i,
					wave.getBeginTime() + wave.getDuration(), 250, 300, ""
							+ SpriteTypeConstant.MONSTER_ZIGZAG, 15, 2);
			i++;
			waveList.add(wave);

			wave = generateWaveV(i, wave.getBeginTime() + wave.getDuration(),
					250, 300, "" + SpriteTypeConstant.MONSTER_BASIC, 20);
			i++;
			waveList.add(wave);

			wave = generateWaveEquis(i,
					wave.getBeginTime() + wave.getDuration(), 250, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC);
			i++;
			waveList.add(wave);

			wave = generateWaveROMBO(i,
					wave.getBeginTime() + wave.getDuration(), 200, 300, ""
							+ SpriteTypeConstant.MONSTER_ARMOR);
			i++;
			waveList.add(wave);

			wave = generateWaveRightFastLeftSlow(i,
					wave.getBeginTime() + wave.getDuration(), 450, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC, 80, 8,
					BehaviorTypeConstant.BEHAVIOR_ZIGZAG);
			i++;
			waveList.add(wave);

			wave = generateWaveZigZagMid(i,
					wave.getBeginTime() + wave.getDuration(), 350, 300, ""
							+ SpriteTypeConstant.MONSTER_ARMOR, 15, 10);
			i++;
			waveList.add(wave);

			wave = generateWave2C2CT(i,
					wave.getBeginTime() + wave.getDuration(), 250, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC, 25);
			i++;
			waveList.add(wave);

			wave = generateWaveZigZagMid(i,
					wave.getBeginTime() + wave.getDuration(), 350, 300, ""
							+ SpriteTypeConstant.MONSTER_ARMOR, 15, 10);
			i++;
			waveList.add(wave);

		} catch (Exception e) {

			e.printStackTrace();

		}
		return waveList;
	}

	public static ArrayList<Wave> generateWaveLevel16() {
		ArrayList<Wave> waveList = new ArrayList<Wave>();
		try {

			Long i = 0l;
			Wave wave = new Wave();
			wave.setBeginTime(0l);
			wave.setDuration(0);

			// creating WAVE---------------------------------------------

			wave = generateWaveCasita(i,
					wave.getBeginTime() + wave.getDuration(), 250, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC, 20);
			i++;
			waveList.add(wave);

			wave = generateWavePiramid(i,
					wave.getBeginTime() + wave.getDuration(), 250, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC, 10,
					BehaviorTypeConstant.BEHAVIOR_NORMAL);
			i++;
			waveList.add(wave);

			wave = generateWaveLeftFastRightSlow(i,
					wave.getBeginTime() + wave.getDuration(), 200, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC, 0, 16,
					BehaviorTypeConstant.BEHAVIOR_ZIGZAG);
			i++;
			waveList.add(wave);

			wave = generateWaveRightFastLeftSlow(i,
					wave.getBeginTime() + wave.getDuration(), 250, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC, 0, 12,
					BehaviorTypeConstant.BEHAVIOR_ACCELERATE);
			i++;
			waveList.add(wave);

			wave = generateWaveDiamant(i,
					wave.getBeginTime() + wave.getDuration(), 250, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC);
			i++;
			waveList.add(wave);

			wave = generateWave2C2CT(i,
					wave.getBeginTime() + wave.getDuration(), 250, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC, 25);
			i++;
			waveList.add(wave);

			wave = generateWaveRightFastLeftSlow(i,
					wave.getBeginTime() + wave.getDuration(), 450, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC, 100, 6,
					BehaviorTypeConstant.BEHAVIOR_ZIGZAG);
			i++;
			waveList.add(wave);

			wave = generateWaveRightFastLeftSlow(i,
					wave.getBeginTime() + wave.getDuration(), 350, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC, 0, 12,
					BehaviorTypeConstant.BEHAVIOR_ZIGZAG);
			i++;
			waveList.add(wave);

			wave = generateWaveBoss(i,
					wave.getBeginTime() + wave.getDuration(), 200, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC);
			i++;
			waveList.add(wave);
			
			

		} catch (Exception e) {

			e.printStackTrace();

		}
		return waveList;
	}

	public static ArrayList<Wave> generateWaveLevel18() {
		ArrayList<Wave> waveList = new ArrayList<Wave>();
		try {

			Long i = 0l;
			Wave wave = new Wave();
			wave.setBeginTime(0l);
			wave.setDuration(0);

			// creating WAVE---------------------------------------------
			wave = generateWaveDIAGONALHuman(i,
					wave.getBeginTime() + wave.getDuration(), 350, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC);
			i++;
			waveList.add(wave);

			wave = generateWavePiramid(i,
					wave.getBeginTime() + wave.getDuration(), 200, 300, ""
							+ SpriteTypeConstant.MONSTER_ARMOR, 0,
					BehaviorTypeConstant.BEHAVIOR_NORMAL);
			i++;
			waveList.add(wave);

			wave = generateWaveROMBOHuman(i,
					wave.getBeginTime() + wave.getDuration(), 200, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC);
			i++;
			waveList.add(wave);

			wave = generateWaveZIGZAG(i,
					wave.getBeginTime() + wave.getDuration(), 250, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC + ","
							+ SpriteTypeConstant.MONSTER_ARMOR);
			i++;
			waveList.add(wave);

			wave = generateWaveDiamant(i,
					wave.getBeginTime() + wave.getDuration(), 250, 300, ""
							+ SpriteTypeConstant.MONSTER_HOLE);
			i++;
			waveList.add(wave);

			wave = generateWave2C2CT(i,
					wave.getBeginTime() + wave.getDuration(), 250, 300, ""
							+ SpriteTypeConstant.MONSTER_BAT, 25);
			i++;
			waveList.add(wave);

			wave = generateWaveRightFastLeftSlow(i,
					wave.getBeginTime() + wave.getDuration(), 450, 300, ""
							+ SpriteTypeConstant.MONSTER_WITHOUT_ARMOR, 0, 8,
					BehaviorTypeConstant.BEHAVIOR_ZIGZAG);
			i++;
			waveList.add(wave);

			wave = generateWaveEquis(i,
					wave.getBeginTime() + wave.getDuration(), 350, 300, ""
							+ SpriteTypeConstant.MONSTER_ARMOR);
			i++;
			waveList.add(wave);

		} catch (Exception e) {

			e.printStackTrace();

		}
		return waveList;
	}

	public static ArrayList<Wave> generateWaveLevel21() {
		ArrayList<Wave> waveList = new ArrayList<Wave>();
		try {

			Long i = 0l;
			Wave wave = new Wave();
			wave.setBeginTime(0l);
			wave.setDuration(0);

			// creating WAVE---------------------------------------------
			wave = generateWaveCasita(i,
					wave.getBeginTime() + wave.getDuration(), 250, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC, 20);
			i++;
			waveList.add(wave);

			wave = generateWavePiramid(i,
					wave.getBeginTime() + wave.getDuration(), 250, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC, 10,
					BehaviorTypeConstant.BEHAVIOR_NORMAL);
			i++;
			waveList.add(wave);

			wave = generateWaveLeftFastRightSlow(i,
					wave.getBeginTime() + wave.getDuration(), 200, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC, 0, 16,
					BehaviorTypeConstant.BEHAVIOR_ZIGZAG);
			i++;
			waveList.add(wave);

			wave = generateWaveRightFastLeftSlow(i,
					wave.getBeginTime() + wave.getDuration(), 250, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC, 0, 12,
					BehaviorTypeConstant.BEHAVIOR_ACCELERATE);
			i++;
			waveList.add(wave);

			wave = generateWaveDiamant(i,
					wave.getBeginTime() + wave.getDuration(), 250, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC);
			i++;
			waveList.add(wave);

			wave = generateWave2C2CT(i,
					wave.getBeginTime() + wave.getDuration(), 250, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC, 25);
			i++;
			waveList.add(wave);

			wave = generateWaveRightFastLeftSlow(i,
					wave.getBeginTime() + wave.getDuration(), 450, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC, 100, 6,
					BehaviorTypeConstant.BEHAVIOR_ZIGZAG);
			i++;
			waveList.add(wave);

			wave = generateWaveRightFastLeftSlow(i,
					wave.getBeginTime() + wave.getDuration(), 350, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC, 0, 12,
					BehaviorTypeConstant.BEHAVIOR_ZIGZAG);
			i++;
			waveList.add(wave);

		} catch (Exception e) {

			e.printStackTrace();

		}
		return waveList;
	}

	public static ArrayList<Wave> generateWaveLevel3() {
		ArrayList<Wave> waveList = new ArrayList<Wave>();
		try {

			Long i = 0l;
			Wave wave = new Wave();
			wave.setBeginTime(0l);
			wave.setDuration(0);

			// creating WAVE---------------------------------------------
			wave = generateWaveH2TV(i,
					wave.getBeginTime() + wave.getDuration(), 250, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC, 0);
			i++;
			waveList.add(wave);

			wave = generateWaveVampireBat(i,
					wave.getBeginTime() + wave.getDuration(), 250, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC, 0);
			i++;
			waveList.add(wave);

			wave = generateWaveEquis(i,
					wave.getBeginTime() + wave.getDuration(), 250, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC);
			i++;
			waveList.add(wave);

		} catch (Exception e) {

			e.printStackTrace();

		}
		return waveList;
	}

	public static ArrayList<Wave> generateWaveLevel4() {
		ArrayList<Wave> waveList = new ArrayList<Wave>();
		try {

			Long i = 0l;
			Wave wave = new Wave();
			wave.setBeginTime(0l);
			wave.setDuration(0);

			// creating WAVE---------------------------------------------
			wave = generateWaveVampireBat(i,
					wave.getBeginTime() + wave.getDuration(), 250, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC, 0);
			i++;
			waveList.add(wave);

			wave = generateWaveDIAGONAL(i,
					wave.getBeginTime() + wave.getDuration(), 250, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC);
			i++;
			waveList.add(wave);

			wave = generateWaveLeftFastRightSlow(i,
					wave.getBeginTime() + wave.getDuration(), 250, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC, 0, 8,
					BehaviorTypeConstant.BEHAVIOR_NORMAL);
			i++;
			waveList.add(wave);

		} catch (Exception e) {

			e.printStackTrace();

		}
		return waveList;
	}

	public static ArrayList<Wave> generateWaveLevel5() {
		ArrayList<Wave> waveList = new ArrayList<Wave>();
		try {

			Long i = 0l;
			Wave wave = new Wave();
			wave.setBeginTime(0l);
			wave.setDuration(0);

			// creating WAVE---------------------------------------------

			wave = generateWaveLeftFastRightSlow(i,
					wave.getBeginTime() + wave.getDuration(), 250, 300, ""
							+ SpriteTypeConstant.MONSTER_BAT, 0, 8,
					BehaviorTypeConstant.BEHAVIOR_NORMAL);
			i++;
			waveList.add(wave);

			wave = generateWaveHHH(i, wave.getBeginTime() + wave.getDuration(),
					200, 300, "" + SpriteTypeConstant.MONSTER_BASIC);
			i++;
			waveList.add(wave);

			wave = generateWaveDiamant(i,
					wave.getBeginTime() + wave.getDuration(), 250, 300, ""
							+ SpriteTypeConstant.MONSTER_BAT + ","
							+ SpriteTypeConstant.MONSTER_BASIC);
			i++;
			waveList.add(wave);

			wave = generateWaveZigZagMid(i,
					wave.getBeginTime() + wave.getDuration(), 250, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC, 0, 8);
			i++;
			waveList.add(wave);

		} catch (Exception e) {

			e.printStackTrace();

		}
		return waveList;
	}

	public static ArrayList<Wave> generateWaveLevel6() {
		ArrayList<Wave> waveList = new ArrayList<Wave>();
		try {

			Long i = 0l;
			Wave wave = new Wave();
			wave.setBeginTime(0l);
			wave.setDuration(0);

			// creating WAVE---------------------------------------------

			wave = generateWaveZigZagMid(i,
					wave.getBeginTime() + wave.getDuration(), 270, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC, 0, 12);
			i++;
			waveList.add(wave);

			wave = generateWaveRightFastLeftSlow(i,
					wave.getBeginTime() + wave.getDuration(), 250, 300, ""
							+ SpriteTypeConstant.MONSTER_BAT, 15, 8,
					BehaviorTypeConstant.BEHAVIOR_NORMAL);
			i++;
			waveList.add(wave);

			wave = generateWaveV(i, wave.getBeginTime() + wave.getDuration(),
					250, 300, "" + SpriteTypeConstant.MONSTER_BAT + ","
							+ SpriteTypeConstant.MONSTER_BASIC, 5);
			i++;
			waveList.add(wave);

			wave = generateWaveRVP(i, wave.getBeginTime() + wave.getDuration(),
					250, 300, "" + SpriteTypeConstant.MONSTER_BAT + ","
							+ SpriteTypeConstant.MONSTER_BASIC);
			i++;
			waveList.add(wave);

		} catch (Exception e) {

			e.printStackTrace();

		}
		return waveList;
	}

	public static ArrayList<Wave> generateWaveLevel15() {
		ArrayList<Wave> waveList = new ArrayList<Wave>();
		try {

			Long i = 0l;
			Wave wave = new Wave();
			wave.setBeginTime(0l);
			wave.setDuration(0);

			// creating WAVE---------------------------------------------
			wave = generateWavePVHHuman(i,
					wave.getBeginTime() + wave.getDuration(), 320, 300, ""
							+ SpriteTypeConstant.MONSTER_BAT);
			i++;
			waveList.add(wave);

			wave = generateWaveRandom(i,
					wave.getBeginTime() + wave.getDuration(), 250, 300, ""
							+ SpriteTypeConstant.MONSTER_ARMOR);
			i++;
			waveList.add(wave);

			wave = generateWaveROMBOHuman(i,
					wave.getBeginTime() + wave.getDuration(), 320, 300, ""
							+ SpriteTypeConstant.MONSTER_BAT);
			i++;
			waveList.add(wave);

			wave = generateWaveEquis(i,
					wave.getBeginTime() + wave.getDuration(), 350, 300, ""
							+ SpriteTypeConstant.MONSTER_HOLE);
			i++;
			waveList.add(wave);

			wave = generateWaveHHH(i, wave.getBeginTime() + wave.getDuration(),
					320, 300, "" + SpriteTypeConstant.MONSTER_HOLE + ","
							+ SpriteTypeConstant.MONSTER_BAT);
			i++;
			waveList.add(wave);

			wave = generateWavePVZHuman(i,
					wave.getBeginTime() + wave.getDuration(), 320, 300, ""
							+ SpriteTypeConstant.MONSTER_ARMOR + ","
							+ SpriteTypeConstant.MONSTER_BASIC);
			i++;
			waveList.add(wave);

			wave = generateWavePZRHuman(i,
					wave.getBeginTime() + wave.getDuration(), 320, 300, ""
							+ SpriteTypeConstant.MONSTER_ARMOR + ","
							+ SpriteTypeConstant.MONSTER_BASIC);
			i++;
			waveList.add(wave);

			wave = generateWaveRightFastLeftSlow(i,
					wave.getBeginTime() + wave.getDuration(), 350, 300, ""
							+ SpriteTypeConstant.MONSTER_HOLE, 0, 12,
					BehaviorTypeConstant.BEHAVIOR_ZIGZAG);
			i++;
			waveList.add(wave);

		} catch (Exception e) {

			e.printStackTrace();

		}
		return waveList;
	}

	public static ArrayList<Wave> generateWaveLevel14() {
		ArrayList<Wave> waveList = new ArrayList<Wave>();
		try {

			Long i = 0l;
			Wave wave = new Wave();
			wave.setBeginTime(0l);
			wave.setDuration(0);

			// creating WAVE---------------------------------------------
			wave = generateWaveROMBOHuman(i,
					wave.getBeginTime() + wave.getDuration(), 300, 300, ""
							+ SpriteTypeConstant.MONSTER_BAT);
			i++;
			waveList.add(wave);

			wave = generateWaveZIGZAGHuman(i,
					wave.getBeginTime() + wave.getDuration() + 2, 250, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC + ","
							+ SpriteTypeConstant.MONSTER_BAT);
			i++;
			waveList.add(wave);
			
			wave = generateWaveRandom(i,
					wave.getBeginTime() + wave.getDuration(), 350, 300, ""
							+ SpriteTypeConstant.MONSTER_ARMOR);
			i++;
			waveList.add(wave);

			wave = generateWaveDIAGONAL(i,
					wave.getBeginTime() + wave.getDuration() + 4, 270, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC + ","
							+ SpriteTypeConstant.MONSTER_BAT);
			i++;
			waveList.add(wave);

			wave = generateWaveZIGZAGHuman(i,
					wave.getBeginTime() + wave.getDuration() + 2, 250, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC + ","
							+ SpriteTypeConstant.MONSTER_BAT);
			i++;
			waveList.add(wave);
			
			wave = generateWaveZIGZAGHuman(i,
					wave.getBeginTime() + wave.getDuration() + 2, 250, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC + ","
							+ SpriteTypeConstant.MONSTER_BAT);
			i++;
			waveList.add(wave);
			
			wave = generateWaveEquis(i,
					wave.getBeginTime() + wave.getDuration(), 350, 300, ""
							+ SpriteTypeConstant.MONSTER_HOLE);
			i++;
			waveList.add(wave);

			wave = generateWaveEquisHuman(i,
					wave.getBeginTime() + wave.getDuration(), 250, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC + ","
							+ SpriteTypeConstant.MONSTER_BAT);
			i++;
			waveList.add(wave);

			wave = generateWaveHHH(i, wave.getBeginTime() + wave.getDuration(),
					320, 300, "" + SpriteTypeConstant.MONSTER_HOLE + ","
							+ SpriteTypeConstant.MONSTER_BAT);
			i++;
			waveList.add(wave);

		} catch (Exception e) {

			e.printStackTrace();

		}
		return waveList;
	}

	public static ArrayList<Wave> generateWaveLevel13() {
		ArrayList<Wave> waveList = new ArrayList<Wave>();
		try {

			Long i = 0l;
			Wave wave = new Wave();
			wave.setBeginTime(0l);
			wave.setDuration(0);

			// creating WAVE---------------------------------------------
			wave = generateWavePZRHuman(i,
					wave.getBeginTime() + wave.getDuration(), 270, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC);
			i++;
			waveList.add(wave);

			wave = generateWaveRandom(i,
					wave.getBeginTime() + wave.getDuration(), 350, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC + ","
							+ SpriteTypeConstant.MONSTER_HOLE);
			i++;
			waveList.add(wave);

			wave = generateWaveROMBO(i,
					wave.getBeginTime() + wave.getDuration() + 4, 270, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC + ","
							+ SpriteTypeConstant.MONSTER_BAT);
			i++;
			waveList.add(wave);

			wave = generateWaveRandom(i,
					wave.getBeginTime() + wave.getDuration(), 350, 300, ""
							+ SpriteTypeConstant.MONSTER_HOLE);
			i++;
			waveList.add(wave);

			wave = generateWaveZIGZAGHuman(i,
					wave.getBeginTime() + wave.getDuration() + 2, 250, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC + ","
							+ SpriteTypeConstant.MONSTER_BAT);
			i++;
			waveList.add(wave);

			wave = generateWaveRVP(i, wave.getBeginTime() + wave.getDuration()
					+ 2, 270, 300, "" + SpriteTypeConstant.MONSTER_BASIC + ","
					+ SpriteTypeConstant.MONSTER_BAT);
			i++;
			waveList.add(wave);

			wave = generateWaveZIGZAG(i,
					wave.getBeginTime() + wave.getDuration(), 350, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC + ","
							+ SpriteTypeConstant.MONSTER_BAT);
			i++;
			waveList.add(wave);

		} catch (Exception e) {

			e.printStackTrace();

		}
		return waveList;
	}

	public static ArrayList<Wave> generateWaveLevel11() {
		ArrayList<Wave> waveList = new ArrayList<Wave>();
		try {

			Long i = 0l;
			Wave wave = new Wave();
			wave.setBeginTime(0l);
			wave.setDuration(0);

			// creating WAVE---------------------------------------------
			wave = generateWaveHPNZ(i,
					wave.getBeginTime() + wave.getDuration(), 270, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC);
			i++;
			waveList.add(wave);

			wave = generateWaveRandom(i,
					wave.getBeginTime() + wave.getDuration(), 200, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC + ","
							+ SpriteTypeConstant.MONSTER_HOLE);
			i++;
			waveList.add(wave);

			wave = generateWaveDiamantHuman(i,
					wave.getBeginTime() + wave.getDuration() + 4, 250, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC + ","
							+ SpriteTypeConstant.MONSTER_BAT);
			i++;
			waveList.add(wave);

			wave = generateWaveRandom(i,
					wave.getBeginTime() + wave.getDuration(), 200, 300, ""
							+ SpriteTypeConstant.MONSTER_HOLE);
			i++;
			waveList.add(wave);

			wave = generateWaveZIGZAGHuman(i,
					wave.getBeginTime() + wave.getDuration(), 250, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC + ","
							+ SpriteTypeConstant.MONSTER_BAT);
			i++;
			waveList.add(wave);

		} catch (Exception e) {

			e.printStackTrace();

		}
		return waveList;
	}

	public static ArrayList<Wave> generateWaveLevel12() {
		ArrayList<Wave> waveList = new ArrayList<Wave>();
		try {

			Long i = 0l;
			Wave wave = new Wave();
			wave.setBeginTime(0l);
			wave.setDuration(0);

			// creating WAVE---------------------------------------------
			wave = generateWaveHHH(i, wave.getBeginTime() + wave.getDuration(),
					350, 300, "" + SpriteTypeConstant.MONSTER_BASIC);
			i++;
			waveList.add(wave);

			wave = generateWaveHHumanV_VRapido(i,
					wave.getBeginTime() + wave.getDuration(), 200, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC + ","
							+ SpriteTypeConstant.MONSTER_HOLE + ","
							+ SpriteTypeConstant.MONSTER_BAT);
			i++;
			waveList.add(wave);

			wave = generateWaveHPNZ(i,
					wave.getBeginTime() + wave.getDuration(), 200, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC + ","
							+ SpriteTypeConstant.MONSTER_ARMOR + ","
							+ SpriteTypeConstant.MONSTER_BAT);
			i++;
			waveList.add(wave);

			
			wave = generateWaveRandomHuman(i,
					wave.getBeginTime() + wave.getDuration() + 4, 250, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC + ","
							+ SpriteTypeConstant.MONSTER_HOLE, 16);
			i++;
			waveList.add(wave);

			wave = generateWaveDIAGONAL(i,
					wave.getBeginTime() + wave.getDuration() + 4, 250, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC + ","
							+ SpriteTypeConstant.MONSTER_BAT);
			i++;
			waveList.add(wave);

			wave = generateWaveDiamant(i,
					wave.getBeginTime() + wave.getDuration() + 4, 250, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC + ","
							+ SpriteTypeConstant.MONSTER_BAT);
			i++;
			waveList.add(wave);

		} catch (Exception e) {

			e.printStackTrace();

		}
		return waveList;
	}

	public static ArrayList<Wave> generateWaveLevel9() {
		ArrayList<Wave> waveList = new ArrayList<Wave>();
		try {

			Long i = 0l;
			Wave wave = new Wave();
			wave.setBeginTime(0l);
			wave.setDuration(0);

			// creating WAVE---------------------------------------------
			wave = generateWaveRVP(i, wave.getBeginTime() + wave.getDuration(),
					250, 300, "" + SpriteTypeConstant.MONSTER_BASIC);
			i++;
			waveList.add(wave);

			wave = generateWaveRandom(i,
					wave.getBeginTime() + wave.getDuration(), 250, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC + ","
							+ SpriteTypeConstant.MONSTER_HOLE);
			i++;
			waveList.add(wave);

			wave = generateWaveROMBO(i,
					wave.getBeginTime() + wave.getDuration() + 2, 250, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC);
			i++;
			waveList.add(wave);

			wave = generateWaveEquis(i,
					wave.getBeginTime() + wave.getDuration(), 250, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC);
			i++;
			waveList.add(wave);

		} catch (Exception e) {

			e.printStackTrace();

		}
		return waveList;
	}

	public static ArrayList<Wave> generateWaveLevel7() {
		ArrayList<Wave> waveList = new ArrayList<Wave>();
		try {

			Long i = 0l;
			Wave wave = new Wave();
			wave.setBeginTime(0l);
			wave.setDuration(0);

			// creating WAVE---------------------------------------------
			wave = generateWaveLeftFastRightSlow(i,
					wave.getBeginTime() + wave.getDuration(), 250, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC, 0, 8,
					BehaviorTypeConstant.BEHAVIOR_ZIGZAG);
			i++;
			waveList.add(wave);

			wave = generateWaveLeftFastRightSlow(i,
					wave.getBeginTime() + wave.getDuration(), 250, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC, 0, 8,
					BehaviorTypeConstant.BEHAVIOR_ACCELERATE);
			i++;
			waveList.add(wave);

			wave = generateWaveLeftFastRightSlow(i,
					wave.getBeginTime() + wave.getDuration(), 250, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC, 0, 8,
					BehaviorTypeConstant.BEHAVIOR_NORMAL_ZIGZAG);
			i++;
			waveList.add(wave);

			wave = generateWaveZigZagMid(i,
					wave.getBeginTime() + wave.getDuration(), 250, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC, 0, 7);
			i++;
			waveList.add(wave);

		} catch (Exception e) {

			e.printStackTrace();

		}
		return waveList;
	}

	public static ArrayList<Wave> generateWaveLevel8() {
		ArrayList<Wave> waveList = new ArrayList<Wave>();
		try {

			Long i = 0l;
			Wave wave = new Wave();
			wave.setBeginTime(0l);
			wave.setDuration(0);

			// creating WAVE---------------------------------------------
			wave = generateWaveDiamantHuman(i,
					wave.getBeginTime() + wave.getDuration(), 250, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC);
			i++;
			waveList.add(wave);

			wave = generateWaveRandom(i,
					wave.getBeginTime() + wave.getDuration(), 250, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC + ","
							+ SpriteTypeConstant.MONSTER_HOLE);
			i++;
			waveList.add(wave);

			wave = generateWaveEquisHuman(i,
					wave.getBeginTime() + wave.getDuration() + 2, 250, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC);
			i++;
			waveList.add(wave);

			wave = generateWaveHPNZ(i,
					wave.getBeginTime() + wave.getDuration(), 250, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC + ","
							+ SpriteTypeConstant.MONSTER_HOLE);
			i++;
			waveList.add(wave);

		} catch (Exception e) {

			e.printStackTrace();

		}
		return waveList;
	}

	public static ArrayList<Wave> generateWaveLevel10() {
		ArrayList<Wave> waveList = new ArrayList<Wave>();
		try {

			Long i = 0l;
			Wave wave = new Wave();
			wave.setBeginTime(0l);
			wave.setDuration(0);

			// creating WAVE---------------------------------------------
			wave = generateWaveZIGZAG(i,
					wave.getBeginTime() + wave.getDuration(), 270, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC);
			i++;
			waveList.add(wave);

			wave = generateWaveRandom(i,
					wave.getBeginTime() + wave.getDuration(), 270, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC);
			i++;
			waveList.add(wave);

			wave = generateWaveHHH(i, wave.getBeginTime() + wave.getDuration()
					+ 2, 250, 300, "" + SpriteTypeConstant.MONSTER_BAT);
			i++;
			waveList.add(wave);

			wave = generateWaveDiamant(i,
					wave.getBeginTime() + wave.getDuration(), 250, 300, ""
							+ SpriteTypeConstant.MONSTER_BASIC + ","
							+ SpriteTypeConstant.MONSTER_HOLE);
			i++;
			waveList.add(wave);

		} catch (Exception e) {

			e.printStackTrace();

		}
		return waveList;
	}

	private static Wave generateWaveRandom(Long id, Long beginTime, int speed,
			int speedX, String monsterType) {
		Wave wave = new Wave();
		try {
			wave.setId(id);
			wave.setActive(false);
			wave.setBeginTime(beginTime);
			beginTime = 0L;

			int speedAux = (int) (Float.valueOf(speed) / 50f);

			Behavior behavior = null;
			behavior = new Behavior();
			behavior.setSpeed(speed);
			behavior.setBeginTime(0);
			behavior.setBehaviorType(BehaviorTypeConstant.BEHAVIOR_NORMAL);
			behavior.setMonsterType(monsterType);
			behavior.setProbabilityHuman(0);
			behavior.generateMonsterTypeList();
			wave.setSpriteList(behavior.generateRandomVampire(10, 8));

			wave.setDuration(8);

		} catch (Exception e) {

			e.printStackTrace();
		}
		return wave;
	}

	private static Wave generateWaveRandomHuman(Long id, Long beginTime,
			int speed, int speedX, String monsterType, int probabilityHuman) {
		Wave wave = new Wave();
		try {
			wave.setId(id);
			wave.setActive(false);
			wave.setBeginTime(beginTime);
			beginTime = 0L;

			int speedAux = (int) (Float.valueOf(speed) / 50f);

			Behavior behavior = null;
			behavior = new Behavior();
			behavior.setSpeed(speed);
			behavior.setBeginTime(0);
			behavior.setBehaviorType(BehaviorTypeConstant.BEHAVIOR_NORMAL);
			behavior.setMonsterType(monsterType);
			behavior.setProbabilityHuman(probabilityHuman);
			behavior.generateMonsterTypeList();
			wave.setSpriteList(behavior.generateRandomVampire(10, 6));

			wave.setDuration(8);

		} catch (Exception e) {

			e.printStackTrace();
		}
		return wave;
	}

	private static Wave generateWavePZR(Long id, Long beginTime, int speed,
			int speedX, String monsterType) {
		Wave wave = new Wave();
		try {
			wave.setId(id);
			wave.setActive(false);
			wave.setBeginTime(beginTime);
			beginTime = 0L;

			int speedAux = (int) (Float.valueOf(speed) / 50f);

			Behavior behavior = null;
			behavior = new Behavior();
			behavior.setSpeed(speed);
			behavior.setBeginTime(beginTime);
			behavior.setBehaviorType(BehaviorTypeConstant.BEHAVIOR_NORMAL);
			behavior.setMonsterType(monsterType);
			behavior.setProbabilityHuman(0);
			behavior.generateMonsterTypeList();
			wave.setSpriteList(behavior.generateTriangle(0, 4, speed, false,
					speedAux));

			behavior = new Behavior();
			behavior.setSpeed(speed);
			behavior.setBeginTime(beginTime + 4);
			behavior.setBehaviorType(BehaviorTypeConstant.BEHAVIOR_ZIGZAG);
			behavior.setMonsterType(monsterType);
			behavior.setProbabilityHuman(0);
			behavior.generateMonsterTypeList();
			wave.setSpriteList(behavior.generateVertical(speed, 5, 2, 4));

			behavior = new Behavior();
			behavior.setSpeed(speed);
			behavior.setBeginTime(beginTime + 12);
			behavior.setBehaviorType(BehaviorTypeConstant.BEHAVIOR_NORMAL);
			behavior.setMonsterType(monsterType);
			behavior.setProbabilityHuman(0);
			behavior.generateMonsterTypeList();
			wave.setSpriteList(behavior.generateRandomVampire(10, 5));

			wave.setDuration(14);

		} catch (Exception e) {

			e.printStackTrace();
		}
		return wave;
	}

	private static Wave generateWaveHPNZ(Long id, Long beginTime, int speed,
			int speedX, String monsterType) {
		Wave wave = new Wave();
		try {
			wave.setId(id);
			wave.setActive(false);
			wave.setBeginTime(beginTime);
			beginTime = 0L;
			beginTime = 0L;

			int speedAux = (int) (Float.valueOf(speed) / 50f);

			Behavior behavior = null;

			behavior = new Behavior();
			behavior.setSpeed(speed);
			behavior.setBeginTime(beginTime);
			behavior.setBehaviorType(BehaviorTypeConstant.BEHAVIOR_NORMAL);
			behavior.setMonsterType(monsterType);
			behavior.setProbabilityHuman(0);
			behavior.generateMonsterTypeList();
			wave.setSpriteList(behavior.generateHorizontal(speed, 4, 3));

			behavior = new Behavior();
			behavior.setSpeed(speed);
			behavior.setBeginTime(beginTime + 4);
			behavior.setBehaviorType(BehaviorTypeConstant.BEHAVIOR_NORMAL);
			behavior.setMonsterType(monsterType);
			behavior.setProbabilityHuman(0);
			behavior.generateMonsterTypeList();
			wave.setSpriteList(behavior.generateTriangle(1, 3, speed, true,
					speedAux));

			behavior = new Behavior();
			behavior.setSpeed(speed);
			behavior.setBeginTime(beginTime + 8);
			behavior.setBehaviorType(BehaviorTypeConstant.BEHAVIOR_NORMAL_ZIGZAG);
			behavior.setMonsterType(monsterType);
			behavior.setProbabilityHuman(0);
			behavior.generateMonsterTypeList();
			wave.setSpriteList(behavior.generateVertical(speed, 6, 3, speedAux));

			wave.setDuration(18);

		} catch (Exception e) {

			e.printStackTrace();
		}
		return wave;
	}

	private static Wave generateWaveRVP(Long id, Long beginTime, int speed,
			int speedX, String monsterType) {
		Wave wave = new Wave();
		try {
			wave.setId(id);
			wave.setActive(false);
			wave.setBeginTime(beginTime);
			beginTime = 0L;

			int speedAux = (int) (Float.valueOf(speed) / 50f);

			Behavior behavior = null;

			behavior = new Behavior();
			behavior.setSpeed(speed);
			behavior.setBeginTime(beginTime);
			behavior.setBehaviorType(BehaviorTypeConstant.BEHAVIOR_NORMAL);
			behavior.setMonsterType(monsterType);
			behavior.setProbabilityHuman(0);
			behavior.generateMonsterTypeList();
			wave.setSpriteList(behavior.generateRandomVampire(10, 5));

			behavior = new Behavior();
			behavior.setSpeed(speed);
			behavior.setBeginTime(beginTime + 6);
			behavior.setBehaviorType(BehaviorTypeConstant.BEHAVIOR_ZIGZAG);
			behavior.setMonsterType(monsterType);
			behavior.setProbabilityHuman(0);
			behavior.generateMonsterTypeList();
			wave.setSpriteList(behavior.generateVertical(speed, 5, 3, speedAux));

			behavior = new Behavior();
			behavior.setSpeed(speed);
			behavior.setBeginTime(beginTime + 6);
			behavior.setBehaviorType(BehaviorTypeConstant.BEHAVIOR_ZIGZAG);
			behavior.setMonsterType(monsterType);
			behavior.setProbabilityHuman(0);
			behavior.generateMonsterTypeList();

			wave.setSpriteList(behavior.generateVertical(speed, 3, 3, speedAux,
					false));

			behavior = new Behavior();
			behavior.setSpeed(speed);
			behavior.setBeginTime(beginTime + 16);
			behavior.setBehaviorType(BehaviorTypeConstant.BEHAVIOR_NORMAL);
			behavior.setMonsterType(monsterType);
			behavior.setProbabilityHuman(0);
			behavior.generateMonsterTypeList();
			wave.setSpriteList(behavior.generateTriangle(0, 4, speed, false,
					speedAux));

			wave.setDuration(18);

		} catch (Exception e) {

			e.printStackTrace();
		}
		return wave;
	}

	private static Wave generateWaveHHumanV_VRapido(Long id, Long beginTime,
			int speed, int speedX, String monsterType) {
		Wave wave = new Wave();
		try {
			wave.setId(id);
			wave.setActive(false);
			wave.setBeginTime(beginTime);
			beginTime = 0L;

			int speedAux = (int) (Float.valueOf(speed) / 50f);

			Behavior behavior = null;

			behavior = new Behavior();
			behavior.setSpeed(speed);
			behavior.setBeginTime(beginTime);
			behavior.setBehaviorType(BehaviorTypeConstant.BEHAVIOR_NORMAL);
			behavior.setMonsterType(monsterType);
			behavior.setProbabilityHuman(0);
			behavior.generateMonsterTypeList();
			wave.setSpriteList(behavior.generateHorizontal(speed, 3, 1));

			behavior = new Behavior();
			behavior.setSpeed(speed);
			behavior.setBeginTime(beginTime + speedAux / 10f);
			behavior.setBehaviorType(BehaviorTypeConstant.BEHAVIOR_NORMAL);
			behavior.setMonsterType(monsterType);
			behavior.setProbabilityHuman(35);
			behavior.generateMonsterTypeList();
			wave.setSpriteList(behavior.generateHorizontal(speed, 3, 1));

			behavior = new Behavior();
			behavior.setSpeed(speed);
			behavior.setBeginTime(beginTime + speedAux / 10f * 2);
			behavior.setBehaviorType(BehaviorTypeConstant.BEHAVIOR_NORMAL);
			behavior.setMonsterType(monsterType);
			behavior.setProbabilityHuman(0);
			behavior.generateMonsterTypeList();
			wave.setSpriteList(behavior.generateHorizontal(speed, 3, 1));

			behavior = new Behavior();
			behavior.setSpeed(speed);
			behavior.setBeginTime(beginTime + 8);
			behavior.setBehaviorType(BehaviorTypeConstant.BEHAVIOR_ZIGZAG);
			behavior.setMonsterType(monsterType);
			behavior.setProbabilityHuman(0);
			behavior.generateMonsterTypeList();
			wave.setSpriteList(behavior.generateVertical(speed, 2, 4, speedAux));

			behavior = new Behavior();
			behavior.setSpeed(speed);
			behavior.setBeginTime(beginTime + 8);
			behavior.setBehaviorType(BehaviorTypeConstant.BEHAVIOR_ZIGZAG);
			behavior.setMonsterType(monsterType);
			behavior.setProbabilityHuman(0);
			behavior.generateMonsterTypeList();
			wave.setSpriteList(behavior.generateVertical(speed, 2, 4, speedAux,
					false));

			behavior = new Behavior();
			behavior.setSpeed(speed);
			behavior.setBeginTime(beginTime + 15);
			behavior.setBehaviorType(BehaviorTypeConstant.BEHAVIOR_ZIGZAG);
			behavior.setMonsterType(monsterType);
			behavior.setProbabilityHuman(0);
			behavior.generateMonsterTypeList();
			wave.setSpriteList(behavior.generateRandomVampire(12, 3));

			wave.setDuration(18);

		} catch (Exception e) {

			e.printStackTrace();
		}
		return wave;
	}

	private static Wave generateWavePVH(Long id, Long beginTime, int speed,
			int speedX, String monsterType) {
		Wave wave = new Wave();
		try {
			wave.setId(id);
			wave.setActive(false);
			wave.setBeginTime(beginTime);
			beginTime = 0L;

			int speedAux = (int) (Float.valueOf(speed) / 50f);

			Behavior behavior = null;

			behavior = new Behavior();
			behavior.setSpeed(speed);
			behavior.setBeginTime(beginTime);
			behavior.setBehaviorType(BehaviorTypeConstant.BEHAVIOR_ZIGZAG);
			behavior.setMonsterType(monsterType);
			behavior.setProbabilityHuman(100);
			behavior.generateMonsterTypeList();
			wave.setSpriteList(behavior.generateHorizontal(speed, 1, 1));

			behavior = new Behavior();
			behavior.setSpeed(speed);
			behavior.setBeginTime(beginTime + 0.5f);
			behavior.setBehaviorType(BehaviorTypeConstant.BEHAVIOR_NORMAL);
			behavior.setMonsterType(monsterType);
			behavior.setProbabilityHuman(0);
			behavior.generateMonsterTypeList();
			wave.setSpriteList(behavior.generateTriangle(0, 3, speedX, false,
					speedAux));

			behavior = new Behavior();
			behavior.setSpeed(speed);
			behavior.setBeginTime(beginTime + 5);
			behavior.setBehaviorType(BehaviorTypeConstant.BEHAVIOR_NORMAL);
			behavior.setMonsterType(monsterType);
			behavior.setProbabilityHuman(0);
			behavior.generateMonsterTypeList();
			wave.setSpriteList(behavior.generateVertical(speed, 6, 3, speedAux));

			behavior = new Behavior();
			behavior.setSpeed(speed);
			behavior.setBeginTime(beginTime + 12);
			behavior.setBehaviorType(BehaviorTypeConstant.BEHAVIOR_NORMAL);
			behavior.setMonsterType(monsterType);
			behavior.setProbabilityHuman(0);
			behavior.generateMonsterTypeList();
			wave.setSpriteList(behavior.generateHorizontal(speed, 3, 3));

			wave.setDuration(15);

		} catch (Exception e) {

			e.printStackTrace();
		}
		return wave;
	}

	private static Wave generateWavePVZ(Long id, Long beginTime, int speed,
			int speedX, String monsterType) {
		Wave wave = new Wave();
		try {
			wave.setId(id);
			wave.setActive(false);
			wave.setBeginTime(beginTime);
			beginTime = 0L;

			int speedAux = (int) (Float.valueOf(speed) / 50f);

			Behavior behavior = null;

			behavior = new Behavior();
			behavior.setSpeed(speed);
			behavior.setBeginTime(beginTime);
			behavior.setBehaviorType(BehaviorTypeConstant.BEHAVIOR_NORMAL);
			behavior.setMonsterType(monsterType);
			behavior.setProbabilityHuman(0);
			behavior.generateMonsterTypeList();
			wave.setSpriteList(behavior.generateTriangle(0, 3, speed, true,
					speedAux));

			behavior = new Behavior();
			behavior.setSpeed(speed);
			behavior.setBeginTime(beginTime + 3);
			behavior.setBehaviorType(BehaviorTypeConstant.BEHAVIOR_ZIGZAG_NORMAL);
			behavior.setMonsterType(monsterType);
			behavior.setProbabilityHuman(15);
			behavior.generateMonsterTypeList();
			wave.setSpriteList(behavior.generateVertical(speed, 5, 3, speedAux));

			behavior = new Behavior();
			behavior.setSpeed(speed);
			behavior.setBeginTime(beginTime + 10);
			behavior.setBehaviorType(BehaviorTypeConstant.BEHAVIOR_NORMAL);
			behavior.setMonsterType(monsterType);
			behavior.setProbabilityHuman(25);
			behavior.generateMonsterTypeList();
			wave.setSpriteList(behavior.generateRandomVampire(12, 3));

			wave.setDuration(14);

		} catch (Exception e) {

			e.printStackTrace();
		}
		return wave;
	}

	private static Wave generateWaveHHH(Long id, Long beginTime, int speed,
			int speedX, String monsterType) {
		Wave wave = new Wave();
		try {
			wave.setId(id);
			wave.setActive(false);
			wave.setBeginTime(beginTime);
			beginTime = 0L;

			int speedAux = (int) (Float.valueOf(speed) / 50f);

			Behavior behavior = null;

			behavior = new Behavior();
			behavior.setSpeed(speed);
			behavior.setBeginTime(beginTime);
			behavior.setBehaviorType(BehaviorTypeConstant.BEHAVIOR_NORMAL);
			behavior.setMonsterType(monsterType);
			behavior.setProbabilityHuman(0);
			behavior.generateMonsterTypeList();
			wave.setSpriteList(behavior.generateHorizontal(speed, 3, 1));

			behavior = new Behavior();
			behavior.setSpeed(speed);
			behavior.setBeginTime(beginTime + speedAux / 10f);
			behavior.setBehaviorType(BehaviorTypeConstant.BEHAVIOR_NORMAL);
			behavior.setMonsterType(monsterType);
			behavior.setProbabilityHuman(35);
			behavior.generateMonsterTypeList();
			wave.setSpriteList(behavior.generateHorizontal(speed, 3, 1));

			behavior = new Behavior();
			behavior.setSpeed(speed);
			behavior.setBeginTime(beginTime + speedAux / 10f * 2);
			behavior.setBehaviorType(BehaviorTypeConstant.BEHAVIOR_NORMAL);
			behavior.setMonsterType(monsterType);
			behavior.setProbabilityHuman(0);
			behavior.generateMonsterTypeList();
			wave.setSpriteList(behavior.generateHorizontal(speed, 3, 1));

			behavior = new Behavior();
			behavior.setSpeed(speed);
			behavior.setBeginTime(beginTime + speedAux / 10f * 2 + 2);
			behavior.setBehaviorType(BehaviorTypeConstant.BEHAVIOR_NORMAL);
			behavior.setMonsterType(monsterType);
			behavior.setProbabilityHuman(8);
			behavior.generateMonsterTypeList();
			wave.setSpriteList(behavior.generateHorizontal(speed, 3, 6));

			wave.setDuration(10);

		} catch (Exception e) {

			e.printStackTrace();
		}
		return wave;
	}

	private static Wave generateWaveROMBO(Long id, Long beginTime, int speed,
			int speedX, String monsterType) {
		Wave wave = new Wave();
		try {
			wave.setId(id);
			wave.setActive(false);
			wave.setBeginTime(beginTime);
			beginTime = 0L;

			beginTime = 0L;
			int speedAux = (int) (Float.valueOf(speed) / 50f);

			Behavior behavior = null;

			behavior = new Behavior();
			behavior.setSpeed(speed);
			behavior.setBeginTime(beginTime);
			behavior.setBehaviorType(BehaviorTypeConstant.BEHAVIOR_NORMAL);
			behavior.setMonsterType(monsterType);
			behavior.setProbabilityHuman(0);
			behavior.generateMonsterTypeList();
			wave.setSpriteList(behavior.generateTriangle(0, 4, speed, false,
					speedAux));

			behavior = new Behavior();
			behavior.setSpeed(speed);
			behavior.setBeginTime(beginTime + (speedAux / 10f * 5));
			behavior.setBehaviorType(BehaviorTypeConstant.BEHAVIOR_NORMAL);
			behavior.setMonsterType(monsterType);
			behavior.setProbabilityHuman(0);
			behavior.generateMonsterTypeList();
			wave.setSpriteList(behavior.generateTriangle(0, 4, speed, true,
					speedAux));

			wave.setDuration(8);

		} catch (Exception e) {

			e.printStackTrace();
		}
		return wave;
	}

	private static Wave generateWaveZIGZAG(Long id, Long beginTime, int speed,
			int speedX, String monsterType) {
		Wave wave = new Wave();
		try {
			wave.setId(id);
			wave.setActive(false);
			wave.setBeginTime(beginTime);
			beginTime = 0L;

			int speedAux = (int) (Float.valueOf(speed) / 50f);

			Behavior behavior = null;

			behavior = new Behavior();
			behavior.setSpeed(speed);
			behavior.setBeginTime(beginTime);
			behavior.setBehaviorType(BehaviorTypeConstant.BEHAVIOR_ZIGZAG);
			behavior.setMonsterType(monsterType);
			behavior.setProbabilityHuman(0);
			behavior.generateMonsterTypeList();
			wave.setSpriteList(behavior.generateVertical(speed, 4, 2, speedAux));

			behavior = new Behavior();
			behavior.setSpeed(speed);
			behavior.setBeginTime(beginTime);
			behavior.setBehaviorType(BehaviorTypeConstant.BEHAVIOR_ZIGZAG);
			behavior.setMonsterType(monsterType);
			behavior.setProbabilityHuman(0);
			behavior.generateMonsterTypeList();
			wave.setSpriteList(behavior.generateVertical(speed, 4, 2, speedAux,
					false));

			behavior = new Behavior();
			behavior.setSpeed(speed);
			behavior.setBeginTime(beginTime + 6);
			behavior.setBehaviorType(BehaviorTypeConstant.BEHAVIOR_ZIGZAG);
			behavior.setMonsterType(monsterType);
			behavior.setProbabilityHuman(0);
			behavior.generateMonsterTypeList();
			wave.setSpriteList(behavior.generateVertical(speed, 4, 2, speedAux));

			behavior = new Behavior();
			behavior.setSpeed(speed);
			behavior.setBeginTime(beginTime + 6);
			behavior.setBehaviorType(BehaviorTypeConstant.BEHAVIOR_ZIGZAG);
			behavior.setMonsterType(monsterType);
			behavior.setProbabilityHuman(0);
			behavior.generateMonsterTypeList();
			wave.setSpriteList(behavior.generateVertical(speed, 4, 2, speedAux,
					false));

			wave.setDuration(8);

		} catch (Exception e) {

			e.printStackTrace();
		}
		return wave;
	}

	private static Wave generateWaveDIAGONAL(Long id, Long beginTime,
			int speed, int speedX, String monsterType) {
		Wave wave = new Wave();
		try {
			wave.setId(id);
			wave.setActive(false);
			wave.setBeginTime(beginTime);
			beginTime = 0L;

			int speedAux = (int) (Float.valueOf(speed) / 50f);

			Behavior behavior = null;

			behavior = new Behavior();
			behavior.setSpeed(speed);
			behavior.setBeginTime(beginTime);
			behavior.setBehaviorType(BehaviorTypeConstant.BEHAVIOR_NORMAL);
			behavior.setMonsterType(monsterType);
			behavior.setProbabilityHuman(0);
			behavior.generateMonsterTypeList();
			wave.setSpriteList(behavior.generateDiagonaLine(speed, 4, 1,
					speedAux));

			behavior = new Behavior();
			behavior.setSpeed(speed);
			behavior.setBeginTime(beginTime + 5);
			behavior.setBehaviorType(BehaviorTypeConstant.BEHAVIOR_NORMAL);
			behavior.setMonsterType(monsterType);
			behavior.setProbabilityHuman(0);
			behavior.generateMonsterTypeList();
			wave.setSpriteList(behavior.generateDiagonaLineReverse(speed, 4, 1,
					speedAux));

			wave.setDuration(8);
		} catch (Exception e) {

			e.printStackTrace();
		}
		return wave;
	}

	private static Wave generateWaveEquis(Long id, Long beginTime, int speed,
			int speedX, String monsterType) {
		Wave wave = new Wave();
		try {
			wave.setId(id);
			wave.setActive(false);
			wave.setBeginTime(beginTime);
			beginTime = 0L;

			int speedAux = (int) (Float.valueOf(speed) / 50f);

			Behavior behavior = null;

			behavior = new Behavior();
			behavior.setSpeed(speed);
			behavior.setBeginTime(beginTime);
			behavior.setBehaviorType(BehaviorTypeConstant.BEHAVIOR_NORMAL);
			behavior.setMonsterType(monsterType);
			behavior.setProbabilityHuman(0);
			behavior.generateMonsterTypeList();
			wave.setSpriteList(behavior.generateDiagonaLine(speed, 4, 1,
					speedAux));

			behavior = new Behavior();
			behavior.setSpeed(speed);
			behavior.setBeginTime(beginTime);
			behavior.setBehaviorType(BehaviorTypeConstant.BEHAVIOR_NORMAL);
			behavior.setMonsterType(monsterType);
			behavior.setProbabilityHuman(0);
			behavior.generateMonsterTypeList();
			wave.setSpriteList(behavior.generateDiagonaLineReverse(speed, 4, 1,
					speedAux));

			wave.setDuration(5);
		} catch (Exception e) {

			e.printStackTrace();
		}
		return wave;
	}

	private static Wave generateWaveDiamant(Long id, Long beginTime, int speed,
			int speedX, String monsterType) {
		Wave wave = new Wave();
		try {
			wave.setId(id);
			wave.setActive(false);
			wave.setBeginTime(beginTime);
			beginTime = 0L;

			int speedAux = (int) (Float.valueOf(speed) / 50f);

			Behavior behavior = null;

			behavior = new Behavior();
			behavior.setSpeed(speed);
			behavior.setBeginTime(beginTime);
			behavior.setBehaviorType(BehaviorTypeConstant.BEHAVIOR_NORMAL);
			behavior.setMonsterType(monsterType);
			behavior.setProbabilityHuman(0);
			behavior.generateMonsterTypeList();
			wave.setSpriteList(behavior.generateDiagonaLine(speed, 4, 1,
					speedAux));

			behavior = new Behavior();
			behavior.setSpeed(speed);
			behavior.setBeginTime(beginTime);
			behavior.setBehaviorType(BehaviorTypeConstant.BEHAVIOR_NORMAL);
			behavior.setMonsterType(monsterType);
			behavior.setProbabilityHuman(0);
			behavior.generateMonsterTypeList();
			wave.setSpriteList(behavior.generateDiagonaLineReverse(speed, 4, 1,
					speedAux));

			behavior = new Behavior();
			behavior.setSpeed(speed);
			behavior.setBeginTime(beginTime + 2);
			behavior.setBehaviorType(BehaviorTypeConstant.BEHAVIOR_NORMAL);
			behavior.setMonsterType(monsterType);
			behavior.setProbabilityHuman(0);
			behavior.generateMonsterTypeList();
			wave.setSpriteList(behavior.generateDiagonaLine(speed, 4, 1,
					speedAux));

			behavior = new Behavior();
			behavior.setSpeed(speed);
			behavior.setBeginTime(beginTime + 2);
			behavior.setBehaviorType(BehaviorTypeConstant.BEHAVIOR_NORMAL);
			behavior.setMonsterType(monsterType);
			behavior.setProbabilityHuman(0);
			behavior.generateMonsterTypeList();
			wave.setSpriteList(behavior.generateDiagonaLineReverse(speed, 4, 1,
					speedAux));

			wave.setDuration(5);
		} catch (Exception e) {

			e.printStackTrace();
		}
		return wave;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int dur) {
		this.duration = dur;
	}

	// ---------------------------------------human--------------------------------------------------------------------------

	private static Wave generateWaveV(Long id, Long beginTime, int speed,
			int speedX, String monsterType, int probabilityHuman) {
		Wave wave = new Wave();
		try {
			wave.setId(id);
			wave.setActive(false);
			wave.setBeginTime(beginTime);
			beginTime = 0L;

			int speedAux = (int) (Float.valueOf(speed) / 50f);

			Behavior behavior = null;
			behavior = new Behavior();
			behavior.setSpeed(speed);
			behavior.setBeginTime(beginTime);
			behavior.setBehaviorType(BehaviorTypeConstant.BEHAVIOR_NORMAL);
			behavior.setMonsterType(monsterType);
			behavior.setProbabilityHuman(probabilityHuman);
			behavior.generateMonsterTypeList();
			wave.setSpriteList(behavior.generateV(speed));

			behavior = new Behavior();
			behavior.setSpeed(speed);
			behavior.setBeginTime(beginTime + 4 * speedAux / 10);
			behavior.setBehaviorType(BehaviorTypeConstant.BEHAVIOR_ZIGZAG);
			behavior.setMonsterType(monsterType);
			behavior.setProbabilityHuman(probabilityHuman);
			behavior.generateMonsterTypeList();
			wave.setSpriteList(behavior.generateVInverse(speed));

			wave.setDuration(8);

		} catch (Exception e) {

			e.printStackTrace();
		}
		return wave;
	}

	private static Wave generateWavePZRHuman(Long id, Long beginTime,
			int speed, int speedX, String monsterType) {
		Wave wave = new Wave();
		try {
			wave.setId(id);
			wave.setActive(false);
			wave.setBeginTime(beginTime);
			beginTime = 0L;

			int speedAux = (int) (Float.valueOf(speed) / 50f);

			Behavior behavior = null;
			behavior = new Behavior();
			behavior.setSpeed(speed);
			behavior.setBeginTime(beginTime);
			behavior.setBehaviorType(BehaviorTypeConstant.BEHAVIOR_NORMAL);
			behavior.setMonsterType(monsterType);
			behavior.setProbabilityHuman(30);
			behavior.generateMonsterTypeList();
			wave.setSpriteList(behavior.generateTriangle(0, 4, speed, false,
					speedAux));

			behavior = new Behavior();
			behavior.setSpeed(speed);
			behavior.setBeginTime(beginTime + 4);
			behavior.setBehaviorType(BehaviorTypeConstant.BEHAVIOR_ZIGZAG);
			behavior.setMonsterType(monsterType);
			behavior.setProbabilityHuman(30);
			behavior.generateMonsterTypeList();
			wave.setSpriteList(behavior.generateVertical(speed, 5, 2, 4));

			behavior = new Behavior();
			behavior.setSpeed(speed);
			behavior.setBeginTime(beginTime + 12);
			behavior.setBehaviorType(BehaviorTypeConstant.BEHAVIOR_NORMAL);
			behavior.setMonsterType(monsterType);
			behavior.setProbabilityHuman(30);
			behavior.generateMonsterTypeList();
			wave.setSpriteList(behavior.generateRandomVampire(10, 5));

			wave.setDuration(14);

		} catch (Exception e) {

			e.printStackTrace();
		}
		return wave;
	}

	private static Wave generateWaveHPNZHuman(Long id, Long beginTime,
			int speed, int speedX, String monsterType) {
		Wave wave = new Wave();
		try {
			wave.setId(id);
			wave.setActive(false);
			wave.setBeginTime(beginTime);
			beginTime = 0L;

			int speedAux = (int) (Float.valueOf(speed) / 50f);

			Behavior behavior = null;

			behavior = new Behavior();
			behavior.setSpeed(speed);
			behavior.setBeginTime(beginTime);
			behavior.setBehaviorType(BehaviorTypeConstant.BEHAVIOR_NORMAL);
			behavior.setMonsterType(monsterType);
			behavior.setProbabilityHuman(30);
			behavior.generateMonsterTypeList();
			wave.setSpriteList(behavior.generateHorizontal(speed, 4, 3));

			behavior = new Behavior();
			behavior.setSpeed(speed);
			behavior.setBeginTime(beginTime + 4);
			behavior.setBehaviorType(BehaviorTypeConstant.BEHAVIOR_NORMAL);
			behavior.setMonsterType(monsterType);
			behavior.setProbabilityHuman(30);
			behavior.generateMonsterTypeList();
			wave.setSpriteList(behavior.generateTriangle(1, 3, speed, true,
					speedAux));

			behavior = new Behavior();
			behavior.setSpeed(speed);
			behavior.setBeginTime(beginTime + 8);
			behavior.setBehaviorType(BehaviorTypeConstant.BEHAVIOR_NORMAL_ZIGZAG);
			behavior.setMonsterType(monsterType);
			behavior.setProbabilityHuman(30);
			behavior.generateMonsterTypeList();
			wave.setSpriteList(behavior.generateVertical(speed, 6, 3, speedAux));

			wave.setDuration(12);

		} catch (Exception e) {

			e.printStackTrace();
		}
		return wave;
	}

	private static Wave generateWaveRVPHuman(Long id, Long beginTime,
			int speed, int speedX, String monsterType) {
		Wave wave = new Wave();
		try {
			wave.setId(id);
			wave.setActive(false);
			wave.setBeginTime(beginTime);
			beginTime = 0L;

			int speedAux = (int) (Float.valueOf(speed) / 50f);

			Behavior behavior = null;

			behavior = new Behavior();
			behavior.setSpeed(speed);
			behavior.setBeginTime(beginTime);
			behavior.setBehaviorType(BehaviorTypeConstant.BEHAVIOR_NORMAL);
			behavior.setMonsterType(monsterType);
			behavior.setProbabilityHuman(30);
			behavior.generateMonsterTypeList();
			wave.setSpriteList(behavior.generateRandomVampire(10, 5));

			behavior = new Behavior();
			behavior.setSpeed(speed);
			behavior.setBeginTime(beginTime + 6);
			behavior.setBehaviorType(BehaviorTypeConstant.BEHAVIOR_ZIGZAG);
			behavior.setMonsterType(monsterType);
			behavior.setProbabilityHuman(30);
			behavior.generateMonsterTypeList();
			wave.setSpriteList(behavior.generateVertical(speed, 5, 3, speedAux));

			behavior = new Behavior();
			behavior.setSpeed(speed);
			behavior.setBeginTime(beginTime + 6);
			behavior.setBehaviorType(BehaviorTypeConstant.BEHAVIOR_ZIGZAG);
			behavior.setMonsterType(monsterType);
			behavior.setProbabilityHuman(30);
			behavior.generateMonsterTypeList();

			wave.setSpriteList(behavior.generateVertical(speed, 3, 3, speedAux,
					false));

			behavior = new Behavior();
			behavior.setSpeed(speed);
			behavior.setBeginTime(beginTime + 16);
			behavior.setBehaviorType(BehaviorTypeConstant.BEHAVIOR_NORMAL);
			behavior.setMonsterType(monsterType);
			behavior.setProbabilityHuman(30);
			behavior.generateMonsterTypeList();
			wave.setSpriteList(behavior.generateTriangle(0, 4, speed, false,
					speedAux));

			wave.setDuration(18);

		} catch (Exception e) {

			e.printStackTrace();
		}
		return wave;
	}

	private static Wave generateWaveHHumanV_VRapidoHuman(Long id,
			Long beginTime, int speed, int speedX, String monsterType) {
		Wave wave = new Wave();
		try {
			wave.setId(id);
			wave.setActive(false);
			wave.setBeginTime(beginTime);
			beginTime = 0L;

			int speedAux = (int) (Float.valueOf(speed) / 50f);

			Behavior behavior = null;

			behavior = new Behavior();
			behavior.setSpeed(speed);
			behavior.setBeginTime(beginTime);
			behavior.setBehaviorType(BehaviorTypeConstant.BEHAVIOR_NORMAL);
			behavior.setMonsterType(monsterType);
			behavior.setProbabilityHuman(30);
			behavior.generateMonsterTypeList();
			wave.setSpriteList(behavior.generateHorizontal(speed, 3, 1));

			behavior = new Behavior();
			behavior.setSpeed(speed);
			behavior.setBeginTime(beginTime + speedAux / 10f);
			behavior.setBehaviorType(BehaviorTypeConstant.BEHAVIOR_NORMAL);
			behavior.setMonsterType(monsterType);
			behavior.setProbabilityHuman(35);
			behavior.generateMonsterTypeList();
			wave.setSpriteList(behavior.generateHorizontal(speed, 3, 1));

			behavior = new Behavior();
			behavior.setSpeed(speed);
			behavior.setBeginTime(beginTime + speedAux / 10f * 2);
			behavior.setBehaviorType(BehaviorTypeConstant.BEHAVIOR_NORMAL);
			behavior.setMonsterType(monsterType);
			behavior.setProbabilityHuman(30);
			behavior.generateMonsterTypeList();
			wave.setSpriteList(behavior.generateHorizontal(speed, 3, 1));

			behavior = new Behavior();
			behavior.setSpeed(speed);
			behavior.setBeginTime(beginTime + 8);
			behavior.setBehaviorType(BehaviorTypeConstant.BEHAVIOR_ZIGZAG);
			behavior.setMonsterType(monsterType);
			behavior.setProbabilityHuman(30);
			behavior.generateMonsterTypeList();
			wave.setSpriteList(behavior.generateVertical(speed, 2, 4, speedAux));

			behavior = new Behavior();
			behavior.setSpeed(speed);
			behavior.setBeginTime(beginTime + 8);
			behavior.setBehaviorType(BehaviorTypeConstant.BEHAVIOR_ZIGZAG);
			behavior.setMonsterType(monsterType);
			behavior.setProbabilityHuman(30);
			behavior.generateMonsterTypeList();
			wave.setSpriteList(behavior.generateVertical(speed, 2, 4, speedAux,
					false));

			behavior = new Behavior();
			behavior.setSpeed(speed);
			behavior.setBeginTime(beginTime + 15);
			behavior.setBehaviorType(BehaviorTypeConstant.BEHAVIOR_ZIGZAG);
			behavior.setMonsterType(monsterType);
			behavior.setProbabilityHuman(30);
			behavior.generateMonsterTypeList();
			wave.setSpriteList(behavior.generateRandomVampire(12, 3));

			wave.setDuration(18);

		} catch (Exception e) {

			e.printStackTrace();
		}
		return wave;
	}

	private static Wave generateWavePVHHuman(Long id, Long beginTime,
			int speed, int speedX, String monsterType) {
		Wave wave = new Wave();
		try {
			wave.setId(id);
			wave.setActive(false);
			wave.setBeginTime(beginTime);
			beginTime = 0L;

			int speedAux = (int) (Float.valueOf(speed) / 50f);

			Behavior behavior = null;

			behavior = new Behavior();
			behavior.setSpeed(speed);
			behavior.setBeginTime(beginTime);
			behavior.setBehaviorType(BehaviorTypeConstant.BEHAVIOR_ZIGZAG);
			behavior.setMonsterType(monsterType);
			behavior.setProbabilityHuman(100);
			behavior.generateMonsterTypeList();
			wave.setSpriteList(behavior.generateHorizontal(speed, 1, 1));

			behavior = new Behavior();
			behavior.setSpeed(speed);
			behavior.setBeginTime(beginTime + 0.5f);
			behavior.setBehaviorType(BehaviorTypeConstant.BEHAVIOR_NORMAL);
			behavior.setMonsterType(monsterType);
			behavior.setProbabilityHuman(30);
			behavior.generateMonsterTypeList();
			wave.setSpriteList(behavior.generateTriangle(0, 3, speedX, false,
					speedAux));

			behavior = new Behavior();
			behavior.setSpeed(speed);
			behavior.setBeginTime(beginTime + 5);
			behavior.setBehaviorType(BehaviorTypeConstant.BEHAVIOR_NORMAL);
			behavior.setMonsterType(monsterType);
			behavior.setProbabilityHuman(30);
			behavior.generateMonsterTypeList();
			wave.setSpriteList(behavior.generateVertical(speed, 6, 3, speedAux));

			behavior = new Behavior();
			behavior.setSpeed(speed);
			behavior.setBeginTime(beginTime + 12);
			behavior.setBehaviorType(BehaviorTypeConstant.BEHAVIOR_NORMAL);
			behavior.setMonsterType(monsterType);
			behavior.setProbabilityHuman(30);
			behavior.generateMonsterTypeList();
			wave.setSpriteList(behavior.generateHorizontal(speed, 3, 3));

			wave.setDuration(15);

		} catch (Exception e) {

			e.printStackTrace();
		}
		return wave;
	}

	private static Wave generateWavePVZHuman(Long id, Long beginTime,
			int speed, int speedX, String monsterType) {
		Wave wave = new Wave();
		try {
			wave.setId(id);
			wave.setActive(false);
			wave.setBeginTime(beginTime);
			beginTime = 0L;

			int speedAux = (int) (Float.valueOf(speed) / 50f);

			Behavior behavior = null;

			behavior = new Behavior();
			behavior.setSpeed(speed);
			behavior.setBeginTime(beginTime);
			behavior.setBehaviorType(BehaviorTypeConstant.BEHAVIOR_NORMAL);
			behavior.setMonsterType(monsterType);
			behavior.setProbabilityHuman(30);
			behavior.generateMonsterTypeList();
			wave.setSpriteList(behavior.generateTriangle(0, 3, speed, true,
					speedAux));

			behavior = new Behavior();
			behavior.setSpeed(speed);
			behavior.setBeginTime(beginTime + 3);
			behavior.setBehaviorType(BehaviorTypeConstant.BEHAVIOR_ZIGZAG_NORMAL);
			behavior.setMonsterType(monsterType);
			behavior.setProbabilityHuman(15);
			behavior.generateMonsterTypeList();
			wave.setSpriteList(behavior.generateVertical(speed, 5, 3, speedAux));

			behavior = new Behavior();
			behavior.setSpeed(speed);
			behavior.setBeginTime(beginTime + 10);
			behavior.setBehaviorType(BehaviorTypeConstant.BEHAVIOR_NORMAL);
			behavior.setMonsterType(monsterType);
			behavior.setProbabilityHuman(25);
			behavior.generateMonsterTypeList();
			wave.setSpriteList(behavior.generateRandomVampire(12, 3));

			wave.setDuration(14);

		} catch (Exception e) {

			e.printStackTrace();
		}
		return wave;
	}

	private static Wave generateWaveHHHHuman(Long id, Long beginTime,
			int speed, int speedX, String monsterType) {
		Wave wave = new Wave();
		try {
			wave.setId(id);
			wave.setActive(false);
			wave.setBeginTime(beginTime);
			beginTime = 0L;

			int speedAux = (int) (Float.valueOf(speed) / 50f);

			Behavior behavior = null;

			behavior = new Behavior();
			behavior.setSpeed(speed);
			behavior.setBeginTime(beginTime);
			behavior.setBehaviorType(BehaviorTypeConstant.BEHAVIOR_NORMAL);
			behavior.setMonsterType(monsterType);
			behavior.setProbabilityHuman(30);
			behavior.generateMonsterTypeList();
			wave.setSpriteList(behavior.generateHorizontal(speed, 3, 1));

			behavior = new Behavior();
			behavior.setSpeed(speed);
			behavior.setBeginTime(beginTime + speedAux / 10f);
			behavior.setBehaviorType(BehaviorTypeConstant.BEHAVIOR_NORMAL);
			behavior.setMonsterType(monsterType);
			behavior.setProbabilityHuman(35);
			behavior.generateMonsterTypeList();
			wave.setSpriteList(behavior.generateHorizontal(speed, 3, 1));

			behavior = new Behavior();
			behavior.setSpeed(speed);
			behavior.setBeginTime(beginTime + speedAux / 10f * 2);
			behavior.setBehaviorType(BehaviorTypeConstant.BEHAVIOR_NORMAL);
			behavior.setMonsterType(monsterType);
			behavior.setProbabilityHuman(30);
			behavior.generateMonsterTypeList();
			wave.setSpriteList(behavior.generateHorizontal(speed, 3, 1));

			behavior = new Behavior();
			behavior.setSpeed(speed);
			behavior.setBeginTime(beginTime + speedAux / 10f * 2 + 2);
			behavior.setBehaviorType(BehaviorTypeConstant.BEHAVIOR_NORMAL);
			behavior.setMonsterType(monsterType);
			behavior.setProbabilityHuman(8);
			behavior.generateMonsterTypeList();
			wave.setSpriteList(behavior.generateHorizontal(speed, 3, 6));

			wave.setDuration(10);

		} catch (Exception e) {

			e.printStackTrace();
		}
		return wave;
	}

	private static Wave generateWaveROMBOHuman(Long id, Long beginTime,
			int speed, int speedX, String monsterType) {
		Wave wave = new Wave();
		try {
			wave.setId(id);
			wave.setActive(false);
			wave.setBeginTime(beginTime);
			beginTime = 0L;

			int speedAux = (int) (Float.valueOf(speed) / 50f);

			Behavior behavior = null;

			behavior = new Behavior();
			behavior.setSpeed(speed);
			behavior.setBeginTime(beginTime);
			behavior.setBehaviorType(BehaviorTypeConstant.BEHAVIOR_NORMAL);
			behavior.setMonsterType(monsterType);
			behavior.setProbabilityHuman(30);
			behavior.generateMonsterTypeList();
			wave.setSpriteList(behavior.generateTriangle(0, 4, speed, false,
					speedAux));

			behavior = new Behavior();
			behavior.setSpeed(speed);
			behavior.setBeginTime(beginTime + (speedAux / 10f * 5));
			behavior.setBehaviorType(BehaviorTypeConstant.BEHAVIOR_NORMAL);
			behavior.setMonsterType(monsterType);
			behavior.setProbabilityHuman(30);
			behavior.generateMonsterTypeList();
			wave.setSpriteList(behavior.generateTriangle(0, 4, speed, true,
					speedAux));

			wave.setDuration(8);

		} catch (Exception e) {

			e.printStackTrace();
		}
		return wave;
	}

	private static Wave generateWaveZIGZAGHuman(Long id, Long beginTime,
			int speed, int speedX, String monsterType) {
		Wave wave = new Wave();
		try {
			wave.setId(id);
			wave.setActive(false);
			wave.setBeginTime(beginTime);
			beginTime = 0L;

			int speedAux = (int) (Float.valueOf(speed) / 50f);

			Behavior behavior = null;

			behavior = new Behavior();
			behavior.setSpeed(speed);
			behavior.setBeginTime(beginTime);
			behavior.setBehaviorType(BehaviorTypeConstant.BEHAVIOR_ZIGZAG);
			behavior.setMonsterType(monsterType);
			behavior.setProbabilityHuman(30);
			behavior.generateMonsterTypeList();
			wave.setSpriteList(behavior.generateVertical(speed, 4, 2, speedAux));

			behavior = new Behavior();
			behavior.setSpeed(speed);
			behavior.setBeginTime(beginTime);
			behavior.setBehaviorType(BehaviorTypeConstant.BEHAVIOR_ZIGZAG);
			behavior.setMonsterType(monsterType);
			behavior.setProbabilityHuman(30);
			behavior.generateMonsterTypeList();
			wave.setSpriteList(behavior.generateVertical(speed, 4, 2, speedAux,
					false));

			behavior = new Behavior();
			behavior.setSpeed(speed);
			behavior.setBeginTime(beginTime + 6);
			behavior.setBehaviorType(BehaviorTypeConstant.BEHAVIOR_ZIGZAG);
			behavior.setMonsterType(monsterType);
			behavior.setProbabilityHuman(30);
			behavior.generateMonsterTypeList();
			wave.setSpriteList(behavior.generateVertical(speed, 4, 2, speedAux));

			behavior = new Behavior();
			behavior.setSpeed(speed);
			behavior.setBeginTime(beginTime + 6);
			behavior.setBehaviorType(BehaviorTypeConstant.BEHAVIOR_ZIGZAG);
			behavior.setMonsterType(monsterType);
			behavior.setProbabilityHuman(30);
			behavior.generateMonsterTypeList();
			wave.setSpriteList(behavior.generateVertical(speed, 4, 2, speedAux,
					false));

			wave.setDuration(8);

		} catch (Exception e) {

			e.printStackTrace();
		}
		return wave;
	}

	private static Wave generateWaveDIAGONALHuman(Long id, Long beginTime,
			int speed, int speedX, String monsterType) {
		Wave wave = new Wave();
		try {
			wave.setId(id);
			wave.setActive(false);
			wave.setBeginTime(beginTime);
			beginTime = 0L;

			int speedAux = (int) (Float.valueOf(speed) / 50f);

			Behavior behavior = null;

			behavior = new Behavior();
			behavior.setSpeed(speed);
			behavior.setBeginTime(beginTime);
			behavior.setBehaviorType(BehaviorTypeConstant.BEHAVIOR_NORMAL);
			behavior.setMonsterType(monsterType);
			behavior.setProbabilityHuman(30);
			behavior.generateMonsterTypeList();
			wave.setSpriteList(behavior.generateDiagonaLine(speed, 4, 1,
					speedAux));

			behavior = new Behavior();
			behavior.setSpeed(speed);
			behavior.setBeginTime(beginTime + 5);
			behavior.setBehaviorType(BehaviorTypeConstant.BEHAVIOR_NORMAL);
			behavior.setMonsterType(monsterType);
			behavior.setProbabilityHuman(30);
			behavior.generateMonsterTypeList();
			wave.setSpriteList(behavior.generateDiagonaLineReverse(speed, 4, 1,
					speedAux));

			wave.setDuration(8);
		} catch (Exception e) {

			e.printStackTrace();
		}
		return wave;
	}

	private static Wave generateWaveEquisHuman(Long id, Long beginTime,
			int speed, int speedX, String monsterType) {
		Wave wave = new Wave();
		try {
			wave.setId(id);
			wave.setActive(false);
			wave.setBeginTime(beginTime);
			beginTime = 0L;

			int speedAux = (int) (Float.valueOf(speed) / 50f);

			Behavior behavior = null;

			behavior = new Behavior();
			behavior.setSpeed(speed);
			behavior.setBeginTime(beginTime);
			behavior.setBehaviorType(BehaviorTypeConstant.BEHAVIOR_NORMAL);
			behavior.setMonsterType(monsterType);
			behavior.setProbabilityHuman(30);
			behavior.generateMonsterTypeList();
			wave.setSpriteList(behavior.generateDiagonaLine(speed, 4, 1,
					speedAux));

			behavior = new Behavior();
			behavior.setSpeed(speed);
			behavior.setBeginTime(beginTime);
			behavior.setBehaviorType(BehaviorTypeConstant.BEHAVIOR_NORMAL);
			behavior.setMonsterType(monsterType);
			behavior.setProbabilityHuman(30);
			behavior.generateMonsterTypeList();
			wave.setSpriteList(behavior.generateDiagonaLineReverse(speed, 4, 1,
					speedAux));

			wave.setDuration(5);
		} catch (Exception e) {

			e.printStackTrace();
		}
		return wave;
	}

	private static Wave generateWaveDiamantHuman(Long id, Long beginTime,
			int speed, int speedX, String monsterType) {
		Wave wave = new Wave();
		try {
			wave.setId(id);
			wave.setActive(false);
			wave.setBeginTime(beginTime);
			beginTime = 0L;

			int speedAux = (int) (Float.valueOf(speed) / 50f);

			Behavior behavior = null;

			behavior = new Behavior();
			behavior.setSpeed(speed);
			behavior.setBeginTime(beginTime);
			behavior.setBehaviorType(BehaviorTypeConstant.BEHAVIOR_NORMAL);
			behavior.setMonsterType(monsterType);
			behavior.setProbabilityHuman(30);
			behavior.generateMonsterTypeList();
			wave.setSpriteList(behavior.generateDiagonaLine(speed, 4, 1,
					speedAux));

			behavior = new Behavior();
			behavior.setSpeed(speed);
			behavior.setBeginTime(beginTime);
			behavior.setBehaviorType(BehaviorTypeConstant.BEHAVIOR_NORMAL);
			behavior.setMonsterType(monsterType);
			behavior.setProbabilityHuman(30);
			behavior.generateMonsterTypeList();
			wave.setSpriteList(behavior.generateDiagonaLineReverse(speed, 4, 1,
					speedAux));

			behavior = new Behavior();
			behavior.setSpeed(speed);
			behavior.setBeginTime(beginTime + 2);
			behavior.setBehaviorType(BehaviorTypeConstant.BEHAVIOR_NORMAL);
			behavior.setMonsterType(monsterType);
			behavior.setProbabilityHuman(30);
			behavior.generateMonsterTypeList();
			wave.setSpriteList(behavior.generateDiagonaLine(speed, 4, 1,
					speedAux));

			behavior = new Behavior();
			behavior.setSpeed(speed);
			behavior.setBeginTime(beginTime + 2);
			behavior.setBehaviorType(BehaviorTypeConstant.BEHAVIOR_NORMAL);
			behavior.setMonsterType(monsterType);
			behavior.setProbabilityHuman(30);
			behavior.generateMonsterTypeList();
			wave.setSpriteList(behavior.generateDiagonaLineReverse(speed, 4, 1,
					speedAux));

			wave.setDuration(5);
		} catch (Exception e) {

			e.printStackTrace();
		}
		return wave;
	}

	private static Wave generateWaveH2TV(Long id, Long beginTime, int speed,
			int speedX, String monsterType, int probabilityHuman) {
		Wave wave = new Wave();
		try {
			wave.setId(id);
			wave.setActive(false);
			wave.setBeginTime(beginTime);
			beginTime = 0L;

			int speedAux = (int) (Float.valueOf(speed) / 50f);

			Behavior behavior = null;

			behavior = new Behavior();
			behavior.setSpeed(speed);
			behavior.setBeginTime(beginTime);
			behavior.setBehaviorType(BehaviorTypeConstant.BEHAVIOR_NORMAL);
			behavior.setMonsterType(monsterType);
			behavior.setProbabilityHuman(probabilityHuman);
			behavior.generateMonsterTypeList();
			wave.setSpriteList(behavior.generateHorizontarPosition(speed, 10,
					3, speedAux * 3, 1, 2));

			behavior = new Behavior();
			behavior.setSpeed(speed);
			behavior.setBeginTime(beginTime + 3);
			behavior.setBehaviorType(BehaviorTypeConstant.BEHAVIOR_NORMAL);
			behavior.setMonsterType(monsterType);
			behavior.setProbabilityHuman(probabilityHuman);
			behavior.generateMonsterTypeList();
			wave.setSpriteList(behavior.generateTriangle(0, 2, speed, true,
					speedAux));

			behavior = new Behavior();
			behavior.setSpeed(speed);
			behavior.setBeginTime(beginTime + 5);
			behavior.setBehaviorType(BehaviorTypeConstant.BEHAVIOR_NORMAL);
			behavior.setMonsterType(monsterType);
			behavior.setProbabilityHuman(probabilityHuman);
			behavior.generateMonsterTypeList();
			wave.setSpriteList(behavior.generateTriangle(2, 2, speed, true,
					speedAux));

			behavior = new Behavior();
			behavior.setSpeed(speed);
			behavior.setBeginTime(beginTime + 8);
			behavior.setBehaviorType(BehaviorTypeConstant.BEHAVIOR_NORMAL);
			behavior.setMonsterType(monsterType);
			behavior.setProbabilityHuman(probabilityHuman);
			behavior.generateMonsterTypeList();
			wave.setSpriteList(behavior.generateVertical(speed, 4, 1, speedAux));

			wave.setDuration(12);
		} catch (Exception e) {

			e.printStackTrace();
		}
		return wave;
	}

	private static Wave generateWaveVampireBat(Long id, Long beginTime,
			int speed, int speedX, String monsterType, int probabilityHuman) {
		Wave wave = new Wave();
		try {
			wave.setId(id);
			wave.setActive(false);
			wave.setBeginTime(beginTime);
			beginTime = 0L;

			int speedAux = (int) (Float.valueOf(speed) / 50f);

			Behavior behavior = null;

			behavior = new Behavior();
			behavior.setSpeed(speed);
			behavior.setBeginTime(beginTime);
			behavior.setBehaviorType(BehaviorTypeConstant.BEHAVIOR_NORMAL);
			behavior.setMonsterType(monsterType);
			behavior.setProbabilityHuman(probabilityHuman);
			behavior.generateMonsterTypeList();
			wave.setSpriteList(behavior.generateV(speed));

			behavior = new Behavior();
			behavior.setSpeed(speed);
			behavior.setBeginTime(beginTime + 2);
			behavior.setBehaviorType(BehaviorTypeConstant.BEHAVIOR_NORMAL);
			behavior.setMonsterType(monsterType);
			behavior.setProbabilityHuman(probabilityHuman);
			behavior.generateMonsterTypeList();
			wave.setSpriteList(behavior.generateDiagonaLine(speed, 3, 1,
					speedAux));

			behavior = new Behavior();
			behavior.setSpeed(speed);
			behavior.setBeginTime(beginTime + 4);
			behavior.setBehaviorType(BehaviorTypeConstant.BEHAVIOR_ACCELERATE);
			behavior.setMonsterType(SpriteTypeConstant.MONSTER_BAT + "");
			behavior.setProbabilityHuman(probabilityHuman);
			behavior.generateMonsterTypeList();
			wave.setSpriteList(behavior.generateRandomVampire(3, 3));

			wave.setDuration(7);
		} catch (Exception e) {

			e.printStackTrace();
		}
		return wave;
	}

	private static Wave generateWave2C2CT(Long id, Long beginTime, int speed,
			int speedX, String monsterType, int probabilityHuman) {
		Wave wave = new Wave();
		try {
			wave.setId(id);
			wave.setActive(false);
			wave.setBeginTime(beginTime);
			beginTime = 0L;

			int speedAux = (int) (Float.valueOf(speed) / 50f);

			Behavior behavior = null;

			behavior = new Behavior();
			behavior.setSpeed(speed);
			behavior.setBeginTime(beginTime);
			behavior.setBehaviorType(BehaviorTypeConstant.BEHAVIOR_NORMAL);
			behavior.setMonsterType(monsterType);
			behavior.setProbabilityHuman(probabilityHuman);
			behavior.generateMonsterTypeList();
			wave.setSpriteList(behavior.generateHorizontarPosition(speed, 10,
					2, speedAux, 1, 2));

			behavior = new Behavior();
			behavior.setSpeed(speed);
			behavior.setBeginTime(beginTime + 2);
			behavior.setBehaviorType(BehaviorTypeConstant.BEHAVIOR_NORMAL);
			behavior.setMonsterType(monsterType);
			behavior.setProbabilityHuman(probabilityHuman);
			behavior.generateMonsterTypeList();
			wave.setSpriteList(behavior.generateHorizontarPosition(speed, 210,
					2, speedAux, 1, 2));

			behavior = new Behavior();
			behavior.setSpeed(speed);
			behavior.setBeginTime(beginTime + 4);
			behavior.setBehaviorType(BehaviorTypeConstant.BEHAVIOR_NORMAL);
			behavior.setMonsterType(monsterType);
			behavior.setProbabilityHuman(probabilityHuman);
			behavior.generateMonsterTypeList();
			wave.setSpriteList(behavior.generateVInverse(speed));

			wave.setDuration(7);
		} catch (Exception e) {

			e.printStackTrace();
		}
		return wave;
	}

	private static Wave generateWaveCasita(Long id, Long beginTime, int speed,
			int speedX, String monsterType, int probabilityHuman) {
		Wave wave = new Wave();
		try {
			wave.setId(id);
			wave.setActive(false);
			wave.setBeginTime(beginTime);
			beginTime = 0L;

			int speedAux = (int) (Float.valueOf(speed) / 50f);

			Behavior behavior = null;

			behavior = new Behavior();
			behavior.setSpeed(speed);
			behavior.setBeginTime(beginTime);
			behavior.setBehaviorType(BehaviorTypeConstant.BEHAVIOR_NORMAL);
			behavior.setMonsterType(monsterType);
			behavior.setProbabilityHuman(probabilityHuman);
			behavior.generateMonsterTypeList();
			wave.setSpriteList(behavior.generateHorizontarPosition(speed, 60,
					3, speedAux, 1, 3));

			behavior = new Behavior();
			behavior.setSpeed(speed);
			behavior.setBeginTime(beginTime + 2);
			behavior.setBehaviorType(BehaviorTypeConstant.BEHAVIOR_NORMAL);
			behavior.setMonsterType(monsterType);
			behavior.setProbabilityHuman(probabilityHuman);
			behavior.generateMonsterTypeList();
			wave.setSpriteList(behavior.generateVInverse(speed));

			wave.setDuration(7);
		} catch (Exception e) {

			e.printStackTrace();
		}
		return wave;
	}

	private static Wave generateWaveZigZagMid(Long id, Long beginTime,
			int speed, int speedX, String monsterType, int probabilityHuman,
			int quantity) {
		Wave wave = new Wave();
		try {
			wave.setId(id);
			wave.setActive(false);
			wave.setBeginTime(beginTime);
			beginTime = 0L;

			int speedAux = (int) (Float.valueOf(speed) / 50f);

			if (speed > 300 && speed <= 350)
				speedAux = 4;

			if (speed > 350 && speed < 450)
				speedAux = 2;
			Behavior behavior = null;

			behavior = new Behavior();
			behavior.setSpeed(speed);
			behavior.setBeginTime(beginTime);
			behavior.setBehaviorType(BehaviorTypeConstant.BEHAVIOR_ZIGZAG);
			behavior.setMonsterType(monsterType);
			behavior.setProbabilityHuman(probabilityHuman);
			behavior.generateMonsterTypeList();
			wave.setSpriteList(behavior.generateVerticalPosition(speed, 175,
					quantity, speedAux, 1));

			behavior = new Behavior();
			behavior.setSpeed(speed);
			behavior.setBeginTime(beginTime);
			behavior.setBehaviorType(BehaviorTypeConstant.BEHAVIOR_ZIGZAG);
			behavior.setMonsterType(monsterType);
			behavior.setProbabilityHuman(probabilityHuman);
			behavior.generateMonsterTypeList();
			wave.setSpriteList(behavior.generateVerticalPosition(speed, 175,
					quantity, speedAux, -1));

			wave.setDuration(quantity * speedAux / 10);
		} catch (Exception e) {

			e.printStackTrace();
		}
		return wave;
	}

	private static Wave generateWaveLeftFastRightSlow(Long id, Long beginTime,
			int speed, int speedX, String monsterType, int probabilityHuman,
			int quantity, int behaviorType) {
		Wave wave = new Wave();
		try {
			wave.setId(id);
			wave.setActive(false);
			wave.setBeginTime(beginTime);
			beginTime = 0L;

			int speedAux = (int) (Float.valueOf(speed) / 50f);

			Behavior behavior = null;

			behavior = new Behavior();

			behavior.setSpeed((int) (speed * 2));
			behavior.setBeginTime(beginTime);
			behavior.setBehaviorType(behaviorType);
			behavior.setMonsterType(monsterType);
			behavior.setProbabilityHuman(probabilityHuman);
			behavior.generateMonsterTypeList();
			wave.setSpriteList(behavior.generateVerticalPosition(
					(int) (speed * 1.5f), 10, quantity, speedAux, 1));

			behavior = new Behavior();
			behavior.setSpeed(speed);
			behavior.setBeginTime(beginTime);
			behavior.setBehaviorType(behaviorType);
			behavior.setMonsterType(monsterType);
			behavior.setProbabilityHuman(probabilityHuman);
			behavior.generateMonsterTypeList();
			wave.setSpriteList(behavior.generateVerticalPosition(speed, 310,
					quantity / 2, speedAux, -1));

			wave.setDuration(quantity * speedAux / 10);
		} catch (Exception e) {

			e.printStackTrace();
		}
		return wave;
	}

	private static Wave generateWaveRightFastLeftSlow(Long id, Long beginTime,
			int speed, int speedX, String monsterType, int probabilityHuman,
			int quantity, int behaviorType) {
		Wave wave = new Wave();
		try {
			wave.setId(id);
			wave.setActive(false);
			wave.setBeginTime(beginTime);
			beginTime = 0L;

			int speedAux = (int) (Float.valueOf(speed) / 50f);

			if (speed > 300 && speed <= 350)
				speedAux = 4;

			if (speed > 350 && speed < 450)
				speedAux = 2;

			Behavior behavior = null;

			behavior = new Behavior();

			behavior.setSpeed((int) (speed * 2));
			behavior.setBeginTime(beginTime);
			behavior.setBehaviorType(behaviorType);
			behavior.setMonsterType(monsterType);
			behavior.setProbabilityHuman(probabilityHuman);
			behavior.generateMonsterTypeList();
			wave.setSpriteList(behavior.generateVerticalPosition(speed, 310,
					quantity / 2, speedAux, -1));

			behavior = new Behavior();
			behavior.setSpeed(speed);
			behavior.setBeginTime(beginTime);
			behavior.setBehaviorType(behaviorType);
			behavior.setMonsterType(monsterType);
			behavior.setProbabilityHuman(probabilityHuman);
			behavior.generateMonsterTypeList();
			wave.setSpriteList(behavior.generateVerticalPosition(
					(int) (speed * 1.5f), 10, quantity, speedAux, 1));

			wave.setDuration(quantity * speedAux / 10);
		} catch (Exception e) {

			e.printStackTrace();
		}
		return wave;
	}

	private static Wave generateWaveRandomRectangles(Long id, Long beginTime,
			int speed, int speedX, String monsterType, int probabilityHuman,
			int behaviorType, int quantity) {
		Wave wave = new Wave();
		try {
			wave.setId(id);
			wave.setActive(false);
			wave.setBeginTime(beginTime);
			beginTime = 0L;

			int speedAux = (int) (Float.valueOf(speed) / 50f);

			Behavior behavior = null;

			for (int i = 0; i < quantity; i++) {

				behavior = new Behavior();
				behavior.setSpeed((int) (speed * 2));
				behavior.setBeginTime(beginTime);
				behavior.setBeginTime(beginTime);
				behavior.setMonsterType(monsterType);
				behavior.setProbabilityHuman(probabilityHuman);
				behavior.generateMonsterTypeList();

				Double row = ((Math.random() * 1000000) % 3) + 2;
				Double position = ((Math.random() * 1000000) % 300) + 10;

				wave.setSpriteList(behavior.generateHorizontarPosition(speed,
						position.floatValue(), row.intValue(), speedAux, 1,
						row.intValue()));
				beginTime = beginTime + 1 + quantity * speedAux / 10;

			}
			wave.setDuration(beginTime.intValue() + 3);
		} catch (Exception e) {

			e.printStackTrace();
		}
		return wave;
	}

	private static Wave generateWavePiramid(Long id, Long beginTime, int speed,
			int speedX, String monsterType, int probabilityHuman,
			int behaviorType) {
		Wave wave = new Wave();
		try {
			wave.setId(id);
			wave.setActive(false);
			wave.setBeginTime(beginTime);
			beginTime = 0L;

			int speedAux = (int) (Float.valueOf(speed) / 50f);

			Behavior behavior = null;

			behavior = new Behavior();

			behavior.setSpeed((int) (speed * 2));
			behavior.setBeginTime(beginTime);
			behavior.setBehaviorType(behaviorType);
			behavior.setMonsterType(monsterType);
			behavior.setProbabilityHuman(probabilityHuman);
			behavior.generateMonsterTypeList();
			wave.setSpriteList(behavior.generateTriangle(0, 4, speed, true,
					speedAux));

			wave.setDuration(4 * speedAux / 10);
		} catch (Exception e) {

			e.printStackTrace();
		}
		return wave;
	}

	private static Wave generateWaveRandomVampiresa(Long id, Long beginTime,
			int speed, int speedX, String monsterType, int probabilityHuman,
			int behaviorType, int quantity, int time) {
		Wave wave = new Wave();
		try {
			wave.setId(id);
			wave.setActive(false);
			wave.setBeginTime(beginTime);
			beginTime = 0L;

			int speedAux = (int) (Float.valueOf(speed) / 50f);

			Behavior behavior = null;

			behavior = new Behavior();

			behavior.setSpeed((int) (speed));
			behavior.setBeginTime(beginTime);
			behavior.setBehaviorType(behaviorType);
			behavior.setMonsterType(monsterType);
			behavior.setProbabilityHuman(probabilityHuman);
			behavior.generateMonsterTypeList();
			wave.setSpriteList(behavior.generateRandomVampire(quantity, time));

			wave.setDuration(quantity + 2 * speedAux / 10);
		} catch (Exception e) {

			e.printStackTrace();
		}
		return wave;
	}

	private static Wave generateWaveBoss(Long id, Long beginTime, int speed,
			int speedX, String monsterType) {
		Wave wave = new Wave();
		try {
			wave.setId(id);
			wave.setActive(false);
			wave.setBeginTime(beginTime);
			beginTime = 0L;

			int speedAux = (int) (Float.valueOf(speed) / 50f);

			Behavior behavior = null;

			behavior = new Behavior();

			behavior.setSpeed((int) (speed));
			behavior.setBeginTime(beginTime);
			behavior.setBehaviorType(BehaviorTypeConstant.BEHAVIOR_BOSS);
			behavior.setMonsterType(monsterType);
			behavior.setProbabilityHuman(0);
			behavior.generateMonsterTypeList();
			wave.setSpriteList(behavior.generateBoss());

			wave.setDuration(10);
		} catch (Exception e) {

			e.printStackTrace();
		}
		return wave;
	}

}
