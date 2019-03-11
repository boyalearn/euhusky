package com.euhusky.remote.netty;

import com.euhusky.common.URL;

public class ClientTest {
	@SuppressWarnings("unused")
	public static void main(String[] args){
		NettyClient client=new NettyClient();
		URL url=new URL();
		client.connect(url);
		for(;;) {
		System.out.println("send:"+"dfsdfsdf1");
		Object data=client.send(url);
		System.out.println(url);
		}
		
		
	}

}
