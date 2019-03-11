package com.euhusky.app;

import com.euhusky.app.service.DemoService;
import com.euhusky.config.ReferenceConfig;
import com.euhusky.config.RegisterConfig;

public class ClientApplicationTest {

	public static void main(String[] args) {
		RegisterConfig registry = new RegisterConfig();
		registry.addAddress("127.0.0.1:2181");
	
		ReferenceConfig<DemoService> reference = new ReferenceConfig<DemoService>();
		reference.setRegister(registry); 
		reference.setRef(DemoService.class);
		DemoService demo=reference.get();
		System.out.println("dfsdfsdfdsfsd"+demo.say());
		 
	}

}
