package com.mgl.drop.game.controller;

import java.util.ArrayList;

import org.andengine.entity.IEntity;
import org.andengine.entity.scene.ITouchArea;

import com.mgl.base.MyEntity;
import com.mgl.base.MySpriteGeneral;

public class GameObjectsController extends MyEntity {

	private ArrayList<MySpriteGeneral> spriteList;
	private LevelController levelController;
	private Long idLevel;
	private int gameType;

	public GameObjectsController(LevelController levelController, Long idLevel,
			int gametype) {
		try {
			this.idLevel = idLevel;
			this.levelController = levelController;
			this.gameType = gametype;

			loadGameObject();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void loadGameObject() {
		try {

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void setMustUpdate(boolean mustUpdate) {
		this.mustUpdate = mustUpdate;
	}

	@Override
	public float getTime() {
		return 0;
	}

	@Override
	public IEntity getEntity() {
		return null;
	}

	@Override
	public ITouchArea getTouchArea() {
		return null;
	}

	@Override
	public void updateChild(float dTime, LevelController level) {

		try {

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getGameType() {
		return gameType;
	}

	public void setGameType(int gameType) {
		this.gameType = gameType;
	}

}
