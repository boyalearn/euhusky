package com.euhusky.remote.netty;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import com.euhusky.common.URL;
import com.euhusky.common.exception.RpcException;
import com.euhusky.common.util.ReferenceCache;
import com.euhusky.remote.netty.docde.MessageDecode;
import com.euhusky.remote.netty.docde.MessageEncode;
import com.euhusky.remote.netty.handler.ClientHandler;
import com.euhusky.remote.netty.handler.DataWrap;
import com.euhusky.remote.netty.util.IOCoordinatorUtil;
import com.euhusky.remote.transport.Client;
import com.euhusky.serialization.DefaultSerializable;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import io.netty.handler.codec.bytes.ByteArrayEncoder;

public class NettyClient implements Client{
	
	private static final ConcurrentHashMap<String,Channel> channelMap=new ConcurrentHashMap<String,Channel>();
	
	private Bootstrap boot;
	
	private EventLoopGroup work;
	
	private DefaultSerializable  serialutil=new DefaultSerializable();
	
	private Channel channel;
	
	private static final AtomicInteger seq=new AtomicInteger(0);
	
	private boolean isConnect;
	
	
	public NettyClient() {
		boot=new Bootstrap();
		work=new NioEventLoopGroup();
		boot.group(work);
		boot.channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true);
		boot.handler(new ChannelInitializer<SocketChannel>() {

			@Override
			protected void initChannel(SocketChannel channel) throws Exception {
				channel.pipeline().addLast("decoder", new MessageDecode());
				channel.pipeline().addLast("decoder2", new ByteArrayDecoder());
				channel.pipeline().addLast("encoder2", new ByteArrayEncoder());
				channel.pipeline().addLast("encoder", new MessageEncode());
				channel.pipeline().addLast(new ClientHandler());
			}
			
		});
	}
	
	public Channel connect(URL url){
		isConnect=true;
		try {
			ChannelFuture f = boot.connect(url.getAddr(), url.getPort()).sync();
			channel=f.channel();
			NettyClient.channelMap.put(url.getAddr()+":"+url.getPort(), channel);
			return channel;
		} catch (InterruptedException e) {
			return null;
		}
	}
	
	
	
	public boolean isConnect() {
		return isConnect;
	}
	
	public Channel getChannel(String host) {
		return channelMap.get(host);
	}


	@Override
	public Object send(URL url) {
		int currId=NettyClient.seq.incrementAndGet();
		DataWrap warp=new DataWrap();
		warp.setData(url);
		warp.setDataId(currId);
		IOCoordinatorUtil.add(warp);
		try {
			getChannel(url).writeAndFlush(serialutil.serialize(warp));
			warp.await();
			return warp.getData();
		} catch (RpcException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Channel getChannel(URL url) throws RpcException{
		List<URL> references=ReferenceCache.getReferences(url.getServiceName());
		if(null==references||references.size()<=0) {
			throw new RpcException("no references for name "+url.getServiceName());
		}

		URL refer=references.get(0);
		url.setAddr(refer.getAddr());
		url.setPort(refer.getPort());
		String host=refer.getAddr()+":"+refer.getPort();
		Channel channel=channelMap.get(host);
		if(null==channel){
			channel=connect(url);
		}
		return channel;
	}


}
