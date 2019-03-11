package com.euhusky.cluster;

public interface Invoker extends Cluster{
	
	public Object doInvoker(Invoker invoker);
}
