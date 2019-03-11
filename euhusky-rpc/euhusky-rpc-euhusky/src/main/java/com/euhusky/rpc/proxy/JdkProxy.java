package com.euhusky.rpc.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import com.euhusky.common.URL;
import com.euhusky.remote.transport.Client;

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
		Object result=client.send(wrapUrl(bean,method,args));
		return result;
	}
	
	private URL wrapUrl(Object bean, Method method, Object[] args){
		URL url=new URL();
		url.setMethodName(method.getName());
		url.setServiceName(bean.getClass().getSuperclass().getName());
		Class<?>[] paramTypeClss=method.getParameterTypes();
		String[] argTypes=new String[paramTypeClss.length];
		for(int i=0;i<paramTypeClss.length;i++) {
			argTypes[i]=paramTypeClss[i].getTypeName();
		}
		url.setParamTypes(argTypes);
		url.setParams(args);
		return url;
	}


}
