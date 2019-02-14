package com.euhusky.remote.netty;

public class ServerTest {
    public static void main(String[] args){
		NettyServer server=new NettyServer();
		server.start(5656);
	}

}
