package org.example;

import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.common.extension.SPI;

@SPI(value="xmlLogger")
public interface SuperLogger {

	@Activate
	void  configure ();
}
