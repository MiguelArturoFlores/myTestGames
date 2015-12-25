package com.mgl.worldeditor;

import com.mgl.worldeditor.view.EditorFrame;

public class MainWorldEditor {

	
	
	
	public static void main(String[] args) {
		try {
			
			EditorFrame editor = EditorFrame.getInstance();
			editor.resize(editor.getWidth()-1, editor.getHeight()-1);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
