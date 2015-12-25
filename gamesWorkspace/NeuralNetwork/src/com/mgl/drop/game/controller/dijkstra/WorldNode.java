package com.mgl.drop.game.controller.dijkstra;

import java.util.ArrayList;

import com.mgl.drop.util.Point;

public class WorldNode {

	public static Long EMPTY = 1l;
	public static Long OCCUPED = 2l;
	
//	private ArrayList<WorldNode> nodeList = new ArrayList<WorldNode>();
	private ArrayList<NodeArc> arcList = new ArrayList<NodeArc>();
	
	private float x;
	private float y;
	
	private Long state;

	private boolean visited = false;

	public WorldNode(float x, float y, Long state) {
		super();
		this.x = x;
		this.y = y;
		this.state = state;
	}

	public static Long getEMPTY() {
		return EMPTY;
	}

	public static void setEMPTY(Long eMPTY) {
		EMPTY = eMPTY;
	}

	public static Long getOCCUPED() {
		return OCCUPED;
	}

	public static void setOCCUPED(Long oCCUPED) {
		OCCUPED = oCCUPED;
	}

	

	public void setNodeList(ArrayList<WorldNode> nodeList) {
		
		try {
			arcList = new ArrayList<NodeArc>();
			for(WorldNode node : nodeList){
				NodeArc arc = new NodeArc(Point.distanceBetween(new Point(this.getX(), this.getY()), new Point(node.getX(), node.getY())), this, node);
				arcList.add(arc);
			}
			/*
			for(WorldNode node : this.nodeList){
				if(MainDropActivity.getRandomMax(0, 100)<25){
					node.setState(OCCUPED);
				}else{
					node.setState(EMPTY);
				}
			}
			*/
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public Long getState() {
		return state;
	}

	public void setState(Long state) {
		this.state = state;
	}

	public ArrayList<NodeArc> getArcList() {
		return arcList;
	}

	public void setArcList(ArrayList<NodeArc> arcList) {
		this.arcList = arcList;
	}

	public boolean isVisited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}


	
	
	
	
	
}
