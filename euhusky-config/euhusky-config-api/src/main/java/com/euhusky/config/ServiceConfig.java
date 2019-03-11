package com.euhusky.config;

import com.euhusky.common.URL;

public class ServiceConfig extends AbstractConfig{
	
	private Class<?> service;
	
	public ServiceConfig() {}
	
	public ServiceConfig(Class<?> service) {
		this.service=service;
	}
	
	public Class<?> getService() {
		return service;
	}

	public void setService(Class<?> service) {
		this.service = service;
	}

	public void export() {
		this.protocol.start();
		URL url=new URL(this.service.getName());
		url.setPort(this.protocol.getPort());
		this.register.register(url);
	}

}
