package com.euhusky.app;

import com.euhusky.app.service.DemoService;
import com.euhusky.config.ProtocolConfig;
import com.euhusky.config.RegisterConfig;
import com.euhusky.config.ServiceConfig;

public class ApplicationTest {
	
	public static void main(String[] args) {
		ServiceConfig service=new ServiceConfig(DemoService.class);
		ProtocolConfig protocol=new ProtocolConfig();
		protocol.setPort(9090);
		RegisterConfig register=new RegisterConfig();
		register.addAddress("127.0.0.1:2181");
		service.setProtocol(protocol);
		service.setRegister(register);
		service.export();
		
	}

}
