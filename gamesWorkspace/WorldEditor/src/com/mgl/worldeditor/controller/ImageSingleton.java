package com.mgl.worldeditor.controller;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;

import com.mgl.worldeditor.model.MyObjectXml;

public class ImageSingleton {

	private static ImageSingleton instance = null;

	private HashMap<Long, BufferedImage> imageHash;

	private ImageSingleton() {
		try {
			
			loadObjectFromFile();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void loadObjectFromFile() {

		try {
			
			imageHash = new HashMap<>();
			
			File file = new File("info/imageLoader.txt");
			if(!file.exists()){
				System.err.println("File doesnt exist");
				return;
			}
			
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String sCurrentLine;
			
			while ((sCurrentLine = reader.readLine()) != null) {
				
				System.out.println(sCurrentLine);
				MyObjectXml obj = new MyObjectXml();
				
				String[] str = sCurrentLine.split(",");
				try {
					obj.setId(Integer.valueOf(str[0]));
				} catch (Exception e) {
					continue;
				}
				try {
					obj.setImage(ImageIO.read(new File("images/"+str[1])));
					obj.setName(str[2]);
				} catch (Exception e) {
					continue;
				}
				
				imageHash.put(Long.valueOf(obj.getId()), obj.getImage());
			}
			
			reader.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static ImageSingleton getInstance() {
		try {
			if (instance == null) {
				instance = new ImageSingleton();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return instance;
	}

	public BufferedImage getImage(int id) {
		try {
			return imageHash.get(Long.valueOf(id));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}

}
