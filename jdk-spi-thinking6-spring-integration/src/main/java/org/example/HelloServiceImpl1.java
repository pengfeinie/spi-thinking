package org.example;

import org.example.spi.spring.framework.plugin.anotation.InterceptorMethod;

public class HelloServiceImpl1 implements HelloService{

	@InterceptorMethod(interceptorClasses= {HelloInterceptor.class})
	@Override
	public void hello() {
		System.out.println("hello 1");
	}

}
