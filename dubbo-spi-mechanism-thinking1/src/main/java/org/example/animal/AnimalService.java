package org.example.animal;

import org.apache.dubbo.common.extension.SPI;

@SPI
public interface AnimalService {

	public void speak();

}
