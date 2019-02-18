package com.euhusky.remote.netty.channel;

import java.util.concurrent.ConcurrentHashMap;

import io.netty.channel.Channel;

public class NettyChannel {
	
	private static final ConcurrentHashMap<String,Channel> channelMap=new ConcurrentHashMap<String,Channel>();
	
	
	public Channel getChannel(String host) {
		return channelMap.get(host);
	}
	
	public static void setChannel(String host,Channel channel) {
		channelMap.put(host, channel);
	}
}
