package com.euhusky.config;

import org.springframework.beans.factory.InitializingBean;

public class ServiceBean implements ServiceConfig,InitializingBean{

	private static final long serialVersionUID = 8803916098760132436L;
	
	private Class<?> service;
	
	private String value;
	
	@Override
	public void afterPropertiesSet() throws Exception {
	}

	public Class<?> getService() {
		return service;
	}

	public void setService(Class<?> service) {
		this.service = service;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	

	
	
}
