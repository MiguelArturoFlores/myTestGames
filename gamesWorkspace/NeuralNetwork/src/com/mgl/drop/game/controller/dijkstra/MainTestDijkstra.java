package com.mgl.drop.game.controller.dijkstra;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

import com.mgl.drop.util.Point;
import com.mgl.neural.Neuron;

public class MainTestDijkstra {

	public static void main(String[] args) {

		try {
			
			//testDijkstra();
			
			testAngle();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void testAngle() {
		
		try {
			
			//derecha 45 >>> -45
			//izquierda -135 >>>  -180  y 135  - 180   
			//abajo  45 - 135			
			//arriba -45 ** -135
			
			Point p1 = new Point(0, 0);
			Point p2 = new Point(0, 10);
			
			
			float angle = Point.angleBetween(p1, p2);
			angle = (float) (angle * 180/Math.PI);
			
			System.out.println("angle "+angle);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

	private static void testDijkstra() {
		// g f c
		WorldNode nodeA = new WorldNode(0, 0, WorldNode.EMPTY);
		WorldNode nodeB = new WorldNode(0, 1, WorldNode.EMPTY);
		WorldNode nodeC = new WorldNode(0, 2, WorldNode.EMPTY);
		WorldNode nodeD = new WorldNode(0, 3, WorldNode.EMPTY);
		WorldNode nodeE = new WorldNode(0, 4, WorldNode.EMPTY);
		WorldNode nodeF = new WorldNode(0, 5, WorldNode.EMPTY);
		WorldNode nodeG = new WorldNode(0, 6, WorldNode.EMPTY);

		// node A arcs
		ArrayList<NodeArc> arcAlist = new ArrayList<>();
		arcAlist.add(new NodeArc(2f, nodeA, nodeB));
		arcAlist.add(new NodeArc(3f, nodeA, nodeC));
		nodeA.setArcList(arcAlist);

		// node B arcs
		ArrayList<NodeArc> arcB = new ArrayList<>();
		arcB.add(new NodeArc(2f, nodeB, nodeA));
		arcB.add(new NodeArc(1f, nodeB, nodeE));
		arcB.add(new NodeArc(4f, nodeB, nodeD));
		nodeB.setArcList(arcB);

		// node C arcs
		ArrayList<NodeArc> arcC = new ArrayList<>();
		arcC.add(new NodeArc(3f, nodeC, nodeA));
		arcC.add(new NodeArc(5f, nodeC, nodeD));
		arcC.add(new NodeArc(2f, nodeC, nodeF));
		nodeC.setArcList(arcC);

		// node D arcs
		ArrayList<NodeArc> arcD = new ArrayList<>();
		arcD.add(new NodeArc(4f, nodeD, nodeB));
		arcD.add(new NodeArc(5f, nodeD, nodeC));
		nodeD.setArcList(arcD);

		// node E arcs
		ArrayList<NodeArc> arcE = new ArrayList<>();
		arcE.add(new NodeArc(1f, nodeE, nodeB));
		arcE.add(new NodeArc(7f, nodeE, nodeF));
		arcE.add(new NodeArc(5f, nodeE, nodeG));
		nodeE.setArcList(arcE);

		// node F arcs
		ArrayList<NodeArc> arcF = new ArrayList<>();
		arcF.add(new NodeArc(2f, nodeF, nodeC));
		arcF.add(new NodeArc(7f, nodeF, nodeE));
		arcF.add(new NodeArc(5f, nodeF, nodeG));
		nodeF.setArcList(arcF);

		// node G arcs
		ArrayList<NodeArc> arcG = new ArrayList<>();
		arcG.add(new NodeArc(5f, nodeG, nodeE));
		arcG.add(new NodeArc(5f, nodeG, nodeF));
		nodeE.setArcList(arcG);

		DijkstraPathFind dijkstra = new DijkstraPathFind();
		Stack<Point> pointQueue = dijkstra.findPath(nodeA, 20, nodeE);

		while (!pointQueue.isEmpty()) {
			Point p = pointQueue.pop();
			System.out.println(p.getX() + " " + p.getY());
		}

	}

}
