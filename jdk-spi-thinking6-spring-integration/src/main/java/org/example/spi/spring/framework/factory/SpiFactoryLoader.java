package org.example.spi.spring.framework.factory;

public class SpiFactoryLoader {
	
	private SpiFactoryLoader() {}
	
	
	public static <T> SpiFactory<T> getSpiFactory(){
		return new SpiFactory<T>();
	}
	

}
