package com.gpsman.listener;

import com.gpsman.parsers.CarolGPSDataParser;
import com.gpsman.parsers.GpsDataParser;
import com.gpsman.persisters.LocationDataPersister;
import com.gpsman.persisters.MemcachePersister;
import com.gpsman.resolvers.Imei2MsisdnResolver;
import com.gpsman.resolvers.StaticMsisdnResolver;

public class GpsManager {

	public static final String __file_identity = "@(#)$Id:  GpsManager.java,v 1.1 Jun 24, 2013 3:52:13 PM gokhang Exp $";

	public static final String XGPS = "XGPS";

	private Imei2MsisdnResolver msisdnResolver = new StaticMsisdnResolver ();
	
	private GpsDataParser parser = new CarolGPSDataParser ();

	private LocationDataPersister persister = new MemcachePersister ();

	public GpsManager () {
	}

	public String processGpsData (String gpsData) {
		
		if(gpsData != null) {
			if ( gpsData.contains (XGPS) ) {
				String imsi = parser.parseIMEI(gpsData);
				
				String msisdn = msisdnResolver.getMsisdnForImei (imsi);
				Double[] latlon = parser.parseCoordinates (gpsData);
				persister.persistLocationData (msisdn, latlon);
				
			}
			return prepareAckResponse (gpsData);
		}
		return null;
	}

	private String prepareAckResponse (String gpsData) {
		String seqNo = gpsData.substring (2, 6);
		String ackResponse = "A" + seqNo + "\r\n";
		return ackResponse;
	}

}