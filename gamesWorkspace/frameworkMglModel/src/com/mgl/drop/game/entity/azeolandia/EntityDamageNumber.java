package com.mgl.drop.game.entity.azeolandia;

import org.andengine.entity.IEntity;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.entity.text.Text;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MyEntity;
import com.mgl.base.MyFactory;
import com.mgl.base.MySprite;
import com.mgl.base.SpriteType;
import com.mgl.drop.factory.ObjectFactorySingleton;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.texture.TextureSingleton;

public class EntityDamageNumber extends MyEntity {

	private Text numberText;
	
	private float contTime = 0;
	private float timeToDesapear = 0.5f;
	private float timeToAlpha = 0f;
	private boolean beginAlpha = false;
	

	public EntityDamageNumber(String text){
		try {
			
			TextureSingleton texture = TextureSingleton.getInstance();
			
			numberText = ObjectFactorySingleton.getInstance().createText(text,
					texture.getmFont1());
			
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
	public void updateChild(float dTime, LevelController level) {
	
		try {
			numberText.setPosition(numberText.getX(), numberText.getY()-3f);
			contTime +=dTime;
			
			if(!beginAlpha){
				if(contTime>=timeToAlpha){
					beginAlpha = true;
					contTime = 0;
				}
				return;
			}
			
			float alpha = contTime *1/timeToDesapear;
			alpha = 1- alpha; 
			
			
			if(alpha<=0){
				alpha = 0;
				numberText.detachSelf();
				level.removeEntity(this);
			}
			numberText.setAlpha(alpha);
			
			
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

	public Text getNumberText() {
		return numberText;
	}

	public void setNumberText(Text numberText) {
		this.numberText = numberText;
	}

	public float getContTime() {
		return contTime;
	}

	public void setContTime(float contTime) {
		this.contTime = contTime;
	}

	public float getTimeToDesapear() {
		return timeToDesapear;
	}

	public void setTimeToDesapear(float timeToDesapear) {
		this.timeToDesapear = timeToDesapear;
	}

	

}
