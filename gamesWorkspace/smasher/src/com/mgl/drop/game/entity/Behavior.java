package com.mgl.drop.game.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Stack;

import android.R.integer;

import com.mgl.base.MyEntitySmasher;
import com.mgl.base.SpriteTypeConstant;

public class Behavior {

	public static final int BEHAVIOR_RANDOM_SECONDS = 1;
	public static final int BEHAVIOR_HUMAN_INSIDE_4 = 2;
	private static final int BEHAVIOR_TRIANGLE_UP_BASE4 = 3;
	private static final int BEHAVIOR_TRIANGLE_UP_BASE3 = 4;
	private static final int BEHAVIOR_TRIANGLE_UP_BASE2 = 5;
	private static final int BEHAVIOR_TRIANGLE_DOWN_BASE4 = 6;
	private static final int BEHAVIOR_TRIANGLE_DOWN_BASE3 = 7;
	private static final int BEHAVIOR_TRIANGLE_DOWN_BASE2 = 8;
	private static final int BEHAVIOR_VAMPIRE_INSIDE_4HUMAN = 9;
	private static final int BEHAVIOR_VAMPIRE_INSIDE_8HUMAN = 10;
	private static final int BEHAVIOR_HUMAN_INSIDE_8VAMPIRE = 11;
	private static final int BEHAVIOR_RANDOM_HUMAN_VAMPIRE_10 = 12;
	private static final int BEHAVIOR_VERTICAL = 13;
	private static final int BEHAVIOR_HORIZONTAL = 14;
	private static final int BEHAVIOR_BOSS = 15;
	private static final int BEHAVIOR_TRIANGLE = 16;

	private int id;
	private float beginTime;
	private int speed;
	private int idLevel;
	private int behaviorType;
	private int probabilityHuman = 0;

	private String monsterType;
	private String data;
	private int speedX = 1;

