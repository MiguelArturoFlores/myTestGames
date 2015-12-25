package com.mgl.social.server;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.logging.Logger;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.PongMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;

import com.mgl.social.server.controller.ControllerMessage;
import com.mgl.social.server.message.send.SendPrincipal;

@ServerEndpoint(value = "/game")
public class MyServerEndpoint {

	private Logger logger = Logger.getLogger(this.getClass().getName());
	private ArrayList<Client> clientList = new ArrayList<Client>();
	private ControllerMessage controllerMessage;
	int i = 1;
	private String messageComplete = new String();

	// temporal database....

	@OnOpen
	public void onOpen(Session session) {
		try {
			logger.info("Connected ... " + session.getId());
			// Client client = new Client(session.getId());
			controllerMessage = new ControllerMessage(session);

			/*
			 * SendPrincipal msg = new SendPrincipal();
			 * msg.setFragmented(false); SendLoginBase send = new
			 * SendLoginBase(); send.setId("2"); send.setIdAux("2");
			 * send.setMessage("message"); ObjectWriter ow = new
			 * ObjectMapper().writer() .withDefaultPrettyPrinter(); String json
			 * = ow.writeValueAsString(send);
			 * 
			 * msg.setMessage(json);
			 * 
			 * String messageAux = ow.writeValueAsString(msg);
			 * 
			 * System.out.println("messaeAX" + messageAux);
			 * session.getBasicRemote().sendText(messageAux);
			 */
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@OnMessage
	public void onMessage(String messageRec, Session session) {
		try {
			System.out.println("mesage "+messageRec);
			String message = controllerMessage.recieveMessage(messageRec,
					session);
			System.out.println("msg "+message);
			if(!message.equals("close")){
				sendTextMessage(message, session);
			}

		} catch (Exception e) {
			e.printStackTrace();
			// return "error";
		}
	}

	public static void sendTextMessage(String message, Session session) {
		try {

			int MAXIMUN_BUFFER_SIZE = 75000;
			if (message.length() < MAXIMUN_BUFFER_SIZE) {
				SendPrincipal msg = new SendPrincipal();
				msg.setFragmented(false);
				msg.setMessage(message);
				ObjectWriter ow = new ObjectMapper().writer()
						.withDefaultPrettyPrinter();
				String json = ow.writeValueAsString(msg);
				session.getBasicRemote().sendText(json);
				// System.out.println("message SEND ONE: "+json);
				return;
			}
			int i = 0;
			while (i + MAXIMUN_BUFFER_SIZE < message.length()) {

				SendPrincipal msg = new SendPrincipal();
				msg.setFragmented(true);
				msg.setMessage(message.substring(i, i + MAXIMUN_BUFFER_SIZE));
				ObjectWriter ow = new ObjectMapper().writer()
						.withDefaultPrettyPrinter();
				String json = ow.writeValueAsString(msg);
				session.getBasicRemote().sendText(json);
				i = i + MAXIMUN_BUFFER_SIZE;
				// System.out.println("message SEND: "+json);

			}

			SendPrincipal msg = new SendPrincipal();
			msg.setFragmented(false);
			msg.setMessage(message.substring(i, message.length()));
			ObjectWriter ow = new ObjectMapper().writer()
					.withDefaultPrettyPrinter();
			String json = ow.writeValueAsString(msg);
			session.getBasicRemote().sendText(json);
			// System.out.println("message SEND FINISH: "+json);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@OnMessage
	public void binaryMessage(Session session, ByteBuffer msg) {
		System.out.println("Binary message: " + msg.toString());
	}

	@OnMessage
	public void pongMessage(Session session, PongMessage msg) {
		System.out.println("Pong message: "
				+ msg.getApplicationData().toString());
	}

	@OnClose
	public void onClose(Session session, CloseReason closeReason) {
		logger.info(String.format("Session %s closed because of %s",
				session.getId(), closeReason));
	}
}