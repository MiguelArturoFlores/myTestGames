package com.mgl.worldeditor.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.*;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.*;
import org.xml.sax.helpers.*;

import com.mgl.worldeditor.model.MyObjectXml;
import com.mgl.worldeditor.view.EditorFrame;

public class ExportListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {

		try {

			JFileChooser chooser = new JFileChooser();
			chooser.setCurrentDirectory(new java.io.File("."));
			FileNameExtensionFilter filter = new FileNameExtensionFilter(
					"XML File", "xml");
			chooser.setFileFilter(filter);
			chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			int returnVal = chooser.showSaveDialog(null);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				// System.out.println("You chose to open this file: " +
				// chooser.getSelectedFile().getName());

				// System.out.println("You chose to open this filepath: "
				// +chooser.getSelectedFile().getAbsolutePath());
				exportXmlFile(chooser.getSelectedFile());

			}

		} catch (Exception e2) {
			e2.printStackTrace();
		}

	}

	private void exportXmlFile(File selectedFile) {
		try {

			String name = selectedFile.getAbsolutePath() + ".xml";
			File file = new File(name);

			/*FileWriter fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);

			bw.write("este es el xml");
			bw.close();
			 */
			// creating xml
			DocumentBuilderFactory docFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			Document doc = docBuilder.newDocument();

			Element rootElement = doc.createElement("Objects");
			doc.appendChild(rootElement);
			
			for(MyObjectXml myObject : EditorFrame.getInstance().getWorldPanel().getObjectList()){
				
				Element objElement = doc.createElement("Object");
				rootElement.appendChild(objElement);
				objElement.setAttribute("id",myObject.getId()+"");
				objElement.setAttribute("x",myObject.getX()+"");
				objElement.setAttribute("y",myObject.getY()+"");
				objElement.setAttribute("angle",myObject.getAngle()+"");
				objElement.setAttribute("name",myObject.getName()+"");
				objElement.setAttribute("parameters",myObject.getParameters()+"");
			}
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(file);
			transformer.transform(source, result);
			
			System.out.println("Export XML Done");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
