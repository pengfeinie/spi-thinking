package org.example.animal;

import org.apache.dubbo.common.extension.ExtensionLoader;

public class App {

	public static void main( String[] args ){
    	ExtensionLoader<AnimalService> extensionLoader = ExtensionLoader.getExtensionLoader(AnimalService.class);
    	AnimalService dogServiceImpl = extensionLoader.getExtension("dogServiceImpl");
    	dogServiceImpl.speak();
	}
}
