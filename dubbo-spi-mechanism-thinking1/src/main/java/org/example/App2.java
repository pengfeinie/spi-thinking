package org.example;


import org.apache.dubbo.common.extension.ExtensionLoader;
import org.example.logger.SuperLogger;

public class App2 {
	
    public static void main( String[] args ){
    	ExtensionLoader<SuperLogger> extensionLoader = ExtensionLoader.getExtensionLoader(SuperLogger.class);
    	SuperLogger defaultLogger = extensionLoader.getDefaultExtension();
    	defaultLogger.configure();
    	SuperLogger xmlLogger = extensionLoader.getExtension("xmlLogger");
    	xmlLogger.configure();
    	SuperLogger jsonLogger = extensionLoader.getExtension("jsonLogger");
    	jsonLogger.configure();
	}
}
