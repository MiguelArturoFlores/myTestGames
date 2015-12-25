package com.mgl.drop.game.sprites.arunner;

import org.andengine.entity.text.Text;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.color.Color;

import com.mgl.base.MySprite;
import com.mgl.base.SpriteType;
import com.mgl.base.userinfo.UserInfoSingleton;
import com.mgl.drop.factory.ObjectFactorySingleton;
import com.mgl.drop.game.constant.GameConstants;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.texture.TextureSingleton;

public class SpritePowerSelect extends MySprite {

	private Text quantity;
	private int level;
	private int powerType;
	
	public SpritePowerSelect(float pX, float pY, float pWidth, float pHeight,
			ITextureRegion pTextureRegion,
			VertexBufferObjectManager vertexBufferObjectManager,
			LevelController level, int levelQ) {
		super(pX, pY, pWidth, pHeight, pTextureRegion,
				vertexBufferObjectManager, level);
		this.level = levelQ;
		this.powerType = 0;
		try {
			
			quantity = ObjectFactorySingleton.getInstance().createText(
					"" +levelQ ,
					TextureSingleton.getInstance().getmFont1());
			quantity.setColor(Color.BLACK);
			this.attachChild(quantity);
			this.setPosition(3, -5);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public SpritePowerSelect(float pX, float pY, float pWidth, float pHeight,
			ITextureRegion pTextureRegion,
			VertexBufferObjectManager vertexBufferObjectManager,
			LevelController level, int levelQ, int powerType) {
		super(pX, pY, pWidth, pHeight, pTextureRegion,
				vertexBufferObjectManager, level);
		this.level = levelQ;
		this.powerType = powerType;
		try {
			
			quantity = ObjectFactorySingleton.getInstance().createText(
					"" +levelQ ,
					TextureSingleton.getInstance().getmFont1());
			quantity.setColor(Color.BLACK);
			this.attachChild(quantity);
			this.setPosition(3, -5);
			
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
	public void update(float dTime, LevelController level) {
		// TODO Auto-generated method stub

	}

	public void reloadLevel() {
		try {
			
			quantity.detachSelf();
			
			quantity = ObjectFactorySingleton.getInstance().createText(
					"" +level ,
					TextureSingleton.getInstance().getmFont1());
			quantity.setColor(Color.BLACK);
			this.attachChild(quantity);
			this.setPosition(3, -5);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void increasseLevel() {
		try {
			
			switch (powerType) {
			case GameConstants.POWER_A:
					UserInfoSingleton.getInstance().increasePowerA(1);
				break;
			case GameConstants.POWER_B:
				UserInfoSingleton.getInstance().increasePowerB(1);
			break;
			case GameConstants.POWER_C:
				UserInfoSingleton.getInstance().increasePowerC(1);
			break;
			case GameConstants.POWER_D:
				UserInfoSingleton.getInstance().increasePowerD(1);
			break;

			default:
				break;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public Text getQuantity() {
		return quantity;
	}

	public void setQuantity(Text quantity) {
		this.quantity = quantity;
	}

	public int getLevelQ() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getPowerType() {
		return powerType;
	}

	public void setPowerType(int powerType) {
		this.powerType = powerType;
	}

	
	
}
