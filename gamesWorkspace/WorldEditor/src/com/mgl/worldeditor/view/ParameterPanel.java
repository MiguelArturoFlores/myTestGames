package com.mgl.worldeditor.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.mgl.worldeditor.controller.DataSingleton;
import com.mgl.worldeditor.model.MyObjectXml;
import com.mgl.worldeditor.model.Parameter;

public class ParameterPanel extends JPanel{

	private JLabel objectSelectedLabel;
	private ParameterPanelList parameterPanelList;
	
	public ParameterPanel (){
		try {
			
			this.setSize(new Dimension(200,600));
			this.setPreferredSize(new Dimension(200,600));
			this.setVisible(true);
			this.setLayout(new BorderLayout());
			
			objectSelectedLabel =  new JLabel(" No object Select ");
			parameterPanelList = new ParameterPanelList();
			
			this.add(objectSelectedLabel,BorderLayout.NORTH);
			this.add(parameterPanelList,BorderLayout.CENTER);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void notifyObjectSelect() {
		try {
			
			MyObjectXml object = DataSingleton.getInstance().getObjectSelected();
			if(object != null){

				String val = new String("current Object "+object.getName());
				objectSelectedLabel.setText(val);

				parameterPanelList.setParameterList(object.getParameterList());
				

				
			}else{
				objectSelectedLabel.setText("No hay objecto");
				parameterPanelList.setParameterList(null);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
