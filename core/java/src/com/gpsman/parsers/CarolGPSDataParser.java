package com.gpsman.parsers;

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
public class CarolGPSDataParser implements GpsDataParser {
	public static final String __file_identity = "@(#)$Id:  CoordinateUtils.java,v 1.1 Jun 25, 2013 3:31:39 PM gokhang Exp $";

	public Double[] parseCoordinates (String gpsData) {

		Double[] latitudeLongitude = new Double[] { 0.0, 0.0 };

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

	public String parseIMSI (String gpsData) {
		return gpsData.substring (7, 22);
	}
	
	public static void main (String[] args) {
		String data = "IM0002-862170012786040@XGPS01130510115921N4059400E028491940028000090081011000000000020";
		System.out.println (data.substring (7, 22));
	}
	
	/**
	 * 
	 * @param latitude
	 *            : 7 numeric digit DDMMmmm
	 * @return
	 */
	public static Double convertLatitudeToDecimalDegrees (String latitude) {
		return convertlongitudeToDecimalDegrees ("0".concat (latitude));
	}

	/**
	 * 
	 * @param longitude
	 *            8 digit DDDMMmmm
	 * 
	 * @return
	 */
	public static Double convertlongitudeToDecimalDegrees (String longitude) {

		Double degreePart = Double.parseDouble (longitude.substring (0, 3));
		Double minutePart = Double.parseDouble (longitude.substring (3, 5));

		Double milisecondPart = Double.parseDouble (longitude.substring (5, 8));

		Double seconds = milisecondPart / 1000;

		Double decimalDegrees = degreePart + minutePart / 60 + seconds / 3600;
		System.out.println ("Converted [" + longitude + "] to decimal degrees -> [" + decimalDegrees + "]");

		return decimalDegrees;
	}


}
