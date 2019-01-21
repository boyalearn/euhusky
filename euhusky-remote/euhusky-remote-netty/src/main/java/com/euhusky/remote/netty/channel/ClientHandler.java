package com.euhusky.remote.netty.channel;

import java.util.concurrent.locks.Condition;

import com.euhusky.rpc.context.RpcResponse;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ClientHandler extends ChannelInboundHandlerAdapter {
	
	private Condition condition;
	
	private RpcResponse rpcResponse;
	
	public ClientHandler(Condition condition) {
		this.condition=condition;
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		this.rpcResponse.setMsg(msg);
		this.condition.signal();
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		super.channelReadComplete(ctx);
	}

	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {

		super.channelRegistered(ctx);
	}

	public RpcResponse getRpcResponse() {
		return rpcResponse;
	}

	public void setRpcResponse(RpcResponse rpcResponse) {
		this.rpcResponse = rpcResponse;
	}
	
	
	

}
