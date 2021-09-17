package org.example;

import org.apache.dubbo.common.extension.SPI;

@SPI
public interface SuperLogger {

	void  configure ();
}
