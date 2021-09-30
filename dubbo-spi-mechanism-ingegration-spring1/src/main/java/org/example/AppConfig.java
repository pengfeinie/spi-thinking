package org.example;

import org.example.spi.spring.framework.annotation.EnableSpi;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableSpi(basePackages = {"org.example"})
public class AppConfig {

}
