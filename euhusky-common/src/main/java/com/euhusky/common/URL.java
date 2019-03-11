package com.euhusky.common;

import java.io.Serializable;
import java.util.Arrays;

import com.euhusky.common.util.NetWork;

public class URL implements Serializable{
	
	private static final long serialVersionUID = -5556351328728596310L;

	private String serviceName;
	
	private String methodName;
	
	private String[] paramTypes;
	
	private Object[] params;
	
	private String host;
	
	private int port;
	
	public URL() {}
	
	public URL(String serviceName) {
		this.host=NetWork.getLockIP();
		this.serviceName=serviceName;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
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

	public Object[] getParams() {
		return params;
	}

	public void setParams(Object[] params) {
		this.params = params;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	@Override
	public String toString() {
		return "URL [serviceName=" + serviceName + ", methodName=" + methodName + ", paramTypes="
				+ Arrays.toString(paramTypes) + ", params=" + Arrays.toString(params) + ", host=" + host + ", port="
				+ port + "]";
	}

}
