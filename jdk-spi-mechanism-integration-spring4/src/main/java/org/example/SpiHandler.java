package org.example;


import org.springframework.context.annotation.Bean;

import cn.hutool.core.util.ServiceLoaderUtil;

public class SpiHandler {
	
	
	@Bean
	public SuperLogger superLogger(){
		SuperLogger service = ServiceLoaderUtil.loadFirstAvailable(SuperLogger.class);
		return service;
	}
	
	@Bean
	public HelloService helloService(){
		HelloService service = ServiceLoaderUtil.loadFirstAvailable(HelloService.class);
		return service;
	}

}
