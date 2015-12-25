package com.mgl.drop.game.controller;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import org.andengine.entity.IEntity;
import org.andengine.entity.scene.ITouchArea;

import android.util.Log;

import com.mgl.base.MyEntity;
import com.mgl.base.MyEntitySmasher;
import com.mgl.base.MySprite;
import com.mgl.base.MySpriteGeneral;
import com.mgl.base.SpriteType;
import com.mgl.base.SpriteTypeConstant;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.factory.SoundSingleton;
import com.mgl.drop.game.constant.GameConstants;
import com.mgl.drop.game.database.DatabaseDrop;
import com.mgl.drop.game.database.LevelDAO;
import com.mgl.drop.game.entity.EntityFinalWave;
import com.mgl.drop.game.entity.Wave;
import com.mgl.drop.game.scene.GameScene;
import com.mgl.drop.game.scene.SceneManagerSingleton;
import com.mgl.drop.game.sprites.SpriteMonsterBasic;
import com.mgl.drop.texture.TextureSingleton;

public class WaveController extends MyEntity {

	private ArrayList<Wave> waveList;
	private float beginTime = 0;

	private TextureSingleton texture = TextureSingleton.getInstance();
	private int currentWave = 1;
	private int totalWave = 0;

	private boolean win = false;
	private int gameType;

	private int contWave = 0;
	private int contLevel = 1;

	private Wave currentWaveToRestart = null;
	private boolean isShowFinalWave = false;

