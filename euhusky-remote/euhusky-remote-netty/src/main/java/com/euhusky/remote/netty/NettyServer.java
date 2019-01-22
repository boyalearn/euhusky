package com.euhusky.remote.netty;

import java.net.InetSocketAddress;

import com.euhusky.remote.netty.channel.ServerHandler;
import com.euhusky.remote.transport.ServiceServer;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServer implements ServiceServer{
	
	ServerBootstrap  boot=new ServerBootstrap();
	
	private EventLoopGroup boss=new NioEventLoopGroup(1);
	
	private EventLoopGroup work=new NioEventLoopGroup();
	
	
	public NettyServer() {
		boot.group(boss,work);
		boot.channel(NioServerSocketChannel.class);
		boot.localAddress(new InetSocketAddress(5656));
		boot.childHandler(new ChannelInitializer<SocketChannel>() {
			protected void initChannel(SocketChannel ch) throws Exception {
				ch.pipeline().addLast("myHandler", new ServerHandler());
			}
		});
        
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		try {
			@SuppressWarnings("unused")
			ChannelFuture f = boot.bind().sync();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
