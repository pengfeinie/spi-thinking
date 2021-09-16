package org.example;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ServiceLoader;

public class LoggerFactory {
	
	private static Map<Class<?>,SuperLoggerConfiguration> map = new HashMap<Class<?>,SuperLoggerConfiguration>();
	
	 static {
		 ServiceLoader<SuperLoggerConfiguration> serviceLoader = ServiceLoader.load(SuperLoggerConfiguration.class);
		 Iterator<SuperLoggerConfiguration> iterator = serviceLoader.iterator();
		 while (iterator.hasNext()) {
		    //Load and initialize the implementation class
			SuperLoggerConfiguration configuration = iterator.next();
		 	map.put(configuration.getClass(), configuration);
		 }
	 }
	 
	 public static SuperLoggerConfiguration getLogger(Class<?> clazz) {
		 return map.get(clazz);
	 }
}
