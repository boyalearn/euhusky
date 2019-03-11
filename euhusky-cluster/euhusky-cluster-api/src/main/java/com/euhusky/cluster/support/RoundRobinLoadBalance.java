package com.euhusky.cluster.support;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import com.euhusky.cluster.LoadBalance;
import com.euhusky.common.URL;

public class RoundRobinLoadBalance implements LoadBalance{
	
	private static Map<String,AtomicInteger> sequences=new ConcurrentHashMap<String,AtomicInteger>();
	
	public static URL select(List<URL> urls) {
		int size=urls.size();
		AtomicInteger index=sequences.get(urls.get(0).getServiceName());
		return urls.get(index.incrementAndGet()%size);
	}
}
