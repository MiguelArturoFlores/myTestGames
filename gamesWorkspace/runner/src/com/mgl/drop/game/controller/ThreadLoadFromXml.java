package com.mgl.drop.game.controller;

public class ThreadLoadFromXml extends Thread{
	
	
	private LevelController controller;
	private Long idLevel;
	private boolean isFinish= false;
	
	public ThreadLoadFromXml(LevelController controller , Long idLevel){
		this.controller = controller;
		this.idLevel = idLevel;
		isFinish= false;
	}
	
	public void run(){
		try {
			
			controller.loadNextLevel(idLevel);
			
			isFinish = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public LevelController getController() {
		return controller;
	}

	public void setController(LevelController controller) {
		this.controller = controller;
	}

	public Long getIdLevel() {
		return idLevel;
	}

	public void setIdLevel(Long idLevel) {
		this.idLevel = idLevel;
	}

	public boolean isFinish() {
		return isFinish;
	}

	public void setFinish(boolean isFinish) {
		this.isFinish = isFinish;
	}

	
	
}
