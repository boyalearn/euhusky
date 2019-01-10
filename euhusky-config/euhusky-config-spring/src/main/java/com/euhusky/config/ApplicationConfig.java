package com.euhusky.config;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.SmartLifecycle;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.euhusky.common.URL;
import com.euhusky.common.util.JavaSPIUtil;
import com.euhusky.common.util.NetWorkUtil;
import com.euhusky.config.properties.ApplicationProperties;
import com.euhusky.register.Register;
import com.euhusky.rpc.proxy.ProxyFactory;

@Configuration
@ConditionalOnClass(ApplicationConfig.class)
@EnableConfigurationProperties(ApplicationProperties.class)
public class ApplicationConfig implements Application,ApplicationContextAware,SmartLifecycle{
	
	private final Log logger = LogFactory.getLog(getClass());
	
	private ApplicationContext context;
	
	@Autowired
	private Register register;

	@Autowired
	private ApplicationProperties applicationProperties;
	
	
	@Bean
	public Register getRegister() {
		return (Register)JavaSPIUtil.getImpl(Register.class);
	}
	
	@Bean
	public ProxyFactory getProxyFactory() {
		return (ProxyFactory)JavaSPIUtil.getImpl(ProxyFactory.class);
	}
	
	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		this.context=context;
	}
	
	@Override
	public void start() {
		logger.info("Application start inbind :"+applicationProperties.getServerPort());
		String[] serviceNames=context.getBeanNamesForType(ServiceBean.class);
		for(String serviceName:serviceNames){
			ServiceBean serviceBean=(ServiceBean)context.getBean(serviceName);
			registerService(serviceBean);
		}
		
	}
	
	private void registerService(ServiceBean serviceBean){
		URL url=new URL();
		url.setServiceName(serviceBean.getService().getName());
		url.setPort(Integer.valueOf(applicationProperties.getServerPort()));
		url.setHost(NetWorkUtil.getLocalHost());
		register.register(url);
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
	
	

	public ApplicationProperties getApplicationProperties() {
		return applicationProperties;
	}

	public void setApplicationProperties(ApplicationProperties applicationProperties) {
		this.applicationProperties = applicationProperties;
	}
	
	
	
	

}
