package com.mgl.otto.util;

import com.squareup.otto.Bus;

public class BusProvider {
	
	private static final Bus BUS = new Bus();
	
	private BusProvider(){
		try {
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Bus getInstance(){
		return BUS;
	}

}
