package com.euhusky.rpc.context;

import java.io.Serializable;
import java.util.Arrays;

public class RpcRequest implements Serializable{

	private static final long serialVersionUID = 1L;

	private String requestId;
	
	private String methodName;
	
	private String[] paramTypes;
	
	private String className;
	
	private Object[] args;

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String[] getParamTypes() {
		return paramTypes;
	}

	public void setParamTypes(String[] paramTypes) {
		this.paramTypes = paramTypes;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Object[] getArgs() {
		return args;
	}

	public void setArgs(Object[] args) {
		this.args = args;
	}

	@Override
	public String toString() {
		return "RpcRequest [requestId=" + requestId + ", methodName=" + methodName + ", paramTypes="
				+ Arrays.toString(paramTypes) + ", className=" + className + ", args=" + Arrays.toString(args) + "]";
	}

	

}
