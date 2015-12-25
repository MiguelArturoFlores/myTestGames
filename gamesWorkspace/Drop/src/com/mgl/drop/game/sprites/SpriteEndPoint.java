package com.mgl.drop.game.sprites;

import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MySprite;
import com.mgl.base.MySpriteGeneral;
import com.mgl.base.SpriteType;
import com.mgl.base.StatusType;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.texture.TextureSingleton;

public class SpriteEndPoint extends MySprite{
	
	private SpriteInvisibleTouch sprCollition;

	public SpriteEndPoint(float pX, float pY, float pWidth, float pHeight,
			ITextureRegion pTextureRegion,
			VertexBufferObjectManager vertexBufferObjectManager, LevelController level) {
		super(pX, pY, pWidth, pHeight, pTextureRegion, vertexBufferObjectManager, level);
		status = StatusType.NORMAL;
		
		sprCollition = new SpriteInvisibleTouch((0f),(0f),200f,200f,TextureSingleton.getInstance().getTextureByName("point.png"),vertexBufferObjectManager,this);
		
		
		this.attachChild((sprCollition));
		sprCollition.setPosition(30,0);
		sprCollition.setWidth(pWidth);
		sprCollition.setHeight(pHeight);
		//sprCollition.setAlpha(0.2f);
		sprCollition.setVisible(false);
		
	}

	@Override
	public SpriteType getSpriteType() {

		return SpriteType.DECORATIVE;
	}

	@Override
	public void update(float dTime, LevelController level) {
		try {
			
			if(status.equals(StatusType.NORMAL)){
				for(MySpriteGeneral sprite : level.getSpriteList()){
					
					if(sprite.getSpriteType().equals(SpriteType.PLAYER)){
						SpritePlayer spr = (SpritePlayer) sprite;
						if(spr.collidesWith(sprCollition)){
							if(!spr.getStatus().equals(StatusType.FINISHED)){
								spr.setStatus(StatusType.FINISHED);
								level.changePoopEnd();
							}
							
							for(MySpriteGeneral spriteAux : level.getSpriteList()){
								if(spriteAux.getSpriteType().equals(SpriteType.POOP) && !spriteAux.getStatus().equals(StatusType.FINISHED)){
									return;
								}
							}
							
							level.validateWin();
							status=StatusType.WAITING;
							
						}
					}
					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	
	
}
