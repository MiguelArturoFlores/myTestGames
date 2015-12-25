package com.mgl.drop.game.hud.zeolandia;

import org.andengine.entity.text.Text;

import com.mgl.base.ButtonListener;
import com.mgl.base.MyFactory;
import com.mgl.base.TextFactory;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.game.HUDManagerSingleton;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.hud.MyHud;
import com.mgl.drop.game.hud.sprites.SpriteRemoveHud;
import com.mgl.drop.game.sprites.SpriteBackground;
import com.mgl.drop.game.sprites.button.ButtonGeneral;

public class MenuHUD extends MyHud{

	private LevelController controller;
	
	private ButtonGeneral buttonEquip;
	private ButtonGeneral buttonItems;
	private ButtonGeneral buttonCards;
	private ButtonGeneral buttonExit;
	
	public MenuHUD(LevelController levelController){
		try {
	
			controller = levelController;
			
			Text menu = TextFactory.getMenuText(texture.getmFont());
			menu.setPosition(10,10);
			
			SpriteBackground background = new SpriteBackground(0, 0, texture.getTextureByName("backgroundMenu.png"), texture.getVertexBufferObjectManager());
			background.setSize(MainDropActivity.CAMERA_WIDTH, MainDropActivity.CAMERA_HEIGHT);
			//background.setAlpha(0.6f);
			this.attachChild(background);
			
			buttonEquip = new ButtonGeneral(0, 50, texture.getTextureByName("buttonBar.png"), texture.getVertexBufferObjectManager(), null);
			buttonItems = new ButtonGeneral(0, 95, texture.getTextureByName("buttonBar.png"), texture.getVertexBufferObjectManager(), null);
			buttonCards = new ButtonGeneral(0, 140, texture.getTextureByName("buttonBar.png"), texture.getVertexBufferObjectManager(), null);
			buttonExit = new ButtonGeneral(0, 185, texture.getTextureByName("buttonBar.png"), texture.getVertexBufferObjectManager(), null);
			
			buttonEquip.addText(TextFactory.getEquipText(texture.getmFont()));
			buttonItems.addText(TextFactory.getItemText(texture.getmFont()));
			buttonCards.addText(TextFactory.getCardsText(texture.getmFont()));
			buttonExit.addText(TextFactory.getExitText(texture.getmFont()));
			
			this.attachChild(buttonCards);
			this.attachChild(buttonEquip);
			this.attachChild(buttonExit);
			this.attachChild(buttonItems);
			
			this.attachChild(menu);
			
			initButtonListener();
			
			SpriteRemoveHud remove = new SpriteRemoveHud(0, 0, texture.getTextureByName("closeHud.png"), texture.getVertexBufferObjectManager(), this);
			remove.setPosition(MainDropActivity.CAMERA_WIDTH - remove.getWidth()-5,5);
			this.attachChild(remove);
			this.registerTouchArea(remove);
			
			this.registerTouchArea(buttonEquip);
			this.registerTouchArea(buttonCards);
			this.registerTouchArea(buttonExit);
			this.registerTouchArea(buttonItems);
			
			this.registerTouchArea(background);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void initButtonListener() {
		try {
			
			buttonEquip.setButtonListener(new ButtonListener() {
				
				@Override
				public void onActionUp(float x, float y) {
					EquipHUD equipHud = new EquipHUD();
					HUDManagerSingleton.getInstance().addHUD(equipHud, true);
				}
				
				@Override
				public void onActionMove(float x, float y) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onActionDown(float x, float y) {
					// TODO Auto-generated method stub
					
				}
			});
			
			buttonItems.setButtonListener(new ButtonListener() {
				
				@Override
				public void onActionUp(float x, float y) {

					ItemHUD itemHud = new ItemHUD(null);
					HUDManagerSingleton.getInstance().addHUD(itemHud, true);
				}
				
				@Override
				public void onActionMove(float x, float y) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onActionDown(float x, float y) {
					// TODO Auto-generated method stub
					
				}
			});
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onCloseAction() {
		try {
			
			controller.setMustUpdate(true);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	
	
}
