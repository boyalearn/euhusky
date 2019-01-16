package com.euhusky.rpc.proxy;

import com.euhusky.config.EuhuskyContext;

public interface ProxyFactory {
	public IProxy getProxy(Class<?> interfaceClass);
	
	public void setEuhuskyContext(EuhuskyContext euhuskyContext);
	
}
