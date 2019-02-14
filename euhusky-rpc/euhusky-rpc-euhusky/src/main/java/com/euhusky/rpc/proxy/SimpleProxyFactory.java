package com.euhusky.rpc.proxy;

import com.euhusky.remote.transport.RequetClient;

public class SimpleProxyFactory implements ProxyFactory{
	
	private RequetClient client;
	
	public SimpleProxyFactory(){
	}
	public SimpleProxyFactory(RequetClient client){
		this.client=client;
	}

	@Override
	public Object createProxy(Class<?> cls) {
		if(cls.isInterface()){
			return new JdkProxy(client).createProxy(cls);
		}else{
			return new CglibProxy(client).createProxy(cls);
		}
	}
	@Override
	public void setClient(RequetClient client) {
		this.client=client;
	}
	
}
