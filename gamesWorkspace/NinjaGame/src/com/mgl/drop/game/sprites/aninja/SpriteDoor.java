package com.mgl.drop.game.sprites.aninja;

import java.util.ArrayList;

import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MyAnimateProperty;
import com.mgl.base.MyAnimateSprite;
import com.mgl.base.MyFactory;
import com.mgl.base.MySpriteGeneral;
import com.mgl.base.SpriteType;
import com.mgl.base.TextFactory;
import com.mgl.drop.factory.MyXmlParser;
import com.mgl.drop.game.HUDManagerSingleton;
import com.mgl.drop.game.constant.State;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.controller.dijkstra.WorldNode;
import com.mgl.drop.game.hud.sprites.SpriteMessage;
import com.mgl.ninja.R;

public class SpriteDoor extends MyAnimateSprite{

	private Long id;
	
	public SpriteDoor(float pX, float pY,
			ITextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager,
			LevelController level) {
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager, level);
		// TODO Auto-generated constructor stub
	}

	@Override
	public SpriteType getSpriteType() {
		// TODO Auto-generated method stub
		return SpriteType.DOOR;
	}

	@Override
	public void initAnimationParams() {

		try {

			changeAnimateState(State.CLOSE, false);
			anime(false);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initHashMap() {
		try {

			fps = new long[] { 83, 83};

			stateAnimate.put(State.CLOSE, new MyAnimateProperty(0, 2, fps));
			stateAnimate.put(State.OPEN, new MyAnimateProperty(2, 2, fps));
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	
	@Override
	public void updateAnimated(float dTime, LevelController level) {
		// TODO Auto-generated method stub
		
	}

	public void open(LevelController level) {
		try {
			
			for(MySpriteGeneral spr : level.getSpriteList()){
				
				try {
				
					if(spr.getSpriteType().equals(SpriteType.KEY)){
						SpriteKey key = (SpriteKey) spr;
						if(key.getId().equals(id) ){
							if(key.isPicked()){
								
								if(!currentState.equals(State.OPEN)){
									level.getLevelWorld().getBox(this.getX()+this.getWidth()/2, this.getY()+this.getHeight()/2).setState(WorldNode.EMPTY);
								}
								
								changeAnimateState(State.OPEN, false);
							}
							else{
								SpriteMessage message = MyFactory
										.createMessage(TextFactory
												.createText(R.string.NEED_KEY),
												2);
								HUDManagerSingleton.getInstance().getTop()
										.attachChild(message);
								message.setAutoUpdate();
							}
							return;
						}
					}
					
				} catch (Exception e) {

				}
			}

			if(!currentState.equals(State.OPEN)){
				level.getLevelWorld().getBox(this.getX()+this.getWidth()/2, this.getY()+this.getHeight()/2).setState(WorldNode.EMPTY);
			}
			
			changeAnimateState(State.OPEN, false);
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public void setXmlParameter(String parameter) {
		try {
			
			ArrayList<Long> parameterList = MyXmlParser.getParameterList(parameter);
			int i = 0;
			for(Long param : parameterList){
				try {
					if(i==0){
						id = param;
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

	public void openCloseDirect(LevelController level) {
		try {
			
			if(!currentState.equals(State.OPEN)){
				level.getLevelWorld().getBox(this.getX()+this.getWidth()/2, this.getY()+this.getHeight()/2).setState(WorldNode.EMPTY);
				changeAnimateState(State.OPEN, false);
			}else{
				level.getLevelWorld().getBox(this.getX()+this.getWidth()/2, this.getY()+this.getHeight()/2).setState(WorldNode.OCCUPED);
				changeAnimateState(State.CLOSE, false);
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	
}
