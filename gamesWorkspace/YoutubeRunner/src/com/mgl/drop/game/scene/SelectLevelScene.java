package com.mgl.drop.game.scene;


import java.util.ArrayList;

import java.util.HashMap;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.util.color.Color;

import android.util.Log;

import com.mgl.base.publicity.PublicityManagerSingleton;
import com.mgl.base.userinfo.UserInfoSingleton;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.factory.ObjectFactorySingleton;
import com.mgl.drop.game.constant.ZIndexGame;
import com.mgl.drop.game.controller.SelectLevelController;
import com.mgl.drop.game.database.DatabaseDrop;
import com.mgl.drop.game.database.LevelDAO;
import com.mgl.drop.game.database.model.Level;
import com.mgl.drop.game.objects.button.ButtonMoney;
import com.mgl.drop.game.sprites.SpriteBackground;
import com.mgl.drop.game.sprites.SpriteSelectLevel;
import com.mgl.drop.game.sprites.button.ButtonGoBeginGame;
import com.mgl.drop.game.sprites.button.ButtonShareFacebook;
import com.mgl.drop.game.sprites.button.ButtonShop;
import com.mgl.drop.game.sprites.button.ButtonShowUnlockLevelHUD;
import com.mgl.drop.game.sprites.interfaz.SpriteNavigateNext;
import com.mgl.drop.texture.TextureSingleton;

public class SelectLevelScene extends Scene {

	private static final int MAX_LEVEL_SCREEN = 18;
	private ObjectFactorySingleton objectFactorySingleton = ObjectFactorySingleton
			.getInstance();
	private TextureSingleton texture = TextureSingleton.getInstance();

	private int CAMERA_MAX_WIDTH = 2000;
	private int CAMERA_MAX_HEIGHT = 2000;

	private float time = 0;

	private ArrayList<Level> levelList;
	private ArrayList<SpriteSelectLevel> levelSpriteList;

	private ArrayList<Level> levelCurrentList;

	private Integer currentPage = 0;
	private Integer maxPage = 0;
	private HashMap<Integer, ArrayList<Level>> levelHash;

	private SelectLevelController controller;

