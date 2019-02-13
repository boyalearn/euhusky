package com.euhusky.rpc.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.UUID;
import com.euhusky.remote.transport.Client;
import com.euhusky.rpc.context.RpcRequest;

public class JdkProxy implements InvocationHandler{
	
    private Client client;
	
	public JdkProxy(Client client){
		this.client=client;
	}
	
	public Object createProxy(Class<?> refClass) {
		return Proxy.newProxyInstance(this.getClass().getClassLoader(), refClass.getClass().getInterfaces(),this);
	}

	@Override
	public Object invoke(Object bean, Method method, Object[] args) throws Throwable {
		RpcRequest request=new RpcRequest();
		request.setRequestId(UUID.randomUUID().toString());
		request.setMethodName(method.getName());
		
		Object result=client.send(request);
		return result;
	}


}
