package com.euhusky.remote.netty;

import com.euhusky.remote.transport.Server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;

public class NettyServer implements Server{
	
	ServerBootstrap  boot=new ServerBootstrap();
	
	private EventLoopGroup boss=new NioEventLoopGroup(1);
	
	private EventLoopGroup work=new NioEventLoopGroup();
	
	
	public NettyServer() {
		boot.group(boss,work);
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}

}
