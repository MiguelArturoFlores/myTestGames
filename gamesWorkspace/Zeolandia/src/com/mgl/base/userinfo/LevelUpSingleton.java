package com.mgl.base.userinfo;

import com.mgl.drop.game.HUDManagerSingleton;
import com.mgl.drop.game.hud.InformativeHUD;
import com.mgl.drop.game.hud.zeolandia.LevelUpHUD;
import com.mgl.drop.game.model.PlayerModel;

public class LevelUpSingleton {

	private static final int LEVEL2_EXPERIENCE = 20;
	private static final int LEVEL3_EXPERIENCE = 60;
	private static final int LEVEL4_EXPERIENCE = 120;
	private static final int LEVEL5_EXPERIENCE = 200;
	private static final int LEVEL6_EXPERIENCE = 300;
	private static final int LEVEL7_EXPERIENCE = 500;
	
	private static LevelUpSingleton instance = null;
	
	private LevelUpSingleton(){
		try {
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static LevelUpSingleton getInstance() {
		if(instance == null){
			instance = new LevelUpSingleton();
		}
		return instance;
	}

	public static void setInstance(LevelUpSingleton instance) {
		LevelUpSingleton.instance = instance;
	}

	public void increasExperience(PlayerModel player, int experience) {
		try {
			
			int exp = player.getCurrentExperience();
			int level = player.getLevel();
			
			exp = exp + experience;
			player.setCurrentExperience(exp);
			
			
			switch (level) {
			case 1:
				
				if(experience<LEVEL2_EXPERIENCE){
					return;
				}
				
				player.setLevel(2);
				HUDManagerSingleton.getInstance().addHUD(new LevelUpHUD(player.getLevel()), true);
				player.setExperienceNextLevel(LEVEL3_EXPERIENCE);
				levelUp(player, 2);
				
				break;

			case 2:
				
				if(experience<LEVEL3_EXPERIENCE){
					return;
				}
				player.setLevel(3);
				HUDManagerSingleton.getInstance().addHUD(new LevelUpHUD(player.getLevel()), true);
				player.setExperienceNextLevel(LEVEL4_EXPERIENCE);
				break;
				
			case 3:
				
				if(experience<LEVEL4_EXPERIENCE){
					return;
				}
				player.setLevel(4);
				HUDManagerSingleton.getInstance().addHUD(new LevelUpHUD(player.getLevel()), true);
				player.setExperienceNextLevel(LEVEL5_EXPERIENCE);
				
				break;
			
			case 4:
				
				if(experience<LEVEL5_EXPERIENCE){
					return;
				}
				player.setLevel(5);
				HUDManagerSingleton.getInstance().addHUD(new LevelUpHUD(player.getLevel()), true);
				player.setExperienceNextLevel(LEVEL6_EXPERIENCE);
				
				break;
				
			case 5:
				
				if(experience<LEVEL6_EXPERIENCE){
					return;
				}
				player.setLevel(6);
				HUDManagerSingleton.getInstance().addHUD(new LevelUpHUD(player.getLevel()), true);
				player.setExperienceNextLevel(LEVEL7_EXPERIENCE);
				
				break;
				
			case 6:
				
				if(experience<LEVEL7_EXPERIENCE){
					return;
				}
				player.setLevel(7);
				HUDManagerSingleton.getInstance().addHUD(new LevelUpHUD(player.getLevel()), true);
				player.setExperienceNextLevel(LEVEL7_EXPERIENCE);
				
				break;
				
				
			default:
				break;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void levelUp(PlayerModel player, int i) {
		try {
			
			switch (i) {
			case 2:
				
				player.setAttack(4);
				player.setDefense(3);
				player.setMagicPower(2);
				
				player.setTotalHP(25);
				player.setCurrentHP(player.getTotalHP());
				
				player.setTotalMP(9);
				player.setCurrentMP(player.getTotalMP());

				break;
			case 3:
				
				player.setAttack(7);
				player.setDefense(5);
				player.setMagicPower(3);
				
				player.setTotalHP(30);
				player.setCurrentHP(player.getTotalHP());
				
				player.setTotalMP(12);
				player.setCurrentMP(player.getTotalMP());

				break;

			case 4:
				
				player.setAttack(10);
				player.setDefense(7);
				player.setMagicPower(4);
				
				player.setTotalHP(35);
				player.setCurrentHP(player.getTotalHP());
				
				player.setTotalMP(15);
				player.setCurrentMP(player.getTotalMP());

				break;
				
			case 5:
				
				player.setAttack(13);
				player.setDefense(9);
				player.setMagicPower(5);
				
				player.setTotalHP(40);
				player.setCurrentHP(player.getTotalHP());
				
				player.setTotalMP(18);
				player.setCurrentMP(player.getTotalMP());

				break;
				
			case 6:
				
				player.setAttack(16);
				player.setDefense(11);
				player.setMagicPower(6);
				
				player.setTotalHP(45);
				player.setCurrentHP(player.getTotalHP());
				
				player.setTotalMP(21);
				player.setCurrentMP(player.getTotalMP());

				break;

			case 7:
				
				player.setAttack(19);
				player.setDefense(13);
				player.setMagicPower(7);
				
				player.setTotalHP(50);
				player.setCurrentHP(player.getTotalHP());
				
				player.setTotalMP(24);
				player.setCurrentMP(player.getTotalMP());

				break;
				
			default:
				break;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	
}
