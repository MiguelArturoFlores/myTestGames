package com.mgl.base.analitycs;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.mgl.drop.MainDropActivity;

public class ThreadAnalitycs extends Thread{

	public MainDropActivity activity;
	
	public int type;
	
	public ThreadAnalitycs(MainDropActivity mainDropActivity, int type) {
		this.activity = mainDropActivity;
		this.type = type;
	}

	public void run(){
		try {
				GoogleAnalytics.getInstance(activity).reportActivityStart(activity);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
