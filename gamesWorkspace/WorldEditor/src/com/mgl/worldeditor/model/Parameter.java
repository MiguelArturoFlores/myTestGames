package com.mgl.worldeditor.model;

import javax.swing.JLabel;
import javax.swing.JTextField;

public class Parameter {

	private JLabel parameterName;
	private JTextField value;
	
	public Parameter(String parameterName){
		try {
			
			this.parameterName = new JLabel(parameterName);
			value = new JTextField();
			value.setSize(100, 50);
			value.setColumns(16);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Parameter(String text, String text2) {
		try {
			
			this.parameterName = new JLabel(text);
			value = new JTextField();
			value.setSize(100, 50);
			value.setColumns(16);
			value.setText(text2);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public JLabel getParameterName() {
		return parameterName;
	}

	public void setParameterName(JLabel parameterName) {
		this.parameterName = parameterName;
	}

	public JTextField getValue() {
		return value;
	}

	public void setValue(JTextField value) {
		this.value = value;
	}
	
	
	
}
