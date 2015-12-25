package com.mgl.drop.game.sprites.button.azeolandia;

import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MyAnimateProperty;
import com.mgl.base.MyAnimateSprite;
import com.mgl.base.SpriteType;
import com.mgl.drop.game.HUDManagerSingleton;
import com.mgl.drop.game.constant.State;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.sprites.azeoland.SpriteNPC;
import com.mgl.drop.game.sprites.azeoland.SpritePlayerBattle;

public class ButtonDialog extends MyAnimateSprite{
	
	private SpriteNPC npc;

	public ButtonDialog(float pX, float pY, ITextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager,
			LevelController level) {
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager, level);
		// TODO Auto-generated constructor stub
	}

	@Override
	public SpriteType getSpriteType() {
		// TODO Auto-generated method stub
		return SpriteType.DECORATIVE;
	}

	@Override
	public void initAnimationParams() {
		
		try {

			changeAnimateState(State.NORMAL, false);
			anime(false);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initHashMap() {
		try {

			fps = new long[] { 83, 83, 83 ,83 ,83};

			
			stateAnimate.put(State.NORMAL, new MyAnimateProperty(0, 5,
					fps));
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void updateAnimated(float dTime, LevelController level) {
		// TODO Auto-generated method stub
		
	}
	
	

	public void showAnimate(){
		try {
			
			changeAnimateState(State.NORMAL, false);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	@Override
	public boolean onAreaTouched(final TouchEvent pSceneTouchEvent,
			final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
		try {

			switch (pSceneTouchEvent.getAction()) {
			case TouchEvent.ACTION_DOWN:
				
				npc.showDialog();
				
				break;
			case TouchEvent.ACTION_MOVE:

				break;
			case TouchEvent.ACTION_UP:

				
				
				break;
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	public SpriteNPC getNpc() {
		return npc;
	}

	public void setNpc(SpriteNPC npc) {
		this.npc = npc;
	}

	
	
}
