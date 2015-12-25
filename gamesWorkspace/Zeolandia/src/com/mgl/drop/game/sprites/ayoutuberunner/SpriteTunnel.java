package com.mgl.drop.game.sprites.ayoutuberunner;

import java.util.ArrayList;

import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MySprite;
import com.mgl.base.SpriteType;
import com.mgl.drop.factory.MyXmlParser;
import com.mgl.drop.game.constant.GameConstants;
import com.mgl.drop.game.controller.LevelController;

public class SpriteTunnel extends MySprite{

	private int tunelType;

	private boolean hasActivated = false;
	

	public SpriteTunnel(float pX, float pY,
			ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager);
		// TODO Auto-generated constructor stub
		setIgnoreUpdate(true);
	}

	@Override
	public SpriteType getSpriteType() {
		// TODO Auto-generated method stub
		return SpriteType.DECORATIVE;
	}

	@Override
	public void update(float dTime, LevelController level) {
		try {
			
			if(this.collidesWith(level.getPlayer()) && !hasActivated){
				hasActivated  = true;
				switch (tunelType) {
				case GameConstants.TUNNEL_CHANGE_GRAVITY:
					level.getPlayer().changeGravity();
					break;
				case GameConstants.TUNNEL_SHIP:
					level.getPlayer().transformOnShip();
					break;
				default:
					break;
				}

			}
			
						
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public void setXmlParameter(String parameter) {
		try {
			
			ArrayList<Long> parameterList = MyXmlParser.getParameterList(parameter);
			int i = 0;
			for(Long param : parameterList){
				try {
					if(i==0){
						tunelType = param.intValue();
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
