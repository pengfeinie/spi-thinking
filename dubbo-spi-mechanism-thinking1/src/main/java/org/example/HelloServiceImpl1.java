package org.example;

import org.apache.dubbo.common.URL;

public class HelloServiceImpl1 implements HelloService{

	private SuperLogger superLogger;

	public void setSuperLogger(SuperLogger superLogger) {
		this.superLogger = superLogger;
	}


	@Override
	public void hello(URL url) {
		superLogger.configure();
		System.out.println("hello 1");
	}

}
