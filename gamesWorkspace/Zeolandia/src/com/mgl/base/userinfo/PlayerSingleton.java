package com.mgl.base.userinfo;

import com.mgl.drop.MainDropActivity;
import com.mgl.drop.game.HUDManagerSingleton;
import com.mgl.drop.game.constant.GameConstants;
import com.mgl.drop.game.database.DatabaseDrop;
import com.mgl.drop.game.database.LevelDAO;
import com.mgl.drop.game.hud.InformativeHUD;
import com.mgl.drop.game.model.PlayerModel;
import com.mgl.drop.game.scene.SceneManagerSingleton;

public class PlayerSingleton {

	public static final int TECHNIQUE_1 = 1;
	public static final int TECHNIQUE_2 = 2;
	public static final int TECHNIQUE_3 = 3;
	public static final int TECHNIQUE_4 = 4;
	
	public static final int PLAYER_ZEO = 1;
	
	private static PlayerSingleton instance = null;

	private PlayerModel player;
	
	private boolean giveManaPotion = false;
	private boolean giveLifePotion = false;

	private PlayerSingleton() {
		try {

			LevelDAO dao = new LevelDAO(SceneManagerSingleton.getInstance()
					.getActivity(), DatabaseDrop.DB_NAME, null,
					MainDropActivity.DB_VERSION);
			player = dao.loadPlayerById(GameConstants.PLAYER_ZEO);
			
			//player = new PlayerModel();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static PlayerSingleton getInstance() {

		try {

			if (instance == null) {
				instance = new PlayerSingleton();
			}

		} catch (Exception e) {

		}
		return instance;
	}

	public PlayerModel getPlayer() {
		return player;
	}

	public void setPlayer(PlayerModel player) {
		this.player = player;
	}

	public static void setInstance(PlayerSingleton instance) {
		PlayerSingleton.instance = instance;
	}

	public void increaseExperience(int experience) {
		try {
			
			LevelUpSingleton.getInstance().increasExperience(player, experience);
			updatePlayer();
			//HUDManagerSingleton.getInstance().addHUD(new InformativeHUD("Win "+player.getCurrentExperience()+" experience " ), true);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void updatePlayer() {
		try {
			
			LevelDAO dao = new LevelDAO(SceneManagerSingleton.getInstance()
					.getActivity(), DatabaseDrop.DB_NAME, null,
					MainDropActivity.DB_VERSION);
			
			dao.updatePlayerModel(player);
			//HUDManagerSingleton.getInstance().addHUD(new InformativeHUD("upd"), true);
			
			player = dao.loadPlayerById(GameConstants.PLAYER_ZEO);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean isGiveManaPotion() {
		return giveManaPotion;
	}

	public void setGiveManaPotion(boolean giveManaPotion) {
		this.giveManaPotion = giveManaPotion;
	}

	public boolean isGiveLifePotion() {
		return giveLifePotion;
	}

	public void setGiveLifePotion(boolean giveLifePotion) {
		this.giveLifePotion = giveLifePotion;
	}

	public void died() {
		try {
			
			player.setCurrentHP(player.getTotalHP());
			player.setCurrentMP(player.getTotalMP());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void unlockTechniqueOneZEO() {
		try {
			
			LevelDAO dao = new LevelDAO(SceneManagerSingleton.getInstance()
					.getActivity(), DatabaseDrop.DB_NAME, null,
					MainDropActivity.DB_VERSION);
			
			dao.unlockPlayerTechnique(PLAYER_ZEO, TECHNIQUE_1);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	
}
