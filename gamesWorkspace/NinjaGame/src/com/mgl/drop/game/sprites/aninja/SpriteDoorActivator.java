package com.mgl.drop.game.sprites.aninja;

import java.util.ArrayList;

import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MyFactory;
import com.mgl.base.MySprite;
import com.mgl.base.MySpriteGeneral;
import com.mgl.base.SpriteType;
import com.mgl.base.TextFactory;
import com.mgl.drop.factory.MyXmlParser;
import com.mgl.drop.game.HUDManagerSingleton;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.hud.sprites.SpriteMessage;
import com.mgl.drop.game.sprites.ninja.SpritePlayer;
import com.mgl.drop.util.Point;
import com.mgl.ninja.R;

public class SpriteDoorActivator extends MySprite{
	
	private boolean isValidated = false;
	private Long id;

	public SpriteDoorActivator(float pX, float pY,
			ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager);
		// TODO Auto-generated constructor stub
		this.setAlpha(0);
	}

	@Override
	public SpriteType getSpriteType() {
		// TODO Auto-generated method stub
		return SpriteType.DOOR_ACTIVATOR;
	}

	@Override
	public void update(float dTime, LevelController level) {
		try {
			
			SpritePlayer player = level.getPlayer();
			Point p = new Point(player.getX() + player.getMidPoint().getX(),player.getY() + player.getMidPoint().getY());
			
			if(p.getX() >= this.getX() && p.getX()<= this.getX()+ this.getWidth() ){
				if(p.getY() >= this.getY() && p.getY()<= this.getY()+ this.getHeight() ){
					validateDoorState(level);
					return;
				}
			}
			isValidated = false;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void validateDoorState(LevelController level) {
		try {
			
			if(isValidated){
				return;
			}
			isValidated = true;
			
			for(MySpriteGeneral spr : level.getSpriteList()){
				
				try {
				
					if(spr.getSpriteType().equals(SpriteType.DOOR)){
						SpriteDoor door = (SpriteDoor) spr;
						if(door.getId().equals(id)){
							door.open(level);
							return;
						}							
					}
					
				} catch (Exception e) {

				}
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
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

	
}
