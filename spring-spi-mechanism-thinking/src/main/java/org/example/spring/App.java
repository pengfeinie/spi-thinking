package org.example.spring;


import java.util.Map;
import java.util.Set;
import org.example.HelloService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class App {
	 
	@SuppressWarnings("resource")
	public static void main( String[] args ){
		AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
		Map<String, HelloService> beanMap = ac.getBeansOfType(HelloService.class);
		Set<Map.Entry<String, HelloService>> entries = beanMap.entrySet();
		for (Map.Entry<String, HelloService> entry : entries) {
			HelloService helloService = entry.getValue();
			helloService.hello();
		}
	}
}
