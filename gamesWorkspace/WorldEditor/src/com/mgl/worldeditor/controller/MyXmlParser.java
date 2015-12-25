package com.mgl.worldeditor.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mgl.worldeditor.model.MyObjectXml;

public class MyXmlParser extends DefaultHandler {

	private InputStream myFile;
	private MyObjectXml objectXml;
	private ArrayList<MyObjectXml> objectList;

	public MyXmlParser(InputStream inputStream) {
		try {
			this.myFile = inputStream;
			objectList = new ArrayList<MyObjectXml>();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	public void parseDocument() {
		// parse
		SAXParserFactory factory = SAXParserFactory.newInstance();
		try {
			SAXParser parser = factory.newSAXParser();
			parser.parse(myFile, this);
		} catch (ParserConfigurationException e) {
			System.out.println("ParserConfig error");
		} catch (SAXException e) {
			System.out.println("SAXException : xml not well formed");
		} catch (IOException e) {
			System.out.println("IO error");
		}
	}

	@Override
	public void startElement(String s, String s1, String elementName,
			Attributes attributes) throws SAXException {
		// if current element is book , create new book
		// clear tmpValue on start of element

		if (elementName.equalsIgnoreCase("Object")) {
			objectXml = new MyObjectXml();
			objectXml.setId(Integer.valueOf(attributes.getValue("id")));
			objectXml.setX(Float.valueOf(attributes.getValue("x")));
			objectXml.setY(Float.valueOf(attributes.getValue("y")));
			objectXml.setAngle(Integer.valueOf(attributes.getValue("angle")));
			objectXml.setName((attributes.getValue("name")));
			objectXml.setParameters((attributes.getValue("parameters")));
			
//			
//			try {
//				objectXml.setImage(ImageIO.read(new File("images/"+objectXml.getName())));
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
			
			
		}
	}

	@Override
	public void endElement(String s, String s1, String element)
			throws SAXException {
		// if end of book element add to list
		if (element.equals("Object")) {
			objectList.add(objectXml);
		}
	}

	public InputStream getMyFile() {
		return myFile;
	}

	public void setMyFile(InputStream myFile) {
		this.myFile = myFile;
	}

	public MyObjectXml getObjectXml() {
		return objectXml;
	}

	public void setObjectXml(MyObjectXml objectXml) {
		this.objectXml = objectXml;
	}

	public ArrayList<MyObjectXml> getObjectList() {
		return objectList;
	}

	public void setObjectList(ArrayList<MyObjectXml> objectList) {
		this.objectList = objectList;
	}

	
	
}
