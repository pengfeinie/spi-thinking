package org.example;


import org.apache.dubbo.common.extension.ExtensionLoader;
import org.example.world.WorldService;

public class App1 {
	
    public static void main( String[] args ){
    	ExtensionLoader<WorldService> extensionLoader = ExtensionLoader.getExtensionLoader(WorldService.class);
    	WorldService worldService1 = extensionLoader.getExtension("worldServiceImpl1");
    	worldService1.worldCN();
	}
}
