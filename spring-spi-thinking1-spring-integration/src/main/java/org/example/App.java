package org.example;

import java.util.Map;
import java.util.Set;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {
	
	@SuppressWarnings("resource")
    public static void main( String[] args ){
		AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
		Map<String, SuperLogger> beanMap = ac.getBeansOfType(SuperLogger.class);
		Set<Map.Entry<String, SuperLogger>> entries = beanMap.entrySet();
		for (Map.Entry<String, SuperLogger> entry : entries) {
			SuperLogger logger = entry.getValue();
			logger.configure();
		}
		Map<String, HelloService> helloBeanMap = ac.getBeansOfType(HelloService.class);
		Set<Map.Entry<String, HelloService>> helloEntries = helloBeanMap.entrySet();
		for (Map.Entry<String, HelloService> entry : helloEntries) {
			HelloService helloService = entry.getValue();
			helloService.hello();
		}
    }
}
