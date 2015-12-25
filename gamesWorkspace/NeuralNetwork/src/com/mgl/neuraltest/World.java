package com.mgl.neuraltest;

import java.awt.Graphics;
import java.util.ArrayList;

public class World {

	private Long world[][];

	private int width;
	private int height;

	private NeuralObject object1;
	private NeuralObject object2;

	private boolean mustStop = false;
	private Double time = 0D;

	public World(int width, int height) {

		try {

			this.width = width;
			this.height = height;

			world = new Long[width][height];

			for (int j = 0; j < height; j++) {
				for (int i = 0; i < width; i++) {
					world[i][j] = WorldConstants.EMPTY;

				}
			}
			object1 = new NeuralObject(0, 0);
			world[object1.getX()][object1.getY()] = WorldConstants.NEURAL_OBJECT;

			object2 = new NeuralObject(5, 5);
			world[object2.getX()][object2.getY()] = WorldConstants.NEURAL_OBJECT;
			update();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void update() {
		try {

			time = (double) System.currentTimeMillis();

			while (!mustStop) {

				time = System.currentTimeMillis() - time;

				update(time);

				object1.update(time.floatValue(), this);
				object2.update(time.floatValue(), this);

				printWorld();
				Thread.sleep(800);

			}

			System.out.println("Finish Round");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void update(Double time2) {
		try {

			// world[object1.getX()][object1.getY()]=WorldConstants.NEURAL_OBJECT;
			// world[object2.getX()][object2.getY()]=WorldConstants.NEURAL_OBJECT;

			// if()

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void draw(Graphics g) {
		try {

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void printWorld() {
		try {

			for (int j = 0; j < height; j++) {
				for (int i = 0; i < width; i++) {
					System.out.print(world[i][j] + " - ");

				}
				System.out.println("");
			}
			System.out
					.println("--------------------------------------------------------------------------------");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static float getRandomMax(int min, int max) {

		try {

			Double val = min + ((Math.random() * 123456323) % (1 + max - min));
			return val.floatValue();

		} catch (Exception e) {

			e.printStackTrace();

		}

		return 1;
	}

	public boolean canMoveDown(int x, int y) {
		try {

			if (y + 1 <= height - 1) {
				return true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean canMoveUp(int x, int y) {
		try {

			if (y - 1 >= 0) {
				return true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public void moveDown(int x, int y, Long objectType) {
		try {

			if (!canMoveDown(x, y)) {
				return;
			}

			world[x][y] = WorldConstants.EMPTY;
			world[x][y + 1] = objectType;

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void moveUp(int x, int y, Long objectType) {
		try {

			if (!canMoveUp(x, y)) {
				return;
			}

			world[x][y] = WorldConstants.EMPTY;
			world[x][y - 1] = objectType;

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean canMoveRight(int x, int y) {
		try {

			if (x + 1 <= width-1) {
				return true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean canMoveLeft(int x, int y) {
		try {

			if (x - 1 >= 0) {
				return true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public void moveRight(int x, int y, Long objectType) {
		try {

			if (!canMoveRight(x, y)) {
				return;
			}

			world[x][y] = WorldConstants.EMPTY;
			world[x+1][y] = objectType;

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void moveLeft(int x, int y, Long objectType) {
		try {

			if (!canMoveLeft(x, y)) {
				return;
			}

			world[x][y] = WorldConstants.EMPTY;
			world[x-1][y] = objectType;

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Long[][] getWorld() {
		return world;
	}

	public void setWorld(Long[][] world) {
		this.world = world;
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

	public NeuralObject getObject1() {
		return object1;
	}

	public void setObject1(NeuralObject object1) {
		this.object1 = object1;
	}

	public NeuralObject getObject2() {
		return object2;
	}

	public void setObject2(NeuralObject object2) {
		this.object2 = object2;
	}

	public boolean isMustStop() {
		return mustStop;
	}

	public void setMustStop(boolean mustStop) {
		this.mustStop = mustStop;
	}

	public Double getTime() {
		return time;
	}

	public void setTime(Double time) {
		this.time = time;
	}

	
	
}
