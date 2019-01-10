package com.euhusky.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.euhusky.annotation.EuHuskyComponetScan;
import com.euhusky.app.bean.HelloService;

@SpringBootApplication
@EuHuskyComponetScan(basePackages="com.euhusky.app")
public class ApplicationTest {
	public static void main(String[] args){
		SpringApplication application=new SpringApplication(ApplicationTest.class);
		ApplicationContext context=application.run(args);
		HelloService service=context.getBean(HelloService.class);
		service.doTest();
	}
}
