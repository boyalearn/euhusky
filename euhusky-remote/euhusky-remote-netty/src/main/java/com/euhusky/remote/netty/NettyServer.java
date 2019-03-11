package com.euhusky.remote.netty;

import java.net.InetSocketAddress;
import com.euhusky.remote.netty.docde.MessageDecode;
import com.euhusky.remote.netty.docde.MessageEncode;
import com.euhusky.remote.transport.Server;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import io.netty.handler.codec.bytes.ByteArrayEncoder;

public class NettyServer implements Server{
	
	ServerBootstrap  boot=new ServerBootstrap();
	
	private EventLoopGroup boss=new NioEventLoopGroup(1);
	
	private EventLoopGroup work=new NioEventLoopGroup();
	
	private ChannelHandler handler;
	
	
	public NettyServer() { 
	}
	
	public NettyServer(ChannelHandler handler) { 
		this.handler=handler;
	}

	public void setHandler(ChannelHandler handler) {
		this.handler=handler;
	}
	
	@Override
	public void start(int PORT) {
		boot.group(boss,work);
		boot.channel(NioServerSocketChannel.class);
		boot.option(ChannelOption.TCP_NODELAY, true);
		boot.childHandler(new ChannelInitializer<SocketChannel>() {
			protected void initChannel(SocketChannel channel) throws Exception {
				channel.pipeline().addLast("decoder", new MessageDecode());
				channel.pipeline().addLast("decoder2", new ByteArrayDecoder());
				channel.pipeline().addLast("encoder2", new ByteArrayEncoder());
				channel.pipeline().addLast("encoder", new MessageEncode());
				channel.pipeline().addLast("handler", handler);
			}
		});
		try {
			@SuppressWarnings("unused")
			ChannelFuture f = boot.bind(new InetSocketAddress(PORT)).sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
