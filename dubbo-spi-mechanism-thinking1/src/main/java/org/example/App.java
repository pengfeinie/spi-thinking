package org.example;


import java.util.HashMap;
import java.util.Map;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.ExtensionLoader;

public class App {
	
    public static void main( String[] args ){
    	ExtensionLoader<SuperLogger> extensionLoader = ExtensionLoader.getExtensionLoader(SuperLogger.class);
    	SuperLogger defaultLogger = extensionLoader.getDefaultExtension();
    	defaultLogger.configure();
    	SuperLogger xmlLogger = extensionLoader.getExtension("xmlLogger");
    	xmlLogger.configure();
    	SuperLogger jsonLogger = extensionLoader.getExtension("jsonLogger");
    	jsonLogger.configure();
    	
    	ExtensionLoader<HelloService> helloServiceExtensionLoader = ExtensionLoader.getExtensionLoader(HelloService.class);
    	HelloService helloservice1 = helloServiceExtensionLoader.getExtension("helloServiceImpl1");
    	 Map<String,String> map = new HashMap<>();
    	    map.put("SuperLogger.configure","xmlLogger");
    	    URL url = new URL("","",1,map);
    	helloservice1.hello(url);
	}
}
