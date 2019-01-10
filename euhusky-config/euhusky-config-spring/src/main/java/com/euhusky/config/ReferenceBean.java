package com.euhusky.config;

import java.util.List;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import com.euhusky.common.URL;
import com.euhusky.register.Register;
import com.euhusky.rpc.proxy.ProxyFactory;

@SuppressWarnings("rawtypes")
public class ReferenceBean implements Reference,FactoryBean,ApplicationContextAware{

	private static final long serialVersionUID = 7777073747857096902L;
	
	private Object invoke;
	
	private List<URL> urls;
	
	@Autowired
	private Register register;
	
	private Class<?> refClass;
	
	private ProxyFactory proxyFactory;
	
	private ApplicationContext applicationContext;

	@Override
	public Object getObject() throws Exception {
		URL url=new URL();
		url.setServiceName(refClass.getName());
		this.urls=register.subscribe(url);
		return proxyFactory.getProxy(refClass).createProxy(refClass, urls);
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

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext=applicationContext;
	}
	
	
	
	

}
