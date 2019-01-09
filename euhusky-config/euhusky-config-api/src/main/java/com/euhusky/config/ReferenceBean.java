package com.euhusky.config;

import org.springframework.beans.factory.FactoryBean;

@SuppressWarnings("rawtypes")
public class ReferenceBean implements ReferenceConfig,FactoryBean{

	private static final long serialVersionUID = 7777073747857096902L;
	
	private Object invoke;

	@Override
	public Object getObject() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class getObjectType() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getInvoke() {
		return invoke;
	}

	public void setInvoke(Object invoke) {
		this.invoke = invoke;
	}
	
	

}
