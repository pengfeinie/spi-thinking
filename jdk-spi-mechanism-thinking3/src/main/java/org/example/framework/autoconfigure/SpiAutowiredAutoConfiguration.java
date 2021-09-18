package org.example.framework.autoconfigure;


import org.example.framework.factory.SpiAutowiredBeanFactoryPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpiAutowiredAutoConfiguration {

    @Bean
    SpiAutowiredBeanFactoryPostProcessor spiAutowiredBeanFactoryPostProcessor(){
        return new SpiAutowiredBeanFactoryPostProcessor();
    }
}
