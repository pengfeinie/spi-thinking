package org.example;

import org.springframework.beans.factory.annotation.Autowired;

public class HelloServiceImpl1 implements HelloService{

	@Autowired
	private SuperLogger superLogger;
	
	@Override
	public void hello() {
		System.out.println("hello 1");
	}

}
