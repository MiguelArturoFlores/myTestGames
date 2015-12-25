package com.mgl.drop.game.controller;

import java.util.ArrayList;
import java.util.Queue;
import java.util.Stack;

import com.mgl.drop.game.controller.dijkstra.DijkstraPathFind;
import com.mgl.drop.game.controller.dijkstra.WorldNode;
import com.mgl.drop.game.sprites.SpriteCollitionRpg;
import com.mgl.drop.util.Point;

public class LevelWorld {

	private int width;
	private int height;

	private float boxWidth;
	private float boxHeight;

	private WorldNode[][] worldNode;

	public LevelWorld(float widthPixel, float heightPixel, float boxWidth,
			float boxHeight) {
		super();

		int width = (int) (widthPixel / boxWidth);
		int height = (int) (heightPixel / boxHeight);

		this.width = width;
		this.height = height;
		this.boxWidth = boxWidth;
		this.boxHeight = boxHeight;

		worldNode = new WorldNode[width][height];
		generateWorld();
	}

	public LevelWorld() {
		super();

	}

	public void createBattleWorld(float widthPixel, float heightPixel,
			float boxWidth, float boxHeight) {
		try {

			int width = (int) (widthPixel / boxWidth);
			int height = (int) (heightPixel / boxHeight);

			this.width = width;
			this.height = height;
			this.boxWidth = boxWidth;
			this.boxHeight = boxHeight;

			worldNode = new WorldNode[width][height];

			// creating empty node World
			for (int j = 0; j < height; j++) {
				for (int i = 0; i < width; i++) {

					worldNode[i][j] = new WorldNode(i * boxWidth,
							j * boxHeight, WorldNode.EMPTY);

				}
			}

			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void generateWorld() {
		try {

			// creating empty node World
			for (int j = 0; j < height; j++) {
				for (int i = 0; i < width; i++) {

					worldNode[i][j] = new WorldNode(i * boxWidth,
							j * boxHeight, WorldNode.EMPTY);

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
						nodeList.add(worldNode[i][j - 1]);
						hasUp = true;
					}
					// down
					if (j + 1 < height) {
						nodeList.add(worldNode[i][j + 1]);
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

					if (true) {
						worldNode[i][j].setNodeList(nodeList);
						continue;
					}
					// up left
					if (hasUp && hasLeft) {
						nodeList.add(worldNode[i - 1][j - 1]);
					}
					// up right
					if (hasUp && hasRight) {
						nodeList.add(worldNode[i + 1][j - 1]);
					}
					// down left
					if (hasDown && hasLeft) {
						nodeList.add(worldNode[i - 1][j + 1]);
					}
					// down right
					if (hasDown && hasRight) {
						nodeList.add(worldNode[i + 1][j + 1]);
					}

					worldNode[i][j].setNodeList(nodeList);

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
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

	public WorldNode getNode(int i, int j) {
		try {
			return worldNode[i][j];
		} catch (Exception e) {
		}
		return null;
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

			int x = (int) ((spr.getX() / boxWidth));
			int y = (int) ((spr.getY() / boxHeight));

			float widthAux = ((spr.getWidth() / boxWidth));
			float heightAux = ((spr.getHeight() / boxHeight));

			int width = Float.valueOf(widthAux).intValue();
			int height = Float.valueOf(heightAux).intValue();

			if (widthAux % boxWidth != 0) {
				width++;
			}

			if (heightAux % boxHeight != 0) {
				height++;
			}
			for (int j = y; j < y + height; j++) {
				for (int i = x; i < x + width; i++) {

					WorldNode node = worldNode[i][j];
					if (node.getX() + boxWidth / 2 >= spr.getX()
							&& node.getX() + boxWidth / 2 <= spr.getX()
									+ spr.getWidth()) {
						if (node.getY() + boxHeight / 2 >= spr.getY()
								&& node.getY() + boxHeight / 2 <= spr.getY()
										+ spr.getHeight()) {
							node.setState(WorldNode.OCCUPED);
						}
					}

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Stack<Point> calculatePath(Point sourcePoint, Point targetPoint,
			int maxDistance) {
		try {

			int xBegin = (int) ((sourcePoint.getX() / boxWidth));
			int yBegin = (int) ((sourcePoint.getY() / boxHeight));

			int xEnd = (int) ((targetPoint.getX() / boxWidth));
			int yEnd = (int) ((targetPoint.getY() / boxHeight));

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

			if (end.getState().equals(WorldNode.OCCUPED)) {
				return new Stack<Point>();
			}

			DijkstraPathFind dijkstra = new DijkstraPathFind();
			return dijkstra.findPath(root, maxDistance, end);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public float getWidthInPixel() {
		return width * boxWidth;
	}

	public float getHeightInPixel() {
		return height * boxHeight;
	}
}
