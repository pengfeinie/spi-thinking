package org.example;

import org.apache.dubbo.common.URL;

public class JsonLogger implements SuperLogger{

	public void configure() {
		System.out.println("json config load success");
	}

	@Override
	public void configure(URL url) {
		System.out.println("json config load success");
	}

}
