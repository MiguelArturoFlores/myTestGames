package com.mgl.drop.game.controller;

public class DialogController {

	private DialogController dialogYes;
	private DialogController dialogNo;

	private String text;

	private DialogListener dialogListener;
	
	public DialogController(DialogController dialogYes,
			DialogController dialogNo, String text) {
		super();
		this.dialogYes = dialogYes;
		this.dialogNo = dialogNo;
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public DialogController getDialogYes() {
		return dialogYes;
	}

	public void setDialogYes(DialogController dialogYes) {
		this.dialogYes = dialogYes;
	}

	public DialogController getDialogNo() {
		return dialogNo;
	}

	public void setDialogNo(DialogController dialogNo) {
		this.dialogNo = dialogNo;
	}

	public boolean hasYes() {
		if (dialogYes != null) {
			return true;
		}
		return false;
	}

	public boolean hasNo() {
		if (dialogNo != null) {
			return true;
		}
		return false;
	}

	public boolean hasOption() {
		if (hasNo() || hasYes()) {
			return true;
		}
		return false;
	}

	public void actionNo() {
		try {
			if(dialogListener==null){
				return;
			}
			dialogListener.onDialogNo();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void actionClose() {
		try {
			if(dialogListener==null){
				return;
			}
			dialogListener.onDialogClose();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void actionYes() {
		try {
			if(dialogListener==null){
				return;
			}
			dialogListener.onDialogYes();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DialogListener getDialogListener() {
		return dialogListener;
	}

	public void setDialogListener(DialogListener dialogListener) {
		this.dialogListener = dialogListener;
	}

	
}
