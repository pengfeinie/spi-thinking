package org.example;

import java.util.List;
import org.springframework.core.io.support.SpringFactoriesLoader;

public class App {
	
    public static void main( String[] args ){
    	List<HelloService> list = SpringFactoriesLoader.loadFactories(HelloService.class, HelloService.class.getClassLoader());
    	for (HelloService helloService : list) {
    		helloService.hello();
    	}
    }
}
