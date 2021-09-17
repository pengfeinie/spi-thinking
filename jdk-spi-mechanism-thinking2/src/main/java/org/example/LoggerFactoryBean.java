package org.example;

import java.util.Iterator;
import java.util.ServiceLoader;

import org.springframework.beans.factory.FactoryBean;

public class LoggerFactoryBean<T> implements FactoryBean<T> {
	
	private Class<? extends T> loggerInterface;
	

	@SuppressWarnings("unchecked")
	public T getObject() throws Exception {
		ServiceLoader<T> serviceLoader = (ServiceLoader<T>) ServiceLoader.load(loggerInterface);
		Iterator<T> iterator = serviceLoader.iterator();
		T logger = iterator.next();
		return logger;
	}

	public Class<? extends T> getObjectType() {
		return loggerInterface;
	}

	public boolean isSingleton() {
		return true;
	}

	public Class<? extends T> getLoggerInterface() {
		return loggerInterface;
	}

	public void setLoggerInterface(Class<? extends T> loggerInterface) {
		this.loggerInterface = loggerInterface;
	}

}
