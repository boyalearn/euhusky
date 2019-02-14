package com.euhusky.remote.netty.channel;

import java.io.Serializable;


public class DataWrap implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int dataId;
	
	private Object data;

	public int getDataId() {
		return dataId;
	}

	public void setDataId(int dataId) {
		this.dataId = dataId;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "DataWrap [dataId=" + dataId + ", data=" + data + "]";
	}


	
}
