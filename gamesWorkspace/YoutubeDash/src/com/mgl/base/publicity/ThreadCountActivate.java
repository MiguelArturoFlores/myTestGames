package com.mgl.base.publicity;

public class ThreadCountActivate extends Thread{

	private Long timeToSleep;
	private boolean show = false;
	
	
	
	public ThreadCountActivate(Long timeToSleep, boolean show) {
		super();
		this.timeToSleep = timeToSleep;
		this.show = show;
	}



	@Override
	public void run(){
		try {
			
			while(true){
				Thread.sleep(timeToSleep*1000);
				show = true;
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



	public boolean isShow() {
		return show;
	}



	public void setShow(boolean show) {
		this.show = show;
	}

	
}
