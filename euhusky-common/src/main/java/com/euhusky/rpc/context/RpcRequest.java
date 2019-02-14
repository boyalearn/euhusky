package com.euhusky.rpc.context;

import java.io.Serializable;

public class RpcRequest implements Serializable{

	private static final long serialVersionUID = 1L;

	private String requestId;
	
	private String methodName;
	
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

	public Object[] getArgs() {
		return args;
	}

	public void setArgs(Object[] args) {
		this.args = args;
	}
	
	
	
}
