package com.gpsman.resolvers;

public class StaticMsisdnResolver implements Imei2MsisdnResolver {

	public static final String __file_identity = "@(#)$Id:  StaticImsiResolver.java,v 1.1 Jun 25, 2013 4:46:44 PM gokhang Exp $";

//	public static final String TEST_MSISDN = "905301653840";
	public static final String TEST_MSISDN = "918527852228";

	public String getMsisdnForImei (String imsi) {
		return TEST_MSISDN;
	}

}