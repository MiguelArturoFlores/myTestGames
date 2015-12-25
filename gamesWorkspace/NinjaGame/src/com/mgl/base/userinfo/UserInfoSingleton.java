package com.mgl.base.userinfo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.andengine.entity.scene.Scene;

import android.util.Log;

import com.mgl.base.MySpriteGeneral;
import com.mgl.base.publicity.ThreadCountActivate;
import com.mgl.drop.MainDropActivity;
import com.mgl.drop.factory.SoundSingleton;
import com.mgl.drop.game.HUDManagerSingleton;
import com.mgl.drop.game.constant.GameConstants;
import com.mgl.drop.game.constant.GamePurchaseConstant;
import com.mgl.drop.game.constant.ZIndexGame;
import com.mgl.drop.game.database.DatabaseDrop;
import com.mgl.drop.game.database.LevelDAO;
import com.mgl.drop.game.database.model.Level;
import com.mgl.drop.game.database.model.Trophy;
import com.mgl.drop.game.hud.FacebookShareHUD;
import com.mgl.drop.game.hud.InformativeHUD;
import com.mgl.drop.game.hud.RateMeHUD;
import com.mgl.drop.game.hud.WhatsappShareHUD;
import com.mgl.drop.game.objects.button.ButtonMoney;
import com.mgl.drop.game.scene.SceneManagerSingleton;
import com.mgl.drop.game.sprites.SpriteTrophy;
import com.mgl.drop.texture.TextureSingleton;
import com.mgl.drop.util.ManageDate;

public class UserInfoSingleton {

	private static UserInfoSingleton instance = null;

	private int playerSelect;

	private int powerA;
	private int powerB;
	private int powerC;
	private int powerD;

	private int money;
	private int publicity;
	private String date;
	private String dateWhatsapp;

	private int contBasic = 0;
	private int contBat = 0;
	private int contHole = 0;
	private int contArmor = 0;
	private int contZigZag = 0;

	private boolean hasRate;
	private boolean hasLike;
	private boolean showRate;

	private String email;
	
	private boolean hasSendMail;
	private boolean hasSendTwitter;
	private boolean hasSendYoutube;
	
	private String dateFacebook;
	private String language;
	private String youtubeToken;

	private int contShareWhatsapp;
	private int contShareFacebook;
	private int contShareTwitter;
	private int starRating;
	private boolean sendInfo;
	
	private UserInfo userInfo;
	private int currentLevel;

	private LevelDAO dao;

	private ArrayList<Trophy> trophyList;
	private ButtonMoney buttonMoney;
	
	private ThreadCountActivate thread;
	private ThreadCountActivate threadFacebook;

