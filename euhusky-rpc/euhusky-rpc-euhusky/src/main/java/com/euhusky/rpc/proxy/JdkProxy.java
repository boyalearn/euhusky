package com.euhusky.rpc.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;
import java.util.UUID;
import org.springframework.context.ApplicationContext;
import com.euhusky.common.URL;
import com.euhusky.config.EuhuskyContext;
import com.euhusky.remote.netty.NettyClient;
import com.euhusky.remote.transport.Client;
import com.euhusky.rpc.context.RpcRequest;

public class JdkProxy implements IProxy,InvocationHandler{
	
	private List<URL> urls;
	
	private EuhuskyContext euhuskyContext;
	
	private ApplicationContext applicationContext;
	
	public JdkProxy(EuhuskyContext euhuskyContext) {
		this.euhuskyContext=euhuskyContext;
	}
	

	@Override
	public Object invoke(Object bean, Method method, Object[] args) throws Throwable {
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

	@Override
	public Object createProxy(Class<?> refClass, ApplicationContext applicationContext) {
		this.applicationContext=applicationContext;
		return Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class<?>[]{refClass}, this);
	}

}
