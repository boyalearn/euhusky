package com.euhusky.rpc.proxy;

import com.euhusky.remote.transport.Client;

public class SimpleProxyFactory implements ProxyFactory{
	
	private Client client;
	
	public SimpleProxyFactory(){
	}
	public SimpleProxyFactory(Client client){
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
	public void setClient(Client client) {
		this.client=client;
	}
	
}
