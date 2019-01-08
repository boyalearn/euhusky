package com.euhusky.app.bean;

import com.euhusky.annotation.context.Reference;
import com.euhusky.annotation.context.Service;

@Service
public class HelloService {
	
	@Reference
	private DemoService demoService;
	
	
	public void doTest(){
		demoService.say();
	}
	
}
