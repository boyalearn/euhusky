package com.euhusky.rpc.proxy;


import java.lang.reflect.Method;
import com.euhusky.common.URL;
import com.euhusky.remote.transport.RequetClient;
import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class CglibProxy implements MethodInterceptor{
	
	private RequetClient client;
	
	public CglibProxy(RequetClient client){
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
		
		Object result=client.send(wrapUrl(bean,method,args,proxyMethod));
		return result;
	}
	
	private URL wrapUrl(Object bean, Method method, Object[] args, MethodProxy proxyMethod){
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
