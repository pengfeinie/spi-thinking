package org.example;

import org.example.spi.spring.framework.plugin.interceptor.Interceptor;
import org.example.spi.spring.framework.plugin.model.Invocation;

public class HelloInterceptor implements Interceptor {

	@Override
	public int getOrder() {
		return 0;
	}

	@Override
	public boolean preHandle(Invocation invocation) {
		System.out.println("hello preHandle");
		return true;
		
	}

	@Override
	public void afterCompletion(Invocation invocation) {
		System.out.println("hello afterCompletion");
	}

	
}
