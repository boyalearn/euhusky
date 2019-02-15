package com.euhusky.rpc.invoke;

import java.lang.reflect.Method;

import com.euhusky.rpc.context.RpcRequest;

public class DefaultInvoker {
	
	public Object invoke(RpcRequest requset) {
		try {
			Class<?> clss=Class.forName(requset.getClassName());
			Object bean=clss.newInstance();
			String[] argTypes=requset.getParamTypes();
			Class<?>[] paramTypes=new Class<?>[argTypes.length];
			for(int i=0;i<argTypes.length;i++) {
				paramTypes[i]=Class.forName(argTypes[i]);
			}
			Method method=clss.getMethod(requset.getMethodName(),paramTypes);
			
			return method.invoke(bean, requset.getArgs());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
