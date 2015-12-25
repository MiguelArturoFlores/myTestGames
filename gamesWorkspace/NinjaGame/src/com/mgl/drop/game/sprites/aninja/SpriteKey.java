package com.mgl.drop.game.sprites.aninja;

import java.util.ArrayList;

import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MySprite;
import com.mgl.base.SpriteType;
import com.mgl.drop.factory.MyXmlParser;
import com.mgl.drop.game.controller.LevelController;

public class SpriteKey extends MySprite {

	private Long id;

	private boolean picked = false;

	public SpriteKey(float pX, float pY, ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager);
		// TODO Auto-generated constructor stub
	}

	@Override
	public SpriteType getSpriteType() {
		// TODO Auto-generated method stub
		return SpriteType.KEY;
	}

	@Override
	public void update(float dTime, LevelController level) {
		try {

			if(this.collidesWith(level.getPlayer())){
				picked = true;
				this.detachSelf();
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setXmlParameter(String parameter) {
		try {

			ArrayList<Long> parameterList = MyXmlParser
					.getParameterList(parameter);
			int i = 0;
			for (Long param : parameterList) {
				try {
					if (i == 0) {
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isPicked() {
		return picked;
	}

	public void setPicked(boolean picked) {
		this.picked = picked;
	}

}