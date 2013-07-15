package com.gpsman.listener;

import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class ThreadedGpsListener {

//	public static void main (String args[]) throws Exception {
//		int port = 2013;
//		int connectionsTimeout = (15 * 60 * 1000);
//		int corePoolSize = 5;
//		int maxPoolSize = 15;
//
//		TCPServer fTCPServer =
//			new TCPServer ("My TCP server", "Example", InetAddress.getByName ("10.34.39.123"), port,
//				100, connectionsTimeout, corePoolSize, maxPoolSize, new GpsManager ());
//
//		fTCPServer.Start ();
//
//		System.out.format ("***** MINI KAREL-ATU LISTENER STARTED port:%d **** \n", port);
//	}

	public static final int THREAD_COUNT = 3;

	public static void main (String args[]) throws Exception	{

		int port = 2013;
		ServerSocket welcomeSocket = new ServerSocket (port);
		List<ListenerThread> listeners = new ArrayList<ListenerThread> ();

		for (int i = 0; i < THREAD_COUNT; i++) {
			GpsManager gpsManager = new GpsManager ();
			ListenerThread listener = new ListenerThread (welcomeSocket, gpsManager);
			listeners.add (listener);
			Thread listenerThread = new Thread (listener);
			listenerThread.start ();
		}

		System.out.format ("***** MINI KAREL-ATU LISTENER STARTED port:%d **** \n", port);

	}
	
}
