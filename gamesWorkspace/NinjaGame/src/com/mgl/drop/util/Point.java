package com.mgl.drop.util;

public class Point {

	private float x;
	private float y;

	public Point(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public static float distanceBetween(Point p1, Point p2) {

		float x = p1.getX() - p2.getX();
		float y = p1.getY() - p2.getY();

		return (float) Math.sqrt((x * x) + (y * y));
	}

	public static float angleBetween(Point p1, Point p2) {

		float x = p2.getX() - p1.getX();
		float y = p2.getY() - p1.getY();

		return (float) Math.atan2(y, x);

	}

	public static float angleBetween2(Point p1, Point p2) {

		float x = p2.getX() - p1.getX();
		float y = p2.getY() - p1.getY();

		return (float) Math.atan(y / x);

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

	public static Point calculatePointPlusDistance(Point beginPoint,
			Point endPoint, float maxDistance) {

		Point p1 = new Point(0, 0);
		Point p2 = new Point(endPoint.getX() - beginPoint.getX(),
				endPoint.getY() - beginPoint.getY());
		float angle = Point.angleBetween2(p1, p2);

		float x = 0;
		float y = 0;

		if (p1.getX() <= p2.getX() && p1.getY() <= p2.getY()) {
			x = (float) (Math.sin(angle) * maxDistance);
			y = (float) (Math.cos(angle) * maxDistance);

			return new Point(x + beginPoint.getX(), y + beginPoint.getY());
		}

		if (p1.getX() <= p2.getX() && p1.getY() >= p2.getY()) {
			x = (float) (Math.sin(angle) * maxDistance);
			y = (float) (Math.cos(angle) * maxDistance);
			x = x * -1;
			y = y * -1;

			return new Point(x + beginPoint.getX(), y + beginPoint.getY());
		}

		if (p1.getX() >= p2.getX() && p1.getY() <= p2.getY()) {
			x = (float) (Math.sin(angle) * maxDistance);
			y = (float) (Math.cos(angle) * maxDistance);

			return new Point(x + beginPoint.getX(), y + beginPoint.getY());
		}

		if (p2.getX() <= p1.getX() && p2.getY() <= p1.getY()) {
			x = (float) (Math.sin(angle) * maxDistance);
			y = (float) (Math.cos(angle) * maxDistance);

			x = x * -1;
			y = y * -1;

			return new Point(x + beginPoint.getX(), y + beginPoint.getY());
		}

		x = (float) (Math.sin(angle) * maxDistance);
		y = (float) (Math.cos(angle) * maxDistance);

		return new Point(x + beginPoint.getX(), y + beginPoint.getY());
	}

	
}
