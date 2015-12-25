package com.mgl.drop.game.sprites.button;

import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.ButtonListener;
import com.mgl.base.MySprite;
import com.mgl.base.SpriteType;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.sprites.SpriteBackground;

public class ButtonGeneral extends MySprite {

	protected ButtonListener buttonListener;
	
	protected Text text;
	protected SpriteBackground press = null;

	public ButtonGeneral(float pX, float pY,
			ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager,
			ButtonListener buttonListener) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager);
		this.buttonListener = buttonListener;
	}

	
	public void addText(Text text){
		try {
			this.text = text;
			text.setPosition(this.getWidth()/2 - text.getWidth()/2,this.getHeight()/2 - text.getHeight()/2);
			this.attachChild(text);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public SpriteType getSpriteType() {
		// TODO Auto-generated method stub
		return SpriteType.BUTTON_GENERAL;
	}

	@Override
	public void update(float dTime, LevelController level) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onAreaTouched(final TouchEvent pSceneTouchEvent,
			final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
		try {
			if(buttonListener == null){
				return false;
			}
			switch (pSceneTouchEvent.getAction()) {
			
			case TouchEvent.ACTION_DOWN:
					
				buttonListener.onActionDown(pSceneTouchEvent.getX(),pSceneTouchEvent.getY());
				if(press!=null){
					this.setAlpha(0);
					press.setAlpha(1);
				}

				break;
			case TouchEvent.ACTION_MOVE:
				buttonListener.onActionMove(pSceneTouchEvent.getX(),pSceneTouchEvent.getY());

				break;
			case TouchEvent.ACTION_UP:
				buttonListener.onActionUp(pSceneTouchEvent.getX(),pSceneTouchEvent.getY());
				if(press!=null){
					this.setAlpha(1);
					press.setAlpha(0);
				}
				break;
			case TouchEvent.ACTION_OUTSIDE:
				if(press!=null){
					this.setAlpha(1);
					press.setAlpha(0);
				}
				break;
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	
	

	public ButtonListener getButtonListener() {
		return buttonListener;
	}


	public void setButtonListener(ButtonListener buttonListener) {
		this.buttonListener = buttonListener;
	}


	public Text getText() {
		return text;
	}


	public void setText(Text text) {
		this.text = text;
	}


	public SpriteBackground getPress() {
		return press;
	}


	public void setPressSprite(String pressName) {
		try {
			
			press = new SpriteBackground(0, 0, texture.getTextureByName(pressName), texture.getVertexBufferObjectManager());
			
			press.detachSelf();
			this.press.setPosition(this.getWidth()/2 - press.getWidth()/2, this.getHeight()/2 - press.getHeight()/2);
			this.attachChild(this.press);
			press.setAlpha(0);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			press = null;
		}
	}

	
	
	
}
