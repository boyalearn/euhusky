package com.euhusky.rpc.context;

public class RpcResponse {
	
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
