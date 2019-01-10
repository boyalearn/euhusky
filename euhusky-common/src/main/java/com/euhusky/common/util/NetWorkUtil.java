package com.euhusky.common.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class NetWorkUtil {
	public static String getLocalHost(){
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			throw new RuntimeException("Õ¯ø®ªÒ»°“Ï≥£");
		}
	}
}
