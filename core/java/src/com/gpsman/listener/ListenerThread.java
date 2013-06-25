package com.gpsman.listener;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class ListenerThread implements Runnable {

	public static final String __file_identity = "@(#)$Id:  ListenerThread.java,v 1.1 Jun 25, 2013 2:24:53 PM gokhang Exp $";

	private ServerSocket serverSocket;
	
	private boolean alive = true;

	private GpsManager gpsManager;

	public ListenerThread (ServerSocket serverSocket, GpsManager gpsManager) {
		this.serverSocket = serverSocket;
		this.gpsManager = gpsManager;
	}

	public void run () {

		try {
			Socket connectionSocket = serverSocket.accept ();

			while (true) {
				String gpsData;
				BufferedReader inFromClient = new BufferedReader (new InputStreamReader (connectionSocket.getInputStream ()));
				DataOutputStream outToClient = new DataOutputStream (connectionSocket.getOutputStream ());
				gpsData = inFromClient.readLine ();

				log (gpsData);

				String ackResponse = gpsManager.processGpsData(gpsData);

				outToClient.writeBytes (ackResponse);
			}
		} catch (IOException e) {
			alive = false;
		}

	}
	
	
	public boolean getIsalive() {
		return alive;
	}

	private static void log (String gpsData) {
		System.out.println (new Date () + "-: " + gpsData);
	}
}