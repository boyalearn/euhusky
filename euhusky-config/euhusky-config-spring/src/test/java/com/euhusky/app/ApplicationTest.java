package com.euhusky.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.euhusky.app.bean.HelloService;
import com.euhusky.config.annotation.EuHuskyComponetScan;

@SpringBootApplication
@EuHuskyComponetScan(basePackages="com.euhusky.app")
public class ApplicationTest {
	public static void main(String[] args){
		SpringApplication application=new SpringApplication(ApplicationTest.class);
		ApplicationContext context=application.run(args);
		HelloService service=context.getBean(HelloService.class);
		
		new Thread(()->{
			service.doTest();
		}).start();
		try {
			Thread.sleep(Integer.MAX_VALUE);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
