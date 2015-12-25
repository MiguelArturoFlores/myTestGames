package com.mgl.base.analitycs;

import java.util.HashMap;





import android.util.Log;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.mgl.drop.game.scene.SceneManagerSingleton;
import com.mgl.zeolandia.R;

public class AnalyticsSingeleton {
	
	private HashMap<TrackerName, Tracker> mTrackers = new HashMap<TrackerName, Tracker>();
	private static AnalyticsSingeleton instance = null;
	
	private AnalyticsSingeleton(){
		try {
			
			Log.d("SINGLETON ANALITYCS","SINGLETON ANALITYCS");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public synchronized Tracker getTracker(TrackerName trackerId) {
	    if (!mTrackers.containsKey(trackerId)) {

	      GoogleAnalytics analytics = GoogleAnalytics.getInstance(SceneManagerSingleton.getInstance().getActivity());
	      Tracker t = (trackerId == TrackerName.APP_TRACKER) ? analytics.newTracker(trackerId.toString())
	          : (trackerId == TrackerName.GLOBAL_TRACKER) ? analytics.newTracker(R.xml.global_tracker):analytics.newTracker(R.xml.global_tracker);
	      mTrackers.put(trackerId, t);

	    }
	    return mTrackers.get(trackerId);
	  }

	public HashMap<TrackerName, Tracker> getmTrackers() {
		return mTrackers;
	}

	public void setmTrackers(HashMap<TrackerName, Tracker> mTrackers) {
		this.mTrackers = mTrackers;
	}

	
	public static AnalyticsSingeleton getInstance() {
		if(instance==null){
			instance = new AnalyticsSingeleton();
		}
		return instance;
	}

	public void setInstance(AnalyticsSingeleton instance) {
		this.instance = instance;
	}
	

	
	
}
