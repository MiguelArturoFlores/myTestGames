package com.mgl.drop.game.hud.zeolandia;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.text.Text;

import com.mgl.base.TextFactory;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.game.HUDManagerSingleton;
import com.mgl.drop.game.constant.State;
import com.mgl.drop.game.hud.MyHud;
import com.mgl.drop.game.model.Item;
import com.mgl.drop.game.sprites.SpriteBackground;
import com.mgl.drop.game.sprites.azeoland.SpriteAnimator;
import com.mgl.drop.game.sprites.azeoland.SpriteNPC;

public class PickUpHUD extends MyHud{
	
	private Item item;
	private float time = 0;
	private float timeToDessapear = 1.5f;
	
	public PickUpHUD(Item item){
		try {
			this.item = item;
			
			SpriteBackground background = new SpriteBackground(0, 0, texture.getTextureByName("black.jpg"), texture.getVertexBufferObjectManager());
			background.setSize(MainDropActivity.CAMERA_WIDTH, MainDropActivity.CAMERA_HEIGHT);
			background.setAlpha(0.5f);
			this.attachChild(background);
			
			
			SpriteBackground background2 = new SpriteBackground(0, 0, texture.getTextureByName("textBox.png"), texture.getVertexBufferObjectManager());
			background2.setPosition(0,MainDropActivity.CAMERA_HEIGHT-background2.getHeight());
			this.attachChild(background2);
			
			
			Text name = TextFactory.createText(item.getName(), texture.getmFont1());
			name.setPosition(background2.getX()+background2.getWidth()/2 - name.getWidth()/2, background2.getY()+background2.getHeight()/2 - name.getHeight()/2);
			this.attachChild(name);
			
			SpriteAnimator animator = new SpriteAnimator(0, 0, texture.getTextureAnimateByName("surprise.png"), texture.getVertexBufferObjectManager(), null, new long[] {100,100});
			animator.changeAnimateState(State.NORMAL, true);
			animator.anime(true);
			animator.setPosition(MainDropActivity.CAMERA_WIDTH/2 - animator.getWidth()/2, MainDropActivity.CAMERA_HEIGHT/2 - animator.getHeight()/2);
			this.attachChild(animator);
			
			String textureName = item.getTextureName().replace(".png", "");
			textureName = textureName+"B.png";
			SpriteBackground itemB = new SpriteBackground(0, 0, texture.getTextureByName(textureName), texture.getVertexBufferObjectManager());
			itemB.setPosition(MainDropActivity.CAMERA_WIDTH/2 - itemB.getWidth()/2, MainDropActivity.CAMERA_HEIGHT/2 - itemB.getHeight()/2);
			this.attachChild(itemB);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setAutoUpdate() {
		try {
			
			this.registerUpdateHandler(new IUpdateHandler() {
				@Override
				public void reset() {
				}

				@Override
				public void onUpdate(float pSecondsElapsed) {
					// HERE IS THE GAME LOOP
					time += pSecondsElapsed;
					// System.out.println("this is the time elapsed MAIN SCENE: "+time);
					if(time >timeToDessapear){
						HUDManagerSingleton.getInstance().removeAndReplaceHud();
						//unregisterUpdateHandler(this);
					}
					
				}

				
			});
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void onCloseAction() {
		// TODO Auto-generated method stub
		
	}

	
	
}
