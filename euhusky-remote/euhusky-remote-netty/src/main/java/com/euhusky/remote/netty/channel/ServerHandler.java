package com.euhusky.remote.netty.channel;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ServerHandler extends ChannelInboundHandlerAdapter{
	public void channelRead(ChannelHandlerContext ctx, Object msg) { 
		System.out.println("server received data :" + msg);
		ctx.writeAndFlush(msg);
	} 
	public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause) { 
		cause.printStackTrace();
		ctx.close();
	} 
}
