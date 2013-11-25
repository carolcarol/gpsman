package com.gpsman.listener;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.util.Date;

import Extasys.DataFrame;
import Extasys.Network.TCP.Server.Listener.TCPClientConnection;
import Extasys.Network.TCP.Server.Listener.Exceptions.ClientIsDisconnectedException;
import Extasys.Network.TCP.Server.Listener.Exceptions.OutgoingPacketFailedException;

public class TCPServer extends Extasys.Network.TCP.Server.ExtasysTCPServer {

	private GpsManager gpsManager;

	public TCPServer (String name, String description, InetAddress listenerIP, int port,
			int maxConnections, int connectionsTimeOut, int corePoolSize, int maximumPoolSize, GpsManager gpsManager) {
		super (name, description, corePoolSize, maximumPoolSize);
		
		this.gpsManager = gpsManager;
		
		try {
			// Add listener with message collector.
			this.AddListener (
				"ATU Listener", listenerIP, port, maxConnections, 65535, connectionsTimeOut, 100,
				((char) 2));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void OnDataReceive (TCPClientConnection sender, DataFrame data) {
		byte[] reply = new byte[data.getLength () + 1];
		System.arraycopy (data.getBytes (), 0, reply, 0, data.getLength ());
		reply[data.getLength ()] = ((char) 2);

		String gpsData = new String (reply);

		log (gpsData);

		String ackResponse = gpsManager.processGpsData(gpsData);

		try {
			this.ReplyToSender (ackResponse, sender);
		} catch (ClientIsDisconnectedException e) {
			e.printStackTrace();
		} catch (OutgoingPacketFailedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void OnClientConnect (TCPClientConnection client) {
		// New client connected.
		client.setName (client.getIPAddress ()); // Set a name for this client if you want to.
		System.out.println (client.getIPAddress () + " connected.");
		System.out.println ("Total clients connected: " + super.getCurrentConnectionsNumber ());
	}

	@Override
	public void OnClientDisconnect (TCPClientConnection client) {
		// Client disconnected.
		System.out.println (client.getIPAddress () + " disconnected.");
	}

	private static void log (String gpsData) {
		System.out.println (new Date () + "-: " + gpsData);
	}

}
