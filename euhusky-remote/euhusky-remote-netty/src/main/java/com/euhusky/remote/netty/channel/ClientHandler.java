package com.euhusky.remote.netty.channel;

import java.util.concurrent.locks.Condition;

import com.euhusky.remote.netty.util.IOCoordinatorUtil;
import com.euhusky.rpc.context.RpcResponse;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ClientHandler extends ChannelInboundHandlerAdapter {
	
	private Condition condition;
	
	public ClientHandler(Condition condition) {
		this.condition=condition;
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		System.out.println("msg:"+msg);
		RpcResponse reponse=(RpcResponse)IOCoordinatorUtil.getWait("2222222");
		reponse.setMsg(msg);
		IOCoordinatorUtil.wakeUp(reponse);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		super.channelReadComplete(ctx);
	}

	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {

		super.channelRegistered(ctx);
	}

	
	

}
