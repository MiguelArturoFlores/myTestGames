package com.mgl.worldeditor.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.mgl.worldeditor.model.MyObjectXml;
import com.mgl.worldeditor.view.EditorFrame;

public class LoadListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		try {

			JFileChooser chooser = new JFileChooser();
			chooser.setCurrentDirectory(new java.io.File("."));
			FileNameExtensionFilter filter = new FileNameExtensionFilter(
					"XML File", "xml");
			chooser.setFileFilter(filter);
			chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			int returnVal = chooser.showOpenDialog(null);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				// System.out.println("You chose to open this file: " +
				// chooser.getSelectedFile().getName());

				// System.out.println("You chose to open this filepath: "
				// +chooser.getSelectedFile().getAbsolutePath());
				loadXMLFile(chooser.getSelectedFile());

			}

		} catch (Exception e2) {
			e2.printStackTrace();
		}

	}

	private void loadXMLFile(File selectedFile) {
		try {

			if (!selectedFile.exists()) {
				System.out.println("no exitia");
				return;
			}

			BufferedReader reader = new BufferedReader(new FileReader(
					selectedFile));
			String sCurrentLine;
			String xmlString = null;
			while ((sCurrentLine = reader.readLine()) != null) {

				System.out.println(sCurrentLine);
				xmlString = xmlString + sCurrentLine;
			}
			InputStream is = new FileInputStream(selectedFile);

			MyXmlParser parser = new MyXmlParser(is);
			parser.parseDocument();

			EditorFrame.getInstance().getWorldPanel()
					.setObjectList(parser.getObjectList());
			EditorFrame.getInstance().getWorldPanel().loadImagesList();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
