package com.euhusky.rpc.invoke;

import java.lang.reflect.Method;
import com.euhusky.common.URL;

public class DefaultInvoker {
	
	public Object invoke(URL requset) {
		try {
			Class<?> clss=Class.forName(requset.getServiceName());
			Object bean=clss.newInstance();
			String[] argTypes=requset.getParamTypes();
			Class<?>[] paramTypes=new Class<?>[argTypes.length];
			for(int i=0;i<argTypes.length;i++) {
				paramTypes[i]=Class.forName(argTypes[i]);
			}
			Method method=clss.getMethod(requset.getMethodName(),paramTypes);
			
			return method.invoke(bean, requset.getParams());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
