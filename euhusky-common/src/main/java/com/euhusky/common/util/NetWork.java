package com.euhusky.common.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class NetWork {
	public static String getLockIP() {
		try {
			Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
		    while (interfaces.hasMoreElements()) {
		      NetworkInterface current = interfaces.nextElement();
		      if (!current.isUp() || current.isLoopback() || current.isVirtual()) continue;
		      Enumeration<InetAddress> addresses = current.getInetAddresses();
		      while (addresses.hasMoreElements()) {
		        InetAddress addr = addresses.nextElement();
		        if (addr.isLoopbackAddress()) continue;
		        return addr.getHostAddress();
		      }
		    }
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return null;
	}
	
	public static void main(String[] args) {
		System.out.println(getLockIP());
	}
}
