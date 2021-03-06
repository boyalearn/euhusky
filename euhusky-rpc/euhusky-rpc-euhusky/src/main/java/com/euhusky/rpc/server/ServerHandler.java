package com.euhusky.rpc.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.euhusky.common.URL;
import com.euhusky.remote.netty.handler.ClientHandler;
import com.euhusky.remote.netty.handler.DataWrap;
import com.euhusky.rpc.invoke.DefaultInvoker;
import com.euhusky.serialization.DefaultSerializable;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ServerHandler extends ChannelInboundHandlerAdapter{
	
	private final Logger logger=LoggerFactory.getLogger(ClientHandler.class);
	private DefaultSerializable  serialutil=new DefaultSerializable();

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		DataWrap data=(DataWrap)serialutil.deserialize((byte[])msg);
		URL request=(URL)data.getData();
		DefaultInvoker invoker=new DefaultInvoker();
		Object obj=invoker.invoke(request);
		data.setData(null==obj?"":obj);
		ctx.writeAndFlush(serialutil.serialize(data));
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		logger.error(cause.getLocalizedMessage());
	}
	
}
