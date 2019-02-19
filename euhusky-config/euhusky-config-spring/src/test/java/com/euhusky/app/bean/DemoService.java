package com.euhusky.app.bean;

import com.euhusky.config.annotation.context.Service;

@Service("HHHH")
public class DemoService {
	
	
	public DemoService() {
		System.out.println(">>>>>>>>>>>>>>>");
	}
	
	public void say(){
		System.out.println("sdfsdfsd");
	}
	
	public String say(String content){
		return "server"+content;
	}
}
