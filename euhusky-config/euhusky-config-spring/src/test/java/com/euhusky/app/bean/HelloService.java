package com.euhusky.app.bean;

import com.euhusky.config.annotation.context.Reference;
import com.euhusky.config.annotation.context.Service;

@Service
public class HelloService {
	
	@Reference
	private DemoService demoService;
	
	
	public void doTest(){
		demoService.say();
	}
	
}
