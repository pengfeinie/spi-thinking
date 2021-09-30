package org.example.hello;

import org.apache.dubbo.common.URL;
import org.example.logger.SuperLogger;

public class HelloServiceImpl1 implements HelloService{

	private SuperLogger superLogger;

	public void setSuperLogger(SuperLogger superLogger) {
		this.superLogger = superLogger;
	}


	@Override
	public void hello(URL url) {
		superLogger.configure(url);
		System.out.println("hello 1");
	}

}
