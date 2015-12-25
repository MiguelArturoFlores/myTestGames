package com.mgl.drop.game.entity.arunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.ColorModifier;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.util.color.Color;

import com.mgl.base.MyEntity;
import com.mgl.base.SpriteType;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.game.HUDManagerSingleton;
import com.mgl.drop.game.constant.ZIndexGame;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.scene.SceneManagerSingleton;
import com.mgl.drop.game.sprites.SpriteBackground;
import com.mgl.drop.game.sprites.ayoutuberunner.SpriteColorChange;
import com.mgl.drop.texture.TextureSingleton;

public class EntityMoveBackground extends MyEntity {

	private SpriteBackground background1;
	private SpriteBackground background2;
	private SpriteBackground background3;
	private Queue<SpriteBackground> backgroundList;
	private SpriteBackground last;
	private SpriteBackground first;
	private Float previusXPlayer;

	// private SpriteBackground fade;
	private float distance = 0;

	private LevelController controller;

	private float plusR = 0;
	private float plusG = 0;
	private float plusB = 0;

	private float plusRToMove = 0;
	private float plusGToMove = 0;
	private float plusBToMove = 0;

	private float plusRR = 4;
	private float plusGG = 4;
	private float plusBB = 4;
	
	private float contChangeColor = 0;
	private float timeToChange = 1.5f;

	private boolean changeColor = false;
	private SpriteColorChange spriteColor;

	private ColorModifier colorModifier;
	private Color colorToMove;
	
