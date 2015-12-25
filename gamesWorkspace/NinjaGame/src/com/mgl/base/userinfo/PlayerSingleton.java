package com.mgl.base.userinfo;

import com.mgl.drop.game.model.PlayerModel;

public class PlayerSingleton {

	private static PlayerSingleton instance = null;

	private PlayerModel player;

	private PlayerSingleton() {
		try {

			player = new PlayerModel();

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

}
