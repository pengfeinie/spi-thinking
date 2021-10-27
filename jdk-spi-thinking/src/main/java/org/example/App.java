package org.example;

import java.util.Iterator;
import java.util.ServiceLoader;

public class App {
	
    public static void main( String[] args ) {
	   	 ServiceLoader<SuperLogger> serviceLoader = ServiceLoader.load(SuperLogger.class);
		 Iterator<SuperLogger> iterator = serviceLoader.iterator();
		 while (iterator.hasNext()) {
			SuperLogger logger = iterator.next();
			logger.configure();
		 }
    }
}
