package org.example.hello;

import org.apache.dubbo.common.URL;
import org.example.logger.SuperLogger;
import org.example.world.WorldService;

public class HelloServiceImpl1 implements HelloService{

	private SuperLogger superLogger;
	
	private WorldService worldService;

	public void setSuperLogger(SuperLogger superLogger) {
		this.superLogger = superLogger;
	}
	
	public void setWorldService(WorldService worldService) {
		this.worldService = worldService;
	}


	@Override
	public void hello() {
		System.out.println("hello 1");
	}


	@Override
	public void hello(URL url) {
		superLogger.configure(url);
		System.out.println("hello 1");
	}


	@Override
	public void helloWorld(URL url) {
		superLogger.configure(url);
		worldService.worldEN(url);
		System.out.println("hello 1");
	}

}
