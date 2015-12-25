package com.mgl.drop.game.entity.aninja;

import org.andengine.entity.IEntity;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.entity.scene.Scene;

import com.mgl.base.MyEntity;
import com.mgl.drop.game.constant.ZIndexGame;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.sprites.SpriteBackground;
import com.mgl.drop.texture.TextureSingleton;

public class EntityNinjaFade extends MyEntity{

	private SpriteBackground fade1;
	private SpriteBackground fade2;
	private SpriteBackground fade3;
	private SpriteBackground fade4;
	private SpriteBackground fadeMid;
	
	private boolean isAttached = false;
	
	public EntityNinjaFade(Scene scene){
		try {
			
			isAttached = false;
			
			TextureSingleton texture = TextureSingleton.getInstance();
			
			fade1 = new SpriteBackground(0, 0, texture.getTextureByName("blackFade.png"), texture.getVertexBufferObjectManager());
			fade1.setSize(600, 1000);
			
			fade2 = new SpriteBackground(0, 0, texture.getTextureByName("blackFade.png"), texture.getVertexBufferObjectManager());
			fade2.setSize(480, 480);
			
			fade3 = new SpriteBackground(0, 0, texture.getTextureByName("blackFade.png"), texture.getVertexBufferObjectManager());
			fade3.setSize(600, 1000);
			
			fade4 = new SpriteBackground(0, 0, texture.getTextureByName("blackFade.png"), texture.getVertexBufferObjectManager());
			fade4.setSize(480, 480);
			
			fadeMid = new SpriteBackground(0, 0, texture.getTextureByName("ninjaFade.png"), texture.getVertexBufferObjectManager());
			
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
			
			if(!isAttached){
				attachElements(level.getScene());
				isAttached = true;
			}
			
			fadeMid.setPosition(level.getPlayer().getX()+level.getPlayer().getWidth()/2 - fadeMid.getWidth()/2,level.getPlayer().getY()+level.getPlayer().getHeight()/2 - fadeMid.getHeight()/2);
			
			fade1.setPosition(fadeMid.getX()-fade1.getWidth(),fadeMid.getY()+fadeMid.getHeight()/2 -fade1.getHeight()/2);
			fade3.setPosition(fadeMid.getX()+fadeMid.getWidth(),fadeMid.getY()+fadeMid.getHeight()/2 -fade1.getHeight()/2);
			
			fade2.setPosition(fadeMid.getX(),fadeMid.getY()-fade2.getHeight());
			fade4.setPosition(fadeMid.getX(),fadeMid.getY()+fadeMid.getHeight());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void attachElements(Scene scene){
		try {
			
			scene.attachChild(fade1);
			scene.attachChild(fade2);
			scene.attachChild(fade3);
			scene.attachChild(fade4);
			scene.attachChild(fadeMid);
			
			fade1.setZIndex(ZIndexGame.FADE);
			fade2.setZIndex(ZIndexGame.FADE);
			fade3.setZIndex(ZIndexGame.FADE);
			fade4.setZIndex(ZIndexGame.FADE);
			fadeMid.setZIndex(ZIndexGame.FADE);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
