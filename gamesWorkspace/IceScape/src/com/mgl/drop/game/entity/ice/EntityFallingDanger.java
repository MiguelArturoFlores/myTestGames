package com.mgl.drop.game.entity.ice;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.andengine.entity.IEntity;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.extension.physics.box2d.PhysicsFactory;

import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.mgl.base.MyEntity;
import com.mgl.base.userinfo.PhysicSingleton;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.factory.SoundSingleton;
import com.mgl.drop.game.constant.CollitionType;
import com.mgl.drop.game.constant.ZIndexGame;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.sprites.aicerunner.SpriteCoin;
import com.mgl.drop.game.sprites.aicerunner.SpriteObjectFalling;
import com.mgl.drop.texture.TextureSingleton;

public class EntityFallingDanger extends MyEntity{

	private float contTime = 0;
	private float timeToGenerate= 1.5f;
	
	private ArrayList<Long> positionList; 
	
	private float generalTime = 0;
	
	public EntityFallingDanger(){
		
		try {
			
			contTime = 0;
			timeToGenerate= 2f;
			generalTime = 0;
			
			positionList = new ArrayList<Long>();
			positionList.add(0L);
			positionList.add(80L);
			positionList.add(160L);
			positionList.add(240L);
			positionList.add(320L);
			positionList.add(400L);
			
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
			if(!mustUpdate){
				return;
			}
			
			generalTime+=dTime;
			contTime += dTime;
			if(contTime<timeToGenerate){
				return;
			}
			contTime = 0;
			
			timeToGenerate = 1.5f + 1f/(float) MainDropActivity.getRandomMax(5, 10);
			
			generateObstacle(level);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void generateObstacle(LevelController level) {
		try {
			
			Collections.shuffle(positionList);
			
			Long quantity = 1l;
			boolean createCoin = false;
			
			if(generalTime<5){
				
				quantity = 1l;
				
			}else if(generalTime < 12){
				
				if(generalTime < 15){
					createCoin = true;
				}
				quantity = 2l;
				
			}else if(generalTime < 24){
			
				quantity = 3l;
				if(generalTime < 26){
					createCoin = true;
				}
			}else if(generalTime < 40){
			
				quantity = 2l;
				if(generalTime < 42){
					createCoin = true;
				}
				
			}	else if(generalTime < 45){
				
				quantity = 3l;
		
			}else if(generalTime < 55){
				if(generalTime < 60){
					createCoin = true;
				}
				quantity = 2l;
				
			}
			
			float generalTime = 0;
			
			if(this.generalTime>60){
				generalTime = this.generalTime%60;
				
				if(generalTime<10){
					if(generalTime < 6){
						createCoin = true;
					}
					quantity = 3l;
				}
				else if(generalTime<20){
					if(generalTime < 15){
						createCoin = true;
					}
					quantity = 2l;
				}
				else if(generalTime<25){
					quantity = 2l;
				}
				else if(generalTime<40){
					if(generalTime < 35){
						createCoin = true;
					}
					quantity = 3l;
				}
				else if(generalTime<44){
					
					quantity = 2l;
				}
				else if(generalTime<50){
					
					quantity = 3l;
				}
				else{
					if(generalTime < 55){
						createCoin = true;
					}
					quantity = 3l;
				}
					
			}
			
			
			generateObstacle(level,quantity.intValue(), createCoin);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void generateObstacle(LevelController level, int quantity, boolean createCoin) {
		try {
			
			int cont = 1;
			for(Long position : positionList){
				if(cont> quantity){
					if(createCoin){
						
						SoundSingleton.getInstance().playFishAppear();
						
						SpriteCoin falling   = new SpriteCoin(0,0, TextureSingleton.getInstance().getTextureAnimateByName("fish.png"), TextureSingleton.getInstance().getVertexBufferObjectManager(),level);
						falling.setSize(80, 80);
						falling.setPosition(position, -120);
						falling.setCollitionType(CollitionType.COLLITION_CIRCULAR);
						falling.setZIndex(ZIndexGame.PLAYER);
						
						FixtureDef WALL_FIX = PhysicsFactory.createFixtureDef(0f, 0.0f, 0f);
						
						PhysicSingleton.getInstance().loadSpriteInWorld(falling, WALL_FIX, BodyType.DynamicBody, false);
						
						level.getScene().attachChild(falling);
						level.addSpriteToUpdate(falling);
				
						falling.createLight(level);
						
						
					}
					return;
				}
				
				cont++;
				
				String textureName = "ice1.png";
				int val = MainDropActivity.getRandomMax(0, 100);
				
				if(val>50){
					textureName = "ice2.png";
				}
				
				SpriteObjectFalling falling   = new SpriteObjectFalling(0,0, TextureSingleton.getInstance().getTextureByName(textureName), TextureSingleton.getInstance().getVertexBufferObjectManager());
				falling.setSize(80, 80);
				falling.setPosition(position, -120);
				falling.setCollitionType(CollitionType.COLLITION_RECTANGLE);
				falling.setZIndex(ZIndexGame.PLAYER);
				
				FixtureDef WALL_FIX = PhysicsFactory.createFixtureDef(2f, 0.0f, 0f);
				
				PhysicSingleton.getInstance().loadSpriteInWorld(falling, WALL_FIX, BodyType.DynamicBody, false);
				
				level.getScene().attachChild(falling);
				level.addSpriteToUpdate(falling);
				
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	
}
