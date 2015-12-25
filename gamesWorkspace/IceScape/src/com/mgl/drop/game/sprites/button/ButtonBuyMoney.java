package com.mgl.drop.game.sprites.button;

import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.color.Color;

import com.mgl.base.MySprite;
import com.mgl.base.SpriteType;
import com.mgl.base.userinfo.UserInfoSingleton;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.factory.ObjectFactorySingleton;
import com.mgl.drop.factory.SoundSingleton;
import com.mgl.drop.game.constant.GamePurchaseConstant;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.scene.SceneManagerSingleton;
import com.mgl.drop.game.sprites.SpriteBackground;

public class ButtonBuyMoney extends MySprite {

	private float price;
	private int idBuyType;
	private int quantity;
	private float newPrice;
	
	private boolean isNewPrice = false;

	public ButtonBuyMoney(float pX, float pY, ITextureRegion pTextureRegion,
			VertexBufferObjectManager vertexBufferObjectManager, float price,
			int idBuyType, int quantity) {
		super(pX, pY, pTextureRegion, vertexBufferObjectManager);
		try {

			this.price = price;
			this.idBuyType = idBuyType;
			this.quantity = quantity;


			Text text = ObjectFactorySingleton.getInstance().createText(
					"" + quantity + " x " + price + "$", texture.getmFont1());
			text.setPosition(this.getWidth()/2 - text.getWidth()/2, 15);
			text.setColor(Color.GREEN);
			this.attachChild(text);
			
			isNewPrice = false;
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ButtonBuyMoney(float pX, float pY, ITextureRegion pTextureRegion,
			VertexBufferObjectManager vertexBufferObjectManager, float price,
			int idBuyType, int quantity, float newPrice) {
		super(pX, pY, pTextureRegion, vertexBufferObjectManager);
		try {

			isNewPrice  = true;
			
			this.price = price;
			this.idBuyType = idBuyType;
			this.quantity = quantity;
			this.newPrice = newPrice;
			

			Text text = ObjectFactorySingleton.getInstance().createText(
					"" + quantity + " x " + price + "$", texture.getmFont1());
			text.setPosition(this.getWidth()/2 - text.getWidth()/2, -50);
			text.setColor(Color.RED);
			this.attachChild(text);
			
			SpriteBackground redLine = new SpriteBackground(0, 0, texture.getTextureByName("redLine.png"), texture.getVertexBufferObjectManager());
			redLine.setSize(text.getWidth()+40, 5);
			redLine.setPosition(text.getX()-20,text.getY() + text.getHeight()/2 -2);
			this.attachChild(redLine);
			
			Text textNew = ObjectFactorySingleton.getInstance().createText(
					"" + quantity + " x " + newPrice + "$$", texture.getmFont1());
			textNew.setColor(Color.GREEN);
			textNew.setPosition(this.getWidth()/2 - textNew.getWidth()/2, text.getY()+text.getHeight()+5);

			this.attachChild(textNew);
			
			
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
			SoundSingleton.getInstance().playSound("buttonPress.mp3");
			MainDropActivity mainDropActivity = SceneManagerSingleton
					.getInstance().getActivity();

			switch (idBuyType) {
			case GamePurchaseConstant.BUY_A:
				mainDropActivity
						.generatePurchaseFlow(GamePurchaseConstant.BUY_A);
				break;
			case GamePurchaseConstant.BUY_B:
				mainDropActivity
						.generatePurchaseFlow(GamePurchaseConstant.BUY_B);
				break;
			case GamePurchaseConstant.BUY_C:
				mainDropActivity
						.generatePurchaseFlow(GamePurchaseConstant.BUY_C);
				break;
			case GamePurchaseConstant.BUY_D:
				mainDropActivity
						.generatePurchaseFlow(GamePurchaseConstant.BUY_D);
				break;
			case GamePurchaseConstant.BUY_E:
				mainDropActivity
						.generatePurchaseFlow(GamePurchaseConstant.BUY_E);
				break;
			case GamePurchaseConstant.BUY_F:
				mainDropActivity
						.generatePurchaseFlow(GamePurchaseConstant.BUY_F);
				break;
			case GamePurchaseConstant.BUY_G:
				mainDropActivity
						.generatePurchaseFlow(GamePurchaseConstant.BUY_G);
				break;
			case GamePurchaseConstant.BUY_H:
				mainDropActivity
						.generatePurchaseFlow(GamePurchaseConstant.BUY_H);
				break;
			case GamePurchaseConstant.BUY_I:
				mainDropActivity
						.generatePurchaseFlow(GamePurchaseConstant.BUY_I);
				break;
			case GamePurchaseConstant.BUY_D_OFFERT:
				mainDropActivity
						.generatePurchaseFlow(GamePurchaseConstant.BUY_D_OFFERT);
				break;
			case GamePurchaseConstant.BUY_E_OFFERT:
				mainDropActivity
						.generatePurchaseFlow(GamePurchaseConstant.BUY_E_OFFERT);
				break;
			case GamePurchaseConstant.BUY_F_OFFERT:
				mainDropActivity
						.generatePurchaseFlow(GamePurchaseConstant.BUY_F_OFFERT);
				break;
			case GamePurchaseConstant.BUY_G_OFFERT:
				mainDropActivity
						.generatePurchaseFlow(GamePurchaseConstant.BUY_G_OFFERT);
				break;
			case GamePurchaseConstant.BUY_H_OFFERT:
				mainDropActivity
						.generatePurchaseFlow(GamePurchaseConstant.BUY_H_OFFERT);
				break;
			case GamePurchaseConstant.BUY_I_OFFERT:
				mainDropActivity
						.generatePurchaseFlow(GamePurchaseConstant.BUY_I_OFFERT);
				break;
				
				
			default:
				break;
			}

			break;
		default:
			break;
		}
		return true;
	}

}
