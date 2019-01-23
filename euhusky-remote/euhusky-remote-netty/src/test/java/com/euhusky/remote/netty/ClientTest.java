package com.euhusky.remote.netty;

public class ClientTest {
	public static void main(String[] args){
		NettyClient client=new NettyClient();
		client.connect();
		
		for(;;){
			client.send("dfsdfsdf");
		}
	}

}
