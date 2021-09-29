package org.example;

import org.apache.dubbo.common.extension.ExtensionFactory;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.apache.dubbo.common.extension.factory.AdaptiveExtensionFactory;
import org.apache.dubbo.common.extension.factory.SpiExtensionFactory;

public class App {
	
    public static void main( String[] args ){
    	ExtensionLoader<SuperLogger> extensionLoader = ExtensionLoader.getExtensionLoader(SuperLogger.class);
    	SuperLogger xmlLogger = extensionLoader.getExtension("xmlLogger");
    	xmlLogger.configure();
    	SuperLogger jsonLogger = extensionLoader.getExtension("jsonLogger");
    	jsonLogger.configure();
		ExtensionFactory extensionFactory = ExtensionLoader.getExtensionLoader(ExtensionFactory.class).getAdaptiveExtension();
		SuperLogger xmlLogger1 = extensionFactory.getExtension(SuperLogger.class, "xmlLogger");
		System.out.println(xmlLogger1);
	}
}
