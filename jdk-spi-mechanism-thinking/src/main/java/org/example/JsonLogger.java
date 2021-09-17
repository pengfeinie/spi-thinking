package org.example;

public class JsonLogger implements SuperLogger{

	public void configure(String configFile) {
		System.out.println("json config load success");
	}

}
