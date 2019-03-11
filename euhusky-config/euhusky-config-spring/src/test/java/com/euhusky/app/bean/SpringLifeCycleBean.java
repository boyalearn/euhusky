package com.euhusky.app.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringLifeCycleBean implements BeanFactoryAware, BeanNameAware,InitializingBean, DisposableBean{
	
	private String name;
	
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		System.out.println("setName属性注入方法。。。。");
		this.name = name;
	}

	public SpringLifeCycleBean() {
		System.out.println("构造方法。。。。");
	}

	@Override
	public void destroy() throws Exception {
		System.out.println("destroy方法。。。。");
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println("afterPropertiesSet方法。。。。");
	}

	@Override
	public void setBeanName(String name) {
		System.out.println("setBeanName方法。。。。");
		
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		System.out.println("setBeanFactory方法。。。。");
		
	}
	public void myInit() {
		System.out.println("myInit方法。。。。");
	}
	
	public void myDestory() {
		System.out.println("myDestory方法。。。。");
	}
	
	
	
	@Override
	public String toString() {
		return "SpringLifeCycleBean [name=" + name + "]";
	}

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		System.out.println("现在开始初始化容器");
		ApplicationContext factory = new ClassPathXmlApplicationContext("beans.xml");
		System.out.println("容器初始化成功");    
		//得到Preson，并使用
		SpringLifeCycleBean springLifeCycleBean = factory.getBean("springLifeCycleBean",SpringLifeCycleBean.class);
		System.gc();
		System.out.println(springLifeCycleBean);
		System.out.println("现在开始关闭容器！");
		((ClassPathXmlApplicationContext)factory).registerShutdownHook();
	}

}
