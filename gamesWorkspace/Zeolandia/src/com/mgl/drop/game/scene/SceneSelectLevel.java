package com.mgl.drop.game.scene;

import java.util.ArrayList;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.text.Text;

import com.mgl.base.ButtonListener;
import com.mgl.base.TextFactory;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.game.HUDManagerSingleton;
import com.mgl.drop.game.constant.GameConstants;
import com.mgl.drop.game.constant.ZIndexGame;
import com.mgl.drop.game.controller.LevelController;
import com.mgl.drop.game.database.DatabaseDrop;
import com.mgl.drop.game.database.LevelDAO;
import com.mgl.drop.game.database.model.Level;
import com.mgl.drop.game.entity.azeolandia.EntityAppear;
import com.mgl.drop.game.entity.azeolandia.EntityMapCloud;
import com.mgl.drop.game.scene.SceneManagerSingleton.AllScenes;
import com.mgl.drop.game.sprites.SpriteBackground;
import com.mgl.drop.game.sprites.azeoland.SpriteAnimator;
import com.mgl.drop.game.sprites.azeoland.SpriteLevelSelect;
import com.mgl.drop.game.sprites.azeoland.SpriteLoadLevel;
import com.mgl.drop.game.sprites.azeoland.SpriteSharkMap;
import com.mgl.drop.game.sprites.button.ButtonGeneral;
import com.mgl.drop.texture.TextureSingleton;

public class SceneSelectLevel extends Scene{

	TextureSingleton texture = TextureSingleton.getInstance();
	
	private int fadeHeight = 50;
	
	private ArrayList<Level> levelList;
	private ArrayList<ButtonGeneral> levelSpriteList;
	
	private SpriteLevelSelect levelSelect ;
	private int levelPosition = 0;
	
	private SpriteBackground islandImage;
	
	private Text levelName;
	private Text islandName;
	private LevelController level;
	
	private EntityAppear entityAppear;
	
	public SceneSelectLevel(){
		try {
			
			level = new LevelController(this);
			level.createSelecLevel(this);
			
		//	createDefinitiveScene();
			
			updateScene();
			
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
					level.updateSelect(pSecondsElapsed);
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}

	}


