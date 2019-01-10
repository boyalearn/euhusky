package com.euhusky.config;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.SmartLifecycle;

public class ApplicationConfig extends AbstractApplicationConfig implements ApplicationContextAware,SmartLifecycle{
	
	private final Log logger = LogFactory.getLog(getClass());
	
	private RegistryConfig registry;

	private ApplicationContext context;
	
	
	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		this.context=context;
	}
	
	public RegistryConfig getRegistry() {
		return registry;
	}
	
	public void setRegistry(RegistryConfig registry) {
		this.registry = registry;
	}
	
	@Override
	public void start() {
		String[] serviceNames=context.getBeanNamesForType(ServiceBean.class);
		for(String serviceName:serviceNames){
			ServiceBean serviceBean=(ServiceBean)context.getBean(serviceName);
			registerService(serviceBean);
		}
		
	}
	
	private void registerService(ServiceBean serviceBean){
		logger.info("registrService "+serviceBean.getService().getName());

	}
	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean isRunning() {
		return false;
	}
	@Override
	public int getPhase() {
		return 0;
	}

	
}
