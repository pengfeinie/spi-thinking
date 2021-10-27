package org.example;


import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.dubbo.common.URL;
import org.example.hello.HelloService;
import org.example.world.WorldService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App1 {
	
	@SuppressWarnings("resource")
    public static void main( String[] args ){
		AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
    	Map<String,WorldService> map = ac.getBeansOfType(WorldService.class);
    	Set<String> set = map.keySet();
    	for (String name : set) {
    		WorldService worldService = map.get(name);
    		worldService.worldCN();
    	}
    	System.out.println("---------------------------------------------");
    	
    	Map<String,String> mapXml2 = new HashMap<>();
    	mapXml2.put("SuperLogger.configure","xmlLogger");
    	mapXml2.put("WorldService.worldEN","worldServiceImpl1");
    	URL urlXml2 = new URL("","",8080,mapXml2);
    	Map<String,HelloService> helloServiceMap = ac.getBeansOfType(HelloService.class);
    	Set<String> helloServiceSet = helloServiceMap.keySet();
    	for (String name : helloServiceSet) {
    		HelloService helloService = helloServiceMap.get(name);
    		helloService.hello();
    		helloService.helloWorld(urlXml2);
    	}
	}
}
