package com.euhusky.config;

import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

public class ServiceBean extends ServiceConfig implements InitializingBean,ApplicationListener<ContextRefreshedEvent>,ApplicationContextAware{

	private ApplicationContext applicationContext;
	
	private String value;
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public ServiceBean() {
	}
	
	public ServiceBean(Class<?> serviceClass) {
		super(serviceClass);
	}

	
	@Override
	public void afterPropertiesSet() throws Exception {
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if(null==this.protocol) {
			Map<String, ProtocolConfig> applicationConfigMap=BeanFactoryUtils.beansOfTypeIncludingAncestors(applicationContext, ProtocolConfig.class, false, false);
			if (applicationConfigMap != null && applicationConfigMap.size() > 0) {
				ProtocolConfig protocolConfig = null;
                for (ProtocolConfig config : applicationConfigMap.values()) {
                	protocolConfig = config;
                }
                if (protocolConfig != null) {
                    setProtocol(protocolConfig);
                }
            }
		}
		if(null==this.register) {
			Map<String, RegisterConfig> registerConfigMap=BeanFactoryUtils.beansOfTypeIncludingAncestors(applicationContext, RegisterConfig.class, false, false);
			if (registerConfigMap != null && registerConfigMap.size() > 0) {
				RegisterConfig registerConfig = null;
                for (RegisterConfig config : registerConfigMap.values()) {
                	registerConfig = config;
                }
                if (registerConfig != null) {
                    setRegister(registerConfig);
                }
            }
		}
		super.export();
		
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext=applicationContext;
	}
	
	

	
	
}
