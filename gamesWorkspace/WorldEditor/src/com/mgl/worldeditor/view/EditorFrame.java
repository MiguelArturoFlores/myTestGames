package com.mgl.worldeditor.view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.mgl.worldeditor.view.zeolandia.WorldPanel;

public class EditorFrame extends JFrame {
	
	private com.mgl.worldeditor.view.zeolandia.WorldPanel worldPanel;
	private ObjectPanel objectPanel;
	private MenuPanel menuPanel;
	private ParameterPanel parameterPanel;
	
	private float gridWidth = 12;
	private float gridHeight = 12;
	
	private static EditorFrame instance = null;
	
	private EditorFrame() {
		try {
			
			this.setTitle("World Editor");
			this.setSize(1200, 550);
			this.setVisible(true);
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			this.setLayout(new BorderLayout());
			
			worldPanel = new WorldPanel();
			
			objectPanel = new ObjectPanel();
			
			menuPanel = new MenuPanel();
			
			parameterPanel = new ParameterPanel();
			
			JPanel panel = new JPanel();
			panel.setPreferredSize(new Dimension(100,480));
			panel.add(worldPanel);
			this.add(panel,BorderLayout.CENTER);
			this.add(parameterPanel,BorderLayout.EAST);
			this.add(objectPanel,BorderLayout.WEST);
			this.add(menuPanel,BorderLayout.NORTH);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public WorldPanel getWorldPanel() {
		return worldPanel;
	}

	public void setWorldPanel(WorldPanel worldPanel) {
		this.worldPanel = worldPanel;
	}

	public ObjectPanel getObjectPanel() {
		return objectPanel;
	}

	public void setObjectPanel(ObjectPanel objectPanel) {
		this.objectPanel = objectPanel;
	}

	public MenuPanel getMenuPanel() {
		return menuPanel;
	}

	public void setMenuPanel(MenuPanel menuPanel) {
		this.menuPanel = menuPanel;
	}

	public static EditorFrame getInstance() {
		try {
			
			if(instance == null){
				instance = new EditorFrame();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return instance;
	}

	public static void setInstance(EditorFrame instance) {
		EditorFrame.instance = instance;
	}

	public ParameterPanel getParameterPanel() {
		return parameterPanel;
	}

	public void setParameterPanel(ParameterPanel parameterPanel) {
		this.parameterPanel = parameterPanel;
	}

	public float getGridWidth() {
		return gridWidth;
	}

	public void setGridWidth(float gridWidth) {
		this.gridWidth = gridWidth;
	}

	public float getGridHeight() {
		return gridHeight;
	}

	public void setGridHeight(float gridHeight) {
		this.gridHeight = gridHeight;
	}
	
	

	
}
