package com.gpsman.persisters;

public interface LocationDataPersister {
	public static final String __file_identity = "@(#)$Id:  LocationDataPersister.java,v 1.1 Jun 25, 2013 5:26:27 PM gokhang Exp $";

	public void persistLocationData (String msisdn, Double[] latLon);
}