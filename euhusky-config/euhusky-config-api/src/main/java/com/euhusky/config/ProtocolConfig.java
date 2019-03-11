package com.euhusky.config;

import com.euhusky.remote.netty.NettyClient;
import com.euhusky.remote.netty.NettyServer;
import com.euhusky.remote.transport.Client;
import com.euhusky.remote.transport.Server;
import com.euhusky.rpc.server.ServerHandler;

public class ProtocolConfig implements Config{
	
	private Integer port;
	
	private String name;
	
	private Server server;
	
	private Client client;

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Server getServer() {
		return server;
	}

	public void setServer(Server server) {
		this.server = server;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public void start() {
		initServer();	
	}
	
	public void initServer() {
		if(null==server) {
			if(null==name) {
				this.server=new NettyServer();
				this.server.setHandler(new ServerHandler());
				server.start(this.port);
			}	
		}
		
	}
	
	public void initClient() {
		if(null==client) {
			if(null==name) {
				this.client=new NettyClient();
			}	
		}
	}
	

}
