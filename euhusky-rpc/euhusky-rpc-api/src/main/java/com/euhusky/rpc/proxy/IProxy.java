package com.euhusky.rpc.proxy;


import org.springframework.context.ApplicationContext;


public interface IProxy {
	public Object createProxy(Class<?> refClass, ApplicationContext applicationContext);

}
