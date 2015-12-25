package com.mgl.drop.game.sprites.button.azeolandia;

import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MyFactory;
import com.mgl.base.MySprite;
import com.mgl.base.SpriteType;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.sprites.azeoland.SpriteItem;
import com.mgl.drop.game.sprites.button.ButtonGeneral;

public class ButtonItem extends ButtonGeneral {

	private SpriteItem item = null;
	
	public ButtonItem(float pX, float pY, ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager,null);
		// TODO Auto-generated constructor stub
	}

	@Override
	public SpriteType getSpriteType() {
		// TODO Auto-generated method stub
		return SpriteType.DECORATIVE;
	}

	@Override
	public void update(float dTime, LevelController level) {
		// TODO Auto-generated method stub

	}

	public SpriteItem getItem() {
		return item;
	}

	public void setItem(SpriteItem item) {
		
		//this.item = item;
		try {
			//this.detachChildren();
			if(this.item!=null){
				this.item.detachSelf();
			}
			
			if(item==null){
				this.item = null;
				return;
			}
			
			this.item = MyFactory.createItem(item.getItem());//new SpriteItem(0, 0, texture.getTextureByName(item.getItem().getTextureName()), texture.getVertexBufferObjectManager());
			this.attachChild(this.item);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
