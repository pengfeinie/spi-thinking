package org.example;


import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {
	
    public static void main( String[] args ) {
		 AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
		 SuperLogger logger = ac.getBean(SuperLogger.class);
		 logger.configure();
    }
}
