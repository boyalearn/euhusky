package com.euhusky.rpc.proxy;


import org.springframework.context.ApplicationContext;

import com.euhusky.config.EuhuskyContext;

public class CglibProxy implements IProxy{

	public CglibProxy(EuhuskyContext euhuskyContext) {
		
	}
	@Override
	public Object createProxy(Class<?> refClass, ApplicationContext applicationContext) {
		// TODO Auto-generated method stub
		return null;
	}

}
