package com.mgl.base.analitycs;

import java.util.HashMap;

import com.google.android.gms.analytics.Tracker;

public enum TrackerName {

	APP_TRACKER, // Tracker used only in this app.
	GLOBAL_TRACKER, // Tracker used by all the apps from a company. eg: roll-up
					// tracking.
	ECOMMERCE_TRACKER, // Tracker used by all ecommerce transactions from a
						// company.
}
