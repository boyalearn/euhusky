package com.euhusky.remote.netty.channel;

import java.util.concurrent.locks.ReentrantLock;

import com.euhusky.rpc.context.RpcResponse;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ClientHandler extends ChannelInboundHandlerAdapter {
	
	private ReentrantLock lock;
	
	private RpcResponse rpcResponse;
	
	public ClientHandler(ReentrantLock lock) {
		this.lock=lock;
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		this.rpcResponse.setMsg(msg);
		this.lock.notify();
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		super.channelReadComplete(ctx);
	}

	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		super.channelRegistered(ctx);
	}

	public RpcResponse getRpcResponse() {
		return rpcResponse;
	}

	public void setRpcResponse(RpcResponse rpcResponse) {
		this.rpcResponse = rpcResponse;
	}
	
	
	

}
