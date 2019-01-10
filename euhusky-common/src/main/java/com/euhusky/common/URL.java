package com.euhusky.common;

import java.io.Serializable;

public class URL implements Serializable{
	
	private static final long serialVersionUID = -5556351328728596310L;

	private String serviceName;
	
	private String host;
	
	private int port;

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
	
	
}
