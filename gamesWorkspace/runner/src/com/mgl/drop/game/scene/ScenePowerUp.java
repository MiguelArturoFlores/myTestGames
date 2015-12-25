package com.mgl.drop.game.scene;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;

import com.mgl.base.userinfo.PoolObjectSingleton;
import com.mgl.base.userinfo.UserInfoSingleton;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.factory.ObjectFactorySingleton;
import com.mgl.drop.game.constant.GameConstants;
import com.mgl.drop.game.constant.ZIndexGame;
import com.mgl.drop.game.database.DatabaseDrop;
import com.mgl.drop.game.database.LevelDAO;
import com.mgl.drop.game.objects.button.ButtonMoney;
import com.mgl.drop.game.sprites.SpriteBackground;
import com.mgl.drop.game.sprites.SpriteMoneyFree;
import com.mgl.drop.game.sprites.arunner.SpritePlayerModel;
import com.mgl.drop.game.sprites.arunner.SpritePowerSelect;
import com.mgl.drop.game.sprites.arunner.SpriteUpgradePower;
import com.mgl.drop.texture.TextureSingleton;

public class ScenePowerUp extends Scene{
	TextureSingleton texture = TextureSingleton.getInstance();
	
	private SpritePlayerModel playerSelected;

	public ScenePowerUp(){
		try {
			
			/*LevelDAO dao = new LevelDAO(SceneManagerSingleton.getInstance()
					.getActivity(), DatabaseDrop.DB_NAME, null,
					MainDropActivity.DB_VERSION);
			*/

			Sprite background = new Sprite(0, 0,
					texture.getTextureByName("backgroundW2.png"),
					texture.getVertexBufferObjectManager());
			this.attachChild(background);
			
			Text title = ObjectFactorySingleton.getInstance().createText(
					"EVOLUTION", texture.getmFont1());
			title.setPosition(200,50);
			this.attachChild(title);
			
			initRightPart();
			
			
			
			int offset = 50;
			
			SpritePowerSelect powerShot = new SpritePowerSelect(0, 0,60,60, texture.getTextureByName("shotIcon.png"), texture.getVertexBufferObjectManager(),null,UserInfoSingleton.getInstance().getPowerA(),GameConstants.POWER_A);
			powerShot.setPosition(50,150);
			powerShot.setZIndex(ZIndexGame.FADE);
			this.attachChild(powerShot);
			
			SpritePowerSelect powerAccelerate = new SpritePowerSelect (0, 0, 60,60,texture.getTextureByName("accelerateIcon.png"), texture.getVertexBufferObjectManager(),null,UserInfoSingleton.getInstance().getPowerB(),GameConstants.POWER_B);
			powerAccelerate.setPosition(50,powerShot.getY()+powerShot.getHeight() + offset);
			powerAccelerate.setZIndex(ZIndexGame.FADE);
			this.attachChild(powerAccelerate);
			
			SpritePowerSelect powerIce = new SpritePowerSelect (0, 0,60,60, texture.getTextureByName("iceIcon.png"), texture.getVertexBufferObjectManager(),null,UserInfoSingleton.getInstance().getPowerC(),GameConstants.POWER_C);
			powerIce.setPosition(50,powerAccelerate.getY()+powerAccelerate.getHeight() + offset);
			powerIce.setZIndex(ZIndexGame.FADE);
			this.attachChild(powerIce);
			
			addPowerShotPowerUp(powerShot,5,7);
			addPowerShotPowerUp(powerAccelerate,5,7);
			addPowerShotPowerUp(powerIce,5,7);
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initPlayerSelect() {

		try {
			
			if(playerSelected!=null){
				playerSelected.detachSelf();
			}
			
			playerSelected = PoolObjectSingleton.getInstance().getPlayerModelSelected();
			playerSelected.setSize(150, 150);
			playerSelected.setPosition(MainDropActivity.CAMERA_WIDTH-200+10, 180);
			
			this.attachChild(playerSelected);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initRightPart() {
		try {
			
			SpriteBackground background = new SpriteBackground(0, 0, texture.getTextureByName("black.jpg"), texture.getVertexBufferObjectManager());
			background.setSize(250, MainDropActivity.CAMERA_HEIGHT);
			background.setPosition(MainDropActivity.CAMERA_WIDTH-background.getWidth(),0);
			background.setAlpha(0.65f);
			this.attachChild(background);
			
			ButtonMoney money = new ButtonMoney(0, 5,
					texture.getTextureByName("buttonTexture.png"),
					texture.getVertexBufferObjectManager());
			money.setPosition(MainDropActivity.CAMERA_WIDTH-150,5);
			
			this.attachChild(money);
			this.registerTouchArea(money);
			
			int offset = 10;
			
			SpritePowerSelect powerShot = new SpritePowerSelect(0, 0,60,60, texture.getTextureByName("shotIcon.png"), texture.getVertexBufferObjectManager(),null,UserInfoSingleton.getInstance().getPowerA(),GameConstants.POWER_A);
			powerShot.setSize(30, 30);
			powerShot.setPosition(MainDropActivity.CAMERA_WIDTH -background.getWidth() +10,MainDropActivity.CAMERA_HEIGHT/2 +100);
			powerShot.setZIndex(ZIndexGame.FADE);
			
			SpritePowerSelect powerAccelerate = new SpritePowerSelect (0, 0, 60,60,texture.getTextureByName("accelerateIcon.png"), texture.getVertexBufferObjectManager(),null,UserInfoSingleton.getInstance().getPowerB(),GameConstants.POWER_B);
			powerAccelerate.setSize(30, 30);
			powerAccelerate.setPosition(powerShot.getX(),powerShot.getY()+powerShot.getHeight() + offset);
			powerAccelerate.setZIndex(ZIndexGame.FADE);
			
			SpritePowerSelect powerIce = new SpritePowerSelect (0, 0,60,60, texture.getTextureByName("iceIcon.png"), texture.getVertexBufferObjectManager(),null,UserInfoSingleton.getInstance().getPowerC(),GameConstants.POWER_C);
			powerIce.setSize(30, 30);
			powerIce.setPosition(powerAccelerate.getX(),powerAccelerate.getY()+powerAccelerate.getHeight() + offset);
			powerIce.setZIndex(ZIndexGame.FADE);
			
			addRectangleToPower(powerShot);
			addRectangleToPower(powerIce);
			addRectangleToPower(powerAccelerate);
			
			this.attachChild(powerShot);
			this.attachChild(powerIce);
			this.attachChild(powerAccelerate);

			
			initCharacter();
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initCharacter() {
		try {
			
			int offset = 15;
			
			SpritePlayerModel playerASmall = new SpritePlayerModel(0, 0,
					texture.getTextureAnimateByName("virusAModel.png"),
					texture.getVertexBufferObjectManager(), null);
			playerASmall.setSize(50, 50);
			playerASmall.setUpgrade(false);
			playerASmall.setPosition(MainDropActivity.CAMERA_WIDTH-200 +10,100);
			playerASmall.setPlayerSelcet(GameConstants.PLAYER_A);
			this.registerTouchArea(playerASmall);
			this.attachChild(playerASmall);
			
			SpritePlayerModel playerBSmall = new SpritePlayerModel(0, 0,
					texture.getTextureAnimateByName("virusBModel.png"),
					texture.getVertexBufferObjectManager(), null);
			playerBSmall.setSize(50, 50);
			playerBSmall.setPosition(playerASmall.getX()+playerASmall.getWidth()+offset,100);
			playerBSmall.setUpgrade(false);
			playerBSmall.setPlayerSelcet(GameConstants.PLAYER_B);
			this.registerTouchArea(playerBSmall);
			this.attachChild(playerBSmall);
			
			SpritePlayerModel playerCSmall = new SpritePlayerModel(0, 0,
					texture.getTextureAnimateByName("virusCModel.png"),
					texture.getVertexBufferObjectManager(), null);
			playerCSmall.setSize(50, 50);
			playerCSmall.setPosition(playerBSmall.getX()+playerBSmall.getWidth()+offset,100);
			playerCSmall.setUpgrade(false);
			playerCSmall.setPlayerSelcet(GameConstants.PLAYER_C);
			this.registerTouchArea(playerCSmall);
			this.attachChild(playerCSmall);
			
			
			initPlayerSelect();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void addRectangleToPower(SpritePowerSelect power) {
		try {
			
			int powerQ = 0;
			
			String textureName = "adnR.png";
			if(power.getPowerType() == GameConstants.POWER_A){
				powerQ = UserInfoSingleton.getInstance().getPowerA();
			}
			if(power.getPowerType() == GameConstants.POWER_B){
				powerQ = UserInfoSingleton.getInstance().getPowerB();
				textureName = "adnB.png";
			}
			if(power.getPowerType() == GameConstants.POWER_C){
				powerQ = UserInfoSingleton.getInstance().getPowerC();
				textureName = "adnG.png";
			}
			
			for(int  i= 0; i<powerQ; i ++){
				
				SpriteBackground rect = new SpriteBackground(0, 0, texture.getTextureByName(textureName), texture.getVertexBufferObjectManager());
				rect.setPosition(power.getX()+power.getWidth()-5 +i*rect.getWidth() ,power.getY()+5);
				//rect.setPosition(500+ i*50,300);
				//rect.setZIndex(ZIndexGame.FADE);
				this.attachChild(rect);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void addPowerShotPowerUp(SpritePowerSelect powerSelect, int quatity, int total ) {
		try {
			
			TextureSingleton texture = TextureSingleton.getInstance();
			
			int levelPower = powerSelect.getLevelQ();
			
			String textureName = "adnM1.png";
			int currentLevel = powerSelect.getLevelQ();
			
			if(powerSelect.getPowerType() == GameConstants.POWER_B){
				textureName = "adnM1.png";
			}
			if(powerSelect.getPowerType() == GameConstants.POWER_B){
				textureName = "adnM2.png";
			}
			if(powerSelect.getPowerType() == GameConstants.POWER_B){
				textureName = "adnM3.png";
			}
			
			for(int i = 1 ; i <= currentLevel; i++){
				SpriteBackground adnM = new SpriteBackground(0, 0, texture.getTextureByName(textureName), texture.getVertexBufferObjectManager());
				adnM.setPosition(90 + i*adnM.getWidth(),powerSelect.getY() );
				this.attachChild(adnM);
			}
			
			for(int i = 1 ; i <= currentLevel; i++){
				
				float y = powerSelect.getY();
				if(i%2==0){
					y=y+20;
				}
				
				SpriteUpgradePower upgradePower = new SpriteUpgradePower(0, 0,texture.getTextureByName("money.png") , texture.getVertexBufferObjectManager(),i+1, powerSelect);
				
				if(i<quatity){
					
					upgradePower.setSize(30, 30);
					if(levelPower == i){
						upgradePower.setCanUpgrade(true);	
					}if(levelPower>i){
						upgradePower.setAlreadyUpdated(true);
					}
					upgradePower.setPosition(105 + i*58, y);
					this.attachChild(upgradePower);
					this.registerTouchArea(upgradePower);
					
				}else{
					 
					upgradePower.setSize(50, 50);
					upgradePower.setNotAvalible(true);
					upgradePower.setPosition(100 + i*58, y);
					this.attachChild(upgradePower);
					this.registerTouchArea(upgradePower);
				}
				
				upgradePower.setPrice(i*50);
			}
			
			if(true){
				return;
			}
			
			//level1
			SpriteUpgradePower upgradePower = new SpriteUpgradePower(0, 0,texture.getTextureByName("cadiz.png") , texture.getVertexBufferObjectManager(),2, powerSelect); 
			upgradePower.setSize(50, 50);
			if(levelPower == 1){
				upgradePower.setCanUpgrade(true);	
			}if(levelPower>1){
				upgradePower.setAlreadyUpdated(true);
			}
			upgradePower.setPosition(150, powerSelect.getY());
			this.attachChild(upgradePower);
			this.registerTouchArea(upgradePower);
			
			//level2
			upgradePower = new SpriteUpgradePower(0, 0,texture.getTextureByName("cadiz.png") , texture.getVertexBufferObjectManager(),3, powerSelect); 
			upgradePower.setSize(50, 50);
			if(levelPower == 2){
				upgradePower.setCanUpgrade(true);	
			}if(levelPower>2){
				upgradePower.setAlreadyUpdated(true);
			}
			upgradePower.setPosition(250, powerSelect.getY()+50);
			this.attachChild(upgradePower);
			this.registerTouchArea(upgradePower);
			
			//level3
			upgradePower = new SpriteUpgradePower(0, 0,texture.getTextureByName("cadiz.png") , texture.getVertexBufferObjectManager(),4, powerSelect); 
			upgradePower.setSize(50, 50);
			if(levelPower == 3){
				upgradePower.setCanUpgrade(true);	
			}
			if(levelPower>3){
				upgradePower.setAlreadyUpdated(true);
			}
			upgradePower.setPosition(350, powerSelect.getY()+0);
			this.attachChild(upgradePower);
			this.registerTouchArea(upgradePower);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void changePlayerSelected(SpritePlayerModel spritePlayerModel) {
		try {
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	
}
