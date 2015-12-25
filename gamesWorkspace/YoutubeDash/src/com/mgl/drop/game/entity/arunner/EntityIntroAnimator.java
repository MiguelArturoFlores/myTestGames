package com.mgl.drop.game.entity.arunner;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.entity.scene.Scene;

import com.mgl.base.MyEntity;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.sprites.arunner.SpriteIntroAnimate;
import com.mgl.drop.texture.TextureSingleton;

public class EntityIntroAnimator extends MyEntity {

	private TextureSingleton texture = TextureSingleton.getInstance();

	private Scene scene;
	private float contTime = 0;
	private float speed = 4000;

	private boolean playerHasAppear = false;
	private boolean enemy1HasAppear = false;
	private boolean enemy2HasAppear = false;
	private boolean enemy3HasAppear = false;
	private boolean enemy4HasAppear = false;

	
	private SpriteIntroAnimate background1;
	private SpriteIntroAnimate background2;
	
	private SpriteIntroAnimate player;
	
	private SpriteIntroAnimate enemy1;
	private SpriteIntroAnimate enemy2;
	private SpriteIntroAnimate enemy3;
	
	
	private AnimateCapsule animatePlayerCapsule;
	private AnimateCapsule animateEnemy1;
	private AnimateCapsule animateEnemy2;
	private AnimateCapsule animateEnemy3;
	
	public EntityIntroAnimator(Scene scene) {
		try {

			this.scene = scene;

			background1 = new SpriteIntroAnimate(0, 0,
					texture.getTextureByName("introBackground1.png"),
					texture.getVertexBufferObjectManager());
			scene.attachChild(background1);
			
			background2 = new SpriteIntroAnimate(0, 0,
					texture.getTextureByName("introBackground2.png"),
					texture.getVertexBufferObjectManager());
			
			background2.setPosition(0,background1.getHeight());
			scene.attachChild(background2);
			
			scene.registerTouchArea(background1);
			scene.registerTouchArea(background2);
			
			
			initEnemies();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initEnemies() {
		try {
			
			enemy1 = new SpriteIntroAnimate(0, 0, texture.getTextureByName("intro2.png"), texture.getVertexBufferObjectManager());
			enemy1.setPosition(600,600);
			scene.attachChild(enemy1);
			
			enemy2 = new SpriteIntroAnimate(0, 0, texture.getTextureByName("intro3.png"), texture.getVertexBufferObjectManager());
			enemy2.setPosition(350,-200 - enemy2.getHeight());
			scene.attachChild(enemy2);
			
			enemy3 = new SpriteIntroAnimate(0, 0, texture.getTextureByName("intro4.png"), texture.getVertexBufferObjectManager());
			enemy3.setPosition(1200,-50);
			scene.attachChild(enemy3);
			
			enemy3.setRotationCenter(enemy3.getWidth()/2, enemy3.getHeight()/2);
			enemy3.setRotation(-20);
			
			enemy1.setZIndex(8);
			enemy2.setZIndex(7);
			enemy3.setZIndex(10);
			
			animatePlayerCapsule = new AnimateCapsule();
			animateEnemy1 = new AnimateCapsule();
			animateEnemy2 = new AnimateCapsule();
			animateEnemy3 = new AnimateCapsule();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setMustUpdate(boolean mustUpdate) {

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
	public void update(float dTime, LevelController level) {
		try {
			
			updateBackground(dTime);
			
			contTime = contTime + dTime;

			if (contTime > 0 && !playerHasAppear) {
				appearPlayer();
				playerHasAppear = true;
			}

			if (contTime > 1.5f && !enemy1HasAppear) {
				appearEnemy1();
				enemy1HasAppear = true;
			}

			if (contTime > 2 && !enemy2HasAppear) {
				appearEnemy2();
				enemy2HasAppear= true;
			}

			if (contTime > 2.5f && !enemy3HasAppear) {
				appearEnemy3();
				enemy3HasAppear = true;
			}

			if(contTime>2){
				updatePlayerFloating(dTime, player,animatePlayerCapsule);
			}
			
			if(contTime>4){
				//updatePlayerFloating(dTime, enemy1,animateEnemy1);
				//updatePlayerFloating(dTime, enemy2,animateEnemy2);
			}
			
			if(contTime>5){
				updatePlayerFloating(dTime, enemy3,animateEnemy3);
			}
			
			scene.sortChildren();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void updatePlayerFloating(float dTime, SpriteIntroAnimate player , AnimateCapsule animatePlayerCapsule) {
		try {
			
			animatePlayerCapsule.contTimeFloating += dTime;
			if (animatePlayerCapsule.contTimeFloating > 0.05f) {
				animatePlayerCapsule.cont++;
				if (animatePlayerCapsule.cont > 10) {
					animatePlayerCapsule.cont = 0;
					animatePlayerCapsule.isUp = !animatePlayerCapsule.isUp;
				}
				if (animatePlayerCapsule.isUp) {
					player.setY(player.getY() + 1);
				} else {
					player.setY(player.getY() - 1);
				}
				animatePlayerCapsule.contTimeFloating = 0f;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void updateBackground(float dTime) {
		try {
			
			
			float distance = speed * dTime;
			
			background1.setY(background1.getY()-distance);
			background2.setY(background2.getY()-distance);

			if((background1.getY()+background1.getHeight())<0){
				background1.setY(background2.getY()+background2.getHeight());
			}
			
			if((background2.getY()+background2.getHeight())<0){
				background2.setY(background1.getY()+background1.getHeight());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void appearEnemy1() {
		try {
			
			enemy1.registerEntityModifier(new MoveModifier(3, enemy1.getX(), enemy1.getX(), enemy1.getY(), 20));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void appearEnemy2() {
		try {
			
			
			enemy2.registerEntityModifier(new MoveModifier(2, enemy2.getX(), enemy2.getX(), enemy2.getY(), -10));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void appearEnemy3() {
		try {
			
			
			enemy3.registerEntityModifier(new MoveModifier(3, enemy3.getX(), 550, enemy3.getY(), enemy3.getY()));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void appearPlayer() {
		try {
			
			player = new SpriteIntroAnimate(0, 0, texture.getTextureByName("intro1.png"), texture.getVertexBufferObjectManager());
			player.setPosition(50,480);
			scene.attachChild(player);
			
			player.registerEntityModifier(new MoveModifier(1.5f, player.getX(), player.getX(), player.getY(), 100));
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	
	
	@Override
	public void updateChild(float dTime, LevelController level) {
		// TODO Auto-generated method stub
		
	}
	
	private class AnimateCapsule {
		
		private float contTimeFloating;
		private float cont;
		private boolean isUp;
		
		public AnimateCapsule(){
			
			cont = 0;
			contTimeFloating = 0;
			isUp = true;
			
		}
		
		
	}
}
