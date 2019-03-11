package com.euhusky.common.util;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import com.euhusky.common.URL;

public class ReferenceCache {
	
	private static final ConcurrentHashMap<String,List<URL>> serviceMap=new ConcurrentHashMap<String,List<URL>>();
	

	public static List<URL> getReferences(String clss){
		return serviceMap.get(clss);
	}
	
	public static void setReferences(String clss,List<URL> urls){
		serviceMap.put(clss,urls);
	}
}
