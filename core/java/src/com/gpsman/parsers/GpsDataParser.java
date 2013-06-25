package com.gpsman.parsers;



public interface GpsDataParser {

	public static final String __file_identity = "@(#)$Id:  GpsDataParser.java,v 1.1 Jun 25, 2013 5:05:47 PM gokhang Exp $";

	public abstract Double[] parseCoordinates (String gpsData);

	public abstract String parseIMSI (String gpsData);

}