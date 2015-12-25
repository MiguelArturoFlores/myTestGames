package com.mgl.worldeditor.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JPanel;

import com.mgl.worldeditor.model.Parameter;

public class ParameterPanelList extends JPanel{

	private ArrayList<Parameter> paramList;
	
	public ParameterPanelList(){
		try {
			
			this.setSize(new Dimension(200,600));
			this.setPreferredSize(new Dimension(200,600));
			this.setVisible(true);
			paramList = new ArrayList<>();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setParameterList(ArrayList<Parameter> parameterList) {
		try {
			if(parameterList == null){
				this.removeAll();
				this.repaint();
				return;
			}
			
			for(Parameter param : parameterList){
				System.out.println("parameter "+ param.getParameterName().getText());
				this.add(param.getParameterName());
				this.add(param.getValue());
				repaint();
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
