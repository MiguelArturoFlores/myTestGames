package com.mgl.social.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Logger;

import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.DeploymentException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.PongMessage;
import javax.websocket.Session;

import org.glassfish.tyrus.client.ClientManager;
 
@ClientEndpoint
public class WordgameClientEndpoint {
 
    private Logger logger = Logger.getLogger(this.getClass().getName());
 
    @OnOpen
    public void onOpen(Session session) {
        logger.info("Connected ... " + session.getId());
        try {
            session.getBasicRemote().sendText("start");
            session.getBasicRemote().sendText("MIgUEL");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
 
    @OnMessage
    public String onMessage(String message, Session session) {
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try {
            logger.info("Received ...." + message);
            String userInput = bufferRead.readLine();
            return userInput;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
 
    @OnMessage
    public void binaryMessage(Session session, ByteBuffer msg) {
       System.out.println("Binary message: " + msg.toString());
    }
    @OnMessage
    public void pongMessage(Session session, PongMessage msg) {
       System.out.println("Pong message: " + 
                           msg.getApplicationData().toString());
    }
    
    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        logger.info(String.format("Session %s close because of %s", session.getId(), closeReason));
    }
 
    private static CountDownLatch latch;
    
    public static void main(String[] args) {
        latch = new CountDownLatch(1);
 
        ClientManager client = ClientManager.createClient();
        try {
            client.connectToServer(WordgameClientEndpoint.class, new URI("ws://localhost:8025/websockets/game"));
           
            latch.await();
            
            
            
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
 
}