package com.gpsman.listener;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import net.spy.memcached.AddrUtil;
import net.spy.memcached.BinaryConnectionFactory;
import net.spy.memcached.MemcachedClient;

public class GpsManager {

	public static final String __file_identity = "@(#)$Id:  GpsManager.java,v 1.1 Jun 24, 2013 3:52:13 PM gokhang Exp $";

	public static final int THREAD_COUNT = 10;

	public static final String MEMCACHED_CONNECTION_STRING = "";

	private ExecutorService threadPool;

	private MemcachedClient memcachedClient = null;

	public GpsManager () {
		threadPool = Executors.newFixedThreadPool (THREAD_COUNT);
		try {
			memcachedClient = new MemcachedClient (new BinaryConnectionFactory (), AddrUtil.getAddresses (MEMCACHED_CONNECTION_STRING));
		} catch (IOException e) {
			// failed to initialize memcached client
			e.printStackTrace ();
		}
	}

	public String processGpsData (String gpsData) {
		Runnable dataProcesspor = new DataProcessor (gpsData, memcachedClient);
		threadPool.execute (dataProcesspor);
		return prepareAckResponse (gpsData);
	}

	private String prepareAckResponse (String gpsData) {
		String seqNo = gpsData.substring (2, 6);
		String ackResponse = "A" + seqNo + "\r\n";
		return ackResponse;
	}

}