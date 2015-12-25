package com.mgl.base;

import com.mgl.base.userinfo.PoolObjectSingleton;
import com.mgl.drop.game.constant.ZIndexGame;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.hud.sprites.SpriteMessage;
import com.mgl.drop.game.sprites.SpriteHumanBasic;
import com.mgl.drop.game.sprites.SpriteMonsterArmor;
import com.mgl.drop.game.sprites.SpriteMonsterBasic;
import com.mgl.drop.game.sprites.SpriteMonsterBatVampire;
import com.mgl.drop.game.sprites.SpriteMonsterBoss;
import com.mgl.drop.game.sprites.SpriteMonsterHole;
import com.mgl.drop.game.sprites.SpriteMonsterWithoutArmor;
import com.mgl.drop.game.sprites.SpriteMonsterZigzag;
import com.mgl.drop.texture.TextureSingleton;

public class MyFactory {

	public static TextureSingleton texture = TextureSingleton.getInstance();

	public static MySpriteGeneral createSprite(int idSprite,
			LevelController lc, int width, int height, int speed,
			int idBehaviorType, int orientation, int diamant) {

		try {

			MySpriteGeneral sprite = null;

			switch (idSprite) {

			case SpriteTypeConstant.HUMAN_BASIC:
				
				SpriteHumanBasic human = PoolObjectSingleton.getInstance().getHuman(); 
				human.setSpeed(speed);
				human.detachSelf();
				human.setLevel(lc);
				human.setHasPlayRunSound(false);
				human.setStatus(StatusType.NORMAL);
				human.setZIndex(ZIndexGame.VAMPIRE);
				human.setBehavior(idBehaviorType);

				sprite = human;

				break;

			case SpriteTypeConstant.MONSTER_BASIC:
				SpriteMonsterBasic mons = PoolObjectSingleton.getInstance()
						.getMonsterBasic();
				mons.detachSelf();
				mons.setLevel(lc);
				mons.setStatus(StatusType.NORMAL);
				mons.setSpeed(speed);
				mons.setZIndex(ZIndexGame.VAMPIRE);
				mons.setBehavior(idBehaviorType);

				sprite = mons;

				break;
			case SpriteTypeConstant.MONSTER_ZIGZAG:
				SpriteMonsterZigzag zigzag = new SpriteMonsterZigzag(100, 100,
						texture.getTextureAnimateByName("justin.png"),
						texture.getVertexBufferObjectManager(), lc, 100, 200);
				zigzag.setSpeed(speed);

				zigzag.setZIndex(ZIndexGame.VAMPIRE);
				zigzag.setBehavior(idBehaviorType);
				

				sprite = zigzag;

				break;

			case SpriteTypeConstant.MONSTER_HOLE:
				SpriteMonsterHole hole = PoolObjectSingleton.getInstance().getHole();
//				SpriteMonsterHole hole = new SpriteMonsterHole(100, 100,
//						texture.getTextureAnimateByName("desapearVampire.png"),
//						texture.getVertexBufferObjectManager(), lc, 100, 200);
				hole.setSpeed(speed);
				hole.detachSelf();
				hole.setStatus(StatusType.NORMAL);
				hole.init();
				hole.setLevel(lc);
				

				hole.setBehavior(idBehaviorType);
				hole.setZIndex(ZIndexGame.VAMPIRE);

				sprite = hole;

				break;

			case SpriteTypeConstant.MONSTER_ARMOR:
				SpriteMonsterArmor armor = PoolObjectSingleton.getInstance()
						.getArmor();
				// SpriteMonsterArmor armor = new SpriteMonsterArmor(100, 100,
				// texture.getTextureAnimateByName("armorVampire.png"),
				// texture.getVertexBufferObjectManager(), lc, 100, 200);
				armor.detachSelf();
				armor.setStatus(StatusType.NORMAL);
				armor.setSpeed(speed);
				armor.setLevel(lc);

				armor.setZIndex(ZIndexGame.VAMPIRE);
				armor.setBehavior(idBehaviorType);

				sprite = armor;

				break;
			case SpriteTypeConstant.MONSTER_WITHOUT_ARMOR:
				SpriteMonsterWithoutArmor armor2 = PoolObjectSingleton.getInstance()
				.getUnarmor();
//				SpriteMonsterWithoutArmor armor2 = new SpriteMonsterWithoutArmor(
//						100, 100,
//						texture.getTextureAnimateByName("unarmorVampire.png"),
//						texture.getVertexBufferObjectManager(), lc, 100, 200);\
				armor2.detachSelf();
				armor2.setStatus(StatusType.NORMAL);
				armor2.setSpeed(speed);
				armor2.setLevel(lc);
				armor2.setZIndex(ZIndexGame.VAMPIRE);
				armor2.setBehavior(idBehaviorType);

				sprite = armor2;

				break;
			case SpriteTypeConstant.MONSTER_BAT:
				SpriteMonsterBatVampire bat = PoolObjectSingleton.getInstance()
						.getBat();
				// SpriteMonsterBatVampire bat = new
				// SpriteMonsterBatVampire(100, 100,
				// texture.getTextureAnimateByName("batVampire.png"),
				// texture.getVertexBufferObjectManager(), lc, 100, 200);
				bat.detachSelf();
				bat.setStatus(StatusType.NORMAL);
				bat.setSpeed(speed);
				bat.setLevel(lc);

				bat.setZIndex(ZIndexGame.VAMPIRE);
				bat.setBehavior(idBehaviorType);

				sprite = bat;

				break;
			case SpriteTypeConstant.MONSTER_BOSS:
				SpriteMonsterBoss boss = new SpriteMonsterBoss(100, 100,
						texture.getTextureAnimateByName("boss.png"),
						texture.getVertexBufferObjectManager(), lc, 100, 200);
				boss.setSpeed(speed);

				boss.setZIndex(ZIndexGame.VAMPIRE);
				boss.setBehavior(idBehaviorType);
				sprite = boss;

				break;
			default:
				break;
			}

			MyAnimateSprite spr = (MyAnimateSprite) sprite;
			spr.setDiamant(diamant);
			if (orientation < 0) {

				spr.setSpeedX(spr.getSpeedX() * -1);
				return spr;
			}

			return sprite;

		} catch (Exception e) {
			return null;
		}

	}

	public static SpriteMessage createMessage(String message, float timeToShow) {
		try {

			return new SpriteMessage(0, 0,
					texture.getTextureByName("black.jpg"),
					texture.getVertexBufferObjectManager(), message, timeToShow);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
