package com.euhusky.config;

import com.euhusky.register.Register;
import com.euhusky.remote.transport.Client;
import com.euhusky.remote.transport.Server;
import com.euhusky.rpc.proxy.ProxyFactory;

public class AbstractEuhuskyContext implements EuhuskyContext{
	
	protected Register registry;
	
	protected Client client;
	
	protected Server server;
	
	protected ProxyFactory proxyFactory;

	public Register getRegistry() {
		return registry;
	}

	public void setRegistry(Register registry) {
		this.registry = registry;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Server getServer() {
		return server;
	}

	public void setServer(Server server) {
		this.server = server;
	}

	public ProxyFactory getProxyFactory() {
		return proxyFactory;
	}

	public void setProxyFactory(ProxyFactory proxyFactory) {
		this.proxyFactory = proxyFactory;
	}

	
}
