package com.euhusky.app.bean;

import com.euhusky.config.annotation.context.Reference;
import com.euhusky.config.annotation.context.Service;

@Service
public class HelloService {
	
	@Reference
	private DemoService demoService;
	
	public HelloService() {
		System.out.println(">>>>>>>>>>>>>>>");
	}
	
	public void doTest(){
		demoService.say();
	}
	
	public String doTest2(String content){
		return demoService.say(content);
	}
	
}
