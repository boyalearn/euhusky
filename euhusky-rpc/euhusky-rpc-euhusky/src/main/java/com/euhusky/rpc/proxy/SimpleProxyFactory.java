package com.euhusky.rpc.proxy;

public class SimpleProxyFactory implements ProxyFactory{

	@Override
	public IProxy getProxy(Class<?> interfaceClass) {
		if(interfaceClass.isInterface()){
			return new JdkProxy();
		}else{
			return new CglibProxy();
		}
	}

}
