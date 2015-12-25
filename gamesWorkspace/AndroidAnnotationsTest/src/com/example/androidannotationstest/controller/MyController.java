package com.example.androidannotationstest.controller;

import org.androidannotations.annotations.EBean;

//@EBean
public class MyController {

	public String textToShow;
	
	public MyController(){
		try {
			
			textToShow = new String("text from constructor");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getTextToShow() {
		return textToShow;
	}

	public void setTextToShow(String textToShow) {
		this.textToShow = textToShow;
	}
	
	
	
	
}
