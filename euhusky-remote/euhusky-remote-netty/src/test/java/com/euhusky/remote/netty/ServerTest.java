package com.euhusky.remote.netty;

import com.euhusky.remote.netty.handler.ServerHandler;

public class ServerTest {
    public static void main(String[] args){
		NettyServer server=new NettyServer();
		server.setHandler(new ServerHandler());
		server.start(5656);
	}

}
