package com.mgl.drop.game.hud;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.IEntity;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MySprite;
import com.mgl.base.SpriteType;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.factory.SoundSingleton;
import com.mgl.drop.game.HUDManagerSingleton;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.hud.sprites.SpriteRemoveHud;
import com.mgl.drop.game.scene.SceneManagerSingleton;
import com.mgl.drop.texture.TextureSingleton;

public class SpriteImgHUD extends MySprite{

	private HUD hud;
	private boolean mustRemove;
	
	public SpriteImgHUD(float pX, float pY,
			ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager, HUD hud,boolean mustRemove,String backgorund) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager );
		this.hud = hud;
		this.mustRemove= mustRemove;
		

		try {
			if(backgorund==null || backgorund.isEmpty()){
				return;
			}
			
			TextureSingleton texture = TextureSingleton.getInstance();
			Camera camera = SceneManagerSingleton.getInstance().getCamera();
			
			Sprite background = new Sprite(0,0,
					texture.getTextureByName(backgorund),
					texture.getVertexBufferObjectManager());
			background.setSize(camera.getWidth(), camera.getHeight());
			background.setZIndex(-1);
			this.attachChild(background);
			
			this.sortChildren();
			
		} catch (Exception e) {

		e.printStackTrace();
		}
		
	}

	@Override
	public SpriteType getSpriteType() {

		return null;
	}

	@Override
	public void update(float dTime, LevelController level) {

		
	}

	public boolean onAreaTouched(final TouchEvent pSceneTouchEvent,
			final float pTouchAreaLocalX, final float pTouchAreaLocalY) {

		switch (pSceneTouchEvent.getAction()) {
		case TouchEvent.ACTION_DOWN:

			
			SoundSingleton.getInstance().playButtonSound();
			
			hud.detachChild(this);
			hud.unregisterTouchArea(this);
			
			if(mustRemove){
				HUDManagerSingleton.getInstance().removeAndReplaceHud();
			}
			
			break;
		}
		return true;
	}

	public HUD getHud() {
		return hud;
	}

	public void setHud(HUD hud) {
		this.hud = hud;
	}

	public boolean isMustRemove() {
		return mustRemove;
	}

	public void setMustRemove(boolean mustRemove) {
		this.mustRemove = mustRemove;
	}

	
	
}