	private HashMap<Integer, Integer> monsterTypeList;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public float getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(float beginTime) {
		this.beginTime = beginTime;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getIdLevel() {
		return idLevel;
	}

	public void setIdLevel(int idLevel) {
		this.idLevel = idLevel;
	}

	public ArrayList<MyEntitySmasher> generateSmashSprites(Long beginTimeWave) {
		try {

			generateMonsterTypeList();

			ArrayList<MyEntitySmasher> spriteList = new ArrayList<MyEntitySmasher>();

			switch (id) {
			case BEHAVIOR_RANDOM_SECONDS:
				// needs 0 = quantity 1 time
				String[] myData = data.split(",");
				spriteList = generateRandomVampire(Integer.valueOf(myData[0]),
						Integer.valueOf(myData[1]));
				break;

			case BEHAVIOR_HUMAN_INSIDE_4:
				spriteList = generateHumanInside(130, 50, 4);
				// spriteList = generateHorizontal(130, 3, 3);
				break;
			case BEHAVIOR_TRIANGLE_UP_BASE4:
				spriteList = generateTriangle(0, 4, speed, true, 2);
				break;
			case BEHAVIOR_TRIANGLE_UP_BASE3:
				spriteList = generateTriangle(0, 3, speed, true, 2);
				break;
			case BEHAVIOR_TRIANGLE_UP_BASE2:
				spriteList = generateTriangle(0, 2, speed, true, 2);
				break;
			case BEHAVIOR_TRIANGLE_DOWN_BASE4:
				spriteList = generateTriangle(0, 4, speed, true, 4);
				break;
			case BEHAVIOR_TRIANGLE_DOWN_BASE3:
				myData = data.split(",");
				spriteList = generateTriangle(Integer.valueOf(myData[0]),
						Integer.valueOf(myData[1]), speed,
						Integer.valueOf(myData[2]) == 1 ? true : false,
						Integer.valueOf(myData[3]));
				break;
			case BEHAVIOR_TRIANGLE_DOWN_BASE2:
				spriteList = generateTriangle(0, 2, speed, false, 2);
				break;
			case BEHAVIOR_VAMPIRE_INSIDE_4HUMAN:
				spriteList = generateVampireInsideHuman(speed, 0, 4);
				break;
			case BEHAVIOR_HUMAN_INSIDE_8VAMPIRE:
				spriteList = generateHumanInside(speed, 0, 8);
				break;
			case BEHAVIOR_VAMPIRE_INSIDE_8HUMAN:
				spriteList = generateVampireInsideHuman(speed, 0, 8);
				break;
			case BEHAVIOR_RANDOM_HUMAN_VAMPIRE_10:
				spriteList = generateRandomHumanVampire(speed);
				break;
			case BEHAVIOR_VERTICAL:
				// needs 0 = quantity 1 time, 2 time between waves in millis
				myData = data.split(",");
				spriteList = generateVertical(speed,
						Integer.valueOf(myData[0]), Integer.valueOf(myData[1]),
						Integer.valueOf(myData[2]));

				break;

			case BEHAVIOR_HORIZONTAL:
				myData = data.split(",");
				spriteList = generateHorizontal(speed,
						Integer.valueOf(myData[0]), Integer.valueOf(myData[1]));
				break;
			case BEHAVIOR_BOSS:
				myData = data.split(",");
				spriteList = generateBoss();
				break;
			case BEHAVIOR_TRIANGLE:
				myData = data.split(",");
				spriteList = generateTriangle(0, 4, speed, false, 4);
				// spriteList = generateTriangle(Integer.valueOf(myData[0]),
				// Integer.valueOf(myData[1]), speed, Integer.valueOf(myData[2])
				// == 1 ? true : false,Integer.valueOf(myData[3]));
				break;

			default:
				spriteList = generateRandomVampire(speed, 10);
				break;
			}

			for (MyEntitySmasher s : spriteList) {
				if (speedX < 0) {
					s.setOrientation(speed * -1);
				} else if (speedX > 0) {
					s.setOrientation(speed * 1);
				} else {
					double val = (Math.random() * 1000000) % 100;
					if (val > 50) {
						s.setOrientation(speed * -1);
					}
				}
			}

			return spriteList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public ArrayList<MyEntitySmasher> generateBoss() {
		ArrayList<MyEntitySmasher> spriteList = new ArrayList<MyEntitySmasher>();
		try {

			MyEntitySmasher smasher = new MyEntitySmasher(50, -100,
					SpriteTypeConstant.MONSTER_BOSS, false, 0);
			smasher.setSpeed(speed);
			smasher.setIdBehaviorType(behaviorType);
			spriteList.add(smasher);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return spriteList;
	}

	public void generateMonsterTypeList() {
		try {

			monsterTypeList = new HashMap<Integer, Integer>();

			String[] myData = monsterType.split(",");
			for (int i = 0; i < myData.length; i++) {
				monsterTypeList.put(Integer.valueOf(i),
						Integer.valueOf(myData[i]));
			}

		} catch (Exception e) {
			monsterTypeList = new HashMap<Integer, Integer>();
			e.printStackTrace();
		}

	}

	public ArrayList<MyEntitySmasher> generateHorizontal(int speed,
			int quantity, int wave) {
		ArrayList<MyEntitySmasher> spriteList = new ArrayList<MyEntitySmasher>();
		try {

			float time = 0;
			if (quantity > 4) {
				quantity = 4;
			}

			float beginPosition = 10;

			for (int i = 0; i < wave; i++) {

				if (((Math.random() * 100000000) % 100) > 50) {
					beginPosition = 10;
				} else {
					beginPosition = 310;
				}

				for (int j = 0; j < quantity; j++) {

					//float valTime = (float) (((Math.random() * 10000000) % 100) / 250);
					MyEntitySmasher smasher = new MyEntitySmasher(
							beginPosition == 310 ? beginPosition - 100 * j
									: beginPosition + 100 * j, -100,
							getRandomVampireByLevel(idLevel), false, time
									+ beginTime);

					smasher.setSpeed(speed);

					/*
					 * if(orientation<0){ smasher.setOrientation(-1); }else{
					 * smasher.setOrientation(1); }
					 */

					smasher.setIdBehaviorType(behaviorType);
					spriteList.add(smasher);

				}
				time++;

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return spriteList;
	}

	public ArrayList<MyEntitySmasher> generateV(int speed) {
		ArrayList<MyEntitySmasher> spriteList = new ArrayList<MyEntitySmasher>();
		try {

			MyEntitySmasher smasher = new MyEntitySmasher(220, -100,
					getRandomVampireByLevel(idLevel), false, 0 + beginTime);
			smasher.setSpeed(speed);
			smasher.setIdBehaviorType(behaviorType);
			spriteList.add(smasher);

			smasher = new MyEntitySmasher(110, -200,
					getRandomVampireByLevel(idLevel), false, 0 + beginTime);
			smasher.setSpeed(speed);
			smasher.setIdBehaviorType(behaviorType);
			spriteList.add(smasher);

			smasher = new MyEntitySmasher(310, -200,
					getRandomVampireByLevel(idLevel), false, 0 + beginTime);
			smasher.setSpeed(speed);
			smasher.setIdBehaviorType(behaviorType);
			spriteList.add(smasher);

			smasher = new MyEntitySmasher(340, -300,
					getRandomVampireByLevel(idLevel), false, 0 + beginTime);
			smasher.setSpeed(speed);
			smasher.setIdBehaviorType(behaviorType);
			spriteList.add(smasher);

			smasher = new MyEntitySmasher(10, -300,
					getRandomVampireByLevel(idLevel), false, 0 + beginTime);
			smasher.setSpeed(speed);
			smasher.setIdBehaviorType(behaviorType);
			spriteList.add(smasher);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return spriteList;
	}

	public ArrayList<MyEntitySmasher> generateHorizontarPosition(int speed,
			float x, int quantity, float timeB, int orientation, int wave) {
		ArrayList<MyEntitySmasher> spriteList = new ArrayList<MyEntitySmasher>();
		try {

			if (x > 310) {
				x = 310;
			}
			
			if(x<=10){
				
				//quantity = 4;
			}
			if(x>=110){
				if(quantity>3)
				quantity = 3;
			}
			if(x>=210){
				if(quantity>2)
				quantity = 2;
			}
			
			if(x>=310){
				if(quantity>1)
				quantity = 1;
			}

			float time = 0;

			for (int i = 1; i <= wave; i++) {
				for (int j = 0; j < quantity; j++) {
					MyEntitySmasher smasher = new MyEntitySmasher(x+(j*100), -100,
							getRandomVampireByLevel(idLevel), false, time
									+ beginTime);
					smasher.setSpeed(speed);
					spriteList.add(smasher);
					smasher.setIdBehaviorType(behaviorType);
					smasher.setOrientation(orientation);
				}
				time = time + Float.valueOf(timeB) / 10f;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return spriteList;
	}

	public ArrayList<MyEntitySmasher> generateVerticalPosition(int speed,
			float x, int quantity, float timeB, int orientation) {
		ArrayList<MyEntitySmasher> spriteList = new ArrayList<MyEntitySmasher>();
		try {

			if (x > 310) {
				x = 310;
			}

			float time = 0;

			for (int i = 1; i < quantity; i++) {
				MyEntitySmasher smasher = new MyEntitySmasher(x, -100,
						getRandomVampireByLevel(idLevel), false, time
								+ beginTime);
				smasher.setSpeed(speed);
				spriteList.add(smasher);
				smasher.setIdBehaviorType(behaviorType);
				time = time + Float.valueOf(timeB) / 10f;
				smasher.setOrientation(orientation);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return spriteList;
	}

	public ArrayList<MyEntitySmasher> generateVInverse(int speed) {
		ArrayList<MyEntitySmasher> spriteList = new ArrayList<MyEntitySmasher>();
		try {

			MyEntitySmasher smasher = new MyEntitySmasher(220, -300,
					getRandomVampireByLevel(idLevel), false, 0 + beginTime);
			smasher.setSpeed(speed);
			smasher.setIdBehaviorType(behaviorType);
			spriteList.add(smasher);

			smasher = new MyEntitySmasher(110, -200,
					getRandomVampireByLevel(idLevel), false, 0 + beginTime);
			smasher.setSpeed(speed);
			smasher.setIdBehaviorType(behaviorType);
			spriteList.add(smasher);

			smasher = new MyEntitySmasher(310, -200,
					getRandomVampireByLevel(idLevel), false, 0 + beginTime);
			smasher.setSpeed(speed);
			smasher.setIdBehaviorType(behaviorType);
			spriteList.add(smasher);

			smasher = new MyEntitySmasher(340, -100,
					getRandomVampireByLevel(idLevel), false, 0 + beginTime);
			smasher.setSpeed(speed);
			smasher.setIdBehaviorType(behaviorType);
			spriteList.add(smasher);

			smasher = new MyEntitySmasher(10, -100,
					getRandomVampireByLevel(idLevel), false, 0 + beginTime);
			smasher.setSpeed(speed);
			smasher.setIdBehaviorType(behaviorType);
			spriteList.add(smasher);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return spriteList;
	}

	public ArrayList<MyEntitySmasher> generateVertical(int speed, int quantity,
			int wave, int timeB, boolean isRight) {
		try {

			ArrayList<MyEntitySmasher> list = generateVertical(speed, quantity,
					wave, timeB);

			for (MyEntitySmasher ms : list) {
				if (!isRight)
					ms.setOrientation(speed * -1);
			}

			return list;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<MyEntitySmasher>();

	}

	public ArrayList<MyEntitySmasher> generateVertical(int speed, int quantity,
			int wave, int timeB) {
		ArrayList<MyEntitySmasher> spriteList = new ArrayList<MyEntitySmasher>();
		try {
			float time = 0;

			for (int i = 0; i < wave; i++) {

				float valX = (float) (((Math.random() * 100000000) % 310));

				for (int j = 0; j < quantity; j++) {

					MyEntitySmasher smasher = new MyEntitySmasher(valX, -100,
							getRandomVampireByLevel(idLevel), false, time
									+ beginTime);
					smasher.setSpeed(speed);
					smasher.setIdBehaviorType(behaviorType);
					spriteList.add(smasher);
					time = time + Float.valueOf(timeB) / 10f;
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return spriteList;
	}

	public ArrayList<MyEntitySmasher> generateDiagonaLine(int speed,
			int quantity, int wave, int timeB) {
		ArrayList<MyEntitySmasher> spriteList = new ArrayList<MyEntitySmasher>();
		try {
			float time = 0;

			if (quantity > 4)
				quantity = 4;

			for (int j = 0; j < quantity; j++) {

				MyEntitySmasher smasher = new MyEntitySmasher(10 + (j * 100),
						-100, getRandomVampireByLevel(idLevel), false, time
								+ beginTime);
				smasher.setSpeed(speed);
				smasher.setIdBehaviorType(behaviorType);
				spriteList.add(smasher);
				time = time + Float.valueOf(timeB) / 10f;

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return spriteList;
	}

	public ArrayList<MyEntitySmasher> generateDiagonaLineReverse(int speed,
			int quantity, int wave, int timeB) {
		ArrayList<MyEntitySmasher> spriteList = new ArrayList<MyEntitySmasher>();
		try {
			float time = 0;

			if (quantity > 4)
				quantity = 4;

			for (int j = 0; j < quantity; j++) {

				MyEntitySmasher smasher = new MyEntitySmasher(310 - (j * 100),
						-100, getRandomVampireByLevel(idLevel), false, time
								+ beginTime);
				smasher.setSpeed(speed);
				smasher.setIdBehaviorType(behaviorType);
				spriteList.add(smasher);
				time = time + Float.valueOf(timeB) / 10f;

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return spriteList;
	}

	public ArrayList<MyEntitySmasher> generateRandomHumanVampire(int quantity) {

		ArrayList<MyEntitySmasher> spriteList = new ArrayList<MyEntitySmasher>();
		try {

			Stack<Float> timeStack = generateTimeStack(10, 10f);

			while (!timeStack.isEmpty()) {
				float valX = (float) (((Math.random() * 100000) % 310) + 10);
				int speed = 130;

				MyEntitySmasher smasher = new MyEntitySmasher(valX, -100,
						getRandomVampireByLevel(idLevel), false,
						timeStack.pop() + beginTime);

				float val = (float) ((Math.random() * 100000) % 100);
				if (val > 70) {
					smasher.setIdSprite(getRandomHumanByLevel(idLevel));
				}

				smasher.setSpeed(speed);
				smasher.setIdBehaviorType(behaviorType);
				spriteList.add(smasher);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return spriteList;

	}

	public ArrayList<MyEntitySmasher> generateVampireInsideHuman(int speed,
			int position, int quantity) {
		ArrayList<MyEntitySmasher> spriteList = new ArrayList<MyEntitySmasher>();
		try {

			if (position > 100) {
				position = 0;
			}

			MyEntitySmasher smasher = new MyEntitySmasher(10 + position, -100,
					getRandomHumanByLevel(idLevel), false, 1 + beginTime);
			smasher.setSpeed(speed);
			smasher.setIdBehaviorType(behaviorType);
			spriteList.add(smasher);

			smasher = new MyEntitySmasher(210 + position, -100,
					getRandomHumanByLevel(idLevel), false, 1 + beginTime);
			smasher.setSpeed(speed);
			smasher.setIdBehaviorType(behaviorType);
			spriteList.add(smasher);

			smasher = new MyEntitySmasher(110 + position, -100,
					getRandomVampireByLevel(idLevel), false, 1 + beginTime);
			smasher.setSpeed(speed);
			smasher.setIdBehaviorType(behaviorType);
			spriteList.add(smasher);

			smasher = new MyEntitySmasher(110 + position, -100,
					getRandomHumanByLevel(idLevel), false, 0 + beginTime);
			smasher.setSpeed(speed);
			smasher.setIdBehaviorType(behaviorType);
			spriteList.add(smasher);

			smasher = new MyEntitySmasher(110 + position, -100,
					getRandomHumanByLevel(idLevel), false, 2 + beginTime);
			smasher.setSpeed(speed);
			smasher.setIdBehaviorType(behaviorType);
			spriteList.add(smasher);

			if (quantity == 8) {
				smasher = new MyEntitySmasher(10 + position, -100,
						getRandomHumanByLevel(idLevel), false, 0 + beginTime);
				smasher.setSpeed(speed);
				smasher.setIdBehaviorType(behaviorType);
				spriteList.add(smasher);

				smasher = new MyEntitySmasher(210 + position, -100,
						getRandomHumanByLevel(idLevel), false, 0 + beginTime);
				smasher.setSpeed(speed);
				smasher.setIdBehaviorType(behaviorType);
				spriteList.add(smasher);

				smasher = new MyEntitySmasher(10 + position, -100,
						getRandomHumanByLevel(idLevel), false, 2 + beginTime);
				smasher.setSpeed(speed);
				smasher.setIdBehaviorType(behaviorType);
				spriteList.add(smasher);

				smasher = new MyEntitySmasher(210 + position, -100,
						getRandomHumanByLevel(idLevel), false, 2 + beginTime);
				smasher.setSpeed(speed);
				smasher.setIdBehaviorType(behaviorType);
				spriteList.add(smasher);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return spriteList;
	}

	// genera un triangolo con la base hacia abajo, del tamano de base, en la
	// posicion determinada
	public ArrayList<MyEntitySmasher> generateTriangle(float position,
			int base, int speed, boolean isUp, int timeB) {
		ArrayList<MyEntitySmasher> spriteList = new ArrayList<MyEntitySmasher>();
		try {

			if (base >= 4) {
				// generating base
				float time = isUp ? 0 : Float.valueOf(timeB) / 10f * base;
				MyEntitySmasher smasher = new MyEntitySmasher(10, -100,
						getRandomVampireByLevel(idLevel), false, time
								+ beginTime);
				smasher.setSpeed(speed);
				smasher.setIdBehaviorType(behaviorType);
				spriteList.add(smasher);

				smasher = new MyEntitySmasher(110, -100,
						getRandomVampireByLevel(idLevel), false, time
								+ beginTime);
				smasher.setSpeed(speed);
				smasher.setIdBehaviorType(behaviorType);
				spriteList.add(smasher);

				smasher = new MyEntitySmasher(210, -100,
						getRandomVampireByLevel(idLevel), false, time
								+ beginTime);
				smasher.setSpeed(speed);
				smasher.setIdBehaviorType(behaviorType);
				spriteList.add(smasher);

				smasher = new MyEntitySmasher(310, -100,
						getRandomVampireByLevel(idLevel), false, time
								+ beginTime);
				smasher.setSpeed(speed);
				smasher.setIdBehaviorType(behaviorType);
				spriteList.add(smasher);

				// generating up base
				time = time
						+ (isUp ? +Float.valueOf(timeB) / 10f : -Float
								.valueOf(timeB) / 10f);
				smasher = new MyEntitySmasher(60, -100,
						getRandomVampireByLevel(idLevel), false, time
								+ beginTime);
				smasher.setSpeed(speed);
				smasher.setIdBehaviorType(behaviorType);
				spriteList.add(smasher);

				smasher = new MyEntitySmasher(160, -100,
						getRandomVampireByLevel(idLevel), false, time
								+ beginTime);
				smasher.setSpeed(speed);
				smasher.setIdBehaviorType(behaviorType);
				spriteList.add(smasher);

				smasher = new MyEntitySmasher(260, -100,
						getRandomVampireByLevel(idLevel), false, time
								+ beginTime);
				smasher.setSpeed(speed);
				smasher.setIdBehaviorType(behaviorType);
				spriteList.add(smasher);

				// generating top
				time = time
						+ (isUp ? +Float.valueOf(timeB) / 10f : -Float
								.valueOf(timeB) / 10f);
				smasher = new MyEntitySmasher(180, -100,
						getRandomVampireByLevel(idLevel), false, time
								+ beginTime);
				smasher.setSpeed(speed);
				smasher.setIdBehaviorType(behaviorType);
				spriteList.add(smasher);
			} else if (base == 3) {
				// generating base
				if (100 - position < 0) {
					position = 100;
				}
				float time = isUp ? 0 : Float.valueOf(timeB) / 10f * base;

				// generating base
				MyEntitySmasher smasher = new MyEntitySmasher(10 + position,
						-100, getRandomVampireByLevel(idLevel), false, time
								+ beginTime);
				smasher.setSpeed(speed);
				smasher.setIdBehaviorType(behaviorType);
				spriteList.add(smasher);

				smasher = new MyEntitySmasher(110 + position, -100,
						getRandomVampireByLevel(idLevel), false, time
								+ beginTime);
				smasher.setSpeed(speed);
				smasher.setIdBehaviorType(behaviorType);
				spriteList.add(smasher);

				smasher = new MyEntitySmasher(210 + position, -100,
						getRandomVampireByLevel(idLevel), false, time
								+ beginTime);
				smasher.setSpeed(speed);
				smasher.setIdBehaviorType(behaviorType);
				spriteList.add(smasher);

				// generating up base
				time = time
						+ (isUp ? +Float.valueOf(timeB) / 10f : -Float
								.valueOf(timeB) / 10f);
				smasher = new MyEntitySmasher(60 + position, -100,
						getRandomVampireByLevel(idLevel), false, time
								+ beginTime);
				smasher.setSpeed(speed);
				smasher.setIdBehaviorType(behaviorType);
				spriteList.add(smasher);

				smasher = new MyEntitySmasher(160 + position, -100,
						getRandomVampireByLevel(idLevel), false, time
								+ beginTime);
				smasher.setSpeed(speed);
				smasher.setIdBehaviorType(behaviorType);
				spriteList.add(smasher);

				// generating top

				time = time
						+ (isUp ? +Float.valueOf(timeB) / 10f : -Float
								.valueOf(timeB) / 10f);
				smasher = new MyEntitySmasher(110 + position, -100,
						getRandomVampireByLevel(idLevel), false, time
								+ beginTime);
				smasher.setSpeed(speed);
				smasher.setIdBehaviorType(behaviorType);
				spriteList.add(smasher);
			} else {

				if (200 - position < 0) {
					position = 100;
				}
				float time = isUp ? 0 : Float.valueOf(timeB) / 10f * base;

				// generating base
				MyEntitySmasher smasher = new MyEntitySmasher(10 + position,
						-100, getRandomVampireByLevel(idLevel), false, time
								+ beginTime);
				smasher.setSpeed(speed);
				smasher.setIdBehaviorType(behaviorType);
				spriteList.add(smasher);

				smasher = new MyEntitySmasher(110 + position, -100,
						getRandomVampireByLevel(idLevel), false, time
								+ beginTime);
				smasher.setSpeed(speed);
				smasher.setIdBehaviorType(behaviorType);
				spriteList.add(smasher);

				// generating top
				time = time
						+ (isUp ? +Float.valueOf(timeB) / 10f : -Float
								.valueOf(timeB) / 10f);
				smasher = new MyEntitySmasher(60 + position, -100,
						getRandomVampireByLevel(idLevel), false, time
								+ beginTime);
				smasher.setSpeed(speed);
				smasher.setIdBehaviorType(behaviorType);
				spriteList.add(smasher);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return spriteList;
	}

	public ArrayList<MyEntitySmasher> generateHumanInside(int speed,
			int position, int quantity) {
		ArrayList<MyEntitySmasher> spriteList = new ArrayList<MyEntitySmasher>();
		try {

			if (position > 100) {
				position = 0;
			}

			MyEntitySmasher smasher = new MyEntitySmasher(10 + position, -100,
					getRandomVampireByLevel(idLevel), false, 1 + beginTime);
			smasher.setSpeed(speed);
			smasher.setIdBehaviorType(behaviorType);
			spriteList.add(smasher);

			smasher = new MyEntitySmasher(210 + position, -100,
					getRandomVampireByLevel(idLevel), false, 1 + beginTime);
			smasher.setSpeed(speed);
			smasher.setIdBehaviorType(behaviorType);
			spriteList.add(smasher);

			smasher = new MyEntitySmasher(110 + position, -100,
					getRandomHumanByLevel(idLevel), false, 1 + beginTime);
			smasher.setSpeed(speed);
			smasher.setIdBehaviorType(behaviorType);
			spriteList.add(smasher);

			smasher = new MyEntitySmasher(110 + position, -100,
					getRandomVampireByLevel(idLevel), false, 0 + beginTime);
			smasher.setSpeed(speed);
			smasher.setIdBehaviorType(behaviorType);
			spriteList.add(smasher);

			smasher = new MyEntitySmasher(110 + position, -100,
					getRandomVampireByLevel(idLevel), false, 2 + beginTime);
			smasher.setSpeed(speed);
			smasher.setIdBehaviorType(behaviorType);
			spriteList.add(smasher);

			if (quantity == 8) {
				smasher = new MyEntitySmasher(10 + position, -100,
						getRandomVampireByLevel(idLevel), false, 0 + beginTime);
				smasher.setSpeed(speed);
				smasher.setIdBehaviorType(behaviorType);
				spriteList.add(smasher);

				smasher = new MyEntitySmasher(210 + position, -100,
						getRandomVampireByLevel(idLevel), false, 0 + beginTime);
				smasher.setSpeed(speed);
				smasher.setIdBehaviorType(behaviorType);
				spriteList.add(smasher);

				smasher = new MyEntitySmasher(10 + position, -100,
						getRandomVampireByLevel(idLevel), false, 2 + beginTime);
				smasher.setSpeed(speed);
				smasher.setIdBehaviorType(behaviorType);
				spriteList.add(smasher);

				smasher = new MyEntitySmasher(210 + position, -100,
						getRandomVampireByLevel(idLevel), false, 2 + beginTime);
				smasher.setSpeed(speed);
				smasher.setIdBehaviorType(behaviorType);
				spriteList.add(smasher);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return spriteList;
	}

	public int getRandomVampireByLevel(int idLevel) {
		try {

			if (monsterTypeList == null || monsterTypeList.isEmpty()) {
				return SpriteTypeConstant.MONSTER_ARMOR;
			}

			Integer randomId = (int) ((Math.random() * 1000000) % monsterTypeList
					.size());

			double val = (Math.random() * 1000000) % 100;
			if (val < probabilityHuman) {
				return getRandomHumanByLevel(idLevel);
			}
			return monsterTypeList.get(randomId);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return SpriteTypeConstant.MONSTER_BASIC;
	}

	public int getRandomHumanByLevel(int idLevel) {
		try {

			return SpriteTypeConstant.HUMAN_BASIC;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return SpriteTypeConstant.HUMAN_BASIC;
	}

	public ArrayList<MyEntitySmasher> generateRandomVampire(int quantity,
			int time) {
		ArrayList<MyEntitySmasher> spriteList = new ArrayList<MyEntitySmasher>();
		try {

			Stack<Float> timeStack = generateTimeStack(quantity, time);

			while (!timeStack.isEmpty()) {
				float valX = (float) (((Math.random() * 100000) % 310) + 10);
				// int speed = 130;

				MyEntitySmasher smasher = new MyEntitySmasher(valX, -100,
						getRandomVampireByLevel(idLevel), false,
						timeStack.pop() + beginTime);
				smasher.setSpeed(speed);
				smasher.setIdBehaviorType(behaviorType);
				spriteList.add(smasher);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return spriteList;
	}

	// generar tiempos aleatorios para cantidad de monstruos
	public Stack<Float> generateTimeStack(float quantity, float time) {
		Stack<Float> timeStack = new Stack<Float>();
		try {

			for (int i = 0; i < quantity; i++) {
				float timeG = (float) ((Math.random() * 1000000) % time);
				timeStack.add(timeG);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return timeStack;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public int getBehaviorType() {
		return behaviorType;
	}

	public void setBehaviorType(int behaviorType) {
		this.behaviorType = behaviorType;
	}

	public String getMonsterType() {
		return monsterType;
	}

	public void setMonsterType(String monsterType) {
		this.monsterType = monsterType;
	}

	public int getProbabilityHuman() {
		return probabilityHuman;
	}

	public void setProbabilityHuman(int probabilityHuman) {
		this.probabilityHuman = probabilityHuman;
	}

	public HashMap<Integer, Integer> getMonsterTypeList() {
		return monsterTypeList;
	}

	public void setMonsterTypeList(HashMap<Integer, Integer> monsterTypeList) {
		this.monsterTypeList = monsterTypeList;
	}

	public int getSpeedX() {
		return speedX;
	}

	public void setSpeedX(int speedX) {
		this.speedX = speedX;
	}

}
