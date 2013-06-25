package com.gpsman.listener;

import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class ThreadedGpsListener {
	public static final String __file_identity = "@(#)$Id:  ThreadedGpsListener.java,v 1.1 Jun 25, 2013 2:44:19 PM gokhang Exp $";

	public static final int THREAD_COUNT = 3;

	public static void main (String args[]) throws Exception

	{

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
