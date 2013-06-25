package com.gpsman.persisters;

import java.io.IOException;

import com.telenity.canvas.lcgw.locprovider.LocationResult;

import net.spy.memcached.AddrUtil;
import net.spy.memcached.BinaryConnectionFactory;
import net.spy.memcached.MemcachedClient;

public class MemcachePersister implements LocationDataPersister {
	
	public static final String __file_identity = "@(#)$Id:  MemcachedPersister.java,v 1.1 Jun 25, 2013 5:23:15 PM gokhang Exp $";
	public static final String MEMCACHED_CONNECTION_STRING = "myhost13:11213";
	
	MemcachedClient memcacheClient;
	
	public MemcachePersister () {
		try {
			memcacheClient = new MemcachedClient (new BinaryConnectionFactory (), AddrUtil.getAddresses (MEMCACHED_CONNECTION_STRING));
		} catch (IOException e) {
			// failed to initialize memcached client
			e.printStackTrace ();
		}
		// TODO Auto-generated constructor stub
	}
	
	public void persistLocationData (String msisdn, Double[] latLon) {

		System.out.println ("Will put  location to memcache for " + msisdn + "  latitude=" + latLon[0] + " longitude=" + latLon[1]);
		
		LocationResult locationResult = new LocationResult ();
		
		locationResult.setLatitude (latLon[0]);
		locationResult.setLongitude (latLon[1]);
		
		memcacheClient.set (msisdn, 500, locationResult);
		
		System.out.println ("Location set on memcache " + msisdn + "  latitude=" + latLon[0] + " longitude=" + latLon[1]);
	}

}
