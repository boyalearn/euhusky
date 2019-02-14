package com.euhusky.remote.netty;

public class ClientTest {
	public static void main(String[] args){
		NettyClient client=new NettyClient();
		client.connect();
		for(;;) {
		System.out.println("send:"+"dfsdfsdf1");
		Object data=client.send("dfsdfsdf1");
		System.out.println("client:"+data);
		System.out.println("send:"+"dfsdfsdf2");
		Object data2=client.send("dfsdfsdf2");
	    System.out.println("client:"+data2);
		}
		
		
	}

}
