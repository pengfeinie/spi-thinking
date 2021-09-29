package org.example;

import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.common.extension.SPI;

@SPI
public interface SuperLogger {

	@Activate
	void  configure ();
}
