package com.mgl.worldeditor.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.mgl.worldeditor.controller.DataSingleton;
import com.mgl.worldeditor.controller.DataSingleton.State;
import com.mgl.worldeditor.model.MyObjectXml;
import com.mgl.worldeditor.model.Parameter;

public class ObjectPanel extends JScrollPane{
	
	private int tam = 50;
	private ArrayList<MyObjectXml> objectList;
	private Point point;
	private int DISTANCE = 60;
	private int HEIGHT = 650;
	
	public ObjectPanel(){
		try {
			
			point = new Point(50,HEIGHT/2);
			this.setSize(200,100);
			this.setPreferredSize(new Dimension(100,650));
			this.setVisible(true);
			
			loadObjectFromFile();
			
			addListeners();
			this.setFocusable(true);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void addListeners() {
		try {
			
			this.addMouseListener(new MouseListener() {
				
				@Override
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mousePressed(MouseEvent e) {
					try {
						float x = e.getX();
						float y = e.getY();
						if (point.x > WIDTH / 2) {
							x = x + point.x - WIDTH / 2;
						}
						if (point.y > HEIGHT / 2) {
							y = y + point.y - HEIGHT / 2;
						}
						selectObject(new Point(e.getX(),(int)y));
						
					} catch (Exception e2) {
						e2.printStackTrace();
					}
					
				}
				
				@Override
				public void mouseExited(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseEntered(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseClicked(MouseEvent e) {
					try {
						float x = e.getX();
						float y = e.getY();
						if (point.x > WIDTH / 2) {
							x = x + point.x - WIDTH / 2;
						}
						if (point.y > HEIGHT / 2) {
							y = y + point.y - HEIGHT / 2;
						}
						selectObject(new Point(e.getX(),(int)y));
						
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}

				
			});
			
			this.addKeyListener(new KeyListener() {
				
				

				@Override
				public void keyTyped(KeyEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void keyReleased(KeyEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void keyPressed(KeyEvent e) {
					try {
						
						switch (e.getKeyCode()) {
						
						case 38:
							// arriba
							point.y = point.y - DISTANCE ;
							repaint();
							break;
						
						case 40:
							// abajo
							point.y = point.y + DISTANCE;
							repaint();
							break;
						}

						
					} catch (Exception e2) {
						e2.printStackTrace();
					}
					
				}
			});
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void selectObject(Point point) {

		try {
			
			for(MyObjectXml obj : objectList){
				
				if(point.getX()>= obj.getX() && point.getX()<= obj.getX()+tam ){
					
					if(point.getY()>= obj.getY() && point.getY()<= obj.getY()+tam){
						DataSingleton.getInstance().setObjectSelected(obj);
						System.out.println("Selected "+obj.getId());
						DataSingleton.getInstance().setState(State.ADDING);
						
						return;
					}
					
				}
				
			}
			
			DataSingleton.getInstance().setState(State.NORMAL);
			DataSingleton.getInstance().setObjectSelected(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void loadObjectFromFile() {

		try {
			
			objectList = new ArrayList<>();
			
			File file = new File("info/imageLoader.txt");
			if(!file.exists()){
				System.err.println("File doesnt exist");
				return;
			}
			
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String sCurrentLine;
			
			while ((sCurrentLine = reader.readLine()) != null) {
				
				System.out.println(sCurrentLine);
				MyObjectXml obj = new MyObjectXml();
				
				String[] str = sCurrentLine.split(",");
				try {
					obj.setId(Integer.valueOf(str[0]));
				} catch (Exception e) {
					continue;
				}
				try {
					obj.setImage(ImageIO.read(new File("images/"+str[1])));
					obj.setName(str[2]);
				} catch (Exception e) {
					continue;
				}
				
				objectList.add(obj);
				
				try {
					
					String paramStr = str[3];
					if(paramStr == null){
						continue;
					}
					String[] strParam = paramStr.split("/");
					ArrayList<Parameter> parameterList =  new ArrayList<>();
					for(int i = 0; i< strParam.length; i ++){
						
						Parameter parameter = new Parameter(strParam[i]);
						parameterList.add(parameter);
						
					}
					
					obj.setParameterList(parameterList);
					
				} catch (Exception e) {
					continue;
				}
				
			}
			
			reader.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

			
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		try {
			Graphics2D g2d = (Graphics2D) g;
			AffineTransform trans = g2d.getTransform();
			
			g.clearRect(0, 0, 200, 1000);
			
			int xMove = 0;
			int yMove = 0;
			if (point.getY() > HEIGHT / 2) {
				g2d.translate(0, HEIGHT / 2 - point.y);
				yMove = HEIGHT / 2 - point.y;
			}
			
			int i=0;
			
			for(MyObjectXml obj : objectList){
				
				obj.setX(25);
				obj.setY( (i*(tam+10) ) +15);	
				
				g.drawString(obj.getName(), (int) obj.getX(), (int) obj.getY());
				g.drawImage(obj.getImage(),  (int) obj.getX(), (int) obj.getY() ,tam ,tam, null);
				i++;
				
			}
			
			
			
			g2d.setTransform(trans);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	

	
}
