package com.mgl.drop.game.controller;

import java.util.ArrayList;
import java.util.Stack;

import android.provider.UserDictionary.Words;

import com.appflood.mraid.p;
import com.mgl.drop.game.controller.dijkstra.DijkstraPathFind;
import com.mgl.drop.game.controller.dijkstra.NodeArc;
import com.mgl.drop.game.controller.dijkstra.WorldNode;
import com.mgl.drop.game.sprites.SpriteCollitionRpg;
import com.mgl.drop.util.Point;

public class BattleWorld {

	private int width;
	private int height;

	private float boxWidth;
	private float boxHeight;

	private WorldNode[][] worldNode;

	public BattleWorld(float widthPixel, float heightPixel, float boxWidth,
			float boxHeight) {
		super();
		try {

			int width1 = (int) (widthPixel / boxWidth);
			int height1 = (int) (heightPixel / boxHeight);

			this.width = width1 - 4;
			this.height = height1 + 1;
			this.boxWidth = boxWidth;
			this.boxHeight = boxHeight;

			worldNode = new WorldNode[this.width][this.height];

			for (int j = 0; j < height; j++) {
				for (int i = 0; i < width; i++) {

					float y = j * boxHeight;

					if (j > 0) {
						y = (j * boxHeight - (boxHeight / 4) * j + 2 * j);
					}
					y = y + boxHeight / 4;
					float xP = boxWidth / 4;
					if (i > 0) {
						xP = xP + 2 * i;
					}

					worldNode[i][j] = new WorldNode(i * boxWidth + xP, y,
							WorldNode.EMPTY);

					if (j % 2 != 0) {
						Long state = WorldNode.EMPTY;
						// if((i+1>=width )){
						// state = WorldNode.OCCUPED;
						// }
						worldNode[i][j] = new WorldNode(i * boxWidth + boxWidth
								/ 2 + xP, y, state);

					}
				}

			}

			for (int j = 0; j < height; j++) {
				for (int i = 0; i < width; i++) {
					WorldNode node = worldNode[i][j];
					node.setX(node.getX()+50);
				}
			}
			
			// adding relation to each node
			for (int j = 0; j < height; j++) {
				for (int i = 0; i < width; i++) {

					ArrayList<WorldNode> nodeList = new ArrayList<WorldNode>();

					boolean hasUp = false;
					boolean hasDown = false;
					boolean hasLeft = false;
					boolean hasRight = false;

					// validating up
					if (j - 1 >= 0) {
						hasUp = true;
					}
					// down
					if (j + 1 < height) {
						hasDown = true;
					}
					// right
					if (i + 1 < width) {
						nodeList.add(worldNode[i + 1][j]);
						hasRight = true;
					}
					// left
					if (i - 1 >= 0) {
						nodeList.add(worldNode[i - 1][j]);
						hasLeft = true;
					}

					if(j%2==0){
						//arriba derecha
						if(hasUp){
							nodeList.add(worldNode[i][j-1]);
						}
						//arriba izquierda
						if(hasUp && hasLeft){
							nodeList.add(worldNode[i-1][j-1]);
						}
						//abajo derecha
						if(hasDown){
							nodeList.add(worldNode[i][j+1]);
						}
						//abajo izquierda
						if(hasDown && hasLeft){
							nodeList.add(worldNode[i-1][j+1]);
						}
					}else{
						//arriba izquierda
						if(hasUp){
							nodeList.add(worldNode[i][j-1]);
						}
						//arriba derecha
						if(hasUp && hasRight){
							nodeList.add(worldNode[i+1][j-1]);
						}
						//abajo izquierda
						if(hasDown){
							nodeList.add(worldNode[i][j+1]);
						}
						//abajo derecha
						if(hasDown && hasRight){
							nodeList.add(worldNode[i+1][j+1]);
						}
						
					}
					
					
					worldNode[i][j].setNodeList(nodeList);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public WorldNode getNode(int i, int j) {
		try {
			return worldNode[i][j];
		} catch (Exception e) {
		}
		return null;
	}

	public class RowGroup {

		private int beginX = 0;
		private ArrayList<WorldNode> row;

		public RowGroup(int beginX) {
			this.beginX = beginX;
			row = new ArrayList<WorldNode>();
		}

		public int getBeginX() {
			return beginX;
		}

		public void setBeginX(int beginX) {
			this.beginX = beginX;
		}

		public ArrayList<WorldNode> getRow() {
			return row;
		}

		public void setRow(ArrayList<WorldNode> row) {
			this.row = row;
		}

	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public float getBoxWidth() {
		return boxWidth;
	}

	public void setBoxWidth(float boxWidth) {
		this.boxWidth = boxWidth;
	}

	public float getBoxHeight() {
		return boxHeight;
	}

	public void setBoxHeight(float boxHeight) {
		this.boxHeight = boxHeight;
	}

	public WorldNode[][] getWorldNode() {
		return worldNode;
	}

	public void setWorldNode(WorldNode[][] worldNode) {
		this.worldNode = worldNode;
	}

	public void createCollitions(ArrayList<SpriteCollitionRpg> collitionList) {
		try {

			for (SpriteCollitionRpg spr : collitionList) {

				createCollition(spr);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void createCollition(SpriteCollitionRpg spr) {
		try {

			for (int j = 0; j < height; j++) {
				for (int i = 0; i < width; i++) {

					WorldNode node = worldNode[i][j];
					if (validateCollitionWithPoint(spr.getX(), spr.getWidth(),
							spr.getY(), spr.getHeight(),
							new Point(node.getX() + boxWidth / 2, node.getY()
									+ boxHeight / 2))) {
						node.setState(WorldNode.OCCUPED);
						continue;
					}

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public Point placeInFreePosition(int x, int y){
		try {
			
			WorldNode nodeAux = null;
			for (int j = 0; j < height; j++) {
				for (int i = 0; i < width; i++) {

					WorldNode node = worldNode[i][j];
					if(validateCollitionWithPoint(node.getX(), boxWidth, node.getY(), boxHeight, new Point(x, y))){
						
						if(nodeAux == null){
							nodeAux = node;
						}else{
							float distanceA = Point.distanceBetween(new Point(nodeAux.getX(), nodeAux.getY()), new Point(x, y));
							float distanceB = Point.distanceBetween(new Point(node.getX(), node.getY()), new Point(x, y) );
							if(distanceB<distanceA){
								nodeAux = node;
							}
						}
						
					}
					
				}
			}
		
			
			if(nodeAux.getState().equals(WorldNode.OCCUPED)){
				return null;
			}
			nodeAux.setState(WorldNode.OCCUPED);
			return new Point(nodeAux.getX()+boxWidth/2, nodeAux.getY()+boxHeight/2);
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Point occupeDesocupePosition(float x, float y, Long worldNodeConstant){
		try {
			
			WorldNode nodeAux = null;
			for (int j = 0; j < height; j++) {
				for (int i = 0; i < width; i++) {

					WorldNode node = worldNode[i][j];
					if(validateCollitionWithPoint(node.getX(), boxWidth, node.getY(), boxHeight, new Point(x, y))){
						//node.setState(worldNodeConstant);
						//return new Point(node.getX(), node.getY());
						if(nodeAux == null){
							nodeAux = node;
						}else{
							float distanceA = Point.distanceBetween(new Point(nodeAux.getX(), nodeAux.getY()), new Point(x, y));
							float distanceB = Point.distanceBetween(new Point(node.getX(), node.getY()), new Point(x, y) );
							if(distanceB<distanceA){
								nodeAux = node;
							}
						}
					}
					
				}
			}
			
			if(nodeAux == null){
				return null;
			}
			
			nodeAux.setState(worldNodeConstant);
			return new Point(nodeAux.getX(), nodeAux.getY());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private boolean validateCollitionWithPoint(float xBegin, float width,
			float yBegin, float height, Point point) {
		try {

			if (point.getX() >= xBegin && point.getX() <= xBegin + width) {
				if (point.getY() >= yBegin && point.getY() <= yBegin + height) {
					return true;
				}
			}

		} catch (Exception e) {
			return true;
		}
		return false;
	}

	public Stack<Point> calculatePath(Point sourcePoint, Point targetPoint,
			int maxDistance) {
		try {
			
			if(sourcePoint ==null || targetPoint == null){
				return null;
			} 
			
			int xBegin = (int) ((sourcePoint.getX() / boxWidth));
			int yBegin = (int) ((sourcePoint.getY() / boxHeight));

			int xEnd = (int) ((targetPoint.getX() / boxWidth));
			int yEnd = (int) ((targetPoint.getY() / boxHeight));

			
			Point rootPoint = null;
			Point endPoint = null;
			
			for (int j = 0; j < height; j++) {
				for (int i = 0; i < width; i++) {

					WorldNode node = worldNode[i][j];
					
					if(validateCollitionWithPoint(node.getX(), boxWidth, node.getY(), boxHeight, sourcePoint)){
						rootPoint = new Point(i, j);
					}
					if(validateCollitionWithPoint(node.getX(), boxWidth, node.getY(), boxHeight, targetPoint)){
						endPoint = new Point(i, j);
					}
					
				}
			}
			
			if(rootPoint==null || endPoint ==null){
				return null;
			}
			
			xBegin = (int) rootPoint.getX();
			yBegin = (int) rootPoint.getY();
			
			xEnd = (int) endPoint.getX();
			yEnd = (int) endPoint.getY();
			// validate limits in world
			if (xBegin < 0) {
				xBegin = 0;
			}
			if (xBegin >= width) {
				xBegin = width - 1;
			}

			if (xEnd < 0) {
				xEnd = 0;
			}
			if (xEnd >= width) {
				xEnd = width - 1;
			}

			WorldNode root = worldNode[xBegin][yBegin];
			WorldNode end = worldNode[xEnd][yEnd];

			if(root.equals(end)){
				return new Stack<Point>();
			}
			
			if (end.getState().equals(WorldNode.OCCUPED)) {
				float minDistance = Float.MAX_VALUE;
				WorldNode nodeValid = null;
				for(NodeArc arc : end.getArcList()){
					WorldNode nA = arc.getTaget();
					if(nA.getState().equals(WorldNode.OCCUPED)){
						continue;
					}
					
					float distance = Point.distanceBetween(new Point(nA.getX(), nA.getY()), endPoint);
					if(distance<= minDistance){
						minDistance = distance;
						nodeValid = nA;
					}
				
				}	
				if(nodeValid==null){
					return new Stack<Point>();
				}else{
					end = nodeValid;
				}
			}

			DijkstraPathFind dijkstra = new DijkstraPathFind();
			Stack<Point> pt = dijkstra.findPath(root, maxDistance, end);
			for(Point p : pt){
				p.setX(p.getX()+boxWidth/2);
				p.setY(p.getY()+boxHeight/2);
			}
			return pt;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
