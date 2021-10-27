package org.example.world;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.Adaptive;
import org.apache.dubbo.common.extension.SPI;

@SPI
public interface WorldService {
	
	
	public void worldCN();
	
	@Adaptive("WorldService.worldEN")
	public void worldEN(URL url);

}
