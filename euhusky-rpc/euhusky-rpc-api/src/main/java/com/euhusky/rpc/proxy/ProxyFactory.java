package com.euhusky.rpc.proxy;

import com.euhusky.remote.transport.RequetClient;

public interface ProxyFactory {
	public Object createProxy(Class<?> cls);
	
	public void setClient(RequetClient client);
}
