package com.gpsman.listener;

import net.spy.memcached.MemcachedClient;

/**
 * 
 * This class processes ascii data obtained from device. We are interested in XGPS packets like the example below:
 * 
 * IM0002-862170012786040@XGPS01130510115921N4059400E028491940028000090081011000000000020 * ,0.218,ea22
 * 
 * For this example coordinates are N4059400 and  E02849194.
 * 
 * @author gokhang
 */
public class DataProcessor implements Runnable {

	public static final String __file_identity = "@(#)$Id:  DataProcessor.java,v 1.1 Jun 24, 2013 3:57:34 PM gokhang Exp $";

	public static final String XGPS = "XGPS";

	private String gpsData = "";

	private MemcachedClient memcachedClient;

	public DataProcessor (String gpsData, MemcachedClient memcachedClient) {
		this.gpsData = gpsData;
		this.memcachedClient = memcachedClient;
	}

	public DataProcessor (String gpsData) {
		this.gpsData = gpsData;
	}

	public void run () {
		if ( gpsData.contains (XGPS) ) {
			Double[] parseGPDdata = parseGPDdata ();
			//TODO set to memcached;
		}

	}

	private Double[] parseGPDdata () {
		
		Double[] latitudeLongitude = new Double []{0.0, 0.0};
		
		String latitude = gpsData.substring (41, 49);
		String longitude = gpsData.substring (49, 58);
		System.out.println ("Found GPS data with latitude=[" + latitude + "] and  longitude=[" + longitude + "]");

		// cut Northing Easting
		latitude = latitude.substring (1, latitude.length ());
		longitude = longitude.substring (1, longitude.length ());

		latitudeLongitude[0] = convertLatitudeToDecimalDegrees (latitude);
		latitudeLongitude[1] = convertlongitudeToDecimalDegrees (longitude);
		
		return latitudeLongitude;
	}

	/**
	 * 
	 * @param latitude
	 *            : 7 numeric digit DDMMmmm
	 * @return
	 */
	private static Double convertLatitudeToDecimalDegrees (String latitude) {
		return convertlongitudeToDecimalDegrees ("0".concat (latitude));
	}

	/**
	 * 
	 * @param longitude
	 *            8 digit DDDMMmmm
	 * 
	 * @return
	 */
	private static Double convertlongitudeToDecimalDegrees (String longitude) {

		Double degreePart = Double.parseDouble (longitude.substring (0, 3));
		Double minutePart = Double.parseDouble (longitude.substring (3, 5));

		Double milisecondPart = Double.parseDouble (longitude.substring (5, 8));

		Double seconds = milisecondPart / 1000;

		Double decimalDegrees = degreePart + minutePart / 60 + seconds / 3600;
		System.out.println ("Converted [" + longitude + "] to decimal degrees -> [" + decimalDegrees + "]");

		return decimalDegrees;
	}
	
}