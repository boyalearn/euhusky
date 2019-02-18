package com.euhusky.common.util;

import org.springframework.context.ApplicationContext;

public class SpringContextUtil {
	
	private static ApplicationContext context;

	public static ApplicationContext getContext() {
		return context;
	}

	public static void setContext(ApplicationContext context) {
		SpringContextUtil.context = context;
	}
	
	
}
