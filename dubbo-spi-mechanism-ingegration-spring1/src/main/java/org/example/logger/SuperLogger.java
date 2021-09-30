package org.example.logger;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.Adaptive;
import org.apache.dubbo.common.extension.SPI;

@SPI(value="xmlLogger")
public interface SuperLogger {
	
	void  configure ();

	@Adaptive("SuperLogger.configure")
	void  configure (URL url);
}
