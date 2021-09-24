package org.example;

import org.example.spi.spring.framework.factory.SpiFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

	@Bean
	public SpiFactoryBean<SuperLogger> superLogger(){
		SpiFactoryBean<SuperLogger> superLogger = new SpiFactoryBean<SuperLogger>();
		superLogger.setSpiInterface(SuperLogger.class);
		return superLogger;
	}
	
	@Bean
	public SpiFactoryBean<HelloService> helloService(){
		SpiFactoryBean<HelloService> helloService = new SpiFactoryBean<HelloService>();
		helloService.setSpiInterface(HelloService.class);
		return helloService;
	}
}
