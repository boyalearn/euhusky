package com.euhusky.remote.netty.channel;

import java.io.Serializable;
import java.util.concurrent.CountDownLatch;


public class DataWrap implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private transient CountDownLatch cdl = new CountDownLatch(1);

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
	
	public void await() {
		try {
			cdl.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void cutdown() {
		cdl.countDown();
	}

	@Override
	public String toString() {
		return "DataWrap [dataId=" + dataId + ", data=" + data + "]";
	}


	
}
