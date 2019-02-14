package com.euhusky.remote.netty.util;

import java.util.HashMap;

import com.euhusky.remote.netty.channel.DataWrap;

public class IOCoordinatorUtil {
	
	private static final HashMap<Integer,Object> queue=new HashMap<Integer,Object>();
	
	public static void add(DataWrap warp) {
		queue.put(warp.getDataId(), warp);
	}
	
	public static void wait(DataWrap warp){
		synchronized(warp){
		   try {
			   warp.wait();
		   } catch (InterruptedException e) {
			   e.printStackTrace();
		   }
		}
	}
	public static Object get(Integer dataId){
		Object data=queue.get(dataId);
		return data;
	}
	public static void wakeUp(DataWrap warp){
		synchronized(warp){
			warp.notify();
		}
	}

}
