package com.mgl.drop.game.sprites;

import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MyAnimateSprite;
import com.mgl.base.MySprite;
import com.mgl.base.SpriteType;
import com.mgl.drop.game.constant.ZIndexGame;
import com.mgl.drop.game.controller.LevelController;

public class SpriteLife extends MySprite {
	
	private float totalLife = 10;
	private float currentLife = 10;

	private MyAnimateSprite spriteToRemove;

	private SpriteBackground redLife;
	
	public SpriteLife(float pX, float pY, ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager, MyAnimateSprite spriteToRemove, LevelController level) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager);
		try {

			this.level = level;
			this.spriteToRemove = spriteToRemove;
			redLife = new SpriteBackground(2, 0, texture.getTextureByName("lifeRed.png"), texture.getVertexBufferObjectManager());
			this.attachChild(redLife);

			redLife.setWidth(50);
			this.setWidth(50);
			this.setZIndex(ZIndexGame.FADE);
			this.setIgnoreUpdate(true);
			
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
	public void update(float dTime, LevelController level) {
		try {

			
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void recieveHit(float damage){
		try {
			
			currentLife = currentLife-damage;
			
			float cLifePercentage  = currentLife *100 / totalLife;

			float cHeight = cLifePercentage * this.getHeight()/100;
			redLife.setY(redLife.getY()+redLife.getHeight()-cHeight);
			redLife.setHeight(cHeight);
			//redLife.setPosition(redLife.getX(), this.getY()+this.getHeight()-redLife.getHeight());
			
			if(currentLife<=0){
				SpriteMonsterBoss boss = (SpriteMonsterBoss) spriteToRemove;
				boss.killBoss();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public float getTotalLife() {
		return totalLife;
	}

	public void setTotalLife(float totalLife) {
		this.totalLife = totalLife;
	}

	public float getCurrentLife() {
		return currentLife;
	}

	public void setCurrentLife(float currentLife) {
		this.currentLife = currentLife;
	}

	public MyAnimateSprite getSpriteToRemove() {
		return spriteToRemove;
	}

	public void setSpriteToRemove(MyAnimateSprite spriteToRemove) {
		this.spriteToRemove = spriteToRemove;
	}

	public SpriteBackground getRedLife() {
		return redLife;
	}

	public void setRedLife(SpriteBackground redLife) {
		this.redLife = redLife;
	}

	
	
}
