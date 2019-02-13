package com.euhusky.config;

import java.util.Map;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import com.euhusky.common.URL;
import com.euhusky.rpc.proxy.ProxyFactory;

@SuppressWarnings("rawtypes")
public class ReferenceBean implements Reference,FactoryBean,ApplicationContextAware,InitializingBean{

	private static final long serialVersionUID = 7777073747857096902L;
	
	private Object invoke;
	
	private Class<?> refClass;
	
	private ProxyFactory proxyFactory;
	
	private ApplicationContext applicationContext;

	@Override
	public Object getObject() throws Exception {
		URL url=new URL();
		url.setServiceName(refClass.getName());
		return proxyFactory.createProxy(refClass);
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
		this.proxyFactory=(ProxyFactory)applicationContext.getBean(ProxyFactory.class);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Map<String, ProxyFactory> consumerConfigMap=BeanFactoryUtils.beansOfTypeIncludingAncestors(applicationContext, ProxyFactory.class, false, false);
		for(ProxyFactory config:consumerConfigMap.values()){
			this.proxyFactory=config;
		}
	}
	
	
	
	

}
