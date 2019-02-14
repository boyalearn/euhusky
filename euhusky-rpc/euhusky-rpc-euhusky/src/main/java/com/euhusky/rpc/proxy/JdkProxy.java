package com.euhusky.rpc.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.UUID;
import com.euhusky.remote.transport.RequetClient;
import com.euhusky.rpc.context.RpcRequest;

public class JdkProxy implements InvocationHandler{
	
    private RequetClient client;
	
	public JdkProxy(RequetClient client){
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
		
		System.out.println(result);
		return result;
	}


}
