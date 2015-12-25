package com.mgl.drop.game.database.model;

public class PlayerTechnique {

	private Long playerId;
	private Long techniqueId;

	private boolean avalible = false;

	public Long getPlayerId() {
		return playerId;
	}

	public void setPlayerId(Long playerId) {
		this.playerId = playerId;
	}

	public Long getTechniqueId() {
		return techniqueId;
	}

	public void setTechniqueId(Long techniqueId) {
		this.techniqueId = techniqueId;
	}

	public boolean isAvalible() {
		return avalible;
	}

	public void setAvalible(boolean avalible) {
		this.avalible = avalible;
	}

}