	public void createDefinitiveScene() {
		try {
			
			entityAppear = new EntityAppear();
			level.addSpriteToUpdate(entityAppear);
			
			SpriteBackground background = new SpriteBackground(0, 0, texture.getTextureByName("backgroundMap.png"), texture.getVertexBufferObjectManager());
			background.setSize(MainDropActivity.CAMERA_WIDTH, MainDropActivity.CAMERA_HEIGHT);
			this.attachChild(background);
			
			entityAppear.getSpriteList().add(background);
			
			/*SpriteBackground fadeUp = new SpriteBackground(0, 0, texture.getTextureByName("black.jpg"), texture.getVertexBufferObjectManager());
			fadeUp.setSize(MainDropActivity.CAMERA_WIDTH, fadeHeight);
			fadeUp.setAlpha(0.5f);
			this.attachChild(fadeUp);
			*/
			
			SpriteBackground fadeDown = new SpriteBackground(0, 0, texture.getTextureByName("blackBar.png"), texture.getVertexBufferObjectManager());
			//fadeDown.setSize(MainDropActivity.CAMERA_WIDTH, fadeHeight);
			fadeDown.setY(MainDropActivity.CAMERA_HEIGHT-fadeDown.getHeight());
			fadeDown.setAlpha(0.7f);
			this.attachChild(fadeDown);
			fadeDown.setZIndex(ZIndexGame.FADE);
			
			
			levelSelect = new SpriteLevelSelect(0, 0, texture.getTextureByName("arrowMap.png"), texture.getVertexBufferObjectManager(),null);
			
			addEnterButton();
			
			loadLevelList();
			
			addNavigateButtons();
			
			initLevelPosition();
			
			initShip();
			
			initCloudEntity();
			
			initPupl();
			
			initShark();
			
			setTouchAreaBindingOnActionDownEnabled(true);
			setTouchAreaBindingOnActionMoveEnabled(true);
			
			entityAppear.autoSetAlpha();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initShark() {
		try {
			
			SpriteSharkMap shark = new SpriteSharkMap(0, 0, texture.getTextureByName("shark.png"),texture.getVertexBufferObjectManager(), level, new long[] {200,200,200});
			shark.setPosition(MainDropActivity.CAMERA_WIDTH -95, MainDropActivity.CAMERA_HEIGHT -60);
			shark.setSize(34, 34);
			shark.setDistanceToChange(15);
			this.attachChild(shark);
			shark.init(true);
			level.addSpriteToUpdate(shark);
			entityAppear.getSpriteList().add(shark);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void initPupl() {
		try {
			
			SpriteSharkMap pulp = new SpriteSharkMap(0, 0, texture.getTextureByName("pulp.png"), texture.getVertexBufferObjectManager(), level, new long[] { 200, 200, 200});
			this.attachChild(pulp);
			pulp.init(true);
			pulp.setSpeed(2);
			pulp.setDistanceToChange(15);
			pulp.setSize(30, 40);
			pulp.setPosition(MainDropActivity.CAMERA_WIDTH-140,35);
			level.addSpriteToUpdate(pulp);
			entityAppear.getSpriteList().add(pulp);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void initShip() {
		try {
			
			SpriteAnimator ship = new SpriteAnimator(0, 0, texture.getTextureByName("ship.png"), texture.getVertexBufferObjectManager(), level, new long[] { 250, 300, 250});
			this.attachChild(ship);
			ship.init(true);
			ship.setSize(40, 60);
			ship.setPosition(MainDropActivity.CAMERA_WIDTH-150,MainDropActivity.CAMERA_HEIGHT-120);
			
			entityAppear.getSpriteList().add(ship);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void initCloudEntity() {
		try {
			
			EntityMapCloud cloud = new EntityMapCloud();
			
			level.addSpriteToUpdate(cloud);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void initLevelPosition() {
		try {
			
			levelPosition = 0;
			ButtonGeneral level = levelSpriteList.get(levelPosition );
			
			levelSelect.setPosition(level.getX()+level.getWidth()/2 - levelSelect.getWidth()/2,level.getY()+level.getHeight()/2 - levelSelect.getHeight()/2);
			
			this.attachChild(levelSelect);
			
			navigate(0);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void addNavigateButtons() {
		try {
			
			
			
			ButtonGeneral next = new ButtonGeneral(0, 0, texture.getTextureByName("arrowRightMap.png") , texture.getVertexBufferObjectManager(), null);
			ButtonGeneral prev = new ButtonGeneral(0, 0, texture.getTextureByName("arrowLeftMap.png") , texture.getVertexBufferObjectManager(), null);
			
			next.setPressSprite("arrowRightPress.png");
			prev.setPressSprite("arrowLeftPress.png");
			
			prev.setZIndex(ZIndexGame.FADE);
			next.setZIndex(ZIndexGame.FADE);
			
			prev.setPosition(10,MainDropActivity.CAMERA_HEIGHT-prev.getHeight() -5);
			//next.setPosition(MainDropActivity.CAMERA_WIDTH - next.getWidth() -10 ,MainDropActivity.CAMERA_HEIGHT-next.getHeight() -5);
			next.setPosition(prev.getX()+prev.getWidth()+2,prev.getY());
			
			this.attachChild(next);
			this.attachChild(prev);
			this.registerTouchArea(next);
			this.registerTouchArea(prev);
			
			
			next.setButtonListener(new ButtonListener() {
				
				@Override
				public void onActionUp(float x, float y) {
					
					navigate(1);
					
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
			
			prev.setButtonListener(new ButtonListener() {
				
				@Override
				public void onActionUp(float x, float y) {
					navigate(-1);
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

	private void navigate(int dir) {
		try {
			
			int levelPrev = levelPosition;
			
			levelPosition +=dir;
			
			if(levelPosition>=levelSpriteList.size()){
				levelPosition = levelSpriteList.size()-1;
			}
			if(levelPosition<0){
				levelPosition = 0;
			}
			
			ButtonGeneral level = levelSpriteList.get(levelPosition );
			Level levelSel = levelList.get(levelPosition);
			
			if(!levelSel.getAvalible()){
				//sound not level avalible
				levelPosition = levelPrev;
				level = levelSpriteList.get(levelPosition );
				
			}
			
			
			levelSelect.setPosition(level.getX()+level.getWidth()/2 - levelSelect.getWidth()/2,level.getY()+level.getHeight()/2 - levelSelect.getHeight()/2 -10) ;
			
			if(levelName!=null){
				levelName.detachSelf();
			}
			
			levelName = TextFactory.createText(levelList.get(levelPosition ).getName(), texture.getmFont());
			levelName.setPosition(MainDropActivity.CAMERA_WIDTH/2 - levelName.getWidth()/2, MainDropActivity.CAMERA_HEIGHT - levelName.getHeight() -5);
			levelName.setColor(200/255f,30/255f,200/255f);
			levelName.setZIndex(ZIndexGame.FADE);
			this.attachChild(levelName);
			
			if(islandImage!=null){
				islandImage.detachSelf();
			}
			
			if(islandName!=null){
				islandName.detachSelf();
			}
			
			//ZINGO ISLAND
			if(levelPosition<=7){
				
				islandName = TextFactory.createText("ZINGO", texture.getmFont());
				islandImage = new SpriteBackground(10, 5 , texture.getTextureByName("Z.png"), texture.getVertexBufferObjectManager());
				//this.attachChild(islandImage);
				islandName.setZIndex(ZIndexGame.FADE);
			}
			
			
			islandName.setPosition(MainDropActivity.CAMERA_WIDTH/2 - islandName.getWidth()/2, MainDropActivity.CAMERA_HEIGHT - 40);
			this.attachChild(islandName);
			
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

			
			levelSpriteList = new ArrayList<ButtonGeneral>();
			
			int i = -1;
			
			int padding = 2;
			
			int col1 = 25;
			int col2 = col1+35+padding;
			int col3 = col2+35+padding;
			int col4 = col3+35+padding;
			int col5 = col4+35+padding;
			int col6 = col5+35+padding;
			int col7 = col6+35+padding;
			int col8 = col7+35+padding;
			int col9 = col8+35+padding;
			int col10 = col9+35+padding;
			
			int row1 = 70;
			int row2 = row1 +35 + padding;
			int row3 = row2 +35 + padding;
			
			
			
			for(Level l : levelList){
				
				switch (i) {
				
				case -1:
					
					levelSpriteList.add(createLevel(l,col1+35/2,row1/2-1));
					break;

				case 0:
					levelSpriteList.add(createLevel(l,col1,row1));
					break;

				case 1:
					levelSpriteList.add(createLevel(l,col2,row1));
					break;
				case 2:
					levelSpriteList.add(createLevel(l,col1,row2));
					break;
				case 3:
					levelSpriteList.add(createLevel(l,col2,row2));
					break;
				case 4:
					levelSpriteList.add(createLevel(l,col1,row3));
					break;
				case 5:
					levelSpriteList.add(createLevel(l,col2,row3));
					break;
				case 6:
					levelSpriteList.add(createLevel(l,col3,row3));
					break;
				case 7:
					levelSpriteList.add(createLevel(l,col4,row3));
					break;
				case 8:
					levelSpriteList.add(createLevel(l,col5,row3));
					break;
				case 9:
					levelSpriteList.add(createLevel(l,col6,row3));
					break;
				case 10:
					levelSpriteList.add(createLevel(l,col5,row2));
					break;
				case 11:
					levelSpriteList.add(createLevel(l,col6,row2));
					break;
				case 12:
					levelSpriteList.add(createLevel(l,col5,row1));
					break;
				case 13:
					levelSpriteList.add(createLevel(l,col6,row1));
					break;
				case 14:
					levelSpriteList.add(createLevel(l,col7,row3));
					break;
				case 15:
					levelSpriteList.add(createLevel(l,col8,row2));
					break;
				case 16:
					levelSpriteList.add(createLevel(l,col9,row2));
					break;
				case 17:
					levelSpriteList.add(createLevel(l,col10,row2));
					break;
				case 18:
					levelSpriteList.add(createLevel(l,col9,row1));
					break;
				case 19:
					levelSpriteList.add(createLevel(l,col9,row3));
					break;
				case 20:
					levelSpriteList.add(createLevel(l,col9,row1/2));
					break;
				case 21:
					levelSpriteList.add(createLevel(l,col9,row3+36));
					break;
					
				default:
					break;
				}
				
				i++;
				
			}
			
		for(ButtonGeneral b : levelSpriteList){
			this.attachChild(b);
		}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private ButtonGeneral createLevel(Level level, int x, int y) {
		
		try {
			
			ButtonGeneral buttonGeneral = new ButtonGeneral(x, y, texture.getTextureByName("placeMap1.png"), texture.getVertexBufferObjectManager(), null);
			buttonGeneral.setAlpha(0f);
			return buttonGeneral;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	private void addEnterButton() {
		ButtonGeneral enterButton = new ButtonGeneral(0, 0, texture.getTextureByName("enter.png"), texture.getVertexBufferObjectManager(), new ButtonListener() {
			
			@Override
			public void onActionUp(float x, float y) {
				try {
					
					
					SpriteLoadLevel loadLevel = new SpriteLoadLevel(0, 0, texture.getTextureByName("black.jpg"), texture.getVertexBufferObjectManager(),null);
					loadLevel.setScene(getScene());
					loadLevel.init(levelList.get(levelPosition).getIconName());
					loadLevel.setAutoUpdate();	
					
					
					
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
		
		enterButton.setPosition(MainDropActivity.CAMERA_WIDTH-10 - enterButton.getWidth() , MainDropActivity.CAMERA_HEIGHT-enterButton.getHeight() -5);
		enterButton.setPressSprite("enterPress.png");
		this.attachChild(enterButton);
		this.registerTouchArea(enterButton);
		enterButton.setZIndex(ZIndexGame.FADE);
	}
	
	public SceneSelectLevel getScene(){
		return this;
	}

	public void play() {
		try {
			
			SceneManagerSingleton sceneManager = SceneManagerSingleton
					.getInstance();
			
			sceneManager.createGameScene(levelList.get(levelPosition), levelList,
					GameConstants.PLAY_NORMAL);
			sceneManager.setCurrentScene(AllScenes.GAME_BEGIN);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
