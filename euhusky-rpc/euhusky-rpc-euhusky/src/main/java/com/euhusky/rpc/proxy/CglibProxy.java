package com.euhusky.rpc.proxy;


import java.lang.reflect.Method;
import java.util.UUID;

import com.euhusky.remote.transport.Client;
import com.euhusky.rpc.context.RpcRequest;
import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class CglibProxy implements MethodInterceptor{
	
	private Client client;
	
	public CglibProxy(Client client){
		this.client=client;
	}
	
	public Object createProxy(Class<?> refClass) {
		Enhancer eh = new Enhancer();
		eh.setSuperclass(refClass);
		eh.setCallbacks(new Callback[]{ this });
		return eh.create(); 
	}
	@Override
	public Object intercept(Object bean, Method method, Object[] args, MethodProxy proxyMethod) throws Throwable {
		RpcRequest request=new RpcRequest();
		request.setRequestId(UUID.randomUUID().toString());
		request.setMethodName(method.getName());
		
		Object result=client.send(request);
		return result;
	}

}
