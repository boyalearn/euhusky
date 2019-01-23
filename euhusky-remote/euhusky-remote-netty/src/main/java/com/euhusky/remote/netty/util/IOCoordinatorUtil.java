package com.euhusky.remote.netty.util;

import java.util.concurrent.ConcurrentHashMap;

import com.euhusky.rpc.context.RpcResponse;

public class IOCoordinatorUtil {
	
	private static final ConcurrentHashMap<String,Object> queue=new ConcurrentHashMap<String,Object>();
	
	public static void addWait(RpcResponse response){
		synchronized(response){
		   queue.put(response.getReponseId(), response);
		   try {
			   response.wait();
		   } catch (InterruptedException e) {
			   e.printStackTrace();
		   }
		}
	}
	
	public static Object getWait(String responseId){
		return queue.get(responseId);
	}
	public static void wakeUp(RpcResponse response){
		synchronized(response){
		   response.notify();
		}
	}

}
