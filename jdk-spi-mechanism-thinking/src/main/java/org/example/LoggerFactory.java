package org.example;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ServiceLoader;

public class LoggerFactory {
	
	private static Map<Class<?>,SuperLogger> map = new HashMap<Class<?>,SuperLogger>();
	
	 static {
		 ServiceLoader<SuperLogger> serviceLoader = ServiceLoader.load(SuperLogger.class);
		 Iterator<SuperLogger> iterator = serviceLoader.iterator();
		 while (iterator.hasNext()) {
		    //Load and initialize the implementation class
			SuperLogger configuration = iterator.next();
		 	map.put(configuration.getClass(), configuration);
		 }
	 }
	 
	 public static SuperLogger getLogger(Class<?> clazz) {
		 return map.get(clazz);
	 }
}
