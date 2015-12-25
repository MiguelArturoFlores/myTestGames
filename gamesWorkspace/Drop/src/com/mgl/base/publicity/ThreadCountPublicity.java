package com.mgl.base.publicity;

public class ThreadCountPublicity extends Thread{

	private Long timeToSleep;
	private boolean showIntersitial = false;
	
	
	
	public ThreadCountPublicity(Long timeToSleep, boolean showIntersitial) {
		super();
		this.timeToSleep = timeToSleep;
		this.showIntersitial = showIntersitial;
	}



	public void run(){
		try {
			
			while(true){
				Thread.sleep(timeToSleep*1000);
				showIntersitial = true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	public Long getTimeToSleep() {
		return timeToSleep;
	}



	public void setTimeToSleep(Long timeToSleep) {
		this.timeToSleep = timeToSleep;
	}



	public boolean isShowIntersitial() {
		return showIntersitial;
	}



	public void setShowIntersitial(boolean showIntersitial) {
		this.showIntersitial = showIntersitial;
	}

	
}
