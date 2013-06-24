/* $Id:  GpsManager.java,v 1.1 Jun 24, 2013 3:52:13 PM gokhang Exp $
 *******************************************************************************
 *                                                                             *
 *                                                                             *
 * Copyright (c) Telenity A.S.                                                 *
 * All rights reserved.                                                        *
 *                                                                             *
 * This document contains confidential and proprietary information of Telenity *
 * and any reproduction, disclosure, or use in whole or in part is expressly   *
 * prohibited, except as may be specifically authorized by prior written       *
 * agreement or permission of Telenity.                                        *
 *                                                                             *
 *******************************************************************************
 *                                                                             *
 *                     RESTRICTED RIGHTS LEGEND                                *
 * Use, duplication, or disclosure by Government Is Subject to restrictions as *
 * set forth in subparagraph (c)(1)(ii) of the Rights in Technical Data and    *
 * Computer Software clause at DFARS 252.227-7013                              *
 *                                                                             *
 ******************************************************************************/
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