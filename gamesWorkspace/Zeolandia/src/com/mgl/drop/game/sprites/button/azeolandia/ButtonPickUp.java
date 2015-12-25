package com.mgl.drop.game.sprites.button.azeolandia;

import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MyAnimateProperty;
import com.mgl.base.MyAnimateSprite;
import com.mgl.base.SpriteType;
import com.mgl.base.userinfo.InventarySingleton;
import com.mgl.drop.game.HUDManagerSingleton;
import com.mgl.drop.game.constant.State;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.hud.zeolandia.PickUpHUD;
import com.mgl.drop.game.model.Item;
import com.mgl.drop.game.sprites.azeoland.SpriteChestAdventure;
import com.mgl.drop.game.sprites.azeoland.SpriteItem;

public class ButtonPickUp extends MyAnimateSprite{
	
	private SpriteChestAdventure item;
	
	public ButtonPickUp(float pX, float pY, ITextureRegion pTextureRegion,
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
				
				if(item.getItem() == null){
					return true;
				}
				
				if(InventarySingleton.getInstance().pickUpItem(item.getItem())){
					item.changeAnimateState(State.OPEN, false);
					showItemPickUp();
					this.detachSelf();
					return true;
				}
				
				
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

	private void showItemPickUp() {
		try {
			
			PickUpHUD hud = new PickUpHUD(item.getItem());
			HUDManagerSingleton.getInstance().addHUD(hud, true);
			hud.setAutoUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public SpriteChestAdventure getItem() {
		return item;
	}

	public void setItem(SpriteChestAdventure item) {
		this.item = item;
	}

}