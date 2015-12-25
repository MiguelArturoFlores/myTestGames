package com.mgl.drop.game.entity;

import org.andengine.entity.IEntity;
import org.andengine.entity.scene.ITouchArea;

import com.mgl.base.MyEntity;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.hud.LevelHUD;

public class EntityScore extends MyEntity {

	private int score = 0;
	private LevelHUD levelHUD;

	private int comboCont = 0;

	private int noTouchVampire = 0;
	private int contVampireSmash = 0;

	private boolean hasTouchedVampire = false;

	public EntityScore(LevelHUD hud) {
		try {

			score = 0;
			levelHUD = hud;
			hud.changeScoreText(score);

			comboCont = 0;
			noTouchVampire = 0;

			contVampireSmash = 0;

		} catch (Exception e) {
			e.printStackTrace();
		}
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

	public void addTouch() {
		try {

			noTouchVampire = 1;
			comboCont = 0;

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public int updateScore(int score) {
		try {
			comboCont++;

			this.score += score+comboCont;
			levelHUD.changeScoreText(this.score);
			contVampireSmash++;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return comboCont;
	}

	@Override
	public void setMustUpdate(boolean mustUpdate) {
		// TODO Auto-generated method stub

	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getContVampireSmash() {
		return contVampireSmash;
	}

	public void setContVampireSmash(int contVampireSmash) {
		this.contVampireSmash = contVampireSmash;
	}

}
