package com.mgl.drop.game.sprites;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.text.Text;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MySprite;
import com.mgl.base.SpriteType;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.factory.ObjectFactorySingleton;
import com.mgl.drop.game.HUDManagerSingleton;
import com.mgl.drop.game.constant.GamePurchaseConstant;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.hud.LooseHUD;
import com.mgl.drop.game.sprites.button.ButtonRestartWave;

public class SpriteRegresiveCount extends MySprite{
	
	private Text timeText;
	private int time = 8;
	private float contTime = 0 ;
	
	private ButtonRestartWave restartWave;
	private HUD hud;

	
	public SpriteRegresiveCount(float pX, float pY,float pWidth, float pHeight,
			ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager, HUD hud, LevelController level) {
		super(pX, pY,pWidth,pHeight, pNormalTextureRegion, pVertexBufferObjectManager,level);

		try {
			this.setIgnoreUpdate(true);
			this.level = level;
			this.hud = hud;
			
			timeText = ObjectFactorySingleton.getInstance().createText(" "+time+"s",
					texture.getmFont1());
			timeText.setPosition(this.getWidth()/2 - timeText.getWidth()/2, 5);
			this.attachChild(timeText);
			
			Text restartText = ObjectFactorySingleton.getInstance().createText( "To Restart Wave  ",
					texture.getmFont1());
			restartText.setPosition(this.getWidth()/2 - restartText.getWidth()/2, 50);
			this.attachChild(restartText);

			Text restartText2 = ObjectFactorySingleton.getInstance().createText( "x"+GamePurchaseConstant.RESTART_WAVE_MONEY,
					texture.getmFont1());
			restartText2.setPosition(this.getWidth()/2 - restartText2.getWidth()/2, 100);
			this.attachChild(restartText2);

			SpriteBackground diamant = new SpriteBackground(0, 0, texture.getTextureByName("money.png"), texture.getVertexBufferObjectManager());
			diamant.setSize(50, 50);
			diamant.setPosition(restartText2.getX()-50, 85);
			this.attachChild(diamant);
			
			restartWave = new ButtonRestartWave(0, 0, texture.getTextureByName("buttonTextureRed.png"), texture.getVertexBufferObjectManager(), level,"Continue");
			restartWave.setPosition(this.getWidth()/2 - restartWave.getWidth()/2, 150);
			this.attachChild(restartWave);
			hud.registerTouchArea(restartWave);
			
			//UDManagerSingleton.getInstance().getTop().registerTouchArea(restartWave);
			
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
			
			if(time<0){
				this.detachSelf();
				level.removeEntity(this);
				hud.unregisterTouchArea(restartWave);
				
			}
			
			contTime = contTime + dTime;
			if(contTime>1){
				time --;
				contTime = 0;
				if(timeText != null && timeText.hasParent()){
					timeText.detachSelf();
				}
				
				timeText = ObjectFactorySingleton.getInstance().createText("Seconds "+time,
						texture.getmFont1());
				timeText.setPosition(this.getWidth()/2 - timeText.getWidth()/2, 5);
				if(timeText == null){
					return;
				}
				this.attachChild(timeText);
				
				contTime = 0;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	
	
}
