package org.example.spi.spring.framework.factory;


import org.springframework.beans.factory.FactoryBean;

import cn.hutool.core.util.ServiceLoaderUtil;

public class SpiFactoryBean<T> implements FactoryBean<T> {
	
	private Class<? extends T> spiInterface;
	
	public T getObject() throws Exception {
		return ServiceLoaderUtil.loadFirstAvailable(spiInterface);
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
