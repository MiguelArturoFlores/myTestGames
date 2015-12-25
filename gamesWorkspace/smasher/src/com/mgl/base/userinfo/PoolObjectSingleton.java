package com.mgl.base.userinfo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

import com.mgl.drop.MainDropActivity;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.sprites.SpriteBloodInHud;
import com.mgl.drop.game.sprites.SpriteHumanBasic;
import com.mgl.drop.game.sprites.SpriteMonsterArmor;
import com.mgl.drop.game.sprites.SpriteMonsterBasic;
import com.mgl.drop.game.sprites.SpriteMonsterBatVampire;
import com.mgl.drop.game.sprites.SpriteMonsterHole;
import com.mgl.drop.game.sprites.SpriteMonsterWithoutArmor;
import com.mgl.drop.texture.TextureSingleton;

public class PoolObjectSingleton {

	private static PoolObjectSingleton instance = null;

	private Queue<SpriteBloodInHud> bloodHud;
	private Queue<SpriteMonsterBasic> basicList;
	private Queue<SpriteMonsterBatVampire> batList;
	private Queue<SpriteMonsterArmor> armorList;
	private Queue<SpriteMonsterWithoutArmor> unarmorList;
	private Queue<SpriteMonsterHole> holeList;
	
	private Queue<SpriteHumanBasic> humanList;

	private PoolObjectSingleton() {
		try {
			TextureSingleton texture = TextureSingleton.getInstance();

			bloodHud = new LinkedList<SpriteBloodInHud>();
			for (int i = 0; i < 40; i++) {
				SpriteBloodInHud blood = new SpriteBloodInHud(0, 0,
						texture.getTextureAnimateByName("blood.png"),
						texture.getVertexBufferObjectManager());
				bloodHud.add(blood);
			}

			basicList = new LinkedList<SpriteMonsterBasic>();
			for (int i = 0; i < 30; i++) {
				int val = MainDropActivity.getRandomMax(0,100);
				int sprSize = 10;
				String textureName = "monsterFat.png";
				if(val<50){
					//sprSize = 5;
					//textureName = "adam.png";
				}
				
				SpriteMonsterBasic mons = new SpriteMonsterBasic(100, 100,
						texture.getTextureAnimateByName(textureName),
						texture.getVertexBufferObjectManager(), null, 100, 200,sprSize);
				basicList.add(mons);
			}

			batList = new LinkedList<SpriteMonsterBatVampire>();
			for (int i = 0; i < 30; i++) {
				SpriteMonsterBatVampire bat = new SpriteMonsterBatVampire(100,
						100, texture.getTextureAnimateByName("batVampire.png"),
						texture.getVertexBufferObjectManager(), null, 100, 200);
				batList.add(bat);
			}
			
			armorList = new LinkedList<SpriteMonsterArmor>();
			for (int i = 0; i < 30; i++) {
				SpriteMonsterArmor armor = new SpriteMonsterArmor(100, 100,
						texture.getTextureAnimateByName("armorVampire.png"),
						texture.getVertexBufferObjectManager(), null, 100, 200);

				armorList.add(armor);
			}
			
			unarmorList = new LinkedList<SpriteMonsterWithoutArmor>();
			for (int i = 0; i < 30; i++) {
				SpriteMonsterWithoutArmor armor2 = new SpriteMonsterWithoutArmor(
						100, 100,
						texture.getTextureAnimateByName("unarmorVampire.png"),
						texture.getVertexBufferObjectManager(), null, 100, 200);
				unarmorList.add(armor2);
			}
			
			holeList = new LinkedList<SpriteMonsterHole>();
			for (int i = 0; i < 25; i++) {
				SpriteMonsterHole hole = new SpriteMonsterHole(100, 100,
						texture.getTextureAnimateByName("desapearVampire.png"),
						texture.getVertexBufferObjectManager(), null, 100, 200);
				holeList.add(hole);
			}
			
			humanList = new LinkedList<SpriteHumanBasic>();
			for (int i = 0; i < 25; i++) {
				
				int val = MainDropActivity.getRandomMax(0,100);
				int sprSize = 6;
				String textureName = "human.png";
				if(val<50){
					sprSize = 4;
					textureName = "julian.png";
				}
				
				SpriteHumanBasic human = new SpriteHumanBasic(100, 100,
						texture.getTextureAnimateByName(textureName),
						texture.getVertexBufferObjectManager(), null, 100, 200,sprSize);
				
				humanList.add(human);
			}


		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static PoolObjectSingleton getInstance() {
		try {

			if (instance == null) {
				instance = new PoolObjectSingleton();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return instance;
	}

	public SpriteBloodInHud getBloodInHud() {
		try {

			SpriteBloodInHud sprite = bloodHud.poll();
			bloodHud.add(sprite);

			return sprite;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public SpriteMonsterBasic getMonsterBasic() {
		try {

			SpriteMonsterBasic sprite = basicList.poll();
			basicList.add(sprite);

			return sprite;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public SpriteMonsterBatVampire getBat() {
		try {

			SpriteMonsterBatVampire sprite = batList.poll();
			batList.add(sprite);

			return sprite;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public SpriteMonsterArmor getArmor() {
		try {

			SpriteMonsterArmor sprite = armorList.poll();
			armorList.add(sprite);

			return sprite;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public SpriteMonsterWithoutArmor getUnarmor() {
		try {

			SpriteMonsterWithoutArmor sprite = unarmorList.poll();
			unarmorList.add(sprite);

			return sprite;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public SpriteMonsterHole getHole() {
		try {

			SpriteMonsterHole sprite = holeList.poll();
			holeList.add(sprite);

			return sprite;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public SpriteHumanBasic getHuman() {
		try {

			SpriteHumanBasic sprite = humanList.poll();
			humanList.add(sprite);

			return sprite;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
