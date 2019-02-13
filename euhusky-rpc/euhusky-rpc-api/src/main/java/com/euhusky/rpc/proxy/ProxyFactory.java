package com.euhusky.rpc.proxy;

public interface ProxyFactory {
	public Object createProxy(Class<?> cls);
}
