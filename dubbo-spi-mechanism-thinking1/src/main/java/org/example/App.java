package org.example;


import java.util.HashMap;
import java.util.Map;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.example.hello.HelloService;
import org.example.logger.SuperLogger;

public class App {
	
    public static void main( String[] args ){
    	ExtensionLoader<SuperLogger> extensionLoader = ExtensionLoader.getExtensionLoader(SuperLogger.class);
    	SuperLogger defaultLogger = extensionLoader.getDefaultExtension();
    	defaultLogger.configure();
    	SuperLogger xmlLogger = extensionLoader.getExtension("xmlLogger");
    	xmlLogger.configure();
    	SuperLogger jsonLogger = extensionLoader.getExtension("jsonLogger");
    	jsonLogger.configure();
    	System.out.println("-----------------------------------");
    	
    	ExtensionLoader<HelloService> helloServiceExtensionLoader = ExtensionLoader.getExtensionLoader(HelloService.class);
    	HelloService helloservice1 = helloServiceExtensionLoader.getExtension("helloServiceImpl1");
    	Map<String,String> mapJson = new HashMap<>();
    	mapJson.put("SuperLogger.configure","jsonLogger");
    	URL urlJson = new URL("","",8080,mapJson);
    	helloservice1.hello(urlJson);
    	System.out.println("-----------------------------------");
    	
    	Map<String,String> mapXml = new HashMap<>();
    	mapXml.put("SuperLogger.configure","xmlLogger");
    	URL urlXml = new URL("","",8080,mapXml);
    	helloservice1.hello(urlXml);
    	System.out.println("-----------------------------------");
    	
    	Map<String,String> mapXml2 = new HashMap<>();
    	mapXml2.put("SuperLogger.configure","xmlLogger");
    	mapXml2.put("WorldService.worldEN","worldServiceImpl1");
    	URL urlXml2 = new URL("","",8080,mapXml2);
    	helloservice1.helloWorld(urlXml2);
	}
}
