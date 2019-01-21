package com.euhusky.rpc.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;
import java.util.UUID;

import com.euhusky.common.URL;
import com.euhusky.config.AbstractEuhuskyContext;
import com.euhusky.config.EuhuskyContext;
import com.euhusky.rpc.context.RpcRequest;

public class JdkProxy implements IProxy,InvocationHandler{
	
	private List<URL> urls;
	
	private EuhuskyContext euhuskyContext;
	
	public JdkProxy(EuhuskyContext euhuskyContext) {
		this.euhuskyContext=euhuskyContext;
	}
	
	@Override
	public Object createProxy(Class<?> interfaceClas) {
		return Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class<?>[]{interfaceClas}, this);
	}

	@Override
	public Object invoke(Object bean, Method method, Object[] args) throws Throwable {
		RpcRequest request=new RpcRequest();
		request.setRequestId(UUID.randomUUID().toString());
		request.setMethodName(method.getName());
		Object result=((AbstractEuhuskyContext)this.euhuskyContext).getClient().send(request);
		return result;
	}

}
