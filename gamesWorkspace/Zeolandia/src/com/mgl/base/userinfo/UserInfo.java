package com.mgl.base.userinfo;

import com.mgl.drop.util.ManageDate;

public class UserInfo {

	private int playerSelect;

	private int powerA;
	private int powerB;
	private int powerC;
	private int powerD;

	private int money;
	private int publicity;
	private String date;

	private int contBasic = 0;
	private int contBat = 0;
	private int contHole = 0;
	private int contArmor = 0;
	private int contZigZag = 0;

	private boolean hasRate;
	private boolean hasLike;
	private boolean hasBuy;

	private int contPowerA;
	private int contPowerB;
	private int contPowerC;
	private int contPowerD;

	private String dateWhatsapp;
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

	public UserInfo() {

	}

	public int getPowerA() {
		return powerA;
	}

	public void setPowerA(int powerA) {
		this.powerA = powerA;
	}

	public int getPowerB() {
		return powerB;
	}

	public void setPowerB(int powerB) {
		this.powerB = powerB;
	}

	public int getPowerC() {
		return powerC;
	}

	public void setPowerC(int powerC) {
		this.powerC = powerC;
	}

	public int getPowerD() {
		return powerD;
	}

	public void setPowerD(int powerD) {
		this.powerD = powerD;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
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

	public int getContBasic() {
		return contBasic;
	}

	public void setContBasic(int contBasic) {
		this.contBasic = contBasic;
	}

	public int getContBat() {
		return contBat;
	}

	public void setContBat(int contBat) {
		this.contBat = contBat;
	}

	public int getContHole() {
		return contHole;
	}

	public void setContHole(int contHole) {
		this.contHole = contHole;
	}

	public int getContArmor() {
		return contArmor;
	}

	public void setContArmor(int contArmor) {
		this.contArmor = contArmor;
	}

	public int getContZigZag() {
		return contZigZag;
	}

	public void setContZigZag(int contZigZag) {
		this.contZigZag = contZigZag;
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

	public boolean isHasBuy() {
		return hasBuy;
	}

	public void setHasBuy(boolean hasBuy) {
		this.hasBuy = hasBuy;
	}

	public int getContPowerA() {
		return contPowerA;
	}

	public void setContPowerA(int contPowerA) {
		this.contPowerA = contPowerA;
	}

	public int getContPowerB() {
		return contPowerB;
	}

	public void setContPowerB(int contPowerB) {
		this.contPowerB = contPowerB;
	}

	public int getContPowerC() {
		return contPowerC;
	}

	public void setContPowerC(int contPowerC) {
		this.contPowerC = contPowerC;
	}

	public int getContPowerD() {
		return contPowerD;
	}

	public void setContPowerD(int contPowerD) {
		this.contPowerD = contPowerD;
	}

	public int getPlayerSelect() {
		return playerSelect;
	}

	public void setPlayerSelect(int playerSelect) {
		this.playerSelect = playerSelect;
	}

	public String getDateWhatsapp() {
		return dateWhatsapp;
	}

	public void setDateWhatsapp(String dateWhatsapp) {
		this.dateWhatsapp = dateWhatsapp;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isHasSendMail() {
		return hasSendMail;
	}

	public void setHasSendMail(boolean hasSendMail) {
		this.hasSendMail = hasSendMail;
	}

	public boolean isHasSendTwitter() {
		return hasSendTwitter;
	}

	public void setHasSendTwitter(boolean hasSendTwitter) {
		this.hasSendTwitter = hasSendTwitter;
	}

	public boolean isHasSendYoutube() {
		return hasSendYoutube;
	}

	public void setHasSendYoutube(boolean hasSendYoutube) {
		this.hasSendYoutube = hasSendYoutube;
	}

	public String getDateFacebook() {
		return dateFacebook;
	}

	public void setDateFacebook(String dateFacebook) {
		this.dateFacebook = dateFacebook;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getYoutubeToken() {
		return youtubeToken;
	}

	public void setYoutubeToken(String youtubeToken) {
		this.youtubeToken = youtubeToken;
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

	public boolean isSendInfo() {
		return sendInfo;
	}

	public void setSendInfo(boolean sendInfo) {
		this.sendInfo = sendInfo;
	}

	
}
