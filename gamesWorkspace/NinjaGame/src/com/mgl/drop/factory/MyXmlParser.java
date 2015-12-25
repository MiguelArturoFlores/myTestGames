package com.mgl.drop.factory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import com.mgl.drop.game.model.MyObjectXml;

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
			objectXml.setParameter((attributes.getValue("parameters")));
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

	public static ArrayList<Long> getParameterList(String parameter) {
		ArrayList<Long> list = new ArrayList<Long>();
		try {
			//System.out.println("PARAMETERSSSSS "+parameter);
			String[] paramArrayStr = parameter.split("#");
			for (int i = 0; i < paramArrayStr.length; i++) {
				//System.out.println("i "+i);
				String[] individualParam = paramArrayStr[i].split("@");
				Long val = 0L;
				try {
					val = Long.parseLong(individualParam[1]);
					list.add(new Long(val));
				} catch (Exception e) {
					list.add(0L);
				}
				
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public static ArrayList<String> getParameterListGeneric(String parameter) {
		ArrayList<String> list = new ArrayList<String>();
		try {
			//System.out.println("PARAMETERSSSSS "+parameter);
			String[] paramArrayStr = parameter.split("#");
			for (int i = 0; i < paramArrayStr.length; i++) {
				//System.out.println("i "+i);
				String[] individualParam = paramArrayStr[i].split("@");
				try {
					list.add(individualParam[1]);
				} catch (Exception e) {
					list.add("");
				}
				
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
