package com.mgl.drop.game.sprites.interfaz;

import org.andengine.entity.scene.Scene;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MySprite;
import com.mgl.base.SpriteType;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.scene.SceneManagerSingleton;
import com.mgl.drop.game.sprites.SpritePowerFire;
import com.mgl.drop.game.sprites.SpritePowerFood;
import com.mgl.drop.game.sprites.SpritePowerTrunk;
import com.mgl.drop.game.sprites.SpritePowerWall;
import com.mgl.drop.texture.TextureSingleton;

public class SpritePowerBar extends MySprite {

	public SpritePowerBar(float pX, float pY,
			ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager, Scene scene,
			LevelController level, boolean isVertical, boolean touchable) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager);
		try {
			
			int wOffset = 20;
			int hOffset = 50;
			
			
			
			this.level = level;
			this.setAlpha(0.9f);
			TextureSingleton texture = TextureSingleton.getInstance();

			int vertical = 0;
			int horizontal = 0;
			if (isVertical) {
				vertical = 1;
				hOffset = 50;
			} else {
				horizontal = 1;
				wOffset=20;
				hOffset=30;
			}
			SpritePowerFood powerFood = new SpritePowerFood(10, 10,
					texture.getTextureByName("deadPower.png"),
					texture.getVertexBufferObjectManager(), level);
			//powerFood.setSize(60, 60);
			this.attachChild(powerFood);
			powerFood.setPosition( wOffset+ (horizontal * 1), hOffset + (vertical * 1));

			SpritePowerWall powerWall = new SpritePowerWall(10, 80,
					texture.getTextureByName("wallPower.png"),
					texture.getVertexBufferObjectManager(), level);
			//powerWall.setSize(60, 60);
			this.attachChild(powerWall);
			//powerWall.setPosition(wOffset + (horizontal * 105), hOffset + (vertical * 80));
			powerWall.setPosition(powerFood.getX()+powerFood.getWidth()+5, hOffset + (vertical * 80));

			SpritePowerFire powerFire = new SpritePowerFire(10, 80,
					texture.getTextureByName("firePower.png"),
					texture.getVertexBufferObjectManager(), level);
			//powerFire.setSize(60, 60);
			this.attachChild(powerFire);
			//powerFire.setPosition(wOffset + (horizontal * 150),
			//		hOffset + (vertical * 150));
			powerFire.setPosition(powerWall.getX()+powerWall.getWidth()+5,hOffset + (vertical * 150));

			SpritePowerTrunk powerTrunk = new SpritePowerTrunk(10, 80,
					texture.getTextureByName("trunkPower.png"),
					texture.getVertexBufferObjectManager(), level);
			//powerTrunk.setSize(60, 60);
			this.attachChild(powerTrunk);
//			powerTrunk.setPosition(wOffset + (horizontal * 230),
//					hOffset + (vertical * 230));
			powerTrunk.setPosition(powerFire.getX()+powerFire.getWidth()+5,hOffset + (vertical * 150));
			
			if (touchable) {
				scene.registerTouchArea(powerFood);
				scene.registerTouchArea(powerWall);
				scene.registerTouchArea(powerFire);
				scene.registerTouchArea(powerTrunk);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public SpriteType getSpriteType() {
		return SpriteType.DECORATIVE;
	}

	@Override
	public void update(float dTime, LevelController level) {
		// TODO Auto-generated method stub

	}

}
