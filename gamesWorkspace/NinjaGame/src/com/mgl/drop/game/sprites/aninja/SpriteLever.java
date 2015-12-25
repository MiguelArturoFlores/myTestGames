package com.mgl.drop.game.sprites.aninja;

import java.util.ArrayList;

import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MyAnimateProperty;
import com.mgl.base.MyAnimateSprite;
import com.mgl.base.MySpriteGeneral;
import com.mgl.base.SpriteType;
import com.mgl.drop.factory.MyXmlParser;
import com.mgl.drop.game.constant.State;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.util.Point;

public class SpriteLever  extends MyAnimateSprite{
	
	private boolean isActive = false;
	private ArrayList<Long> idList;
	
	private boolean isTouchAreaRegistered = false;

	public SpriteLever(float pX, float pY,
			ITextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager,
			LevelController level) {
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager, level);
		// TODO Auto-generated constructor stub
		isTouchAreaRegistered = false;
	}

	@Override
	public SpriteType getSpriteType() {
		// TODO Auto-generated method stub
		return SpriteType.LEVER;
	}

	@Override
	public void initAnimationParams() {

		try {

			changeAnimateState(State.NORMAL, true);
			anime(true);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initHashMap() {
		try {

			fps = new long[] { 83, 83 };

			stateAnimate.put(State.NORMAL, new MyAnimateProperty(0, 2, fps));
			stateAnimate.put(State.ACTIVE, new MyAnimateProperty(2, 2, fps));
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	
	@Override
	public void updateAnimated(float dTime, LevelController level) {
		try {
			
			if(isTouchAreaRegistered == false){
				level.getScene().registerTouchArea(this);
				isTouchAreaRegistered = true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void setXmlParameter(String parameter) {
		try {
			
			idList = new ArrayList<Long>();
			
			ArrayList<String> parameterList = MyXmlParser.getParameterListGeneric(parameter);
			int i = 0;
			for(String param : parameterList){
				try {
					if(i==0){
						String[] val = param.split(",");
						for(int j = 0; j < val.length; j ++){
							Long id = Long.valueOf(val[j]);
							idList.add(id);
						}
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				i++;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Point getMidPoint(){
		try {
			
			return new Point(this.getX()+this.getWidth()/2, this.getY()+this.getHeight()*0.75f);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void executeAction(){
		try {
			
			if(!currentState.equals(State.ACTIVE)){
				changeAnimateState(State.ACTIVE, false);
			}else{
				changeAnimateState(State.NORMAL, false);
			}
			
			for(Long id : idList){
				for(MySpriteGeneral spr: level.getSpriteList()){
					if(spr instanceof SpriteDoor){
						if(((SpriteDoor) spr).getId().equals(id)){
							
							((SpriteDoor) spr).openCloseDirect(level);
							//return;
						}
						
					}
				}
			}
			
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
				
				
				break;
			case TouchEvent.ACTION_MOVE:

				break;
			case TouchEvent.ACTION_UP:

				level.getPlayer().openLever(this);
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	
}

