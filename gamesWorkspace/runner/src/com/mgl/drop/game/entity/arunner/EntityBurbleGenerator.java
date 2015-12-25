package com.mgl.drop.game.entity.arunner;

import org.andengine.entity.IEntity;
import org.andengine.entity.scene.ITouchArea;

import com.mgl.base.MyEntity;
import com.mgl.base.MyFactory;
import com.mgl.base.SpriteType;
import com.mgl.base.SpriteTypeConstant;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.game.constant.ZIndexGame;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.sprites.arunner.SpriteInvisiblePointToFollow;
import com.mgl.drop.game.sprites.arunner.SpriteVulcanoBurble;

public class EntityBurbleGenerator extends MyEntity{

	private float timeToGenerate = 2;
	private float cont = 0;
	
	@Override
	public SpriteType getSpriteType() {
		// TODO Auto-generated method stub
		return SpriteType.DECORATIVE;
	}
	
	@Override
	public void setMustUpdate(boolean mustUpdate) {
		this.mustUpdate = mustUpdate;
	}

	@Override
	public float getTime() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public IEntity getEntity() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ITouchArea getTouchArea() {
		return null;
	}

	@Override
	public void updateChild(float dTime, LevelController level) {
		try {
			cont = cont +dTime;
			if(cont < timeToGenerate){
				return;
			}
			cont = 0;
			generateBurbles(level);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void generateBurbles(LevelController level) {
		try {

			try {

				for (int i = 0; i < MainDropActivity.getRandomMax(3, 6); i++) {
					SpriteVulcanoBurble burble = (SpriteVulcanoBurble) MyFactory
							.createObstacle(SpriteTypeConstant.BURBLE_VULCANO,
									level);
					
					SpriteInvisiblePointToFollow po = level.getInvisiblePointToFollow();
					float x = MainDropActivity.getRandomMax((int)po.getX()-700,(int)po.getX()+700);
					float y = MainDropActivity.getRandomMax((int)po.getY()+300,(int)po.getY() + 400);

					float w = MainDropActivity.getRandomMax(7, 50);
					// float h = MainDropActivity.getRandomMax(5,20);
					burble.setSize(w, w);

					burble.setPosition(x, y);
					burble.setDirection(SpriteVulcanoBurble.DRECTION_UP);
					burble.setAlpha(0.4f);
					burble.setActive(false);
					burble.setZIndex(ZIndexGame.BURBLE);
					burble.setDistance(800);
					level.getScene().attachChild(burble);
					level.addSpriteToUpdate(burble);

				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
}
