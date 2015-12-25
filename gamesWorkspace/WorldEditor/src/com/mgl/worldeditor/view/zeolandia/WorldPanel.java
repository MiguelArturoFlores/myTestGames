package com.mgl.worldeditor.view.zeolandia;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Point;
import java.awt.Transparency;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.xml.crypto.dsig.Transform;
import javax.xml.stream.events.StartDocument;

import com.mgl.worldeditor.controller.DataSingleton;
import com.mgl.worldeditor.controller.ImageSingleton;
import com.mgl.worldeditor.controller.DataSingleton.State;
import com.mgl.worldeditor.model.MyObjectXml;
import com.mgl.worldeditor.view.EditorFrame;

public class WorldPanel extends JPanel {

	private Point point;
	private int DISTANCE = 100;
	private int ROTATE_ANGLE = 90;
	private int WIDTH = 800;
	private int HEIGHT = 480;

	private boolean paintMouse = false;
	private boolean drawGrid = false;
	private float gridWidth = 12;
	private float gridHeight = 12;

	private ArrayList<MyObjectXml> objectList;
	private MyObjectXml objSelected;

	
	private float scale = 1f;
	
	private float scalePlus = 0.01f;
	
	public WorldPanel() {
		try {

			this.setPreferredSize(new Dimension(800, 480));
			this.setVisible(true);
			// this.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			// this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
			// this.setBounds(50, 30, 300, 50);
			this.setFocusable(true);

			objectList = new ArrayList<>();

			point = new Point(WIDTH / 2, HEIGHT / 2);

			this.addKeyListener(new KeyListener() {

				@Override
				public void keyTyped(KeyEvent e) {

				}

				@Override
				public void keyReleased(KeyEvent e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void keyPressed(KeyEvent e) {
					try {

						System.out.println("key " + e.getKeyChar() + " id "
								+ e.getKeyCode());
						switch (e.getKeyCode()) {

						case 109:						
							// -
							scale = scale - scalePlus;
							applyZoom();
							repaint();
							break;
						case 107:						
							// +
							scale = scale + scalePlus;
							applyZoom();
							repaint();
							break;
						case 37:
							// izquierda
							point.x = point.x - DISTANCE;
							repaint();
							break;
						case 38:
							// arriba
							point.y = point.y - DISTANCE;
							repaint();
							break;
						case 39:
							// derecha
							point.x = point.x + DISTANCE;
							repaint();
							break;
						case 40:
							// abajo
							point.y = point.y + DISTANCE;
							repaint();
							break;

						case 82: // r rotate
							objSelected.setAngle(objSelected.getAngle()
									+ ROTATE_ANGLE);
							if (objSelected.getAngle() > 360) {
								objSelected.setAngle(0);
							}
							DataSingleton.getInstance().setObjectSelected(
									objSelected);
							repaint();
							break;
						case 69: // e rotate
							objSelected.setAngle(objSelected.getAngle()
									- ROTATE_ANGLE);
							if (objSelected.getAngle() < 0) {
								objSelected.setAngle(360);
							}
							DataSingleton.getInstance().setObjectSelected(
									objSelected);
							repaint();
							break;

						default:
							break;
						}

					} catch (Exception e2) {
						e2.printStackTrace();
					}

				}
			});

			this.addMouseMotionListener(new MouseMotionListener() {

				@Override
				public void mouseMoved(MouseEvent e) {
					try {
						try {
							float x = e.getX();
							float y = e.getY();
							System.out.println("mouse X" + x + " Y" + y);
							if (point.x > WIDTH / 2) {
								x = x + point.x - WIDTH / 2;
							}
							if (point.y > HEIGHT / 2) {
								y = y + point.y - HEIGHT / 2;
							} else if (point.y < HEIGHT / 2) {
								// / y = ;
								y = y + point.y - HEIGHT / 2;
							}

							System.out.println("mouse X" + x + " Y" + y);

							if (drawGrid) {
								if (x % gridWidth != 0) {
									x = x - x % gridWidth;
								}

								if (y % gridHeight != 0) {
									y = y - y % gridHeight;
								}
							}

							objSelected = DataSingleton.getInstance()
									.getObjectSelected().cloneObject();
							objSelected.setX(x
									- objSelected.getImage().getWidth() / 2);
							objSelected.setY(y
									- objSelected.getImage().getHeight() / 2);
							// youtuber
							objSelected.setX(x);
							objSelected.setY(y);
							repaint();

						} catch (Exception e2) {
						}

					} catch (Exception e2) {
						e2.printStackTrace();
					}

				}

				@Override
				public void mouseDragged(MouseEvent e) {
					// TODO Auto-generated method stub

				}
			});

			this.addMouseListener(new MouseListener() {

				@Override
				public void mouseReleased(MouseEvent e) {

				}

				@Override
				public void mousePressed(MouseEvent e) {

				}

				@Override
				public void mouseExited(MouseEvent e) {
					paintMouse = false;
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					try {

						paintMouse = true;

					} catch (Exception e2) {

					}
				}

				@Override
				public void mouseClicked(MouseEvent e) {
					try {
						if(scale!=1f){
							scale = 1;
							applyZoom();
							repaint();
							return;
						}
						
						if (SwingUtilities.isRightMouseButton(e)) {
							objSelected = null;
							DataSingleton.getInstance().setObjectSelected(null);
							DataSingleton.getInstance().setState(State.NORMAL);
							return;
						}

						float x = e.getX();
						float y = e.getY();
						if (point.x > WIDTH / 2) {
							x = x + point.x - WIDTH / 2;
						}
						if (point.y > HEIGHT / 2) {
							y = y + point.y - HEIGHT / 2;
						} else {
							y = y + point.y - HEIGHT / 2;
						}

						if (DataSingleton.getInstance().getState()
								.equals(State.NORMAL)) {

							MyObjectXml objSel = calculateSelectedObject(new Point(
									(int) x, (int) y));
							if (objSel == null) {
								return;
							}

							DataSingleton.getInstance().setState(State.MODIFY);
							DataSingleton.getInstance().setObjectSelected(
									objSel);
							objSelected = objSel;
							return;
						}

						if (drawGrid) {
							if (x % gridWidth != 0) {
								x = x - x % gridWidth;
							}

							if (y % gridHeight != 0) {
								y = y - y % gridHeight;
							}
						}
						// System.out.println("mouse X"+ x+" Y"+y);
						MyObjectXml obj = new MyObjectXml();
						obj = DataSingleton.getInstance().getObjectSelected()
								.cloneObject();
						obj.setX(x - objSelected.getImage().getWidth() / 2);
						obj.setY(y - objSelected.getImage().getHeight() / 2);
						// youtuber
						obj.setX(x);
						obj.setY(y);
						objectList.add(obj);
						repaint();

					} catch (Exception e2) {

					}
				}

			});

			ThreadUpdate upd = new ThreadUpdate(this);
			upd.start();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private MyObjectXml calculateSelectedObject(Point point) {
		try {

			MyObjectXml objSel = null;
			for (MyObjectXml obj : objectList) {

				if (point.getX() >= obj.getX()
						&& point.getX() <= obj.getX()
								+ obj.getImage().getWidth()) {

					if (point.getY() >= obj.getY()
							&& point.getY() <= obj.getY()
									+ obj.getImage().getHeight()) {
						DataSingleton.getInstance().setObjectSelected(obj);
						objSel = obj;
					}

				}

			}

			objectList.remove(objSel);

			return objSel;
		} catch (Exception e) {

		}
		return null;
	}

	public void move() {
		try {

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

			int xMove = 0;
			int yMove = 0;
			if (point.getX() > WIDTH / 2) {
				g2d.translate(WIDTH / 2 - point.x, 0);
				xMove = WIDTH / 2 - point.x;
			}
			// if (point.getY() > HEIGHT / 2) {
			g2d.translate(0, HEIGHT / 2 - point.y);
			yMove = HEIGHT / 2 - point.y;
			// }

			AffineTransform transAux = g2d.getTransform();

			g.clearRect(point.x - 800, point.y - 800, 1600, 1600);

			g.setColor(new Color(45 / 255f, 139 / 255f, 255 / 255f));
			g.fillRect(point.x - 800, point.y - 800, 1600, 1600);
			g.setColor(Color.black);
			g.drawOval(point.x, point.y, 10, 10);

			for (MyObjectXml obj : objectList) {
				// AffineTransform affineTransform = new
				// AffineTransform(transAux);
				// affineTransform.setToRotation(Math.toRadians(obj.getAngle()),obj.getImage().getWidth()/2
				// + obj.getX() , obj.getImage().getHeight()/2 + obj.getY());
				// g2d.setTransform(affineTransform);
				//
				g2d.drawImage(obj.getImage(), (int) (obj.getX() * obj.getWidth()),
						(int) (obj.getY()* obj.getWidth()) , (int)(obj.getImage().getWidth() * obj.getWidth()),(int)(obj.getImage().getHeight() * obj.getWidth()), null);

			}

			if (paintMouse) {

				// AffineTransform affineTransform = new AffineTransform(trans);
				// affineTransform.setToRotation(Math.toRadians(objSelected.getAngle()),
				// objSelected.getImage().getWidth()/2+
				// objSelected.getX(),objSelected.getImage().getHeight()/2 +
				// objSelected.getY());
				// g2d.setTransform(affineTransform);

				//g2d.drawImage(objSelected.getImage(), (int) objSelected.getX(),
				//		(int) objSelected.getY(), null);
				g2d.drawImage(objSelected.getImage(), (int) objSelected.getX(),
						(int) objSelected.getY(), (int)(objSelected.getImage().getWidth() * objSelected.getWidth()),(int)(objSelected.getImage().getHeight() * objSelected.getWidth()), null);

				g2d.drawString(
						"x:" + objSelected.getX() + " y:" + objSelected.getY(),
						objSelected.getX(), objSelected.getY()
								+ objSelected.getImage().getHeight());

			}

			/*
			 * g2d.clearRect((int) (objSelected.getX() +
			 * objSelected.getImage().getWidth() / 2 - 10), (int)
			 * (objSelected.getY() + objSelected.getImage().getHeight() / 2 -
			 * 10), 100, 12); g2d.drawString( +objSelected.getX() +
			 * objSelected.getImage().getWidth() / 2 + "," + objSelected.getY()
			 * + objSelected.getImage().getHeight() / 2, objSelected.getX() +
			 * objSelected.getImage().getWidth() / 2, objSelected.getY() +
			 * objSelected.getImage().getHeight() / 2);
			 */
			if (drawGrid) {
				drawGrid((int) gridWidth, (int) gridHeight, g2d);
			}
			g2d.setTransform(trans);

		} catch (Exception e) {
		}
	}
	
	private void applyZoom(){
		try {
			
			for (MyObjectXml obj : objectList) {
				obj.setWidth(scale);
				obj.setHeight(scale);
				
				//obj.setX(obj.getX()*scale);
				//obj.setY(obj.getY()*scale);
				
			}
			try {
			
				DataSingleton.getInstance().getObjectSelected().setWidth(scale);
				DataSingleton.getInstance().getObjectSelected().setHeight(scale);
				
				
				float x = objSelected.getX();
				float y = objSelected.getY();
				objSelected = DataSingleton.getInstance().getObjectSelected().cloneObject();
				
				objSelected.setX(x);
				objSelected.setY(y);
				
			} catch (Exception e) {
				
			}
			
			
			repaint();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void drawGrid(int xWidth, int yHeight, Graphics2D g2d) {
		try {

			Double beginX = point.getX() - 400;
			Double beginY = point.getY() - 240;

			// xWidth = 50;
			// yHeight = 50;
			for (int i = 0; i < 100; i++) {

				g2d.setColor(new Color(200 / 255f, 179 / 255f, 23 / 255f, 0.5f));

				g2d.drawRect((int) (beginX + i * xWidth), (beginY.intValue()),
						1, 1100);
				g2d.drawRect((beginX.intValue()), (int) (beginY + i * yHeight),
						1100, 1);

				float xV = ((int) (beginX + i * xWidth));
				if (xV % 400 == 0) {// draw vertical

					g2d.setColor(Color.RED);
					g2d.drawRect((int) ((int) (beginX + i * xWidth)),
							(beginY.intValue()), 1, 5000);
					g2d.drawString((xV / 400) + "sc", xV,
							beginY.intValue() + 150);

				}

				float yV = (int) (beginY + i * yHeight);
				if (yV % 240 == 0) {// draw horizontal

					g2d.setColor(Color.magenta);
					g2d.drawRect((beginX.intValue()), (int) (beginY + i
							* yHeight), 1200, 1);
					g2d.drawString((yV / 240) + "sc", beginX.intValue() + 200,
							yV);
				}

			}
			g2d.setColor(Color.black);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public class ThreadUpdate extends Thread {

		JPanel panel;

		public ThreadUpdate(JPanel panel) {
			this.panel = panel;
		}

		public void run() {
			try {

				while (true) {
					Thread.sleep(1000);
					panel.repaint();

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public Point getPoint() {
		return point;
	}

	public void setPoint(Point point) {
		this.point = point;
	}

	public int getDISTANCE() {
		return DISTANCE;
	}

	public void setDISTANCE(int dISTANCE) {
		DISTANCE = dISTANCE;
	}

	public int getWIDTH() {
		return WIDTH;
	}

	public void setWIDTH(int wIDTH) {
		WIDTH = wIDTH;
	}

	public int getHEIGHT() {
		return HEIGHT;
	}

	public void setHEIGHT(int hEIGHT) {
		HEIGHT = hEIGHT;
	}

	public boolean isPaintMouse() {
		return paintMouse;
	}

	public void setPaintMouse(boolean paintMouse) {
		this.paintMouse = paintMouse;
	}

	public ArrayList<MyObjectXml> getObjectList() {
		return objectList;
	}

	public void setObjectList(ArrayList<MyObjectXml> objectList) {
		this.objectList = objectList;

	}

	public MyObjectXml getObjSelected() {
		return objSelected;
	}

	public void setObjSelected(MyObjectXml objSelected) {
		this.objSelected = objSelected;
	}

	public void loadImagesList() {
		try {

			for (MyObjectXml obj : objectList) {
				System.out.println("name " + obj.getName() + " id "
						+ obj.getId());
				obj.setImage(ImageSingleton.getInstance().getImage(obj.getId()));
				obj.setAngle(obj.getAngle());
			}
			repaint();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public int getROTATE_ANGLE() {
		return ROTATE_ANGLE;
	}

	public void setROTATE_ANGLE(int rOTATE_ANGLE) {
		ROTATE_ANGLE = rOTATE_ANGLE;
	}

	public boolean isDrawGrid() {
		return drawGrid;
	}

	public void setDrawGrid(boolean drawGrid) {
		this.drawGrid = drawGrid;
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
