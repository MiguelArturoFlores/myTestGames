package com.mgl.drop.game.entity.azeolandia;

import java.util.ArrayList;

import org.andengine.entity.IEntity;
import org.andengine.entity.scene.ITouchArea;

import com.mgl.base.MyAnimateSprite;
import com.mgl.base.MyEntity;
import com.mgl.base.MySprite;
import com.mgl.base.MySpriteGeneral;
import com.mgl.drop.game.controller.LevelController;

public class EntityAppear extends MyEntity{

	private ArrayList<MySpriteGeneral> spriteList ;
	private float alpha = 0;
	private float speedIncrease = 1f;
	
	private boolean mustRemove = false;
	
	public EntityAppear(){
		try {
			
			spriteList = new ArrayList<MySpriteGeneral>();
			
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
			
			float newAlpha = dTime * speedIncrease;
			alpha = alpha +newAlpha;
			
			for(MySpriteGeneral spr : spriteList ){
				if(spr instanceof MySprite){
					
					((MySprite) spr).setAlpha(alpha);
					
					if(((MySprite) spr).getAlpha()>1){
						((MySprite) spr).setAlpha(1);
						mustRemove = true;
					}
					
				}else if(spr instanceof MyAnimateSprite){ 
					
					((MyAnimateSprite) spr).setAlpha(alpha);
					
					if(((MyAnimateSprite) spr).getAlpha()>1){
						((MyAnimateSprite) spr).setAlpha(1);
						mustRemove = true;
					}
					
				}
				
			}
			
			if(mustRemove){
				level.removeEntity(this);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ArrayList<MySpriteGeneral> getSpriteList() {
		return spriteList;
	}

	public void setSpriteList(ArrayList<MySpriteGeneral> spriteList) {
		this.spriteList = spriteList;
	}

	public float getAlpha() {
		return alpha;
	}

	public void setAlpha(float alpha) {
		this.alpha = alpha;
	}

	public float getSpeedIncrease() {
		return speedIncrease;
	}

	public void setSpeedIncrease(float speedIncrease) {
		this.speedIncrease = speedIncrease;
	}

	public boolean isMustRemove() {
		return mustRemove;
	}

	public void setMustRemove(boolean mustRemove) {
		this.mustRemove = mustRemove;
	}

	public void autoSetAlpha() {
		try {
			
			update(0, null);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
