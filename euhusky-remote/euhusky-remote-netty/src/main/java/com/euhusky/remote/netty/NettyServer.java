package com.euhusky.remote.netty;

import java.net.InetSocketAddress;

import com.euhusky.remote.netty.channel.ServerHandler;
import com.euhusky.remote.transport.ServiceServer;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class NettyServer implements ServiceServer{
	
	ServerBootstrap  boot=new ServerBootstrap();
	
	private EventLoopGroup boss=new NioEventLoopGroup(1);
	
	private EventLoopGroup work=new NioEventLoopGroup();
	
	
	public NettyServer() {
		boot.group(boss,work);
		boot.channel(NioServerSocketChannel.class);
		boot.option(ChannelOption.TCP_NODELAY, true);
		boot.childHandler(new ChannelInitializer<SocketChannel>() {
			protected void initChannel(SocketChannel ch) throws Exception {
				ch.pipeline().addLast("decoder", new StringDecoder());
				ch.pipeline().addLast("encoder", new StringEncoder());
				ch.pipeline().addLast(new ServerHandler());
			}
		});
        
	}

	@Override
	public void start(int PORT) {
		try {
			@SuppressWarnings("unused")
			ChannelFuture f = boot.bind(new InetSocketAddress(PORT)).sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
