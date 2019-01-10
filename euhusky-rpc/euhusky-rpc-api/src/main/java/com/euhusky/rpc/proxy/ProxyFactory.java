package com.euhusky.rpc.proxy;


public interface ProxyFactory {
	public IProxy getProxy(Class<?> interfaceClass);
	
}
