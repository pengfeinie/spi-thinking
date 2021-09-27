package org.example;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class App {
	
    @SuppressWarnings("resource")
	public static void main( String[] args ){
    	//ImportSelector
    	 AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
		 HelloService helloService = ac.getBean(HelloService.class);
		 helloService.hello();
		 SuperLogger logger = ac.getBean(SuperLogger.class);
		 logger.configure();
    }
}
