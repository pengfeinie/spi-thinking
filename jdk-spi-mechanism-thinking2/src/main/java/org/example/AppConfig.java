package org.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

	@Bean
	public LoggerFactoryBean<SuperLogger> loggerBean() {
		LoggerFactoryBean<SuperLogger> logger = new LoggerFactoryBean<SuperLogger>();
		logger.setLoggerInterface(SuperLogger.class);
		return logger;
	}
	
}
