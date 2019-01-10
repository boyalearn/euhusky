package com.euhusky.config;

import org.springframework.beans.factory.FactoryBean;

import com.euhusky.app.bean.DemoService;

@SuppressWarnings("rawtypes")
public class ReferenceBean implements ReferenceConfig,FactoryBean{

	private static final long serialVersionUID = 7777073747857096902L;
	
	private Object invoke;
	
	private Class<?> refClass;

	@Override
	public Object getObject() throws Exception {
		return new DemoService();
	}

	@Override
	public Class getObjectType() {
		return refClass.getClass();
	}

	public Object getInvoke() {
		return invoke;
	}

	public void setInvoke(Object invoke) {
		this.invoke = invoke;
	}

	public Class<?> getRefClass() {
		return refClass;
	}

	public void setRefClass(Class<?> refClass) {
		this.refClass = refClass;
	}
	
	
	
	

}
