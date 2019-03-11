package com.euhusky.common.context;

import java.io.Serializable;

public class RpcResponse implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String reponseId;
	
	private Object msg;

	public Object getMsg() {
		return msg;
	}

	public String getReponseId() {
		return reponseId;
	}

	public void setReponseId(String reponseId) {
		this.reponseId = reponseId;
	}

	public void setMsg(Object msg) {
		this.msg = msg;
	}
}
