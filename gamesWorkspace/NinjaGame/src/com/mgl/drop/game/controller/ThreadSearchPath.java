package com.mgl.drop.game.controller;

import java.util.Stack;

import com.mgl.drop.util.Point;

public class ThreadSearchPath extends Thread{
	
	
	private boolean pathFound = false;
	private boolean lookingForPath = false;
	private Point beginPoint;
	private Point endPoint;
	private LevelController controller;
	private Stack<Point> path;
	private int depth;
	
	public ThreadSearchPath(LevelController controller,Point beginPoint, Point endPoint , int depth){
		try {
		
			pathFound = false;
			lookingForPath = false;
			this.controller = controller;
			this.beginPoint = beginPoint;
			this.endPoint = endPoint;
			this.depth = depth;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

	public void run(){
		try {
			
			lookingForPath = true;
			path = controller.getLevelWorld().calculatePath(beginPoint,endPoint,400);
			pathFound = true;
			lookingForPath = false;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public boolean isLookingForPath() {
		return lookingForPath;
	}

	public boolean isPathFound() {
		return pathFound;
	}

	public Stack<Point> getPath() {
		return path;
	}

	public void setPath(Stack<Point> path) {
		this.path = path;
	}
	
}
