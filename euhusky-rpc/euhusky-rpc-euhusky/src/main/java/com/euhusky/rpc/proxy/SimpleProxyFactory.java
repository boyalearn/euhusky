package com.euhusky.rpc.proxy;

import com.euhusky.config.EuhuskyContext;

public class SimpleProxyFactory implements ProxyFactory{
	
	private EuhuskyContext euhuskyContext;

	@Override
	public IProxy getProxy(Class<?> interfaceClass) {
		if(interfaceClass.isInterface()){
			return new JdkProxy(euhuskyContext);
		}else{
			return new CglibProxy(euhuskyContext);
		}
	}

	@Override
	public void setEuhuskyContext(EuhuskyContext euhuskyContext) {
		this.euhuskyContext=euhuskyContext;
	}

}