	private float speedR=0;
	private float speedG=0;
	private float speedB =0;
	
	
	public EntityMoveBackground(LevelController controller) {
		try {

			this.controller = controller;

			TextureSingleton texture = TextureSingleton.getInstance();
			initBackground();
			previusXPlayer = null;
			// fade = new SpriteBackground(0, 0,
			// texture.getTextureByName("fade.png"),
			// texture.getVertexBufferObjectManager());
			// fade.setZIndex(ZIndexGame.FADE);

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	private void initBackground() {
		try {
			TextureSingleton texture = TextureSingleton.getInstance();
			Camera cam = SceneManagerSingleton.getInstance().getCamera();

			background1 = new SpriteBackground(0, 0,
					texture.getTextureByName("backgroundTest.png"),
					texture.getVertexBufferObjectManager());
			background2 = new SpriteBackground(0, 0,
					texture.getTextureByName("backgroundTest.png"),
					texture.getVertexBufferObjectManager());
			background3 = new SpriteBackground(0, 0,
					texture.getTextureByName("backgroundTest.png"),
					texture.getVertexBufferObjectManager());

			/*
			 * background1.setPosition(cam.getCenterX()-MainDropActivity.
			 * CAMERA_WIDTH
			 * /2,cam.getCenterY()-MainDropActivity.CAMERA_HEIGHT/2);
			 * background2
			 * .setPosition(background1.getX()+background1.getWidth(),0);
			 * background3
			 * .setPosition(background2.getWidth()+background2.getX(),0);
			 */

			background1.setWidth(800);
			background2.setWidth(800);
			background3.setWidth(800);
			
			/*
			SpriteBackground backgroundFade = new SpriteBackground(0, 0,
					texture.getTextureByName("levelFade.png"),
					texture.getVertexBufferObjectManager());
			backgroundFade.setWidth(800);
			background1.attachChild(backgroundFade);

			SpriteBackground backgroundFade2 = new SpriteBackground(0, 0,
					texture.getTextureByName("levelFade.png"),
					texture.getVertexBufferObjectManager());
			backgroundFade2.setWidth(800);
			background2.attachChild(backgroundFade2);
			
			SpriteBackground backgroundFade3 = new SpriteBackground(0, 0,
					texture.getTextureByName("levelFade.png"),
					texture.getVertexBufferObjectManager());
			backgroundFade3.setWidth(800);
			background3.attachChild(backgroundFade3);
			*/
			
			background1.setPosition(-MainDropActivity.CAMERA_WIDTH / 2,
					cam.getCenterY() - MainDropActivity.CAMERA_HEIGHT / 2);
			background2.setPosition(
					background1.getX() + background1.getWidth(), 0);
			background3.setPosition(
					background2.getWidth() + background2.getX(), 0);

			controller.getScene().attachChild(background1);
			controller.getScene().attachChild(background2);
			controller.getScene().attachChild(background3);

			backgroundList = new LinkedList<SpriteBackground>();
			backgroundList.add(background1);
			backgroundList.add(background2);
			backgroundList.add(background3);

			plusB = background1.getColor().getBlue();
			plusR = background1.getColor().getRed();
			plusG = background1.getColor().getGreen();
			
			plusBToMove = plusB ;
			plusRToMove = plusR;
			plusGToMove = plusG;

			plusRToMove = MainDropActivity.getRandomMax(0, 1000)/1000f;
			plusGToMove = MainDropActivity.getRandomMax(0, 1000)/1000f;
			plusBToMove = MainDropActivity.getRandomMax(0, 1000)/1000f;
			
			distance = 0;

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateChild(float dTime, LevelController level) {
		try {

			Camera cam = SceneManagerSingleton.getInstance().getCamera();
			int i = 0;

			if (changeColor) {
				//updateChangeColor(dTime, level);
				//updateChangeColorNew(dTime, level);
			}

			for (SpriteBackground back : backgroundList) {

				if (i == 0) {
					background1 = back;
					back.setZIndex(ZIndexGame.BACKGROUND1);
				}
				if (i == 1) {
					background2 = back;
					back.setZIndex(ZIndexGame.BACKGROUND2);
					if (previusXPlayer == null) {
						previusXPlayer = level.getPlayer().getX();
					}

					float distanceB = level.getPlayer().getX() - previusXPlayer;
					distanceB = distanceB * 0.2f;
					distance = distance + distanceB;
					previusXPlayer = level.getPlayer().getX();

					if (distance > back.getWidth()) {
						distance = back.getWidth();
					}
					if (distance < back.getWidth() * -1) {
						distance = back.getWidth() * -1;
					}
					back.setX((int) (cam.getCenterX()
							- MainDropActivity.CAMERA_WIDTH / 2 - distance));
				}
				if (i == 2) {
					background3 = back;
					back.setZIndex(ZIndexGame.BACKGROUND3);
				}
				i++;

			}

			background1.setWidth(MainDropActivity.CAMERA_WIDTH);
			background2.setWidth(MainDropActivity.CAMERA_WIDTH);
			background3.setWidth(MainDropActivity.CAMERA_WIDTH);

			background2.setX((int) background2.getX());

			background1
					.setX((int) (background2.getX() - background2.getWidth()));
			background3
					.setX((int) (background2.getX() + background2.getWidth()));

			if (distance == background2.getWidth()) {

				backgroundList = new LinkedList<SpriteBackground>();
				backgroundList.add(background2);
				backgroundList.add(background3);
				backgroundList.add(background1);
				distance = 0;

			}

			if (distance == background2.getWidth() * -1) {

				backgroundList = new LinkedList<SpriteBackground>();
				backgroundList.add(background3);
				backgroundList.add(background1);
				backgroundList.add(background2);
				distance = 0;

			}

			updateOnY(dTime);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	

	private void updateChangeColor(float dTime, LevelController level) {
		try {
			contChangeColor = contChangeColor + dTime;

			if (contChangeColor < timeToChange) {
				
			}else{
				plusRToMove = MainDropActivity.getRandomMax(0, 1000)/1000f;
				plusGToMove = MainDropActivity.getRandomMax(0, 1000)/1000f;
				plusBToMove = MainDropActivity.getRandomMax(0, 1000)/1000f;
				contChangeColor = 0;
			}

			

			for (SpriteBackground back : backgroundList) {

				if( plusR > plusRToMove){
					plusRR = -5;
				}else {
					plusRR = 5;
				}
				
				if( plusG > plusGToMove){
					plusGG = -5;
				}else {
					plusGG = 5;
				}
				
				if( plusB > plusBToMove){
					plusBB = -5;
				}else {
					plusBB = 5;
				}
				
				plusR = plusR + plusRR / 1000f;
				plusG = plusG + plusGG / 1000f;
				plusB = plusB + plusBB / 1000f;
				
				back.setColor(plusR, plusG, plusB);
				
				
				
				
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private float getNewColorToMove(float colorF) {

		try {
			float retVal = 1;

			if (colorF >= 1) {
				retVal = colorF - 0.5f;
			}else if(colorF <=0.5f){
				retVal = MainDropActivity.getRandomMax(1, 1000)/1000f;
			}else{
				retVal = MainDropActivity.getRandomMax(1, 1000)/1000f;
			}

			
			
			if (retVal > 1) {
				retVal = 1;
			}
			if (retVal < 0) {
				retVal = 0;
			}
			return retVal;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 1;
	}

	private void updateOnY(float dTime) {
		try {

			Camera cam = SceneManagerSingleton.getInstance().getCamera();

			for (SpriteBackground b : backgroundList) {
				b.setY(cam.getCenterY() - MainDropActivity.CAMERA_HEIGHT / 2);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	
	private float distanceBetween(float plus1, float plus2) {
		try {
			
			return (plus2 - plus1);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 1;
	}

	public void updateChangeColorNew(float plusR,float plusG,float plusB) {
		try {
			
			for (SpriteBackground back : backgroundList) {
				
				back.setColor(plusR, plusG, plusB);
				
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
