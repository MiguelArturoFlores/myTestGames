package com.mgl.drop.game.model;

import com.mgl.drop.game.sprites.azeoland.SpriteItem;

public class PlayerModel {

	private Long id;

	private String name;
	private String textureName;
	
	private int totalHP = 20;
	private int currentHP = 20;
	private int totalMP = 5;
	private int currentMP = 2;

	private int distanceToAttack = 30;

	private int currentExperience = 2;
	private int experienceNextLevel = 4;

	private int level = 1;
	private int defense = 1;
	private int attack = 4;
	private int magicPower = 1;

	private int attackExtra = 0;
	private int defenseExtra = 0;
	private int magicPowerExtra = 0;

	private Item sock;
	private Item earing;
	private Item hand;

	private void calculateExtra() {
		try {

			attackExtra = 0;
			defenseExtra = 0;
			magicPowerExtra = 0;

			if (hand != null) {
				attackExtra += hand.getAttack();
				defenseExtra += hand.getDefense();
				magicPowerExtra += hand.getMagicPower();
			}
			if (sock != null) {
				attackExtra += sock.getAttack();
				defenseExtra += sock.getDefense();
				magicPowerExtra += sock.getMagicPower();
			}
			if (earing != null) {
				attackExtra += earing.getAttack();
				defenseExtra += earing.getDefense();
				magicPowerExtra += earing.getMagicPower();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void dropItem(SpriteItem item) {
		try {

			if (earing != null) {
				if (earing.getId().equals(item.getItem().getId())) {
					setEaring(null);
				}
			}
			if (sock != null) {
				if (sock.getId().equals(item.getItem().getId())) {
					setSock(null);
				}
			}
			if (hand != null) {
				if (hand.getId().equals(item.getItem().getId())) {
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

	public int getMagicPower() {
		return magicPower;
	}

	public void setMagicPower(int magicPower) {
		this.magicPower = magicPower;
	}

	public int getAttackExtra() {
		return attackExtra;
	}

	public void setAttackExtra(int attackExtra) {
		this.attackExtra = attackExtra;
	}

	public int getDefenseExtra() {
		return defenseExtra;
	}

	public void setDefenseExtra(int defenseExtra) {
		this.defenseExtra = defenseExtra;
	}

	public int getMagicPowerExtra() {
		return magicPowerExtra;
	}

	public void setMagicPowerExtra(int magicPowerExtra) {
		this.magicPowerExtra = magicPowerExtra;
	}

	public int getCurrentExperience() {
		return currentExperience;
	}

	public void setCurrentExperience(int currentExperience) {
		this.currentExperience = currentExperience;
	}

	public int getExperienceNextLevel() {
		return experienceNextLevel;
	}

	public void setExperienceNextLevel(int experienceNextLevel) {
		this.experienceNextLevel = experienceNextLevel;
	}

	public int getDistanceToAttack() {
		return distanceToAttack;
	}

	public void setDistanceToAttack(int distanceToAttack) {
		this.distanceToAttack = distanceToAttack;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTextureName() {
		return textureName;
	}

	public void setTextureName(String textureName) {
		this.textureName = textureName;
	}

	public boolean usePotion(Item item) {
		try {
			
			
			currentHP = currentHP + item.getTotalHP();
			if(currentHP>=totalHP){
				currentHP = totalHP;
			}
			
			currentMP = currentMP + item.getTotalMP();
			if(currentMP>=totalMP){
				currentMP = totalMP;
			}
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	
	
}
