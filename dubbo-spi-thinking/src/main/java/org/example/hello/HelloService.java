package org.example.hello;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.Adaptive;
import org.apache.dubbo.common.extension.SPI;

@SPI
public interface HelloService {
	
	void hello();
	
	@Adaptive("SuperLogger.configure")
	void hello(URL url);
	
	@Adaptive({"SuperLogger.configure","WorldService.worldEN"})
	void helloWorld(URL url);

}
