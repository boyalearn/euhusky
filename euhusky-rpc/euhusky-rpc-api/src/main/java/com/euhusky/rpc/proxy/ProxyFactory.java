package com.euhusky.rpc.proxy;

import java.util.List;

import com.euhusky.common.URL;

public interface ProxyFactory {
	public Object createProxy(Class<?> interfaceClas,List<URL> urls);
	
}
