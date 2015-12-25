package com.mgl.drop.game.sprites.azeoland;

import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MyFactory;
import com.mgl.base.MySprite;
import com.mgl.base.SpriteType;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.model.Item;
import com.mgl.drop.game.sprites.button.azeolandia.ButtonPickUp;
import com.mgl.drop.util.Point;

public class SpriteItem extends MySprite {

	private Item item;

	public SpriteItem(float pX, float pY, ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager);

	}

	@Override
	public SpriteType getSpriteType() {
		// TODO Auto-generated method stub
		return SpriteType.ITEM;
	}

	@Override
	public void update(float dTime, LevelController level) {

	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

}