	public SelectLevelScene() {
		super();
		try {

			levelList = new ArrayList<Level>();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Scene createScene() {
		try {
			
			PublicityManagerSingleton.getInstance().showBanner();
			PublicityManagerSingleton.getInstance().showIntersitial();
			loadLevelList();

			/*
			 * Sprite background = objectFactorySingleton
			 * .createObject(ObjectType.LEVEL_SELECT_BACKGROUND);
			 * background.setSize(2000, 2000);
			 */

			Sprite background = new Sprite(0, 0,
					texture.getTextureByName("selecLevelBackground.png"),
					texture.getVertexBufferObjectManager());
			this.attachChild(background);

			controller = new SelectLevelController(this);

			calculateCurrentLevel();

			initNavigationButtons();

			navigate();

			ButtonMoney money = UserInfoSingleton.getInstance().getButtonMoney();
			this.attachChild(money);
			this.registerTouchArea(money);

			ButtonShop shop = new ButtonShop(
					MainDropActivity.CAMERA_WIDTH - 70, 5,
					texture.getTextureAnimateByName("shop.png"),
					texture.getVertexBufferObjectManager(), null);

			shop.setZIndex(ZIndexGame.FADE);
			shop.setSize(60, 60);
			shop.setPosition(MainDropActivity.CAMERA_WIDTH-shop.getWidth()-5,5);
			this.attachChild(shop);
			this.registerTouchArea(shop);

			updateScene();

			return this;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void navigate() {
		try {

			int i = 0;
			int j = 0;

			if (levelSpriteList != null) {
				for (SpriteSelectLevel spr : levelSpriteList) {
					spr.setPosition(-100, -100);
					this.detachChild(spr);

				}
			}

			levelSpriteList = new ArrayList<SpriteSelectLevel>();

			if (currentPage <= 0) {
				currentPage = 0;
			}
			if (currentPage >= maxPage) {
				currentPage = maxPage-1;
			}
			if (levelHash.get(currentPage) == null) {
				return;
			}

			for (Level level : levelHash.get(currentPage)) {

				Long px = Long.valueOf(((i * 115) + 60));
				Long py = 120L * j + 80;

				addLevelImage(level, px, py);

				i++;

				if (i >= 6) {
					i = 0;
					j++;

				}

			}

			sortChildren();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void initNavigationButtons() {
		try {

			SpriteNavigateNext sprite = new SpriteNavigateNext(5,
					MainDropActivity.CAMERA_HEIGHT - 50,
					texture.getTextureByName("prev.png"),
					texture.getVertexBufferObjectManager(), this, false);
			sprite.setSize(50, 50);

			sprite.setPosition(5, MainDropActivity.CAMERA_HEIGHT - 150);
			this.attachChild(sprite);
			this.registerTouchArea(sprite);

			SpriteNavigateNext sprite2 = new SpriteNavigateNext(
					MainDropActivity.CAMERA_WIDTH - 50,
					MainDropActivity.CAMERA_HEIGHT - 150,
					texture.getTextureByName("next.png"),
					texture.getVertexBufferObjectManager(), this, true);
			sprite2.setSize(50, 50);
			this.attachChild(sprite2);
			this.registerTouchArea(sprite2);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void addLevelImage(Level level, Long px, Long py) {
		try {

			String textureName = "levelFeatherLocked.png";
			if (level.getAvalible() && !level.getFacebook()) {
				textureName = "levelFeather.png";
			} else if (level.getAvalible() && level.getFacebook()) {
				textureName = "levelBonus.png";
			} else if (!level.getAvalible() && level.getFacebook()) {
				textureName = "levelBonusLocked.png";
			} else if (!level.getAvalible() && !level.getFacebook()) {
				textureName = "levelFeatherLocked.png";
			}

			int val = (int) (1 + ((Math.random() * 1000000) % 4));

			textureName = "tomb" + val + ".png";

			SpriteSelectLevel selectSprite = new SpriteSelectLevel(px, py,
					TextureSingleton.getInstance()
							.getTextureByName(textureName), TextureSingleton
							.getInstance().getVertexBufferObjectManager(),
					level, levelList);

			selectSprite.setPosition(px, py);
			selectSprite.setSize(95, 95);
			// selectSprite.setAlpha(50);
			this.attachChild(selectSprite);

			int yStar = -20;

			Sprite star = new Sprite(0, 0,
					texture.getTextureByName("emptyStar.png"),
					texture.getVertexBufferObjectManager());
			star.setSize(23, 27);
			star.setPosition(5, selectSprite.getHeight() + yStar);
			selectSprite.attachChild(star);

			star = new Sprite(0, 0, texture.getTextureByName("emptyStar.png"),
					texture.getVertexBufferObjectManager());
			star.setSize(23, 27);
			star.setPosition(10 + star.getWidth(), selectSprite.getHeight()
					+ yStar);
			selectSprite.attachChild(star);

			star = new Sprite(0, 0, texture.getTextureByName("emptyStar.png"),
					texture.getVertexBufferObjectManager());
			star.setSize(23, 27);
			star.setPosition(15 + star.getWidth() * 2, selectSprite.getHeight()
					+ yStar);
			selectSprite.attachChild(star);

			if (!level.getAvalible() && level.getFacebook()) {
				selectSprite.getLevelDB().setName("");
			} else if (!level.getAvalible() && !level.getFacebook()) {
				selectSprite.getLevelDB().setName("");
			}
			if (!level.getAvalible()) {
				SpriteBackground lock = new SpriteBackground(0, 0,
						texture.getTextureByName("lock.png"),
						texture.getVertexBufferObjectManager());
				lock.setSize(
						Float.valueOf((float) (selectSprite.getWidth() * 0.6)),
						Float.valueOf((float) (selectSprite.getHeight() * 0.6)));
				lock.setPosition(selectSprite.getWidth() / 2 - lock.getWidth()
						/ 2, selectSprite.getHeight() / 2 - lock.getHeight()
						/ 2);
				selectSprite.attachChild(lock);
			}

			Text textName = objectFactorySingleton.createText(selectSprite
					.getLevelDB().getName(), texture.getmFont1());
			textName.setColor(Color.BLACK);
			textName.setPosition(
					selectSprite.getWidth() / 2 - textName.getWidth() / 2,
					selectSprite.getHeight() / 2 - textName.getHeight() / 2);
			selectSprite.attachChild(textName);

			for (int i = 0; i < selectSprite.getLevelDB().getStars(); i++) {
				star = new Sprite(0, 0,
						texture.getTextureByName("fillStar.png"),
						texture.getVertexBufferObjectManager());
				star.setSize(23, 27);
				star.setPosition(5 + (i * 5) + star.getWidth() * (i),
						selectSprite.getHeight() + yStar);
				selectSprite.attachChild(star);
			}

			// selectSprite.attachChild(textStars);

			levelSpriteList.add(selectSprite);
			if (level.getAvalible()) {
				this.registerTouchArea(selectSprite);
			} else {
				ButtonShowUnlockLevelHUD stu = new ButtonShowUnlockLevelHUD(0,
						0, texture.getTextureByName("unlockFacebook.png"),
						texture.getVertexBufferObjectManager(), level);
				stu.setSize(50, 50);
				stu.setPosition(0, 0);
				// selectSprite.attachChild(stu);
				// registerTouchArea(stu);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void loadLevelList() {
		try {

			LevelDAO dao = new LevelDAO(SceneManagerSingleton.getInstance()
					.getActivity(), DatabaseDrop.DB_NAME, null,
					MainDropActivity.DB_VERSION);
			levelList = dao.loadLevelList();
			Log.d("LevelList Size ", levelList.size() + "");
			initHashLevel();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void initHashLevel() {
		try {

			int cont = 0;
			levelHash = new HashMap<Integer, ArrayList<Level>>();
			levelCurrentList = new ArrayList<Level>();
			Integer currentAux = 0;
			for (Level level : levelList) {

				levelCurrentList.add(level);
				if (level.getAvalible()) {
					currentAux = currentPage;
				}

				cont++;
				if (cont >= MAX_LEVEL_SCREEN) {
					levelHash.put(currentPage, levelCurrentList);
					levelCurrentList = new ArrayList<Level>();
					maxPage = currentPage;
					currentPage++;
					cont = 0;
				}

			}
			if (cont < MAX_LEVEL_SCREEN) {
				levelHash.put(currentPage, levelCurrentList);
				levelCurrentList = new ArrayList<Level>();
				maxPage = currentPage;
			}

			Log.d("levelQuantity", "current " + currentPage + " max " + maxPage);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void calculateCurrentLevel() {
		try {
			currentPage = 0;
			int cont = 0;
			for (Level level : levelList) {

				if (cont >= MAX_LEVEL_SCREEN && level.getAvalible()) {
					currentPage++;
					cont = 0;
				}
				cont++;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void updateScene() {
		try {

			this.registerUpdateHandler(new IUpdateHandler() {
				@Override
				public void reset() {
				}

				@Override
				public void onUpdate(float pSecondsElapsed) {
					// HERE IS THE GAME LOOP
					time += pSecondsElapsed;
					// System.out.println("this is the time elapsed MAIN SCENE: "+time);
					if (time >= 0.1) {
						controller.update(pSecondsElapsed, getScene());
					}
					// level.update(pSecondsElapsed, level);
					// game.update(pSecondsElapsed);
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public Scene getScene() {
		return this;
	}

	/*
	 * private String readFromFile() { try { SceneManagerSingleton sceneManager
	 * = SceneManagerSingleton .getInstance();
	 * 
	 * String displayText = ""; InputStream fileStream =
	 * sceneManager.getActivity().getResources() .openRawResource(R.raw.level1);
	 * 
	 * int fileLen = fileStream.available();
	 * 
	 * // Read the entire resource into a local byte buffer. byte[] fileBuffer =
	 * new byte[fileLen]; fileStream.read(fileBuffer); fileStream.close();
	 * displayText = new String(fileBuffer);
	 * 
	 * return displayText; } catch (IOException e) { // exception handling
	 * return ""; }
	 * 
	 * }
	 */
	public ObjectFactorySingleton getObjectFactorySingleton() {
		return objectFactorySingleton;
	}

	public void setObjectFactorySingleton(
			ObjectFactorySingleton objectFactorySingleton) {
		this.objectFactorySingleton = objectFactorySingleton;
	}

	public TextureSingleton getTexture() {
		return texture;
	}

	public void setTexture(TextureSingleton texture) {
		this.texture = texture;
	}

	public int getCAMERA_MAX_WIDTH() {
		return CAMERA_MAX_WIDTH;
	}

	public void setCAMERA_MAX_WIDTH(int cAMERA_MAX_WIDTH) {
		CAMERA_MAX_WIDTH = cAMERA_MAX_WIDTH;
	}

	public int getCAMERA_MAX_HEIGHT() {
		return CAMERA_MAX_HEIGHT;
	}

	public void setCAMERA_MAX_HEIGHT(int cAMERA_MAX_HEIGHT) {
		CAMERA_MAX_HEIGHT = cAMERA_MAX_HEIGHT;
	}

	public ArrayList<Level> getLevelList() {
		return levelList;
	}

	public void setLevelList(ArrayList<Level> levelList) {
		this.levelList = levelList;
	}

	public HashMap<Integer, ArrayList<Level>> getLevelHash() {
		return levelHash;
	}

	public void setLevelHash(HashMap<Integer, ArrayList<Level>> levelHash) {
		this.levelHash = levelHash;
	}

	public ArrayList<Level> getLevelCurrentList() {
		return levelCurrentList;
	}

	public void setLevelCurrentList(ArrayList<Level> levelCurrentList) {
		this.levelCurrentList = levelCurrentList;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

}
