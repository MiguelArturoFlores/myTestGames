package com.mgl.drop.game.sprites.arunner;

import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.color.Color;

import com.mgl.base.MyFactory;
import com.mgl.base.MySprite;
import com.mgl.base.SpriteType;
import com.mgl.base.userinfo.UserInfoSingleton;
import com.mgl.drop.factory.ObjectFactorySingleton;
import com.mgl.drop.game.HUDManagerSingleton;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.hud.InformativeHUD;
import com.mgl.drop.game.hud.sprites.SpriteMessage;
import com.mgl.drop.game.scene.SceneManagerSingleton;
import com.mgl.drop.game.scene.SceneManagerSingleton.AllScenes;
import com.mgl.drop.game.sprites.SpriteBackground;
import com.mgl.drop.texture.TextureSingleton;

public class SpriteUpgradePower extends MySprite {

	private Text quantity;
	private boolean canUpgrade = false;
	private SpritePowerSelect powerSelect;
	private boolean AlreadyUpdated = false;
	private boolean notAvalible = false;
	private int price;

	public SpriteUpgradePower(float pX, float pY,
			ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager, int levelQ,
			SpritePowerSelect powerSelect) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager);
		try {

			this.powerSelect = powerSelect;

			quantity = ObjectFactorySingleton.getInstance().createText(
					"" + levelQ, TextureSingleton.getInstance().getmFont1());
			quantity.setColor(Color.BLACK);
		//	this.attachChild(quantity);

			this.setPosition(this.getWidth() / 2 - quantity.getWidth() / 2,
					this.getHeight() / 2 - quantity.getHeight() / 2);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setPrice(int price) {
		try {
		
			this.price = price;
			
			Text textMoney = ObjectFactorySingleton.getInstance().createText(
					"x " + price, texture.getmFont2());

			textMoney.setPosition(15 / 2,
					this.getHeight() -10);
			textMoney.setAlpha(this.getAlpha());
			this.attachChild(textMoney);

			SpriteBackground diamant = new SpriteBackground(0, 0,
					texture.getTextureByName("money.png"),
					texture.getVertexBufferObjectManager());
			diamant.setPosition(0, textMoney.getY() - 10);
			diamant.setWidth(15);
			diamant.setHeight(15);
			diamant.setAlpha(this.getAlpha());
			
		//	this.attachChild(diamant);
			
			
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

	@Override
	public boolean onAreaTouched(final TouchEvent pSceneTouchEvent,
			final float pTouchAreaLocalX, final float pTouchAreaLocalY) {

		try {
			switch (pSceneTouchEvent.getAction()) {
			case TouchEvent.ACTION_DOWN:

				if (canUpgrade) {
					
					float money = UserInfoSingleton.getInstance().getMoney();
					if(money<price){
						
						SpriteMessage message = MyFactory.createMessage("You need "+(price - money)+" to upgrade it",1.5f);
						HUDManagerSingleton.getInstance().getTop().attachChild(message);
						message.setAutoUpdate();
						return true;
					}
					money = money - price;
					UserInfoSingleton.getInstance().increaseMoney(price*-1);
					
					powerSelect.increasseLevel();
					powerSelect.reloadLevel();
					SceneManagerSingleton.getInstance().setCurrentScene(
							AllScenes.POWER_UP);
					
					SpriteMessage message = MyFactory.createMessage("Power Updated!",1.5f);
					HUDManagerSingleton.getInstance().getTop().attachChild(message);
					message.setAutoUpdate();
					
					return true;

				} else if (AlreadyUpdated) {

					SpriteMessage message = MyFactory.createMessage("Power Already Updated",1.5f);
					HUDManagerSingleton.getInstance().getTop().attachChild(message);
					message.setAutoUpdate();
							
					return true;
				}

				if (notAvalible) {
					SpriteMessage message = MyFactory.createMessage("Not avalible yet",1.5f);
					HUDManagerSingleton.getInstance().getTop().attachChild(message);
					message.setAutoUpdate();
					return true;
				}

				InformativeHUD hud = new InformativeHUD(
						"You must upgrade first level "
								+ (Long.valueOf(quantity.getText().toString()) - 1));
				HUDManagerSingleton.getInstance().addHUD(hud, true);

				break;
			case TouchEvent.ACTION_MOVE:

				break;
			case TouchEvent.ACTION_UP:

				break;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	public Text getQuantity() {
		return quantity;
	}

	public void setQuantity(Text quantity) {
		this.quantity = quantity;
	}

	public boolean isCanUpgrade() {
		return canUpgrade;
	}

	public void setCanUpgrade(boolean canUpgrade) {
		this.canUpgrade = canUpgrade;
	}

	public SpritePowerSelect getPowerSelect() {
		return powerSelect;
	}

	public void setPowerSelect(SpritePowerSelect powerSelect) {
		this.powerSelect = powerSelect;
	}

	public boolean isAlreadyUpdated() {
		return AlreadyUpdated;
	}

	public void setAlreadyUpdated(boolean alreadyUpdated) {
		try {
		
			AlreadyUpdated = alreadyUpdated;
			if(!alreadyUpdated){
				return;
			}
			
			this.setAlpha(0.2f);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public boolean isNotAvalible() {
		return notAvalible;
	}

	public void setNotAvalible(boolean notAvalible) {
		this.notAvalible = notAvalible;
	}

	public int getPrice() {
		return price;
	}

}