	public WaveController(Long idLevel, LevelController lc, int gameType) {
		try {
			this.gameType = gameType;
			beginTime = 0;
			if (gameType == GameConstants.PLAY_NORMAL) {
				loadWaveList(lc, idLevel);
			} else {
				generateRandomWave();
				contLevel = 1;
			}

			totalWave = waveList.size();
			isShowFinalWave = false;

			lc.getLevelHud().changeWaveText(currentWave + "/" + totalWave);

			win = false;

			generateDiamant();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void generateDiamant() {
		try {
			int cont = 0;
			for (Wave w : waveList) {
				for (MyEntitySmasher ms : w.getSpriteList()) {
					cont++;
					if (cont >= GameConstants.MONSTER_TO_DIAMANT) {
						ms.setDiamant(GameConstants.DIAMANT_MONSTER);
						cont = 0;
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void generateRandomWave() {
		try {
			
			
			
			if (contWave <= 4) {
				
				int idLevel= MainDropActivity.getRandomMax(3, 5);
				waveList = Wave.generateWaveByLevel(Long.valueOf(idLevel));
			} else if (contWave <= 10) {
				
				int idLevel= MainDropActivity.getRandomMax(6, 10);
				waveList = Wave.generateWaveByLevel(Long.valueOf(idLevel));
				
			} else if (contWave <= 16) {
				
				int idLevel= MainDropActivity.getRandomMax(11, 16);
				waveList = Wave.generateWaveByLevel(Long.valueOf(idLevel));
				
			}else {
				
				int idLevel= MainDropActivity.getRandomMax(14, 32);
				waveList = Wave.generateWaveByLevel(Long.valueOf(idLevel));
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void loadWaveList(LevelController lc, Long idLevel) {
		try {

			LevelDAO dao = new LevelDAO(SceneManagerSingleton.getInstance()
					.getActivity(), DatabaseDrop.DB_NAME, null,
					MainDropActivity.DB_VERSION);

			waveList = dao.loadWaveList(idLevel);

			contWave = 0;

			waveList.get(waveList.size() - 1).setLast(true);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public ArrayList<Wave> getWaveList() {
		return waveList;
	}

	public void setWaveList(ArrayList<Wave> waveList) {
		this.waveList = waveList;
	}

	public void setMustUpdate(boolean mustUpdate) {

		this.mustUpdate = mustUpdate;
	}

	@Override
	public float getTime() {

		return 0;
	}

	@Override
	public void updateChild(float dTime, LevelController controller) {
		try {

			if(gameType == GameConstants.PLAY_SURVIVAL){
				updateSurvival(dTime,controller);
				return;
			}
			beginTime = beginTime + dTime;

			ArrayList<Wave> waveToRemove = new ArrayList<Wave>();

			for (Wave w : waveList) {

				if (gameType != GameConstants.PLAY_NORMAL && w.isFinish()
						&& w.getSpriteList().isEmpty()) {
					generateRandomWave();
				}

				if (w.isFinish()) {
					// wave is not active remove
					//waveToRemove.add(w);
				}

				if (w.isActive()) {
					currentWaveToRestart = w;

					// contWave++;

					if (w.isLast() && w.isFinish()
							&& !stillMonsterAlive(controller) && !win) {
						controller.winLevelShowScore();
						if (gameType == GameConstants.PLAY_NORMAL) {
							win = true;
						}

					}

					continue;
				}
				// System.out.println("begin time "+beginTime+
				// " w "+w.getBeginTime() );
				if (beginTime >= w.getBeginTime().floatValue() && !w.isFinish()) {
					w.setActive(true);
					contWave++;
					validateFinalWave(controller);
					w.setTextToChange(contWave + "/" + totalWave);
					controller.addSpriteToUpdate(w);
				}
			}

			waveList.removeAll(waveToRemove);

			/*
			 * if(contWave>currentWave){ currentWave = contWave;
			 * controller.getLevelHud
			 * ().changeWaveText(currentWave+"/"+totalWave); }
			 */

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void updateSurvival(float dTime, LevelController controller) {

		try {
			ArrayList<Wave> waveToRemove = new ArrayList<Wave>();
			
			beginTime = beginTime + dTime;
			int timeToBegin = 0;
			
			boolean genAnotherWave = false;
			
			if(waveList.isEmpty()){
				generateRandomWave();
			}
			
			for (Wave w : waveList) {

				if (w.isFinish()) {
					// wave is not active remove
					//waveToRemove.add(w);
				}

				if (w.isActive()) {
					currentWaveToRestart = w;

					// contWave++;

					if (w.isLast() && w.isFinish()
							&& !stillMonsterAlive(controller) && !win) {
						//TODO change text level controller.winLevelShowScore();
						genAnotherWave = true;

					}

					continue;
				}
				// System.out.println("begin time "+beginTime+
				// " w "+w.getBeginTime() );
				if (beginTime >= w.getBeginTime().floatValue() && !w.isFinish()) {
					w.setActive(true);
					contWave++;
					//validateFinalWave(controller);
					
					w.setTextToChange(contWave + "/" + totalWave);
					controller.addSpriteToUpdate(w);
					timeToBegin = w.getDuration();
					if(contWave == totalWave){
						genAnotherWave = true;
					}
				}
			}

			waveList.removeAll(waveToRemove);
			if(genAnotherWave){
				generateRandomWave();
				generateDiamant();
				totalWave = totalWave + waveList.size();
				genAnotherWave = false;
				beginTime = 0 - timeToBegin;
				showLevelNumber(controller);
				
			}
			
		} catch (Exception e) {

			e.printStackTrace();
			
		}
		
	}

	private void showLevelNumber(LevelController level) {
		try {
			
			EntityFinalWave finalWave = new EntityFinalWave(200, 200, level,false,"LEVEL  "+contLevel);
			level.addSpriteToUpdate(finalWave);
			contLevel++;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void validateFinalWave(LevelController level) {
		try {

			if(contWave == totalWave && !isShowFinalWave){
				isShowFinalWave = true;
				SoundSingleton.getInstance().playSound("finalWave.mp3");
				EntityFinalWave finalWave = new EntityFinalWave(220, 200, level,true,"FINAL WAVE");
				level.addSpriteToUpdate(finalWave);
			}
			
		} catch (Exception e) {

			e.printStackTrace();
		}

	}
	
	private boolean stillMonsterAlive(LevelController controller) {
		try {

			for (MySpriteGeneral mg : controller.getSpriteList()) {

				if (mg.getSpriteType().equals(SpriteType.OBJETIVE)
						|| mg.getSpriteType().equals(SpriteType.SMASH)) {
					return true;
				}

			}

			for (MySpriteGeneral mg : controller.getSpriteListToAdd()) {

				if (mg.getSpriteType().equals(SpriteType.OBJETIVE)) {
					return true;
				}

			}

			return false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	private void updateWaveHUD(float dTime, LevelController controller) {
		try {

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public IEntity getEntity() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ITouchArea getTouchArea() {
		// TODO Auto-generated method stub
		return null;
	}

	public void reloadWave(LevelController controller, Long idLevel) {
		try {
			if (currentWaveToRestart == null) {
				return;
			}
			int contAux = contWave;
			loadWaveList(controller, idLevel);
			contWave = contAux - 1;

			Wave wave = null;

			for (Wave w : waveList) {
				if (beginTime > w.getBeginTime().floatValue()) {
					w.setFinish(true);
					wave = w;

				} else {
					break;
				}

			}

			if (wave == null) {
				return;
			}

			beginTime = wave.getBeginTime();
			wave.setFinish(false);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public float getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(float beginTime) {
		this.beginTime = beginTime;
	}

	public TextureSingleton getTexture() {
		return texture;
	}

	public void setTexture(TextureSingleton texture) {
		this.texture = texture;
	}

	public int getCurrentWave() {
		return currentWave;
	}

	public void setCurrentWave(int currentWave) {
		this.currentWave = currentWave;
	}

	public int getTotalWave() {
		return totalWave;
	}

	public void setTotalWave(int totalWave) {
		this.totalWave = totalWave;
	}

	public boolean isWin() {
		return win;
	}

	public void setWin(boolean win) {
		this.win = win;
	}

	public int getGameType() {
		return gameType;
	}

	public void setGameType(int gameType) {
		this.gameType = gameType;
	}

	public int getContWave() {
		return contWave;
	}

	public void setContWave(int contWave) {
		this.contWave = contWave;
	}

	public Wave getCurrentWaveToRestart() {
		return currentWaveToRestart;
	}

	public void setCurrentWaveToRestart(Wave currentWaveToRestart) {
		this.currentWaveToRestart = currentWaveToRestart;
	}

	public boolean isShowFinalWave() {
		return isShowFinalWave;
	}

	public void setShowFinalWave(boolean isShowFinalWave) {
		this.isShowFinalWave = isShowFinalWave;
	}

	
	
}
