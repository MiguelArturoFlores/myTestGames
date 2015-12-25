package com.mgl.drop.game.sprites.interfaz;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.text.Text;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.google.android.gms.games.internal.GamesConstants;
import com.google.android.gms.internal.qu;
import com.mgl.base.MyFactory;
import com.mgl.base.MySprite;
import com.mgl.base.SpriteType;
import com.mgl.drop.factory.ObjectFactorySingleton;
import com.mgl.drop.game.HUDManagerSingleton;
import com.mgl.drop.game.constant.GameConstants;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.hud.PowerShopHud;
import com.mgl.drop.game.objects.button.ButtonMoney;
import com.mgl.drop.game.sprites.SpriteBackground;
import com.mgl.drop.game.sprites.SpritePowerFire;
import com.mgl.drop.game.sprites.SpritePowerFood;
import com.mgl.drop.game.sprites.SpritePowerWall;
import com.mgl.drop.game.sprites.button.ButtonBuyPower;
import com.mgl.drop.texture.TextureSingleton;

public class SpriteBuyPower extends MySprite {

	public SpriteBuyPower(float pX, float pY,
			ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager,
			int powerType, ButtonMoney money, PowerShopHud powerShopHud) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager);
		try {

			TextureSingleton texture = TextureSingleton.getInstance();

			String textureName = new String();
			String quantityPower = new String();
			String pricePower = new String();
			int price = 0;
			int quantity = 0;

			String powerName = "SUPER POWER";
			
			switch (powerType) {
			case GameConstants.POWER_DEADBODY:

				textureName = "deadPower.png";
				quantityPower = "" + GameConstants.QUANTITY_POWER_A;
				quantity = GameConstants.QUANTITY_POWER_A;
				pricePower = "" + GameConstants.PRICE_POWER_A;
				price = GameConstants.PRICE_POWER_A;
				powerName = "CORPSE";
				
				break;
			case GameConstants.POWER_WALL:

				textureName = "wallPower.png";
				quantityPower = "" + GameConstants.QUANTITY_POWER_B;
				quantity = GameConstants.QUANTITY_POWER_B;
				pricePower = "" + GameConstants.PRICE_POWER_B;
				price = GameConstants.PRICE_POWER_B;
				powerName = "FENCE";
				
				break;
			case GameConstants.POWER_FIRE:

				textureName = "firePower.png";
				quantityPower = "" + GameConstants.QUANTITY_POWER_C;
				quantity = GameConstants.QUANTITY_POWER_C;
				pricePower = "" + GameConstants.PRICE_POWER_C;
				price = GameConstants.PRICE_POWER_C;
				powerName = "FIRE";
				break;
			case GameConstants.POWER_RUSH:

				textureName = "trunkPower.png";
				quantityPower = "" + GameConstants.QUANTITY_POWER_D;
				quantity = GameConstants.QUANTITY_POWER_D;
				pricePower = "" + GameConstants.PRICE_POWER_D;
				price = GameConstants.PRICE_POWER_D;
				powerName = "KILL & ROLL";
				break;

			default:
				break;
			}

			SpriteBackground powerA = new SpriteBackground(40, 20,
					texture.getTextureByName(textureName),
					texture.getVertexBufferObjectManager());
			Text quantityText = ObjectFactorySingleton.getInstance().createText(
					"x " + quantityPower,
					TextureSingleton.getInstance().getmFont1());
			quantityText.setPosition(powerA.getWidth()/3, powerA.getHeight() - quantityText.getHeight() -5);
			powerA.attachChild(quantityText);

			Text priceText = ObjectFactorySingleton.getInstance().createText(
					"" + pricePower,
					TextureSingleton.getInstance().getmFont1());
			priceText.setPosition(280, 35);

			ButtonBuyPower buy = new ButtonBuyPower(315, 70,
					texture.getTextureByName("buttonTextureRed.png"),
					texture.getVertexBufferObjectManager(),
					price, powerType,
					money, quantity);

			Text powerText = ObjectFactorySingleton.getInstance().createText(
					powerName,
					TextureSingleton.getInstance().getmFont1());
			powerText.setPosition(this.getWidth()/2 - powerText.getWidth()/2+10, this.getHeight()/2 -powerText.getHeight()/2);
			
			SpriteBackground splash = new SpriteBackground(0, 0, texture.getTextureByName("buttonTexture2Red.png"), texture.getVertexBufferObjectManager());
			splash.setPosition(buy.getX()-20, buy.getY() -20);
			splash.setSize(buy.getWidth()+40, buy.getHeight()+40);
			this.attachChild(powerA);
			this.attachChild(powerText);
			this.attachChild(priceText);
			this.attachChild(splash);
			this.attachChild(buy);
			

			powerShopHud.registerTouchArea(buy);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public SpriteType getSpriteType() {
		return SpriteType.DECORATIVE;
	}

	@Override
	public void update(float dTime, LevelController level) {
		// TODO Auto-generated method stub

	}

}