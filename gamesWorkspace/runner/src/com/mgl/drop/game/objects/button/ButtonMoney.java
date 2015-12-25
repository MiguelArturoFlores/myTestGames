package com.mgl.drop.game.objects.button;

import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.util.MonthDisplayHelper;

import com.mgl.base.userinfo.UserInfoSingleton;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.factory.ObjectFactorySingleton;
import com.mgl.drop.factory.SoundSingleton;
import com.mgl.drop.game.HUDManagerSingleton;
import com.mgl.drop.game.hud.OffertTimeHUD;
import com.mgl.drop.game.hud.PowerShopHud;
import com.mgl.drop.game.sprites.SpriteBackground;
import com.mgl.drop.texture.TextureSingleton;

public class ButtonMoney extends Sprite {

	TextureSingleton texture = TextureSingleton.getInstance();
	private Text textMoney;
	private int quantity;

	public ButtonMoney(float pX, float pY, ITextureRegion pNormalTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager);

		UserInfoSingleton userInfo = UserInfoSingleton.getInstance();

		this.setAlpha(0);
		
		quantity = userInfo.getMoney();
		textMoney = ObjectFactorySingleton.getInstance().createText(
				"x " + quantity , texture.getmFont1());

		this.setWidth(textMoney.getWidth() + 90);
		this.setHeight(textMoney.getHeight() + 30);

		textMoney.setPosition(this.getWidth() / 2 + 5 - textMoney.getWidth() / 2,
				this.getHeight() / 2 - textMoney.getHeight() / 2);

		this.attachChild(textMoney);

		SpriteBackground diamant = new SpriteBackground(0, 0,
				texture.getTextureByName("money.png"),
				texture.getVertexBufferObjectManager());
		diamant.setPosition(0, textMoney.getY() - 10);
		diamant.setWidth(50);
		diamant.setHeight(50);
		
		this.attachChild(diamant);

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
			if(true){return true;}
			SoundSingleton.getInstance().playStoreSound();

			HUDManagerSingleton.getInstance().removeHud();
			HUDManagerSingleton.getInstance().addHUD(
					new PowerShopHud(null, null), true);

			break;
		}
		return true;
	}

	public void updateText() {
		try {
			UserInfoSingleton userInfo = UserInfoSingleton.getInstance();
			textMoney = MainDropActivity.changeText("" + userInfo.getMoney(),
					textMoney, texture.getmFont1());
			textMoney.setPosition(this.getWidth() / 2 - textMoney.getWidth()
					/ 2 + 5, this.getHeight() / 2 - textMoney.getHeight() / 2);

			this.attachChild(textMoney);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void changeMoney(int money) {
		try {
			TextureSingleton texture = TextureSingleton.getInstance();
			textMoney.detachSelf();
			textMoney = MainDropActivity.changeText(money+"", textMoney, texture.getmFont1());
			this.attachChild(textMoney);
			
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public void setQuantityPlus(int i) {
		try {
			
			quantity = quantity + i;
			changeMoney(quantity);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
