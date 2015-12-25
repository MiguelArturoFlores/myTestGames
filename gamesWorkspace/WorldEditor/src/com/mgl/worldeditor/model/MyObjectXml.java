package com.mgl.worldeditor.model;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class MyObjectXml {

	private String name;
	private int id;
	private BufferedImage image;
	private BufferedImage imageOriginal;
	private int angle = 0;
	private ArrayList<Parameter> parameterList;
	
	private float width = 1;
	private float height = 1;

	private float x;
	private float y;

	public MyObjectXml() {
		parameterList = new ArrayList<>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
		this.imageOriginal = image;
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

	public int getAngle() {
		return angle;
	}

	public void setAngle(int angle) {
		this.angle = angle;
		try {

			if (imageOriginal == null) {
				return;
			}

			double sin = Math.abs(Math.sin(Math.toRadians(angle))), cos = Math
					.abs(Math.cos(Math.toRadians(angle)));

			int w = imageOriginal.getWidth(null), h = imageOriginal
					.getHeight(null);

			int neww = (int) Math.floor(w * cos + h * sin), newh = (int) Math
					.floor(h * cos + w * sin);

			BufferedImage bimg = new BufferedImage(neww, newh,
					BufferedImage.TRANSLUCENT);
			Graphics2D g = bimg.createGraphics();

			g.translate((neww - w) / 2, (newh - h) / 2);
			g.rotate(Math.toRadians(angle), w / 2, h / 2);
			g.drawRenderedImage(imageOriginal, null);
			g.dispose();
			image = bimg;

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public MyObjectXml cloneObject() {
		MyObjectXml obj = new MyObjectXml();
		try {
			obj.setX(x);
			obj.setY(y);
			obj.setId(id);
			obj.setName(name);
			obj.image = (image);
			obj.imageOriginal = (imageOriginal);
			obj.angle = (angle);
			obj.setWidth(width);
			obj.setHeight(height);
			obj.parameterList = new ArrayList<>();
			for (Parameter p : parameterList) {
				Parameter param = new Parameter(p.getParameterName().getText(),
						p.getValue().getText());
				obj.getParameterList().add(param);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return obj;
	}

	public ArrayList<Parameter> getParameterList() {
		return parameterList;
	}

	public void setParameterList(ArrayList<Parameter> parameterList) {
		this.parameterList = parameterList;
	}

	public String getParameters() {
		try {
			String retVal = new String();
			for(Parameter p : parameterList){
				String val = p.getValue().getText();
				if(val==null || val.isEmpty()){
					val = "0";
				}
				retVal = retVal+p.getParameterName().getText()+"@"+val+"#";
			}
			retVal = retVal.substring(0,retVal.length()-1);
			return retVal;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new String();
	}

	public void setParameters(String paramsStr) {
		try {
			
			parameterList = new ArrayList<>();
			
			String[] paramArrayStr = paramsStr.split("#");
			for(int i = 0 ; i < paramArrayStr.length ; i++){
				String[] individualParam = paramArrayStr[i].split("@");
				try {
					Parameter par = new Parameter(individualParam[0],individualParam[1]);
					parameterList.add(par);
				} catch (Exception e) {
					continue;
				}
				
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public BufferedImage getImageOriginal() {
		return imageOriginal;
	}

	public void setImageOriginal(BufferedImage imageOriginal) {
		this.imageOriginal = imageOriginal;
	}

	
	
}
