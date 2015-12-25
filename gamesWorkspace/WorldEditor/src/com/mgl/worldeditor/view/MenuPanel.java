package com.mgl.worldeditor.view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.mgl.worldeditor.controller.ExportListener;
import com.mgl.worldeditor.controller.LoadListener;

public class MenuPanel extends JPanel{
	
	private JMenuBar menuBar;
	private JMenu menu;
	private JMenuItem exportAndengine;
	private JMenuItem loadAndengine;
	
	private JButton buttonShowGrid;
	
	private JTextField gridWidth;
	private JTextField gridHeight;
	
	public MenuPanel(){
		try {
			
			this.setSize(new Dimension(500,60));
			this.setVisible(true);
			
			menuBar = new JMenuBar();
			menu = new JMenu("Options");
			
			//items
			exportAndengine = new JMenuItem("Export XML");
			loadAndengine = new JMenuItem("Load");
			
			menu.add(exportAndengine);
			menu.add(loadAndengine);
			
			menuBar.add(menu);
			
			this.add(menuBar);
			
			menuBar.setFocusable(false);
			
			
			buttonShowGrid = new JButton("Hide Grid");
			this.add(buttonShowGrid);
			
			initGridSelection();
			
			initListeners();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initGridSelection() {
		try {
			
			gridHeight  = new JTextField("12");
			gridWidth  = new JTextField("12");
			
			gridHeight.setColumns(3);
			gridWidth.setColumns(3);
			
			
			gridHeight.addKeyListener(new KeyListener() {
				
				@Override
				public void keyTyped(KeyEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void keyReleased(KeyEvent e) {
					
					Float width = 0f;
					Float height = 0f;
					try {
						System.out.println("releasing " + gridWidth.getText());
						width = Float.valueOf(gridWidth.getText());
						height = Float.valueOf(gridHeight.getText());
						
						EditorFrame.getInstance().getWorldPanel().setGridHeight(height);
						EditorFrame.getInstance().getWorldPanel().setGridWidth(width);
						EditorFrame.getInstance().getWorldPanel().repaint();
						
					} catch (Exception e2) {

					}
					
				}
				
				@Override
				public void keyPressed(KeyEvent e) {
					// TODO Auto-generated method stub
					
				}
			});
			
			gridWidth.addKeyListener(new KeyListener() {
				
				@Override
				public void keyTyped(KeyEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void keyReleased(KeyEvent e) {
					
					Float width = 0f;
					Float height = 0f;
					try {
						System.out.println("releasing " + gridWidth.getText());
						width = Float.valueOf(gridWidth.getText());
						height = Float.valueOf(gridHeight.getText());
						
						EditorFrame.getInstance().getWorldPanel().setGridHeight(height);
						EditorFrame.getInstance().getWorldPanel().setGridWidth(width);
						EditorFrame.getInstance().getWorldPanel().repaint();
						
					} catch (Exception e2) {

					}
					
				}
				
				@Override
				public void keyPressed(KeyEvent e) {
					// TODO Auto-generated method stub
					
				}
			});
			
			this.add(gridWidth);
			this.add(gridHeight);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initListeners() {
		try {
			
			exportAndengine.addActionListener(new ExportListener());
			
			loadAndengine.addActionListener(new LoadListener());
			
			buttonShowGrid.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						boolean val = EditorFrame.getInstance().getWorldPanel().isDrawGrid();
						
						EditorFrame.getInstance().getWorldPanel().setDrawGrid(!val);
						
						if(val){
							buttonShowGrid.setText("SHOW GRID");
						}else{
							buttonShowGrid.setText("HIDE GRID");
						}
						
						buttonShowGrid.setFocusable(false);
						
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
			});
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
