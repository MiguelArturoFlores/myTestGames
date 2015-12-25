package com.mgl.worldeditor.controller;

import com.mgl.worldeditor.model.MyObjectXml;
import com.mgl.worldeditor.view.EditorFrame;

public class DataSingleton {

	private static DataSingleton instance = null;
	
	private MyObjectXml objectSelected = null;
	
	public enum State {
		MODIFY, NORMAL, ADDING
	}
	
	public State state = State.NORMAL;
	
	private DataSingleton(){
		try {
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static DataSingleton getInstance(){
		try {
			
			if(instance == null){
				instance = new DataSingleton();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return instance;
	}

	public MyObjectXml getObjectSelected() {
		return objectSelected;
	}

	public void setObjectSelected(MyObjectXml objectSelected) {
		this.objectSelected = objectSelected;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		EditorFrame.getInstance().getParameterPanel().notifyObjectSelect();
		this.state = state;
	}
	
	
	
}
