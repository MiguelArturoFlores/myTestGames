package com.mgl.drop.game.model;

public class Item {

	private PlayerModel playerEquiped;

	private Long id;

	private String name;
	private String textureName;

	private int totalHP;
	private int totalMP;

	private int level = 0;
	private int defense = 0;
	private int attack = 0;
	private int dexterity = 0;
	private int strength = 0;
	private int intelligence = 0;

	public enum ItemType {
		EAR, FOOT, HAND
	}

	private ItemType itemType;

	public Item() {
	}

	public Item(Long id,PlayerModel playerEquiped, String name, String textureName,
			int totalHP, int totalMP, int level, int defense, int attack,
			int dexterity, int strength, int intelligence, ItemType itemType) {
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
		this.dexterity = dexterity;
		this.strength = strength;
		this.intelligence = intelligence;
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

	public ItemType getItemType() {
		return itemType;
	}

	public void setItemType(ItemType itemType) {
		this.itemType = itemType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
