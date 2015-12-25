package com.mgl.drop.game.entity;

import org.andengine.entity.IEntity;
import org.andengine.entity.scene.ITouchArea;

import com.mgl.base.MyEntity;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.hud.LevelHUD;

public class EntityLive extends MyEntity {

	private Long live;
	private LevelHUD levelHUD;
	
	private Long totalLive;

	public EntityLive(Long i, LevelHUD hud) {
		try {

			this.totalLive = i ;
			this.live = i;
			this.levelHUD = hud;
			hud.changeLiveText(i + "");

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

	public void looseLive() {
		try {

			live--;
			levelHUD.changeLiveText(live.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public Long getLive() {
		return live;
	}

	public void setLive(Long live) {
		this.live = live;
	}

	public LevelHUD getLevelHUD() {
		return levelHUD;
	}

	public void setLevelHUD(LevelHUD levelHUD) {
		this.levelHUD = levelHUD;
	}

	@Override
	public void setMustUpdate(boolean mustUpdate) {
		// TODO Auto-generated method stub

	}

	public void resetLife() {
		try {

			live = totalLive;
			levelHUD.changeLiveText(live + "");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
