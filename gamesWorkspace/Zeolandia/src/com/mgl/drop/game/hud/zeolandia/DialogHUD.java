package com.mgl.drop.game.hud.zeolandia;

import java.util.ArrayList;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.text.Text;

import com.google.android.gms.drive.realtime.internal.BeginCompoundOperationRequest;
import com.mgl.base.ButtonListener;
import com.mgl.base.MyFactory;
import com.mgl.base.MyObjectGeneral;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.factory.ObjectFactorySingleton;
import com.mgl.drop.game.HUDManagerSingleton;
import com.mgl.drop.game.controller.DialogController;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.hud.InformativeHUD;
import com.mgl.drop.game.hud.MyHud;
import com.mgl.drop.game.hud.sprites.SpriteRemoveHud;
import com.mgl.drop.game.sprites.SpriteBackground;
import com.mgl.drop.game.sprites.azeoland.SpriteNPC;
import com.mgl.drop.game.sprites.button.ButtonGeneral;
import com.mgl.drop.texture.TextureSingleton;

public class DialogHUD extends MyHud {

	// private String textToShow;

	private DialogController dialogController;
	private SpriteNPC npc;

	private LevelController controller;

	public DialogHUD(DialogController dialogController, SpriteNPC npc,
			LevelController controller) {
		super();
		try {

			this.dialogController = dialogController;
			this.npc = npc;
			this.controller = controller;

			create();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void create() {

		try {
			TextureSingleton texture = TextureSingleton.getInstance();

			SpriteBackground background = new SpriteBackground(0, 0,
					texture.getTextureByName("black.jpg"),
					texture.getVertexBufferObjectManager());
			background.setAlpha(0.2f);
			background.setSize(MainDropActivity.CAMERA_WIDTH,
					MainDropActivity.CAMERA_HEIGHT);
			this.attachChild(background);

			SpriteBackground backgroundText = new SpriteBackground(0, 0,
					texture.getTextureByName("textBox.png"),
					texture.getVertexBufferObjectManager());
			backgroundText.setPosition(0, MainDropActivity.CAMERA_HEIGHT
					- backgroundText.getHeight());
			this.attachChild(backgroundText);

			SpriteBackground character = new SpriteBackground(0, 0,
					texture.getTextureByName("npc1Face.png"),
					texture.getVertexBufferObjectManager());
			character.setPosition(0,
					MainDropActivity.CAMERA_HEIGHT - character.getHeight());
			this.attachChild(character);

			addText(backgroundText);

			SpriteRemoveHud remove = new SpriteRemoveHud(0, 0,
					texture.getTextureByName("skip.png"),
					texture.getVertexBufferObjectManager(), this);
			remove.setPosition(
					MainDropActivity.CAMERA_WIDTH - remove.getWidth() - 2,
					backgroundText.getY() - remove.getHeight() / 2 - 10);
			remove.setSize(60, 60);
			this.attachChild(remove);
			this.registerTouchArea(remove);

			this.registerTouchArea(background);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void addText(SpriteBackground boxText) {
		try {

			DialogPaginator paginator = new DialogPaginator(this, boxText);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onCloseAction() {
		try {

			controller.setMustUpdate(true);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DialogController getDialogController() {
		return dialogController;
	}

	public void setDialogController(DialogController dialogController) {
		this.dialogController = dialogController;
	}

	public SpriteNPC getNpc() {
		return npc;
	}

	public void setNpc(SpriteNPC npc) {
		this.npc = npc;
	}

	public LevelController getController() {
		return controller;
	}

	public void setController(LevelController controller) {
		this.controller = controller;
	}

	public class DialogPaginator {

		private ArrayList<Text> textList;

		int beginPosition = 0;
		int endPosition = 0;

		private ButtonGeneral readMore;
		private ButtonGeneral buttonYes;
		private ButtonGeneral buttonNo;

		private DialogHUD dialogHud;
		private SpriteBackground background;

		TextureSingleton texture = TextureSingleton.getInstance();

		public DialogPaginator(DialogHUD dialogHUD2, SpriteBackground boxText) {
			try {

				this.dialogHud = dialogHUD2;
				this.background = boxText;
				beginPosition = 0;

				addText(beginPosition);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		private void addText(int beginPosition) {
			try {

				if (textList != null) {
					for (Text t : textList) {
						t.detachSelf();
					}
				}

				if (readMore != null) {
					readMore.detachSelf();
					// dialogHud.unregisterTouchArea(readMore);
				}

				if (buttonNo != null) {
					buttonNo.detachSelf();
				}

				if (buttonYes != null) {
					buttonYes.detachSelf();
				}

				textList = new ArrayList<Text>();

				int j = 0;
				String str = new String();

				boolean finishText = true;
				for (int i = this.beginPosition; i < dialogHud.dialogController
						.getText().length(); i++) {

					str = str + dialogHud.dialogController.getText().charAt(i);
					endPosition = i;
					if (str.length() >= 30) {
						Text text = ObjectFactorySingleton.getInstance()
								.createText(
										str,
										TextureSingleton.getInstance()
												.getmFont2());
						textList.add(text);
						str = new String();
						j++;
						if (j >= 4) {
							finishText = false;
							break;
						}
					}
					// finishText = true;
				}

				if (finishText) {
					Text text = ObjectFactorySingleton.getInstance()
							.createText(str,
									TextureSingleton.getInstance().getmFont2());
					textList.add(text);
				}

				j = 0;
				for (Text t : textList) {
					t.setPosition(background.getWidth() / 2, 12 + j * 15);
					background.attachChild(t);
					j++;
				}

				this.beginPosition = endPosition;

				if (finishText) {
					// add buttons yes no etc
					addFinishButtons();
				} else {
					// add button read more
					addButtonReadMore();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		private void addFinishButtons() {

			try {

				if (!dialogController.hasOption()) {
					return;
				}

				if (buttonNo == null) {
					buttonNo = new ButtonGeneral(0, 0,
							texture.getTextureByName("no.png"),
							texture.getVertexBufferObjectManager(),
							new ButtonListener() {

								@Override
								public void onActionUp(float x, float y) {
									//dialogController.actionNo();
									dialogController = dialogController
											.getDialogNo();
									beginPosition = 0;
									addText(beginPosition);
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
				}

				if (buttonYes == null) {
					buttonYes = new ButtonGeneral(0, 0,
							texture.getTextureByName("yes.png"),
							texture.getVertexBufferObjectManager(),
							new ButtonListener() {

								@Override
								public void onActionUp(float x, float y) {
									dialogController.actionYes();
									dialogController = dialogController
											.getDialogYes();
									beginPosition = 0;
									addText(beginPosition);
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
				}
				
				buttonNo.setPosition(
						background.getWidth()/2 +20,
						background.getHeight() - buttonNo.getHeight());
				
				buttonYes.setPosition(
						buttonNo.getX() + 5 + buttonNo.getWidth(),
						background.getHeight() - buttonYes.getHeight());
				
				background.attachChild(buttonNo);
				dialogHud.registerTouchArea(buttonNo);
				
				background.attachChild(buttonYes);
				dialogHud.registerTouchArea(buttonYes);

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		private void addButtonReadMore() {
			try {

				if (readMore == null) {
					readMore = new ButtonGeneral(0, 0,
							texture.getTextureByName("readMore.png"),
							texture.getVertexBufferObjectManager(),
							new ButtonListener() {

								@Override
								public void onActionUp(float x, float y) {
									addText(beginPosition);
									// readMore.detachSelf();

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
				}
				readMore.setSize(20, 20);
				readMore.setPosition(
						background.getWidth() - readMore.getWidth(),
						background.getHeight() - readMore.getHeight());
				background.attachChild(readMore);
				dialogHud.registerTouchArea(readMore);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
}
