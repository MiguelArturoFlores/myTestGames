package com.mgl.drop.util;

import java.util.LinkedList;
import java.util.Queue;


public class Path {

	private Queue<Point> path;

	public Path() {
		super();
		path = new LinkedList<Point>();
		
	}

	@Override
	public Path clone() {
		Path pa = new Path();
		Queue<Point> queue = new LinkedList<Point>();
		for (Point p1 : path) {
			queue.add(new Point(p1.getX(), p1.getY()));
		}
		pa.setPath(queue);
		return pa;
	}

	public boolean isEmpty() {
		return path.isEmpty();
	}

	public void push(Point point) {
		path.add(point);
	}

	public Point pop() {
		return path.poll();
	}

	public Point peek() {
		return path.peek();
	}

	public Queue<Point> getPath() {
		return path;
	}

	public void setPath(Queue<Point> path) {
		this.path = path;
	}

}