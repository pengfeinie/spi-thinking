package org.example;


import java.util.HashMap;
import java.util.Map;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.example.hello.HelloService;

public class App4 {
	
    public static void main( String[] args ){
    	ExtensionLoader<HelloService> helloServiceExtensionLoader = ExtensionLoader.getExtensionLoader(HelloService.class);
    	HelloService helloservice1 = helloServiceExtensionLoader.getExtension("helloServiceImpl1");
    	
    	Map<String,String> mapXml2 = new HashMap<>();
    	mapXml2.put("SuperLogger.configure","xmlLogger");
    	mapXml2.put("WorldService.worldEN","worldServiceImpl1");
    	URL urlXml2 = new URL("","",8080,mapXml2);
    	helloservice1.helloWorld(urlXml2);
	}
}
