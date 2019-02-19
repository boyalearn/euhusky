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
			//for(;;) {
				System.out.println("client1:"+service.doTest2("我是小XX1"));
				System.out.println("client2:"+service.doTest2("我是小XX2"));
				System.out.println("client3:"+service.doTest2("我是小XX3"));
				System.out.println("client4:"+service.doTest2("我是小XX4"));
		    //}
		}).start();
		try {
			System.gc();
			Thread.sleep(Integer.MAX_VALUE);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
