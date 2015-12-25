package com.mgl.drop.game.controller.dijkstra;

public class NodeArc {

	private float distance;
	private WorldNode source;
	private WorldNode taget;

	public NodeArc(float distance, WorldNode source, WorldNode taget) {
		super();
		this.distance = distance;
		this.source = source;
		this.taget = taget;
	}

	public float getDistance() {
		return distance;
	}

	public void setDistance(float distance) {
		this.distance = distance;
	}

	public WorldNode getSource() {
		return source;
	}

	public void setSource(WorldNode source) {
		this.source = source;
	}

	public WorldNode getTaget() {
		return taget;
	}

	public void setTaget(WorldNode taget) {
		this.taget = taget;
	}

}
