package org.example;


import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {
	
    @SuppressWarnings("resource")
	public static void main( String[] args ) {
		 AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
		 HelloService logger = ac.getBean(HelloService.class);
		 logger.hello();
    }
}
