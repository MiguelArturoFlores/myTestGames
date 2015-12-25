package com.mgl.drop.game.entity.ice;

import org.andengine.entity.IEntity;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.entity.sprite.Sprite;

import com.mgl.base.MyEntity;
import com.mgl.base.MySpriteGeneral;
import com.mgl.base.userinfo.PoolObjectSingleton;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.game.constant.ZIndexGame;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.sprites.aicerunner.SpriteCloud;
import com.mgl.drop.game.sprites.aicerunner.SpriteSnow;
import com.mgl.drop.texture.TextureSingleton;

public class EntityPenguinBackground extends MyEntity {

	private TextureSingleton texture = TextureSingleton.getInstance();

	private float timeToGenerate = 6f;
	private float contTime = timeToGenerate;
	
	private SpriteCloud lastGenerate = null;
	
	private float timeSnow = 1;
	private float contSnow = timeSnow;

	public EntityPenguinBackground(LevelController controller) {
		try {

			String textureName = new String("backgroundW1.png");
			
			if(MainDropActivity.getRandomMax(0, 100)<50){
				textureName = new String("backgroundW2.png");
			}
			
			Sprite background = new Sprite(
					0,
					0,
					texture.getTextureByName(textureName),
					texture.getVertexBufferObjectManager());
			background.setSize(480, 800);
			background.setZIndex(ZIndexGame.BACKGROUND);
			controller.getScene().attachChild(background);

			Sprite fade = new Sprite(0, 0,
					texture.getTextureByName("fade.png"),
					texture.getVertexBufferObjectManager());
			fade.setZIndex(ZIndexGame.FADE);
			//controller.getScene().attachChild(fade);

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
		try {

			updateSnow(dTime,level);
			
			contTime += dTime;

			if (contTime < timeToGenerate) {
				return;
			}

			contTime = 0;

			String textureName = "cloud1.png";
			int val = MainDropActivity.getRandomMax(0, 100);

			if (val < 33) {
				textureName = "cloud2.png";
			} else if (val < 66) {
				textureName = "cloud3.png";
			}

			SpriteCloud cloud = new SpriteCloud(0, 0,
					texture.getTextureByName(textureName),
					texture.getVertexBufferObjectManager());

			float pY = MainDropActivity.getRandomMax(25,
					(int) (480 - cloud.getHeight()));

			cloud.setX(cloud.getWidth() * -1);
			cloud.setY(pY);
			cloud.setZIndex(ZIndexGame.BACKGROUND2);

			level.addSpriteToUpdate(cloud);
			level.getScene().attachChild(cloud);
			
			validateCloudPosition(cloud);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void validateCloudPosition(SpriteCloud cloud) {
		try {
			
			if(lastGenerate==null){
				lastGenerate = cloud;
				return;
			}
			
			int i = 0;
			while (lastGenerate.collidesWith(cloud)){
				float pY = MainDropActivity.getRandomMax(25,
						(int) (480 - cloud.getHeight()));
	
				cloud.setX(cloud.getWidth() * -1);
				cloud.setY(pY);
				i++;
				if(i>10){
					return;
				}
			}
			
			lastGenerate = cloud;
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void updateSnow(float dTime, LevelController level) {
		try {
			
			contSnow +=dTime;
			if(contSnow< timeSnow){
				return;
			}
			contSnow = 0;
			
			int quantity = MainDropActivity.getRandomMax(10, 20);

			for(int i = 0 ; i < quantity ; i ++){
				
				SpriteSnow snow = (SpriteSnow) PoolObjectSingleton.getInstance().getSnow();
				snow.reset(); 
				float x = MainDropActivity.getRandomMax(0, (int) (MainDropActivity.CAMERA_WIDTH - snow.getWidth()/2));
				float y = MainDropActivity.getRandomMax(20,100);
				y=y*-1;
				
				snow.setZIndex(ZIndexGame.BACKGROUND2);
				snow.setPosition(x,y);
				level.addSpriteToUpdate(snow);
				level.getScene().attachChild(snow);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
