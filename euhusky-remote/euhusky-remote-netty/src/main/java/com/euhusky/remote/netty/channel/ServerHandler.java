package com.euhusky.remote.netty.channel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.euhusky.serialization.DefaultSerializable;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ServerHandler extends ChannelInboundHandlerAdapter{
	
	private final Logger logger=LoggerFactory.getLogger(ClientHandler.class);
	private DefaultSerializable  serialutil=new DefaultSerializable();
	
	public void channelRead(ChannelHandlerContext ctx, Object msg) { 
		System.out.println("server received data :" + serialutil.deserialize((byte[])msg));
		ctx.writeAndFlush(msg);
	} 
	public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause) { 
		cause.printStackTrace();
		ctx.close();
	} 
}
