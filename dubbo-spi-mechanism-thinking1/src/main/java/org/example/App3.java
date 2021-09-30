package org.example;


import java.util.HashMap;
import java.util.Map;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.example.hello.HelloService;

public class App3 {
	
    public static void main( String[] args ){
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
	}
}
