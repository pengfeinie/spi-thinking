package org.example.spi.spring.framework.factory;

import java.util.Iterator;
import java.util.ServiceLoader;

import org.springframework.beans.factory.FactoryBean;

public class SpiFactoryBean<T> implements FactoryBean<T> {
	
	private Class<? extends T> spiInterface;
	
	@SuppressWarnings("unchecked")
	public T getObject() throws Exception {
		ServiceLoader<T> serviceLoader = (ServiceLoader<T>) ServiceLoader.load(spiInterface);
		Iterator<T> iterator = serviceLoader.iterator();
		T logger = iterator.next();
		return logger;
	}

	public Class<? extends T> getObjectType() {
		return spiInterface;
	}

	public boolean isSingleton() {
		return true;
	}

	public Class<? extends T> getLoggerInterface() {
		return spiInterface;
	}

	public void setSpiInterface(Class<? extends T> spiInterface) {
		this.spiInterface = spiInterface;
	}
}
