package com.mgl.drop.game.controller.dijkstra;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

import com.mgl.drop.util.Point;

public class DijkstraPathFind {

	
	
	
	public Stack<Point> findPath(WorldNode source, float maxDistance, WorldNode target){
		Stack<Point> pointStack = new Stack<Point>();
		try {
			
			PriorityQueue<DijkstraNode> possibleDefinitiveList = new PriorityQueue<DijkstraNode>(); 
			ArrayList<DijkstraNode> definitiveList = new ArrayList<DijkstraNode>();

			//I need to keep the distance in world node as infinite once its in our queue
			boolean stop = false;
			
			DijkstraNode root = new DijkstraNode(source,0f,false,null);
			possibleDefinitiveList.add(root);
			while(!possibleDefinitiveList.isEmpty() && !stop){
				
				//obtener el definitivo
				DijkstraNode node = possibleDefinitiveList.poll();
				
				//in theory this is not necessary
				if(node.getNode().isVisited()){
					continue;
				}
				
				node.setDefinitive(true);
				definitiveList.add(node);
				
				if(node.getNode().equals(target)){
					break;
				}
				
				//agregar hijos del definitivo a la lista
				for(NodeArc arc : node.getNode().getArcList()){
					//si el nodo es definitivo no agregarlo
					if(arc.getTaget().isVisited()){
						continue;
					}
					//asignar distacia de nodo definitivo mas la que ya tiene acumulada
					DijkstraNode nodeSon =  new DijkstraNode(arc.getTaget(), arc.getDistance()+node.getMinDistance(), false, node);
					possibleDefinitiveList.add(nodeSon);
				}
				
				
			}
			DijkstraNode targetDijkstra = null;
			for(DijkstraNode node : definitiveList){
				node.getNode().setVisited(false);
				if(node.getNode().equals(target)){
					targetDijkstra = node;
				}
				//System.out.println("node "+node.getX()+" "+node.getY());
			}
			
			System.out.println("Total visited "+definitiveList.size());
			if(targetDijkstra==null){
				System.out.println("No encontre el nodo");
			}else{
				
				while(targetDijkstra.getSource()!=null){
				//	System.out.println("node "+targetDijkstra.getNode().getX()+" "+targetDijkstra.getNode().getY());
					pointStack.add(new Point(targetDijkstra.getNode().getX(), targetDijkstra.getNode().getY()));
					targetDijkstra = targetDijkstra.getSource();
				}
				
			}
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return pointStack;
	}
	
	public class DijkstraNode implements Comparable<DijkstraNode> {
		
		private WorldNode node;
		private Float minDistance = Float.MAX_VALUE;
		private DijkstraNode source;
		
		
		public DijkstraNode(WorldNode node, Float minDistance,
				boolean isDefinitive, DijkstraNode source) {
			super();
			this.node = node;
			this.minDistance = minDistance;
			node.setVisited(isDefinitive);
			this.source = source;
		}


		public WorldNode getNode() {
			return node;
		}


		public void setNode(WorldNode node) {
			this.node = node;
		}



		public Float getMinDistance() {
			return minDistance;
		}



		public void setMinDistance(Float minDistance) {
			this.minDistance = minDistance;
		}



		public boolean isDefinitive() {
			return node.isVisited();
		}



		public void setDefinitive(boolean isDefinitive) {
			node.setVisited(isDefinitive);
		}



		@Override
		public int compareTo(DijkstraNode another) {
			
				return this.minDistance.compareTo(another.minDistance);
		}


		public DijkstraNode getSource() {
			return source;
		}


		public void setSource(DijkstraNode source) {
			this.source = source;
		}
		
		
		
	}

	
	
}

