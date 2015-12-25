package com.mgl.social.server.controller;

import com.mgl.social.server.message.MsgPrincipal;

public class LastMessage {

	private String messageComplete;
	private MsgPrincipal myMessage;

	public String getMessageComplete() {
		return messageComplete;
	}

	public void setMessageComplete(String messageComplete) {
		this.messageComplete = messageComplete;
	}

	public MsgPrincipal getMyMessage() {
		return myMessage;
	}

	public void setMyMessage(MsgPrincipal myMessage) {
		this.myMessage = myMessage;
	}

}
