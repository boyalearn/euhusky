package com.euhusky.remote.netty;

import java.util.concurrent.locks.ReentrantLock;

import com.euhusky.remote.netty.channel.ClientHandler;
import com.euhusky.remote.transport.Client;
import com.euhusky.rpc.context.RpcResponse;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyClient implements Client{
	
	private Bootstrap boot;
	
	private EventLoopGroup work;
	
	private Channel channel;
	
	private ReentrantLock lock=new ReentrantLock(); 
	
	private ChannelHandler handler=new ClientHandler(lock);
	
	
	public NettyClient() {
		boot=new Bootstrap();
		work=new NioEventLoopGroup();
		boot.group(work);
		boot.channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true);
		boot.handler(new ChannelInitializer<SocketChannel>() {

			@Override
			protected void initChannel(SocketChannel channel) throws Exception {
				channel.pipeline().addLast(handler);
				
			}
			
		});
		try {
			ChannelFuture f = boot.connect("127.0.0.1", 5656).sync();
			channel=f.channel();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public Object send(Object message) {
		RpcResponse response=new RpcResponse();
		this.lock.lock();
		((ClientHandler)this.handler).setRpcResponse(response);
		channel.writeAndFlush(message);
		try {
			this.lock.wait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			this.lock.unlock();
		}
		return response;
	}


}
