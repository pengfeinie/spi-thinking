package org.example.logger;

import org.apache.dubbo.common.URL;

public class XMLLogger implements SuperLogger{

	public void configure() {
		System.out.println("xml config load success");
	}

	@Override
	public void configure(URL url) {
		System.out.println("@Adaptive xml config load success");
		
	}

	
}
