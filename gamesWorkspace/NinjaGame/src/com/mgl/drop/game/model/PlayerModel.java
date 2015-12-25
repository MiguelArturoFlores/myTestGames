package com.mgl.drop.game.model;

import com.mgl.drop.game.sprites.azeoland.SpriteItem;

public class PlayerModel {

	private int totalHP = 10;
	private int currentHP = 8;
	private int totalMP = 5;
	private int currentMP = 2;

	private int level = 1;
	private int defense = 1;
	private int attack = 1;
	private int dexterity = 1;
	private int strength = 1;
	private int intelligence = 1;

	private int dexterityExtra = 0;
	private int strengthExtra = 0;
	private int intelligenceExtra = 0;

	private Item sock;
	private Item earing;
	private Item hand;

	private void calculateExtra() {
		try {

			strengthExtra = 0;
			intelligenceExtra = 0;
			dexterityExtra = 0;

			if (hand != null) {
				strengthExtra += hand.getStrength();
				dexterityExtra += hand.getDexterity();
				intelligenceExtra += hand.getIntelligence();
			}
			if (sock != null) {
				strengthExtra += sock.getStrength();
				dexterityExtra += sock.getDexterity();
				intelligenceExtra += sock.getIntelligence();
			}
			if (earing != null) {
				strengthExtra += earing.getStrength();
				dexterityExtra += earing.getDexterity();
				intelligenceExtra += earing.getIntelligence();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void dropItem(SpriteItem item) {
		try {
			
			if(earing!=null){
				if(earing.getId().equals(item.getItem().getId())){
					setEaring(null);
				}
			}
			if(sock!=null){
				if(sock.getId().equals(item.getItem().getId())){
					setSock(null);
				}
			}
			if(hand!=null){
				if(hand.getId().equals(item.getItem().getId())){
					setHand(null);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int getTotalHP() {
		return totalHP;
	}

	public void setTotalHP(int totalHP) {
		this.totalHP = totalHP;
	}

	public int getCurrentHP() {
		return currentHP;
	}

	public void setCurrentHP(int currentHP) {
		this.currentHP = currentHP;
	}

	public int getTotalMP() {
		return totalMP;
	}

	public void setTotalMP(int totalMP) {
		this.totalMP = totalMP;
	}

	public int getCurrentMP() {
		return currentMP;
	}

	public void setCurrentMP(int currentMP) {
		this.currentMP = currentMP;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getDefense() {
		return defense;
	}

	public void setDefense(int defense) {
		this.defense = defense;
	}

	public int getAttack() {
		return attack;
	}

	public void setAttack(int attack) {
		this.attack = attack;
	}

	public int getDexterity() {
		return dexterity;
	}

	public void setDexterity(int dexterity) {
		this.dexterity = dexterity;
	}

	public int getStrength() {
		return strength;
	}

	public void setStrength(int strength) {
		this.strength = strength;
	}

	public int getIntelligence() {
		return intelligence;
	}

	public void setIntelligence(int intelligence) {
		this.intelligence = intelligence;
	}

	public Item getSock() {
		return sock;
	}

	public void setSock(Item sock) {
		this.sock = sock;
		calculateExtra();
	}

	public Item getEaring() {
		return earing;
	}

	public void setEaring(Item earing) {
		this.earing = earing;
		calculateExtra();
	}

	public Item getHand() {
		return hand;
	}

	public void setHand(Item hand) {
		this.hand = hand;
		calculateExtra();
	}

	public int getDexterityExtra() {
		return dexterityExtra;
	}

	public void setDexterityExtra(int dexterityExtra) {
		this.dexterityExtra = dexterityExtra;
	}

	public int getStrengthExtra() {
		return strengthExtra;
	}

	public void setStrengthExtra(int strengthExtra) {
		this.strengthExtra = strengthExtra;
	}

	public int getIntelligenceExtra() {
		return intelligenceExtra;
	}

	public void setIntelligenceExtra(int intelligenceExtra) {
		this.intelligenceExtra = intelligenceExtra;
	}


}
