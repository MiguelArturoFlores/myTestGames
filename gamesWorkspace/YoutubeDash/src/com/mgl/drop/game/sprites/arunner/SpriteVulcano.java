package com.mgl.drop.game.sprites.arunner;

import java.util.ArrayList;

import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MyFactory;
import com.mgl.base.MySprite;
import com.mgl.base.SpriteType;
import com.mgl.base.SpriteTypeConstant;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.factory.MyXmlParser;
import com.mgl.drop.factory.SoundSingleton;
import com.mgl.drop.game.constant.CollitionType;
import com.mgl.drop.game.constant.ZIndexGame;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.util.Point;

public class SpriteVulcano extends MySprite {

	private static final float MIN_DISTANCE_TO_UPDATE = 1000;
	private float contTime = 0;
	private float contGenerating = 0;
	private float timeBurble = 0.4f;
	private float timeToActivate = 0.4f;
	private float timeActive = 3f;
	private boolean isActive = false;
	private Long direction = 0L;
	
	private float contSoundBurbles = 0;
	private float timeToSoundBurbles = 0.5f;

	public SpriteVulcano(float pX, float pY,
			ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager);
		contTime = 0;
		isActive = false;
		contGenerating = 0;
		direction = 0L;
	}

	@Override
	public SpriteType getSpriteType() {
		// TODO Auto-generated method stub
		return SpriteType.VULCANO;
	}

	@Override
	public void update(float dTime, LevelController level) {
		try {
			
			if(!mustUpdate){
				return;
			}
			
			if (isActive) {
				updateActive(dTime, level);
			} else {
				contSoundBurbles = timeToSoundBurbles;
				updateInactive(dTime, level);
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	private void updateInactive(float dTime, LevelController level) {
		try {
			contTime = contTime + dTime;

			if (contTime > timeActive) {
				contTime = 0;
				isActive = true;
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void updateActive(float dTime, LevelController level) {

		try {

			SpritePlayer player = level.getPlayer();
			
			contSoundBurbles = contSoundBurbles + dTime;
			if(contSoundBurbles>timeToSoundBurbles){
				contSoundBurbles = 0;
				if(Point.distanceBetween(new Point(player.getX(), player.getY()), new Point(this.getX(), this.getY()))>MIN_DISTANCE_TO_UPDATE){
					
				}else{
				SoundSingleton.getInstance().playBurbleSoft();
				}
				timeToSoundBurbles = (MainDropActivity.getRandomMax(5, 15))/10f;
				
			}
			
			contTime = contTime + dTime;

			if (contTime > timeActive) {
				contTime = 0;
				isActive = false;
				return;
			}

			contGenerating = contGenerating + dTime;
			if (contGenerating > timeBurble) {
				generateBubles(level,dTime);
				contGenerating = 0;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void generateBubles(LevelController level, float dTime) {
		try {
			SpritePlayer player = level.getPlayer();
			
			if(Point.distanceBetween(new Point(player.getX(), player.getY()), new Point(this.getX(), this.getY()))>MIN_DISTANCE_TO_UPDATE){
				return;
			}
			for (int i = 0; i < MainDropActivity.getRandomMax(6, 10); i++) {
				SpriteVulcanoBurble burble = (SpriteVulcanoBurble) MyFactory
						.createObstacle(SpriteTypeConstant.BURBLE_VULCANO,
								level);

				float x = MainDropActivity.getRandomMax(
						(int) (this.getX() + 40),
						(int) (this.getX() + this.getHeight() - 45));
				float y = MainDropActivity
						.getRandomMax((int) (this.getY() + 15),
								(int) (this.getY() + 15 + 25));

				if (!direction.equals(0L)) {
					x = MainDropActivity.getRandomMax((int) (this.getX() + 35),
							(int) (this.getX() + this.getHeight() - 35));
					y = MainDropActivity.getRandomMax((int) (this.getY() +this.getHeight() - 60),
							(int) (this.getY() +this.getHeight()- 60 - 25));
				}
				
				float w = MainDropActivity.getRandomMax(7,17);
				//float h = MainDropActivity.getRandomMax(5,20);
				burble.setSize(w, w);
				
				
				burble.setDistance(MainDropActivity.getRandomMax(120,200));
				burble.setPosition(x, y);
				burble.setDirection(direction);
				burble.setZIndex(ZIndexGame.BURBLE);
				burble.setCollitionType(CollitionType.COLLITION_NONE);
				level.getScene().attachChild(burble);
				level.addSpriteToUpdate(burble);
				
				

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void setXmlParameter(String parameter) {
		try {

			ArrayList<Long> parameterList = MyXmlParser
					.getParameterList(parameter);
			int i = 0;
			for (Long param : parameterList) {
				try {
					if (i == 0) {
						direction = param;
					}

				} catch (Exception e) {

				}
				i++;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
