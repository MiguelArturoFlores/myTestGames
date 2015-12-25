package com.mgl.drop.game.model;

public class Item {

	private PlayerModel playerEquiped;

	private Long id;
	private Long idOwner;
	
	
	private String name;
	private String textureName;

	private int totalHP;
	private int totalMP;

	private int level = 0;
	private int defense = 0;
	private int attack = 0;
	private int magicPower = 0;
	private int equiped = 0;

	private int consumable = 0;
	
//	public enum ItemType {
//		EAR, FOOT, HAND
//	}
	
	public static final int POTION = 0;
	public static final int SOCK = 1;
	public static final int EAR = 2;
	public static final int HAND = 3;

	public static final int HEALTH_POTION = 7;
	public static final int MANA_POTION = 8;



	private int itemType;

	public Item() {
	}

	public Item(Long id,PlayerModel playerEquiped, String name, String textureName,
			int totalHP, int totalMP, int level, int defense, int attack,
			 int magicPower, int itemType) {
		super();

		this.id = id;
		this.itemType = itemType;
		this.playerEquiped = playerEquiped;
		this.name = name;
		this.textureName = textureName;
		this.totalHP = totalHP;
		this.totalMP = totalMP;
		this.level = level;
		this.defense = defense;
		this.attack = attack;
		this.magicPower = magicPower;
	}

	public PlayerModel getPlayerEquiped() {
		return playerEquiped;
	}

	public void setPlayerEquiped(PlayerModel playerEquiped) {
		this.playerEquiped = playerEquiped;
	}

	public int getTotalHP() {
		return totalHP;
	}

	public void setTotalHP(int totalHP) {
		this.totalHP = totalHP;
	}

	public int getTotalMP() {
		return totalMP;
	}

	public void setTotalMP(int totalMP) {
		this.totalMP = totalMP;
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



	public int getItemType() {
		return itemType;
	}

	public void setItemType(int itemType) {
		this.itemType = itemType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getMagicPower() {
		return magicPower;
	}

	public void setMagicPower(int magicPower) {
		this.magicPower = magicPower;
	}

	public int getConsumable() {
		return consumable;
	}

	public void setConsumable(int consumable) {
		this.consumable = consumable;
	}

	public int getEquiped() {
		return equiped;
	}

	public void setEquiped(int equiped) {
		this.equiped = equiped;
	}

	public Long getIdOwner() {
		return idOwner;
	}

	public void setIdOwner(Long idOwner) {
		this.idOwner = idOwner;
	}


}
