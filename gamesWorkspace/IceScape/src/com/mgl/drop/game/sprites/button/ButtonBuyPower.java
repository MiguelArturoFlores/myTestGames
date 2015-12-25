package com.mgl.drop.game.sprites.button;

import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.mgl.base.MySprite;
import com.mgl.base.SpriteType;
import com.mgl.base.userinfo.UserInfoSingleton;
import com.mgl.drop.factory.ObjectFactorySingleton;
import com.mgl.drop.game.constant.GameConstants;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.objects.button.ButtonMoney;
import com.mgl.drop.game.scene.SceneManagerSingleton;
import com.mgl.drop.game.scene.SceneManagerSingleton.AllScenes;

public class ButtonBuyPower extends MySprite {

	private int price;
	private int powerType;
	private ButtonMoney money;
	private int quantity;

	public ButtonBuyPower(float pX, float pY, ITextureRegion pTextureRegion,
			VertexBufferObjectManager vertexBufferObjectManager, int price,
			int powerType, ButtonMoney money, int quantity) {
		super(pX, pY, pTextureRegion, vertexBufferObjectManager);

		this.quantity = quantity;
		this.price = price;
		this.powerType = powerType;
		this.money = money;

		Text text = ObjectFactorySingleton.getInstance().createText("BUY",
				texture.getmFont1());
		this.setWidth(text.getWidth() + 30);
		this.setHeight(text.getHeight() + 30);

		text.setPosition(this.getWidth() / 2 - text.getWidth() / 2,
				this.getHeight() / 2 - text.getHeight() / 2);

		this.attachChild(text);

	}

	@Override
	public SpriteType getSpriteType() {
		// TODO Auto-generated method stub
		return SpriteType.DECORATIVE;
	}

	@Override
	public void update(float dTime, LevelController level) {
		try {

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public boolean onAreaTouched(final TouchEvent pSceneTouchEvent,
			final float pTouchAreaLocalX, final float pTouchAreaLocalY) {

		switch (pSceneTouchEvent.getAction()) {
		case TouchEvent.ACTION_DOWN:

			break;
		case TouchEvent.ACTION_MOVE:

			break;
		case TouchEvent.ACTION_UP:

			UserInfoSingleton userInfo = UserInfoSingleton.getInstance();
			if (userInfo.getMoney() - price < 0) {
				
				SceneManagerSingleton.getInstance().setCurrentScene(AllScenes.MONEY_SHOP);
				return true;
				
			}
			userInfo.consumeMoney(userInfo.getMoney() - price);
			money.updateText();
			switch (powerType) {
			case GameConstants.POWER_DEADBODY:

				userInfo.increasePowerA(quantity);
				SceneManagerSingleton.getInstance().sendGoogleTrack("Buy Power A");

				break;
			case GameConstants.POWER_WALL:

				userInfo.increasePowerB(quantity);
				SceneManagerSingleton.getInstance().sendGoogleTrack("Buy Power B");

				break;
			case GameConstants.POWER_FIRE:

				userInfo.increasePowerC(quantity);
				SceneManagerSingleton.getInstance().sendGoogleTrack("Buy Power C");

				break;
			case GameConstants.POWER_RUSH:

				userInfo.increasePowerD(quantity);
				SceneManagerSingleton.getInstance().sendGoogleTrack("Buy Power D");

				break;

			default:
				break;
			}
			
			break;
		}
		return true;
	}

}