	private UserInfoSingleton() {
		try {

			dao = new LevelDAO(SceneManagerSingleton.getInstance()
					.getActivity(), DatabaseDrop.DB_NAME, null,
					MainDropActivity.DB_VERSION);

			trophyList = dao.loadTrophyList();
			loadUserInfo();

			TextureSingleton texture = TextureSingleton.getInstance();

			thread =  new ThreadCountActivate((long) (90l), true);
			thread.start();
			
			threadFacebook = new ThreadCountActivate(200l, true);
			threadFacebook.start();
			
			showRate = true;

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void loadUserInfo() throws Exception {
		try {

			userInfo = dao.loadUserInfo();
			powerA = userInfo.getPowerA();
			powerB = userInfo.getPowerB();
			powerC = userInfo.getPowerC();
			powerD = userInfo.getPowerD();
			money = userInfo.getMoney();
			date = userInfo.getDate();

			contArmor = userInfo.getContArmor();
			contBasic = userInfo.getContBasic();
			contBat = userInfo.getContBat();
			contHole = userInfo.getContHole();
			contZigZag = userInfo.getContZigZag();

			hasRate = userInfo.isHasRate();
			hasLike = userInfo.isHasLike();

			publicity = userInfo.getPublicity();
			playerSelect = userInfo.getPlayerSelect();

			dateWhatsapp = userInfo.getDateWhatsapp();
			
			email = userInfo.getEmail();
			hasSendMail = userInfo.isHasSendMail();
			hasSendTwitter = userInfo.isHasSendTwitter();
			hasSendYoutube = userInfo.isHasSendYoutube();
			
			dateFacebook = userInfo.getDateFacebook();
			language = userInfo.getLanguage();
			youtubeToken = userInfo.getYoutubeToken();
			contShareWhatsapp = userInfo.getContShareWhatsapp();
			contShareFacebook = userInfo.getContShareFacebook();
			contShareTwitter = userInfo.getContShareTwitter();
			starRating = userInfo.getStarRating();
			sendInfo = userInfo.isSendInfo();
			currentLevel = userInfo.getCurrentLevel();
			
			// validateTrophy();

		} catch (Exception e) {
			throw e;
		}

	}

	public static UserInfoSingleton getInstance() {
		try {
			if (instance == null) {
				instance = new UserInfoSingleton();
			}

			return instance;
		} catch (Exception e) {
			e.printStackTrace();
			return instance;
		}

	}

	public void setInstance(UserInfoSingleton instance) {
		this.instance = instance;
	}

	public void consumePowerA() {
		try {
			powerA--;
			if (powerA < 0) {
				powerA = 0;
			}
			dao.increasePower("powera", powerA);
			loadUserInfo();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void consumePowerB() {

		try {
			powerB--;
			if (powerB < 0) {
				powerB = 0;
			}
			dao.increasePower("powerb", powerB);
			loadUserInfo();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void consumePowerC() {

		try {
			powerC--;
			if (powerC < 0) {
				powerC = 0;
			}
			dao.increasePower("powerc", powerC);
			loadUserInfo();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void consumePowerD() {

		try {
			powerD--;
			if (powerD < 0) {
				powerD = 0;
			}
			dao.increasePower("powerd", powerD);
			loadUserInfo();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void consumeMoney(int quantity) {
		try {
			money = quantity;
			dao.increasePower("money", money);
			loadUserInfo();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public int getPowerA() {
		try {
			loadUserInfo();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return powerA;
	}

	public int getPowerB() {
		try {
			loadUserInfo();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return powerB;
	}

	public int getPowerC() {
		try {
			loadUserInfo();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return powerC;
	}

	public int getPowerD() {
		try {
			loadUserInfo();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return powerD;
	}

	public int getMoney() {
		try {
			loadUserInfo();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return money;
	}

	public void increasePowerA(int quantity) {

		try {
			powerA = powerA + quantity;
			dao.increasePower("powera", powerA);
			dao.increasePower("contpowera", userInfo.getPowerA() + 1);
			loadUserInfo();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void increasePowerB(int quantity) {
		try {
			powerB = powerB + quantity;
			dao.increasePower("powerB", powerB);
			dao.increasePower("contpowerb", userInfo.getPowerB() + 1);
			loadUserInfo();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void increasePowerC(int quantity) {
		try {
			powerC = powerC + quantity;
			dao.increasePower("powerc", powerC);
			dao.increasePower("contpowerc", userInfo.getPowerC() + 1);
			loadUserInfo();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void increasePowerD(int quantity) {
		try {
			powerD = powerD + quantity;
			dao.increasePower("powerd", powerD);
			dao.increasePower("contpowerd", userInfo.getPowerD() + 1);
			loadUserInfo();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void buyItem(int buyType) {
		try {
			dao.changeUserInfo("publicity", 0 + "");
			UserInfoSingleton.getInstance().setHasBuy(true);
			loadUserInfo();

			switch (buyType) {
			case GamePurchaseConstant.BUY_A:

				money = money + GamePurchaseConstant.QUANTITY_BUY_A;

				break;
			case GamePurchaseConstant.BUY_B:

				money = money + GamePurchaseConstant.QUANTITY_BUY_B;

				break;
			case GamePurchaseConstant.BUY_C:

				money = money + GamePurchaseConstant.QUANTITY_BUY_C;

				break;
			case GamePurchaseConstant.BUY_D:

				money = money + GamePurchaseConstant.QUANTITY_BUY_D;

				break;
			case GamePurchaseConstant.BUY_E:

				money = money + GamePurchaseConstant.QUANTITY_BUY_E;

				break;
			case GamePurchaseConstant.BUY_F:

				money = money + GamePurchaseConstant.QUANTITY_BUY_F;

				break;
			case GamePurchaseConstant.BUY_G:

				money = money + GamePurchaseConstant.QUANTITY_BUY_G;

				break;
			case GamePurchaseConstant.BUY_H:

				money = money + GamePurchaseConstant.QUANTITY_BUY_H;

				break;
			case GamePurchaseConstant.BUY_I:

				money = money + GamePurchaseConstant.QUANTITY_BUY_I;

				break;

			default:
				break;
			}

			dao.increasePower("money", money);
			loadUserInfo();
			buttonMoney.changeMoney(money);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public int getPublicity() {
		return publicity;
	}

	public void setPublicity(int publicity) {
		this.publicity = publicity;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void increaseMoney(int freeDailyMoney) {
		try {

			loadUserInfo();
			money = money + freeDailyMoney;
			dao.increasePower("money", money);
			loadUserInfo();

			buttonMoney.changeMoney(money);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void increaseMoneyInGame(final int freeDailyMoney) {
		try {

			SceneManagerSingleton.getInstance().getActivity()
					.runOnUiThread(new Runnable() {

						@Override
						public void run() {

							try {

								loadUserInfo();
								money = money + freeDailyMoney;
								dao.increasePower("money", money);
								loadUserInfo();

							} catch (Exception e) {
								e.printStackTrace();
							}

						}
					});

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void setLastLogin(String formatDate) {
		try {

			dao.lastLogin(formatDate);
			loadUserInfo();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void increaseContBasic(int val) {
		try {
			contBasic = contBasic + val;

			SceneManagerSingleton.getInstance().getActivity()
					.runOnUiThread(new Runnable() {

						@Override
						public void run() {

							try {

								dao.increasePower("contBasic", contBasic);

							} catch (Exception e) {
								e.printStackTrace();
							}

						}
					});

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public void increaseContBat(int val) {
		try {

			contBat = contBat + val;
			SceneManagerSingleton.getInstance().getActivity()
					.runOnUiThread(new Runnable() {

						@Override
						public void run() {

							try {

								dao.increasePower("contBat", contBat);

							} catch (Exception e) {
								e.printStackTrace();
							}

						}
					});

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public void increaseContHole(int val) {
		try {
			contHole = contHole + val;
			SceneManagerSingleton.getInstance().getActivity()
					.runOnUiThread(new Runnable() {

						@Override
						public void run() {

							try {

								dao.increasePower("contHole", contHole);
							} catch (Exception e) {
								e.printStackTrace();
							}

						}
					});

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public void increaseContArmor(int val) {
		try {

			contArmor = contArmor + val;
			SceneManagerSingleton.getInstance().getActivity()
					.runOnUiThread(new Runnable() {

						@Override
						public void run() {

							try {

								dao.increasePower("contArmor", contArmor);

							} catch (Exception e) {
								e.printStackTrace();
							}

						}
					});

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public void increaseContZigZag(int val) {
		try {

			contZigZag = contZigZag + val;
			SceneManagerSingleton.getInstance().getActivity()
					.runOnUiThread(new Runnable() {

						@Override
						public void run() {

							try {

								dao.increasePower("contZigZag", contZigZag);

							} catch (Exception e) {
								e.printStackTrace();
							}

						}
					});

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public void setUserRate() {
		try {

			dao.changeUserInfo("hasrate", "1");
			loadUserInfo();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void showTrophey(Trophy t) {
		try {
			TextureSingleton texture = TextureSingleton.getInstance();

			SpriteTrophy trophy = new SpriteTrophy(0, 0,
					texture.getTextureByName("buttonTextureRed.png"),
					texture.getVertexBufferObjectManager(), t);
			trophy.setAlpha(0.8f);
			trophy.setPosition(
					MainDropActivity.CAMERA_WIDTH / 2 - trophy.getWidth() / 2,
					MainDropActivity.CAMERA_HEIGHT / 2 - trophy.getHeight() / 2);
			trophy.setZIndex(ZIndexGame.FADE);
			HUDManagerSingleton.getInstance().getTop().attachChild(trophy);
			trophy.setAutoUpdate();

			SoundSingleton.getInstance().playTrophySound();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public boolean isHasRate() {
		return hasRate;
	}

	public void setHasRate(boolean hasRate) {
		this.hasRate = hasRate;
	}

	public boolean isHasLike() {
		return hasLike;
	}

	public void setHasLike(boolean hasLike) {
		this.hasLike = hasLike;
	}

	public void showInternalPopUp(){
		try {
			
			if(showRateme()){
				return;
			}
			
			
			if(MainDropActivity.getRandomMax(0, 100) < 50){
				if(shareFacebook()){
					return;
				}
				shareWhatsapp();
			}else{
				if(shareWhatsapp()){
					return;
				}
				shareFacebook();
				
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean showRateme() {
		try {

			if (!OffertGameSingleton.getInstance().canIShowRateMe() || hasRate
					|| !showRate) {
				return false;
			}

			RateMeHUD hud = new RateMeHUD();
			HUDManagerSingleton.getInstance().addHUD(hud, true);

		} catch (Exception e) {

			e.printStackTrace();

		}
		return true;
	}

	public void noShowRate() {
		try {

			showRate = false;

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public void setUserLikeUs() {
		try {

			dao.changeUserInfo("haslike", "1");
			loadUserInfo();

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	public void setHasBuy(boolean hasBuy) {
		try {

			dao.changeUserInfo("hasbuy", "1");
			loadUserInfo();

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	public void validateTrophyAllStar() {
		try {

			Trophy tropy = null;
			for (Trophy t : trophyList) {
				if (t.getId() == TrophySingleton.WIN_3_STAR) {
					tropy = t;
					if (t.isUnlock()) {
						return;
					}
					for (Level l : dao.loadLevelList()) {
						if (l.getStars() < 3) {
							return;
						}
					}
					break;
				}
			}
			tropy.setUnlock(true);
			dao.unlockTrophy(tropy.getId());
			showTrophey(tropy);

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	public boolean isSurvivalActive() {
		try {

			for (Level l : dao.loadLevelList()) {
				if (l.getId() >= GameConstants.LEVEL_TO_UNLOCK_SURVIVAL
						&& l.getAvalible()) {
					return true;
				}
			}

		} catch (Exception e) {

			e.printStackTrace();

		}
		return false;
	}

	public ButtonMoney getButtonMoney() {
		try {
			// if(buttonMoney!=null)
			// buttonMoney.detachSelf();

			TextureSingleton texture = TextureSingleton.getInstance();
			ButtonMoney buttonMoney = new ButtonMoney(0, 5,
					texture.getTextureByName("buttonTexture.png"),
					texture.getVertexBufferObjectManager());
			this.buttonMoney = buttonMoney;
			return buttonMoney;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return buttonMoney;
	}

	public void setButtonMoney(ButtonMoney buttonMoney) {
		this.buttonMoney = buttonMoney;
	}

	public int getPlayerSelected() {
		try {

			return playerSelect;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public void setPlayerSelect(int playerSelcet) {
		try {

			dao.updateUserInfo("playerselected", playerSelcet);
			loadUserInfo();
			this.playerSelect = playerSelcet;

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public boolean shareWhatsapp() {
		try {

			Date today = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			Date lastLogin = sdf.parse(dateWhatsapp);

			if (lastLogin == null || !thread.isShow()) {
				return false;
			}
			
			if (lastLogin.before(today)) {
			
				HUDManagerSingleton.getInstance().addHUD(new WhatsappShareHUD(4),
						true);
				thread.setShow(false);
				
				
				return true;
			}
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean canShareWhatsapp() {
		try {

			Date today = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			Date lastLogin = sdf.parse(dateWhatsapp);

			if (lastLogin == null || !thread.isShow()) {
				return false;
			}
			
			if (lastLogin.before(today)) {
			
				thread.setShow(false);
				
				return true;
			}
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	
	public void setUserShareWhatsapp() {

		try {
			Date today = new Date();
			
			Calendar c = Calendar.getInstance();
			c.setTime(today);
			c.add(Calendar.DAY_OF_YEAR, 1);
			today = c.getTime();

			dao.lastWhatsappShare(ManageDate.formatDate(today, ManageDate.YYYY_MM_DD));
			dao.changeUserInfo("sendInfo", "1");
			loadUserInfo();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		try {
			this.email = email;	
			dao.changeEmail("email", email);
			loadUserInfo();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public boolean isHasSendMail() {
		return hasSendMail;
	}

	public void setHasSendMail(boolean hasSendMail) {
		this.hasSendMail = hasSendMail;
		try {
			
			dao.changeUserInfo("hasSendMail", "1");
			loadUserInfo();
			
		} catch (Exception e) {
			
		}
	}

	public boolean isHasSendTwitter() {
		return hasSendTwitter;
	}

	public void setHasSendTwitter(boolean hasSendTwitter) {
		this.hasSendTwitter = hasSendTwitter;
		try {
			dao.changeUserInfo("hasSendTwitter", "1");
			loadUserInfo();	
		} catch (Exception e) {
		
		}			
	}

	public boolean isHasSendYoutube() {
		return hasSendYoutube;
	}

	public void setHasSendYoutube(boolean hasSendYoutube) {
		this.hasSendYoutube = hasSendYoutube;
		try {
			dao.changeUserInfo("hasSendYoutube", "1");
			loadUserInfo();	
		} catch (Exception e) {
		
		}	
	}

	public boolean shareFacebook() {
		try {
			
			Date today = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			Date lastLogin = sdf.parse(dateFacebook);

			if (lastLogin == null || !threadFacebook.isShow()) {
				return false;
			}
			
			if (lastLogin.before(today)) {
				HUDManagerSingleton.getInstance().addHUD(new FacebookShareHUD(), true);
				
				threadFacebook.setShow(false);
				return true;
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean canShareFacebook() {
		try {
			
			Date today = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			Date lastLogin = sdf.parse(dateFacebook);

			if (lastLogin == null || !threadFacebook.isShow()) {
				return false;
			}
			
			if (lastLogin.before(today)) {
				
				threadFacebook.setShow(false);
				return true;
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	

	public int getContShareWhatsapp() {
		return contShareWhatsapp;
	}

	public void setContShareWhatsapp(int contShareWhatsapp) {
		this.contShareWhatsapp = contShareWhatsapp;
	}

	public int getContShareFacebook() {
		return contShareFacebook;
	}

	public void setContShareFacebook(int contShareFacebook) {
		this.contShareFacebook = contShareFacebook;
	}

	public int getContShareTwitter() {
		return contShareTwitter;
	}

	public void setContShareTwitter(int contShareTwitter) {
		this.contShareTwitter = contShareTwitter;
	}

	public int getStarRating() {
		return starRating;
	}

	public void setStarRating(int starRating) {
		this.starRating = starRating;
	}

	public void setStar(int starNumber) {
		try {
			starRating = starNumber;
			dao.changeUserInfo("starRating", starRating+"");
			dao.changeUserInfo("sendInfo", "1");
			loadUserInfo();	
		} catch (Exception e) {

		}
		
	}

	public void increaseContWhatsapp() {
		try {
			contShareWhatsapp = contShareWhatsapp + 1;
			dao.changeUserInfo("contShareWhatsapp", contShareWhatsapp+"");
			dao.changeUserInfo("sendInfo", "1");
			loadUserInfo();	
		} catch (Exception e) {

		}
		
	}

	public void increaseContFacebook() {
		try {
			contShareFacebook= contShareFacebook+ 1;
			dao.changeUserInfo("contShareFacebook", contShareFacebook+"");
			dao.changeUserInfo("sendInfo", "1");
			loadUserInfo();	
		} catch (Exception e) {

		}
		
	}

	
	public boolean isSendInfo() {
		return sendInfo;
	}

	public void setSendInfo(boolean sendInfo) {
		try {
			this.sendInfo = sendInfo;
			String inf = "0";
			if(sendInfo){
				inf = "1";
			}
			dao.changeUserInfo("sendInfo", inf);
			loadUserInfo();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void setUserShareFacebook() {
		try {
			Date today = new Date();
			
			Calendar c = Calendar.getInstance();
			c.setTime(today);
			c.add(Calendar.DAY_OF_YEAR, 1);
			today = c.getTime();

			dao.lastFacebookShare(ManageDate.formatDate(today, ManageDate.YYYY_MM_DD));
			loadUserInfo();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void increaseTwitterShare() {
		try {
			contShareTwitter = contShareTwitter + 1;
			dao.changeUserInfo("contShareTwitter", contShareTwitter+"");
			dao.changeUserInfo("sendInfo", "1");
			loadUserInfo();	
		} catch (Exception e) {

		}
		
	}

	public int getTotalLike() {
		try {
			
			return dao.getTotalLike();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int getCurrentLevel() {
		return currentLevel;
	}

	public void setCurrentLevel(int currentLevel) {
		this.currentLevel = currentLevel;
		try {
			dao.changeUserInfo("currentLevel", currentLevel+"");
			loadUserInfo();	
		} catch (Exception e) {
		
		}
	}


	
	
}
