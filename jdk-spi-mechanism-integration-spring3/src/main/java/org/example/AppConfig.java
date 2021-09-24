package org.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import cn.hutool.core.util.*;

@Configuration
public class AppConfig {
	
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
