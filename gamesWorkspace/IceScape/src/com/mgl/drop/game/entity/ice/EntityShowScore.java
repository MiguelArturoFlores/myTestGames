package com.mgl.drop.game.entity.ice;

import org.andengine.entity.IEntity;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.text.Text;

import com.mgl.base.MyEntity;
import com.mgl.base.MyFactory;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.factory.ObjectFactorySingleton;
import com.mgl.drop.game.constant.ZIndexGame;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.texture.TextureSingleton;

public class EntityShowScore extends MyEntity{

	private Text scoreText;
	private int score;
	
	private Scene scene;
	
	public EntityShowScore(Scene scene){
		try {
			
			this.scene = scene;
			score = 0;
			
			attachScore();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void attachScore() {
		try {
			
			if(scoreText!=null){
				scoreText.detachSelf();
			}
			
			scoreText = ObjectFactorySingleton.getInstance().createText(score+"", TextureSingleton.getInstance().getmFont1());
			scoreText.setPosition(MainDropActivity.CAMERA_WIDTH/2 - scoreText.getWidth()/2, MainDropActivity.CAMERA_HEIGHT - 150);
			scene.attachChild(scoreText);
			
			scoreText.setZIndex(ZIndexGame.FADE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setMustUpdate(boolean mustUpdate) {
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateChild(float dTime, LevelController level) {
		// TODO Auto-generated method stub
		
	}

	public void increaseScore(int quantity) {
		try {
			this.score = this.score + quantity;
			attachScore();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	
	
}
