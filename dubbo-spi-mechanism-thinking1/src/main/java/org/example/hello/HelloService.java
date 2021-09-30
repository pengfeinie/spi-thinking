package org.example.hello;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.Adaptive;
import org.apache.dubbo.common.extension.SPI;

@SPI
public interface HelloService {
	
	@Adaptive("SuperLogger.configure")
	void hello(URL url);

}
