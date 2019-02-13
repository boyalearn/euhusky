package com.euhusky.rpc.proxy;

import com.euhusky.remote.transport.Client;

public interface ProxyFactory {
	public Object createProxy(Class<?> cls);
	
	public void setClient(Client client);
}
