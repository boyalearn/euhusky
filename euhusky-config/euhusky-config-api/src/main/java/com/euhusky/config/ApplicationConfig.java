package com.euhusky.config;


import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import com.euhusky.app.bean.HelloService;

public class ApplicationConfig implements ApplicationContextAware,InitializingBean {

	private ApplicationContext context;
	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		this.context=context;
	}
	@Override
	public void afterPropertiesSet() throws Exception {
	
		Object obj=this.context.getBean(HelloService.class);
		System.out.println(obj);
		
	}

}
