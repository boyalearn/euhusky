package com.euhusky.remote.netty;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import com.euhusky.remote.netty.channel.ClientHandler;
import com.euhusky.remote.netty.channel.DataWrap;
import com.euhusky.remote.netty.docde.MessageDecode;
import com.euhusky.remote.netty.docde.MessageEncode;
import com.euhusky.remote.netty.util.IOCoordinatorUtil;
import com.euhusky.remote.transport.RequetClient;
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

public class NettyClient implements RequetClient{
	
	private Bootstrap boot;
	
	private EventLoopGroup work;
	
	private DefaultSerializable  serialutil=new DefaultSerializable();
	
	private Channel channel;
	
	private AtomicInteger seq=new AtomicInteger(0);
	
	private final CountDownLatch cdl = new CountDownLatch(1);
	
	
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
	
	public void connect(){
		isConnect=true;
		try {
			ChannelFuture f = boot.connect("127.0.0.1", 5656).sync();
			channel=f.channel();
			cdl.countDown();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	public boolean isConnect() {
		return isConnect;
	}

	@Override
	public Object send(Object message) {
		if(!isConnect()) {
			
			connect();
			try {
				cdl.await();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		int currId=this.seq.incrementAndGet();
		DataWrap warp=new DataWrap();
		warp.setData(message);
		warp.setDataId(currId);
		IOCoordinatorUtil.add(warp);
		channel.writeAndFlush(serialutil.serialize(warp));
		warp.await();
		return warp.getData();
	}


}
