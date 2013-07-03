package com.gpsman.resolvers;

public interface Imei2MsisdnResolver {

	public static final String __file_identity = "@(#)$Id:  IMSI2MSISDNResolver.java,v 1.1 Jun 25, 2013 4:45:00 PM gokhang Exp $";

	String getMsisdnForImei (String imei);

}
