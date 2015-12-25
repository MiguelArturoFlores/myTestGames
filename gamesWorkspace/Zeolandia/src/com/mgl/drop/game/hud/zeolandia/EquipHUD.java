package com.mgl.drop.game.hud.zeolandia;

import org.andengine.entity.text.Text;
import org.andengine.util.color.Color;

import com.mgl.base.ButtonListener;
import com.mgl.base.MyFactory;
import com.mgl.base.TextFactory;
import com.mgl.base.userinfo.PlayerSingleton;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.game.HUDManagerSingleton;
import com.mgl.drop.game.hud.MyHud;
import com.mgl.drop.game.hud.sprites.SpriteRemoveHud;
import com.mgl.drop.game.sprites.SpriteBackground;
import com.mgl.drop.game.sprites.button.ButtonGeneral;
import com.mgl.drop.game.sprites.button.azeolandia.ButtonItem;

public class EquipHUD extends MyHud {

	private ButtonItem sock;
	private ButtonItem ear;
	private ButtonItem hand;

	public EquipHUD() {

		try {

			Text equip = TextFactory.getEquipText(texture.getmFont());
			equip.setPosition(10, 10);

			Text name = TextFactory.getText("ZEO", texture.getmFont());
			name.setPosition(equip.getX(), equip.getY() + equip.getHeight() + 5);

			SpriteBackground background = new SpriteBackground(0, 0,
					texture.getTextureByName("backgroundZeo.png"),
					texture.getVertexBufferObjectManager());
			background.setSize(MainDropActivity.CAMERA_WIDTH,
					MainDropActivity.CAMERA_HEIGHT);
			// background.setAlpha(0.6f);
			this.attachChild(background);

			sock = new ButtonItem(0, 0,
					texture.getTextureByName("buttonItem.png"),
					texture.getVertexBufferObjectManager());
			ear = new ButtonItem(0, 0,
					texture.getTextureByName("buttonItem.png"),
					texture.getVertexBufferObjectManager());
			hand = new ButtonItem(0, 0,
					texture.getTextureByName("buttonItem.png"),
					texture.getVertexBufferObjectManager());

			sock.addText(TextFactory.getSockText(texture.getmFont()));
			ear.addText(TextFactory.getEaringText(texture.getmFont()));
			hand.addText(TextFactory.getHandText(texture.getmFont()));

			sock.getText().setY(-20);
			ear.getText().setY(-20);
			hand.getText().setY(-20);

			ear.setPosition(50, 100);
			hand.setPosition(ear.getX() + ear.getWidth() + 5, ear.getY() + 20);
			sock.setPosition(hand.getX() + hand.getWidth() + 5, ear.getY());

			hand.setItem(MyFactory.createItem(PlayerSingleton.getInstance()
					.getPlayer().getHand()));
			ear.setItem(MyFactory.createItem(PlayerSingleton.getInstance()
					.getPlayer().getEaring()));
			sock.setItem(MyFactory.createItem(PlayerSingleton.getInstance()
					.getPlayer().getSock()));

			this.attachChild(sock);
			this.attachChild(ear);
			this.attachChild(hand);

			this.attachChild(equip);
			this.attachChild(name);

			initButtonListener();

			addStatusBars();

			SpriteRemoveHud remove = new SpriteRemoveHud(0, 0,
					texture.getTextureByName("closeHud.png"),
					texture.getVertexBufferObjectManager(), this);
			remove.setPosition(10,
					MainDropActivity.CAMERA_HEIGHT - remove.getHeight() - 5);
			this.attachChild(remove);
			this.registerTouchArea(remove);

			this.registerTouchArea(sock);
			this.registerTouchArea(ear);
			this.registerTouchArea(hand);

			this.registerTouchArea(background);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void addExperienceBar(SpriteBackground background) {
		try {
			float barWidth = 100f;

			Text lifeText = TextFactory.createText("EXP "
					+ PlayerSingleton.getInstance().getPlayer()
							.getCurrentExperience()
					+ "/"
					+ PlayerSingleton.getInstance().getPlayer()
							.getExperienceNextLevel(), texture.getmFont2());
			lifeText.setPosition(
					background.getWidth() / 2 - lifeText.getWidth() / 2,
					background.getHeight() - 20);
			background.attachChild(lifeText);

			SpriteBackground totalLife = new SpriteBackground(5,
					lifeText.getY() + lifeText.getHeight()+1,
					texture.getTextureByName("emptyBar.png"),
					texture.getVertexBufferObjectManager());

			totalLife.setWidth(barWidth);
			background.attachChild(totalLife);

			SpriteBackground currentLife = new SpriteBackground(5,
					totalLife.getY(), texture.getTextureByName("expBar.png"),
					texture.getVertexBufferObjectManager());

			currentLife.setWidth(PlayerSingleton.getInstance().getPlayer()
					.getCurrentExperience()
					* totalLife.getWidth()
					/ PlayerSingleton.getInstance().getPlayer()
							.getExperienceNextLevel());

			background.attachChild(currentLife);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void addStatusBars() {
		try {

			SpriteBackground background = new SpriteBackground(0, 0,
					texture.getTextureByName("black.jpg"),
					texture.getVertexBufferObjectManager());
			background.setSize(120, MainDropActivity.CAMERA_HEIGHT / 2);
			background.setAlpha(0.7f);
			background.setPosition(
					MainDropActivity.CAMERA_WIDTH - background.getWidth(),
					MainDropActivity.CAMERA_HEIGHT - background.getHeight());
			this.attachChild(background);

			float barWidth = 100f;

			// add life bar====================================================
			Text lifeText = TextFactory.createText("HP "
					+ PlayerSingleton.getInstance().getPlayer().getCurrentHP()
					+ "/"
					+ PlayerSingleton.getInstance().getPlayer().getTotalHP(),
					texture.getmFont2());
			lifeText.setPosition(
					background.getWidth() / 2 - lifeText.getWidth() / 2, 5);
			background.attachChild(lifeText);

			SpriteBackground totalLife = new SpriteBackground(5, 15,
					texture.getTextureByName("emptyBar.png"),
					texture.getVertexBufferObjectManager());
			SpriteBackground currentLife = new SpriteBackground(5, 15,
					texture.getTextureByName("fillLifeBar.png"),
					texture.getVertexBufferObjectManager());

			totalLife.setWidth(barWidth);

			currentLife.setWidth(PlayerSingleton.getInstance().getPlayer()
					.getCurrentHP()
					* totalLife.getWidth()
					/ PlayerSingleton.getInstance().getPlayer().getTotalHP());

			background.attachChild(totalLife);
			background.attachChild(currentLife);

			// ===========add mp
			// bar========================================================

			Text manaText = TextFactory.createText("MP "
					+ PlayerSingleton.getInstance().getPlayer().getCurrentMP()
					+ "/"
					+ PlayerSingleton.getInstance().getPlayer().getTotalMP(),
					texture.getmFont2());
			manaText.setPosition(
					background.getWidth() / 2 - manaText.getWidth() / 2, 25);
			background.attachChild(manaText);

			SpriteBackground totalMana = new SpriteBackground(5, 35,
					texture.getTextureByName("emptyBar.png"),
					texture.getVertexBufferObjectManager());
			SpriteBackground currentMana = new SpriteBackground(5, 35,
					texture.getTextureByName("fillManaBar.png"),
					texture.getVertexBufferObjectManager());

			totalMana.setWidth(barWidth);

			currentMana.setWidth(PlayerSingleton.getInstance().getPlayer()
					.getCurrentMP()
					* totalMana.getWidth()
					/ PlayerSingleton.getInstance().getPlayer().getTotalMP());

			background.attachChild(totalMana);
			background.attachChild(currentMana);

			// =========================add atts
			// ================================================================

			String str = "ATK/"
					+ PlayerSingleton.getInstance().getPlayer().getAttack();
			Text strenght = TextFactory.createText(str, texture.getmFont3());
			strenght.setPosition(10, totalMana.getY() + totalMana.getHeight()
					+ 5);
			background.attachChild(strenght);

			str = PlayerSingleton.getInstance().getPlayer().getAttackExtra() >= 0 ? " + "
					: " ";
			str = str
					+ PlayerSingleton.getInstance().getPlayer()
							.getAttackExtra();
			Text strenghtPlus = TextFactory
					.createText(str, texture.getmFont3());
			if (PlayerSingleton.getInstance().getPlayer().getAttackExtra() >= 0) {
				strenghtPlus.setColor(Color.GREEN);
			} else {
				strenghtPlus.setColor(Color.RED);
			}
			strenghtPlus.setPosition(strenght.getX() + strenght.getWidth(),
					strenght.getY());
			background.attachChild(strenghtPlus);

			// =============================dexterity========================================
			str = "DEF/"
					+ PlayerSingleton.getInstance().getPlayer().getDefense();
			Text dex = TextFactory.createText(str, texture.getmFont3());
			dex.setPosition(10, strenght.getY() + strenght.getHeight() + 5);
			background.attachChild(dex);

			str = PlayerSingleton.getInstance().getPlayer().getDefenseExtra() >= 0 ? " + "
					: " ";
			str = str
					+ PlayerSingleton.getInstance().getPlayer()
							.getDefenseExtra();
			Text dexterityPlus = TextFactory.createText(str,
					texture.getmFont3());
			if (PlayerSingleton.getInstance().getPlayer().getDefenseExtra() >= 0) {
				dexterityPlus.setColor(Color.GREEN);
			} else {
				dexterityPlus.setColor(Color.RED);
			}
			dexterityPlus.setPosition(dex.getX() + dex.getWidth(), dex.getY());
			background.attachChild(dexterityPlus);

			// =============================intelligence========================================
			str = "MMP/"
					+ PlayerSingleton.getInstance().getPlayer().getMagicPower();
			Text intelligence = TextFactory
					.createText(str, texture.getmFont3());
			intelligence.setPosition(10, dex.getY() + dex.getHeight() + 5);
			background.attachChild(intelligence);

			str = PlayerSingleton.getInstance().getPlayer()
					.getMagicPowerExtra() >= 0 ? " + " : " ";
			str = str
					+ PlayerSingleton.getInstance().getPlayer()
							.getMagicPowerExtra();
			Text IntelligencePlus = TextFactory.createText(str,
					texture.getmFont3());
			if (PlayerSingleton.getInstance().getPlayer().getMagicPowerExtra() >= 0) {
				IntelligencePlus.setColor(Color.GREEN);
			} else {
				IntelligencePlus.setColor(Color.RED);
			}
			IntelligencePlus.setPosition(
					intelligence.getX() + intelligence.getWidth(),
					intelligence.getY());
			background.attachChild(IntelligencePlus);

			addExperienceBar(background);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initButtonListener() {
		try {

			sock.setButtonListener(new ButtonListener() {

				@Override
				public void onActionUp(float x, float y) {
					try {

						ItemHUD itemHud = new ItemHUD(sock.getItem());
						HUDManagerSingleton.getInstance().removeAndReplaceHud();
						HUDManagerSingleton.getInstance().addHUD(itemHud, true);

					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				@Override
				public void onActionMove(float x, float y) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onActionDown(float x, float y) {
					// TODO Auto-generated method stub

				}
			});

			ear.setButtonListener(new ButtonListener() {

				@Override
				public void onActionUp(float x, float y) {

					ItemHUD itemHud = new ItemHUD(ear.getItem());
					HUDManagerSingleton.getInstance().removeAndReplaceHud();
					HUDManagerSingleton.getInstance().addHUD(itemHud, true);

				}

				@Override
				public void onActionMove(float x, float y) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onActionDown(float x, float y) {
					// TODO Auto-generated method stub

				}
			});

			hand.setButtonListener(new ButtonListener() {

				@Override
				public void onActionUp(float x, float y) {

					ItemHUD itemHud = new ItemHUD(hand.getItem());
					HUDManagerSingleton.getInstance().removeAndReplaceHud();
					HUDManagerSingleton.getInstance().addHUD(itemHud, true);

				}

				@Override
				public void onActionMove(float x, float y) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onActionDown(float x, float y) {
					// TODO Auto-generated method stub

				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onCloseAction() {
		// TODO Auto-generated method stub

	}

}
