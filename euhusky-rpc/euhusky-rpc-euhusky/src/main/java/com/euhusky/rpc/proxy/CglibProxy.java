package com.euhusky.rpc.proxy;


import java.lang.reflect.Method;
import java.util.UUID;

import org.springframework.context.ApplicationContext;

import com.euhusky.config.EuhuskyContext;
import com.euhusky.remote.netty.NettyClient;
import com.euhusky.remote.transport.Client;
import com.euhusky.rpc.context.RpcRequest;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class CglibProxy implements IProxy, MethodInterceptor{
	
	private ApplicationContext applicationContext;

	public CglibProxy(EuhuskyContext euhuskyContext) {
		
	}
	@Override
	public Object createProxy(Class<?> refClass, ApplicationContext applicationContext) {
		this.applicationContext=applicationContext;
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
		NettyClient client=(NettyClient)((ApplicationContext)this.applicationContext).getBean(Client.class);
		if(!client.isConnect()){
			client.connect();
		}
		Object result=client.send(request);
		return result;
	}

}
