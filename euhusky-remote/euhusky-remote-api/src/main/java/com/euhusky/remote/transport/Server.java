package com.euhusky.remote.transport;

import io.netty.channel.ChannelHandler;

public interface Server {
	public void start(int PORT);
	
	public void setHandler(ChannelHandler handler);
}
