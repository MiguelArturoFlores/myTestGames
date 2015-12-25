package com.mgl.drop.game.controller;

public class DialogController {

	private DialogController dialogYes;
	private DialogController dialogNo;

	private String text;

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

}
