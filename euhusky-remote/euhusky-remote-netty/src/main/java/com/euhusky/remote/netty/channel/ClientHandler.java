package com.euhusky.remote.netty.channel;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.euhusky.remote.netty.util.IOCoordinatorUtil;
import com.euhusky.serialization.DefaultSerializable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ClientHandler extends ChannelInboundHandlerAdapter {
	private final Logger logger=LoggerFactory.getLogger(ClientHandler.class);
	private DefaultSerializable  serialutil=new DefaultSerializable();

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		
		DataWrap data=(DataWrap)serialutil.deserialize((byte[])msg);
		System.out.println(data);
		DataWrap result=(DataWrap)IOCoordinatorUtil.get(data.getDataId());
		result.setData(data.getData());
		IOCoordinatorUtil.wakeUp(result);
		
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		logger.error(cause.getMessage());
	}
	
	


	
	

}
