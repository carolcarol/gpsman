package com.gpsman.persisters;

public class StdOutPersister implements LocationDataPersister {

	public static final String __file_identity = "@(#)$Id:  StdOutPersister.java,v 1.1 Jun 25, 2013 5:49:55 PM gokhang Exp $";

	public void persistLocationData (String msisdn, Double[] latLon) {

		System.out.println ("Got location for " + msisdn + "  latitude=" + latLon[0] + " longitude=" + latLon[1]);
	}

}