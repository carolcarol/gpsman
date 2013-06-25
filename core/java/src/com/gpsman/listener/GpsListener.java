package com.gpsman.listener;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class GpsListener {

	public static void main (String args[]) throws Exception

	{
		GpsManager gpsManager = new GpsManager ();

		int port = 2013;
		ServerSocket welcomeSocket = new ServerSocket (port);
		Socket connectionSocket = welcomeSocket.accept ();

		System.out.format ("***** MINI KAREL-ATU LISTENER STARTED port:%d **** \n", port);

		while (true) {
			String gpsData;
			BufferedReader inFromClient = new BufferedReader (new InputStreamReader (connectionSocket.getInputStream ()));
			DataOutputStream outToClient = new DataOutputStream (connectionSocket.getOutputStream ());
			gpsData = inFromClient.readLine ();

			log (gpsData);

			String ackResponse = gpsManager.processGpsData (gpsData);

			outToClient.writeBytes (ackResponse);
		}
	}

	private static void log (String gpsData) {
		System.out.println (new Date () + "-: " + gpsData);
	}
}
