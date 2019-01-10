package com.euhusky.rpc.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

import com.euhusky.common.URL;

public class JdkProxy implements IProxy,InvocationHandler{
	
	private List<URL> urls;
	
	@Override
	public Object createProxy(Class<?> interfaceClas, List<URL> urls) {
		this.urls=urls;
		return Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class<?>[]{interfaceClas}, this);
	}

	@Override
	public Object invoke(Object bean, Method method, Object[] args) throws Throwable {
		return null;
	}

}
