package com.mgl.social.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.glassfish.tyrus.server.Server;

public class WebSocketServer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		runServer();
	}

	public static void runServer() {

		// 186.89.89.175 public
		// 192.168.1.101 private
		Server server = new Server("192.168.1.105", 8025, "/websockets",
				MyServerEndpoint.class);

		try {
			server.start();

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					System.in));
			System.out.print("Please press a key to stop the server.");
			reader.readLine();

		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			server.stop();
		}
	}
}